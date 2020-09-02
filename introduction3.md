# Java入門3（変数の寿命、GC、オーバーロード、複数クラス）
- コード例で既に出てきている箇所で、プログラミング1で習った内容と異なる部分をピックアップ。
- ＜目次＞
  - <a href="#variable-lifetime">変数の寿命</a>
  - <a href="#gc">ガベージコレクション（GC: Garbage Collection）</a>
  - <a href="#overload">オーバーロード（overload, 多重定義）</a>
  - <a href="#multiple-classes">複数クラスを用いた開発</a>

<hr>

## <a name="variable-lifetime">変数の寿命</a>
- 教科書 p.107, 「3.2節 ブロックの書き方」。
  - ルール2：ブロック内で宣言した変数の寿命。
    - 「ブロックの中で新たに変数を宣言することもできます。しかし、ブロック内で変数した宣言はそのブロックが終わると同時に消滅します。」
    - スコープ。

<hr>

## <a name="gc">ガベージコレクション（GC: Garbage Collection）</a>
- 教科書 pp.164, 「4.5節 配列の舞台裏」。
  - 例：``int[] scores = new int[5];`` を実行したときのメモリ上のようす。
    - int型の要素を5つ持つ配列がメモリ上に作成される。
    - int[]型の配列変数scoresがメモリ上に作成される。
    - 配列変数scoresに配列の先頭要素のアドレスが代入される。
  - 配列変数は「参照型」変数。
    - 参照型変数の場合、``=`` 演算子で渡される値はアドレスになる。（int, double, charといった基本型の場合には値そのものが渡される）
- 教科書 pp.168, 「4.6節 配列の後片付け」
  - **メモリ上のゴミ（どの変数からも参照されなくなったメモリ領域）を自動的に探し出し、片付ける機能**。検出方法や片付けるタイミングはGCにお任せ。
  - コード例1では、配列変数arrayはif文を終えた時点で消滅する。しかし各要素である ``1, 2, 3`` はまだメモリ上に残されている。これらの値はアドレスを保存した配列変数arrayからは参照できるが、それが失われたために参照できなくなってしまう。このような状況が前述の「メモリ上のゴミ（どの変数からも参照されなくなったメモリ領域）」に相当する。
  - コード例2では、配列変数arrayにnullを代入した。これはどこも参照しない特別な値である。参照変数arrayがどこも参照しなくなったため、コード例1と同様に ``1, 2, 3`` はメモリ上に残っているがそれらへアクセスすることができない状況になっている。意図的に参照を切る（＝GC対象とする）というコード例になっている。
    - コード例2を実行すると ``NullPointerException`` が発生。（アドレスを保存していない配列変数arrayに対し array[0] を参照しようとしたためにエラー）

```Java
//コード例1（教科書p.168を少し改変）
public class Main {
  public static void main(String[] args){
    boolean judge = true;
    if (judge == true){
      int[] array = {1, 2, 3};
    }
    System.out.println("end");
  }
}

//コード例2（教科書p.169）
public class Main {
  public static void main(String[] args){
    int[] array = {1, 2, 3};
    array = null; //配列変数arrayにnullを代入。
    array[0] = 10;
  }
}
```

<hr>

