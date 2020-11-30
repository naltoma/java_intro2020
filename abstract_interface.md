# 抽象クラスとインタフェース
- 教科書11章。
- ＜目次＞
  - <a href="#terms">用語説明</a>
  - <a href="#abstract-example">抽象メソッドがあると嬉しい状況？</a>
  - <a href="#interface-example">インタフェースの実装イメージ</a>
  - <a href="#goal">当面の目標</a>
  - <a href="#generics">おまけ：ジェネリクス（generics）</a>

<hr>

## <a name="terms">用語説明</a>
- **具象クラス（concrete class）**
  - 全てのメソッドを実装し終えているクラスのこと。
- **抽象クラス（abstract class）**
  - 一部または全体を抽象化して記述したクラスのこと。ここでいう抽象化とは「メソッド名・引数・戻り値だけを定義して中身（実装）を省略すること」を指す。実装を省略したメソッドを **抽象メソッド（abstarct method）** と呼ぶ。
  - 実装済みメソッドと抽象メソッドとが混在していても構わないが、必ず一つ以上のメソッドが抽象化されている必要がある。
  - 抽象メソッドがあるクラスは未実装のメソッドが含まれているため、new演算子でオブジェクトを生成することはできない。抽象クラスのオブジェクトを生成するためには、その抽象クラスを継承したサブクラスにおいて実装を記述（override）し、そのサブクラスのオブジェクトとして生成する必要がある。つまり、**実装を強制** することができる。
  - クラスなので単一継承しかできない。抽象クラスを継承して抽象クラスを記述しても良い。
- **インタフェース（interface）**
  - インタフェースはAPI（application programming interface）のインタフェースを意識して付けた名称だと思われる。公式ドキュメントでは「[Interfaces as APIs](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)」と書かれている。
  - 基本的には抽象メソッドだけを記述したクラスのようなもの。抽象クラスに似ているが、インタフェースでは「原則として実装しない」ことが前提である。記述できるものは定数（finalなフィールド変数）、抽象メソッド、デフォルトメソッド（default methods）、スタティックメソッド（static methods）ぐらいである。
  - 抽象クラスと同様に抽象メソッドを含むため、インタフェースから直接オブジェクトを生成することはできない。
- 関連
  - クラス、抽象クラス、インタフェース、配列は参照型である。

<hr>

