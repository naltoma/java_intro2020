# 静的メンバ
- 教科書
  - 静的メンバ: 14.3節
- ＜目次＞
  - <a href="#terms">用語説明</a>
  - <a href="#example">静的メンバがあると嬉しい状況？</a>
  - <a href="#note">補足</a>

<hr>

## <a name="terms">用語説明</a>
- 静的メンバ（static member）
  - 関連語
    - 静的フィールド（static field）
    - 静的メソッド（static method）
  - そもそも「クラスメンバ」とは、コンストラクタを除いた、オブジェクトが有するフィールドやメソッドのこと。
  - 「静的メンバ」とは、クラスメンバにおける特殊なメンバを指す。
    - 通常のクラスメンバは、オブジェクトごとに固有に存在する。
    - 静的なクラスメンバは、**クラス間で1つの実体を共有している。オブジェクトとは分離されている**。
  - 本来「オブジェクト指向」ならば、「オブジェクト（何かしら動作する主体） を中心に見据え、そのオブジェクトの振る舞いを捉える（教科書7章）」ためにフィールドやメソッドを設計し、クラスとして実装する。このように実装されたクラスから生成したオブジェクトの持つフィールドやメソッドをクラスメンバと呼んでいた。
  - これに対し静的メンバとは、オブジェクト指向とは異なる設計指針で実装されることが多い。何かしら動作する主体を中心に据えておらず、単に振る舞いのみを記述し、フィールドとは無関係に常に同じ処理を実行するものであることが多い。
    - 静的メンバは「（通常の）フィールドとは無関係」である点に注意。フィールドを参照する処理は記述できないという制約がある。
- 静的メンバの例
  - 例1: [Integer.parseInt()](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String))
  - 例2: [Math.abs()](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/Math.html#abs(double))
  - これらは static メソッドとして実装されている。通常のメソッドと異なり、いちいちnew演算子でオブジェクトを生成することなく即座に利用することができる。これは「フィールドとは無関係に常に同じ処理を実行する」ためである。

<hr>

## <a name="example">静的メンバがあると嬉しい状況？</a>
- 理由1
  - newせずに手軽に呼び出すため。（教科書, p.550）
- 理由2
  - newではなく静的メソッドを使ってインスタンスを生成するため。（同上）
  - 例: [java.util.ArrayList.iterator()](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/ArrayList.html#iterator())

```Java
// newなしでオブジェクト生成する例

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Sample {
    public static void main(String[] args){
        List<String> list = new ArrayList<String>();
        list.add("item1");
        list.add("item2");
        list.add("item3");

        Iterator iterator = list.iterator(); // newなしでオブジェクト生成
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
```

<hr>

## <a name="note">補足</a>
- 繰り返しになるが、静的メンバは「クラス間で共有」されている。
  - 通常のメンバはオブジェクトごとに独立している。例えば何度か例に挙げた「会員クラス」なら、オブジェクトごとに異なる名前を保存することができる。これに対し、もし会員クラスを静的メンバとして実装すると、全ての会員が同じ名前になってしまう。これが「クラス間で共有」の意味である。このため通常の実装では静的メンバを使うことはないと考えて良い。
- どのようなときに静的メンバを使うのか？
  - Mathクラス等、引数や静的メンバのみで処理結果が決定できる場合。
