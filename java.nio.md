# java.nio.file を用いた File I/O
前回のコード例では教科書になぞらえて java.io.* を利用しました。しかしこれらはどちらかというと後方互換性のために残っており、使いづらいか非効率的な処理になってしまうことがあります。ここでは java.io と java.nio の違いと、java.nioを用いたファイル入出力を行うコード例を示します。

## java.ioパッケージ
[java.ioパッケージの説明](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/package-summary.html)では次のように書かれています。

 > Provides for system input and output through data streams, serialization and the file system.

「input and output through data streams」とは、ファイル入出力時のデータがバイトや文字の連続ストリームとして扱われることを意味しており、1バイトもしくは1文字ずつ処理されるため計算効率が悪いです（まとめて1MB分のデータを処理するようなことがでず、1バイト読み込んで処理、1バイト読み込んで処理、、、という形でしか処理できません）。また上記には説明が出てきませんが、java.ioは「ブロッキングI/O」で行われていることも非効率となる要因の一つです。このためデータの読み書き操作が完了するまで実行中のスレッドがブロック（停止する）されます。

## java.nioパッケージ
java.ioは使いづらいか非効率のため、より効率の良い実装である[java.nio.fileパッケージ](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/package-summary.html)を使うことが多いです。

java.ioではストリーム指向かつブロッキングI/Oのため非効率でした。これに対しjava.nioではバッファ指向とすることでデータをブロック単位で寄りすることができるため、一度に大量のデータを読み書きすることができます。またノンブロッキングI/Oで実装されているため、スレッドがブロックされずに他のタスクを続けることができます。

ストリーム指向 vs バッファ指向、ブロッキングI/O vs ノンブロッキングI/Oの詳細については、2年次の授業「オペレーティング・システム」で習います。

## File I/Oのための代表的なクラス
| class | description |
|---|---|
| java.nio.file.Path | ファイルシステム内のファイルやディレクトリへのパスを表し、ファイルシステムの階層構造内での位置を抽象化したもの。 |
| java.nio.file.Paths | Pathオブジェクトを簡単に作成するためのユーティリティクラス。 |
| java.nio.file.Files | ファイルおよびディレクトリに対する多様な操作を静的メソッドとして提供するユーティリティクラス。 |

Paths, Files はユーティリティクラスとして提供されており、これらを new でインスタンス生成して使うのではなく、直接メソッドを介して利用することができます。（Math.sinとかを計算するためにMathクラスのインスタンスを用意する必要がないのと同じです）

## ファイルI/Oのコード例 (NioFileIOExample.java)
```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NioFileIOExample {
    public static void main(String[] args){
        Path path = Paths.get("output.txt");
        String target = "この文字列を書き込みます\n2行目\n3行目";

        //ファイル書き込み
        try {
            Files.write(path, target.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println(e);
        }

        //ファイル読み込み
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for(String line : lines){
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }    
}
```

補足
- [String.getBytes(Charset)](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#getBytes%28java.nio.charset.Charset%29): 文字列をCharsetで指定したバイト列にエンコードします。文字コードが異なる場合に問題が生じる可能性があるため、ここでは読み書きどちらでも StandardCharsets.UTF_8 で指定しています。
- [java.util.List](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html): インタフェースのため本来ならそのまま利用することはできません。しかしここでは [Files.readAllLines](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html#readAllLines%28java.nio.file.Path,java.nio.charset.Charset%29)の内部で自動的に実装済みサブクラス（恐らくArrayListかLinkedList）が利用され、それがスーパークラスであるListに変換されて利用できるようになっています。