## <a name="abstract-example">抽象メソッドがあると嬉しい状況？</a>
- [Abstract Methods and Classes](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
  - ドロー系アプリケーションを実装しようとした際の例。
  - 各図形は「基準点（x, y）を元に図形を描画する、移動する、サイズを変更する、角度を変更する」といったメソッドを有する。これら全ての共通機能をスーパークラスとして実装し、個別図形については継承して実装することができるかもしれない。しかし一度スーパークラスとして実装すると「共通させることが困難な部分」がどうしてもあり、結果的にサブクラスで実装せざるを得ないことが多い。例えば四角形のサイズを変更する場合と楕円を変更する場合とでは同じメソッドで記述できない。
  - 「サブクラスで具体的な実装を書く」こと自体は良いが、例えば図形毎に開発者が異なる状況を想像しよう。話し合いにより全ての図形においてサイズを変更するメソッドは ``void resize(int)``、移動するメソッドは ``void moveTo(int x, int y)`` のように仕様を決めておいたとし、スーパークラス GraphicObject を用意したとする。また、それを元に四角形担当者が Rectangle クラスを実装したとしよう。

```Java
/* スーパークラス */
public class GraphicObject {
  public void reseize(int rate){ /*継承してここ実装してね*/ }
  public void moveTo(int x, int y){ /*継承してここ実装してね*/ }
}

/* サブクラス */
class Rectangle extends GraphicObject {
  public void resize(double rate){ /* 実装しました */ }
  public void moveto(int x, int y){ /* 実装しました */ }
}
```

- 上記の何が問題か
  - 直接的には、スーパークラスではresize(int), moveTo(int, int)での実装を想定しているにも関わらず、サブクラスでは異なるAPIで実装している。このためスーパークラスを元にビュー等他の部分を実装している機能との齟齬が生じる。
  - そもそも論として、「決めた通りに実装しないこと（人）が悪い」のだろうか？　それを悪いとするなら100%誤りのないドキュメントを用意してそれを読んだ上で実装するだけでよいだろう。だけどそれで本当に100%正しい実装になるのだろうか？　授業における課題レポート程度の小さなプログラムですら条件を守理切れていない学生は多いし、人間はコンピュータではないのだから「指定したからといって指定通りにやれるとは限らない」。それを踏まえたアプローチはないだろうか？
  - これに対する一つの答えが抽象クラス。抽象クラスでは指定した抽象メソッドをそのAPI通りに実装しないとオブジェクトを生成することすらできない。このため複数人で開発している際に齟齬が生じにくくなるし、一人で開発してたとしても実装し忘れやタイプミスのようなケアレスミスを事前に防ぎやすくなる。
  - スーパークラスを抽象クラスで用意すると以下のようになる。

```Java
/* スーパークラス */
public abstarct class GraphicObject {
  public abstract void reseize(int rate); /*継承してここ実装してね*/
  public abstract void moveTo(int x, int y); /*継承してここ実装してね*/
}

/* サブクラス */
class Rectangle extends GraphicObject {
  @Override
  public void resize(int rate){ /* 実装しました */ }
  @Override
  public void moveTo(int x, int y){ /* 実装しました */ }
}
```

- 抽象クラスの記述方法
  - クラス名に **abstract** 修飾子を付ける。
  - 抽象メソッドにも abstract 修飾子を付け、実装を省略する。上記ではわかりにくいが **ブレース{}がなくなり、セミコロンでメソッドを定義しているだけ** という点に注意しよう。
- 具象クラスの記述方法
  - 抽象クラスを継承するだけ。

<hr>

## <a name="interface-example">インタフェースの実装イメージ</a>
- 抽象クラスに似ているが、インタフェースの記述方法は大きく異なる。
  - インタフェース側は **interface**。
    - 抽象メソッドの書き方は抽象クラスと同じでも良いが、インタフェースでは public を省略して書ける。
    - デフォルトメソッドを書くときは default 修飾子を追加。
  - インタフェースを実装する側は **implements** を使う。extends ではない点に注意。

```Java
/* インタフェース */
public interface GraphicObject {
  final float version = 0.1;
  void resize(int rate);
  void moteTo(int x, int y);
  default float getVersion(){ return this.version; }
}

/* 実装 */
class Rectangle implements GraphicObject {
  public void resize(int rate){}
  public void moveTo(int rate){}
}
```

- インタフェースでは多重継承のようなことも実現できる。

```Java
/* インタフェース2 */
interface Color {
  void setColor(int red, int green, int blue);
}

/* 実装 */
class Rectangle implements GraphicObject, Color {
  public void resize(int rate){}
  public void moveTo(int rate){}
  public void setColor(int red, int green, int blue){}
}
```

- 継承と合わせてインタフェースを利用することもできる。

```Java
public abstarct class Color {
  public setColor(int red, int green, int blue);
}

interface GraphicObject {
  void resize(int rate);
  void moteTo(int x, int y);
}

/* 実装 */
class Rectangle extends Color implements GraphicObject {
  public void resize(int rate){}
  public void moveTo(int rate){}
  public void setColor(int red, int green, int blue){}
}
```

<hr>

## <a name="goal">当面の目標</a>
- 1年次授業時点で抽象クラスやインタフェースを含めた設計・実装をすることは求めていない。ただし、抽象クラスやインタフェースを利用することはできるようになろう。
- 例
  - Pythonでは当たり前のように使えていたリストは、Javaでは[public interface List\<E\> extends Collection\<E\>](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/List.html)としてインタフェースになっており、このままでは使えない。
  - ``<E>`` は **ジェネリクス（generics）** と呼ばれている。（後述）

<hr>

## <a name="generics">おまけ：ジェネリクス（generics）</a>
- ``<E>`` のように比較演算子で型を囲う形で記述する。Eの部分は型名と考えてよい。例えば自身で作ったHOGEクラスがあるるなら``<HOGE>``と書くこともできる。なにか特定の要素を指したい時にはElementの頭文字を使って``<E>``。辞書のKey, Valueを指すときにはそれらの頭文字``<K>, <V>``。あまり特定のものでは説明しづらい場合にはTypeの頭文字``<T>``を使うことが多い。
  - ``<E>``は「任意の型を持つ要素」ぐらいの意味。
- 主に [java.util.Collection](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Collection.html) のように異なる型が混在する状況下で使うことが多い（多分）。
- Listインタフェースを実装したArrayListを用いた以下のコードは、list.add() の時点では任意のオブジェクトを追加することができる。しかしint型の5やdouble型の3.14は``(String)``でキャストすることができないため、そこで実行時にエラーとなる。

```Java
// ジェネリクスを使わない例
import java.util.List;
import java.util.ArrayList;

public class WithoutGenerics {
    public static void main(String[] args){
        List list = new ArrayList();
        list.add("abc");
        list.add(5);
        list.add(3.14);

        for(Object obj : list){
            String str = (String)obj;
            System.out.println(str);
        }
    }
}
```

- 本来Listは``<E>``のため任意のオブジェクトを格納できるという点では便利。しかし、例えば「私のクラスではStringしか使わないのに、毎回型を確認してキャストするコードを書かないといけない」のは面倒だったりする。これを緩和するのがジェネリクス。``List<String>``のように型指定するとそれ以外の型を受け付けず（コンパイル時にエラーを出す）、予め型が分かっているためキャストも不要になる。

```Java
import java.util.List;
import java.util.ArrayList;

public class WithGenerics {
    public static void main(String[] args){
        List<String> list = new ArrayList();
        list.add("abc");
        //list.add(5); // コンパイル時にエラー
        //list.add(3.14);

        for(Object obj : list){
            //String str = (String)obj; //キャスト不要。
            System.out.println(obj);
        }
    }
}
```