## <a name="overload">オーバーロード（overload, 多重定義）</a>
- 教科書 p.205, 「5.4節 オーバーロードの利用」
- [System.out.println](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/System.html#out) のドキュメントで参照したように、Javaのメソッドには「同じ名前のメソッド」が複数用意されていることがある。printlnを例に取ると、その引数がint型の場合、float型の場合、double型の場合、、、といった引数の型に応じて適切なメソッドを選び、実行することになる。このように「同じ名前のメソッドを定義すること」を **オーバーロード（overload）または多重定義** と呼ぶ。
  - 関数のように「異なる名前の関数を複数定義する」としても問題なさそうに思えるが、利用者からすると大量の名前を覚えることになってしまい、大変。例えば引数の型に応じて出力するメソッドが異なるとしたら大変だ。

<hr>

## <a name="multiple-classes">複数クラスを用いた開発</a>
- 教科書 p.223, 「第6章 複数クラスを用いた開発」
- これまでのコード
  - 一つのファイルの中に public class が1つあり、その中で main メソッドを記述している。一時的に作成しているだけのプログラムならばこういう書き方もありだが、一般的には main メソッドは別ファイルとして用意することが多い。これはPythonでいうところの import して利用するケースと、直接ファイル実行するケースの違いに相当する。例えば Math クラスはそれ自身を直接実行することはなく、必要な機能を取り出して利用する。同様に一般的にはシステム自体を実装した部分と、それを取り出して利用する部分とを分けて書くことが多い。便宜上授業では後者を Main クラスとして書くことにしよう。
- package指定なしの場合。
  - 下記2つのクラス（Diceクラス、Mainクラス）を2つのファイルに分けて保存しよう。この際、両者ともpackage指定はしていないので、srcディレクトリ直下に置くこと。**実行するのはmainメソッドを記述しているMain.java（正確にはMainクラス）** である点に注意。
  - Diceクラス。
    - src/Dice.javaとして保存。
    - public class として記述する。ファイル名はクラス名に合わせて Dice.java とする。
    - サイコロのように1〜6のint型の値をランダムに返す play メソッドを持つ。
  - Mainクラス。
    - src/Main.javaとして保存。
    - public class として記述する。ファイル名はクラス名に合わせて Main.java とする。
    - Diceクラスを利用する main メソッドを持つクラス。

```Java
// コード例3
// src/Dice.java
public class Dice {
    public static int play(){
        return (int)(Math.random()*6) + 1;
    }
}

// src/Main.java
public class Main {
    public static void main(String[] args) {
        System.out.println(Dice.play());
    }
}
```

- package指定ありの場合。
  - 先程「一般的にはシステム自体を実装した部分と、それを取り出して利用する部分とを分けて書くことが多い」と述べた。このシステムは自身とは異なる人・チームが開発していることが殆どであり、結果的にpackage名が独自に指定されていることが多い。ここでは Dice クラスを ``jp.ac.uryukyu.ie.tnal`` でパッケージ指定することと想定し、動作させてみよう。
  - Dice.java
    - 以下のコードでは Dice.java のパッケージを ``jp.ac.uryukyu.ie.tnal`` と指定した。この場合には Dice.java を src/jp/ac/uryukyu/ie/tnal/Dice.java に保存する必要がある。
    - この時点では src/Dice.java と src/jp/ac/uryukyu/ie/tnal/Dice.java の2つがあることになる。Mainクラスから呼び出された際にどちらが使われるのかを確認しやすくするため、ここでは意図的にパッケージ側を常に -1 を返すように変更した。
  - Main.java
    - Main.javaはsrc直下のままで良い。
    - 動作確認
      - Dice.play() と jp.ac.uryukyu.tnal.Dice.play() の違いを観察しよう。今回は意図的に Dice.java が2つ存在する状況を用意した。Main.javaでは ``import jp.ac.uryukyu.ie.tnal.*;`` と記述しているため、Dice.play() と書くと jp.ac.uryukyu.ie.tnal.Dice.play() が実行されそうに思うが、そうなっていない。これはクラスを検索する順序が影響しているおり、Mainクラスの置かれているsrc直下が優先されるためである。
      - このようなことを避けるためには、``jp.ac.uryukyu.tnal.Dice.play()`` のようにパッケージを省略せずに記述するか、もしくは重複するクラス名を避けて実装することになる。実際、src/Dice.java を削除してから Main クラスを実行すると、``Dice.play()`` と省略して書いた場合には ``jp.ac.uryukyu.tnal.Dice.play()`` が実行されるようになる。

```Java
// コード例4
// src/jp/ac/uryukyu/tnal/Dice.java
package jp.ac.uryukyu.tnal;

public class Dice {
    public static int play(){
        return -1; //意図的に修正。理由は上記解説参照。
    }
}

// src/Main.java
import jp.ac.uryukyu.tnal.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(Dice.play());
        System.out.println(jp.ac.uryukyu.tnal.Dice.play());
    }
}
```
