# オブジェクト指向型プログラミング2（コンストラクタのオーバーロード、カプセル化）
- 教科書関連箇所
  - 9.1節: クラス型と参照
  - 9.2節: コンストラクタ
  - 13章: カプセル化
- ＜目次＞
  - <a href="#class_reference">クラス型と参照（9.1節）</a>
  - <a href="#overload">コンストラクタのオーバーロード（9.2節）</a>
  - <a href="#encapsulation">カプセル化（第13章）</a>
  - <a href="#access_control_for_member">メンバに対するアクセス制御（13.2節）</a>
  - <a href="#access_control_for_class">クラスに対するアクセス制御（13.4節）</a>
  - <a href="#background">カプセル化を支えている考え方（13.5節）</a>

<hr>

## <a name="class_reference">クラス型と参照（9.1節）</a>
- クラスとオブジェクトの復習
  - クラスとはフィールドとメソッドを持つ設計図。
  - オブジェクトとはクラスを元に生成した実体。一つのクラスから複数のオブジェクトを生成できる。生成したオブジェクトは「クラス型」となる。
    - 代表的なクラス型
      - Object, String, java.util.* (Arrays, ArrayList,,,)
- クラス型オブジェクトを用いた代入の動作は参照となる
  - 基本型(int, double, boolean)変数を用いたイコール演算子では値が複製される。
  - 参照型変数を用いたイコール演算子では、参照情報が複製される。オブジェクトそのものは複製されない。
- コード例: 前回の[RentalBook.java](https://github.com/naltoma/java_intro2020/blob/master/ObjectOrientedProgramming.md#step1)でオブジェクトを参照する例。

```
//貸し出し図書の準備
RentalBook book1 = new RentalBook("何故ルートビアは美味いのか", 123);
System.out.println(book1.title);
book1.printSummary(); // => title =何故ルートビアは美味いのか, barcode = 123

RentalBook book2 = book1; // book1オブジェクトの参照を代入
book2.title = "ルートビアは世界一";
book1.printSummary(); // => title =ルートビアは世界一, barcode = 123
```

<hr>

## <a name="overload">コンストラクタのオーバーロード（9.2節）</a>
- コンストラクタ復習
  - コンストラクタとは、new演算子で生成したオブジェクトに対して自動実行しておきたい処理、多くの場合はフィールドの初期化をまとめたもの。
    - コンストラクタとみなされる条件
      - (1) 名称がクラス名と完全一致していること。
      - (2) 戻り値が設定されていないこと。
  - コンストラクタは、new演算子でオブジェクト生成するときのみ利用できる。
- オーバーロード
  - 同じ名称のコンストラクタやメソッドを複数定義すること。ただし、異なる引数で定義する必要がある。引数が異なることで、JVMはどのコンストラクタ（もしくはメソッド）を実行すべきかを自己解決することができる。
  - 例: [PrintStream.println](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/System.html#out)

<hr>

## <a name="encapsulation">カプセル化（第13章）</a>
- 多くの教科書ではオブジェクト指向の3大要素を「継承・多態性・カプセル化」であり、この順序で説明することが多い。これは間違いではないが、使われる頻度という点からは「カプセル化」が最も重要である。そのため教科書後半ではあるが先に13章から説明する。
- カプセル化の主な目的
  - ミスや想定外の使われ方を防ぐ手段の一つで、「やれることを制限する」ために用いられる。

-　制限したくなる背景
  - 勝手にフィールドを書き換えられたくない。
    - 例: [RentalBook.java](https://github.com/naltoma/java_intro2020/blob/master/ObjectOrientedProgramming.md#step1)の RentalBook.title は、本来なら一度設定したら後は変更不要のはず。だが、図書館員なりが後から変更することが可能なプログラムになっているため、タイトルを意図的・間接的に変更されてしまい、本来は所蔵している本を探せなくなる可能性がある。
  - 後からフィールドを変更したくなることがある。（クラスを設計変更しやすくなる）
    - 例: [Member.java](https://github.com/naltoma/java_intro2020/blob/master/ObjectOrientedProgramming.md#step2)の Member.name は、元々はここにフルネームを保存する予定だった。しかしファーストネーム、ラストネームを個別に検索することを求められ、フィールドを変更したくなった。
  - フィールドへのアクセスを検査できる。
    - 例: [Member.java](https://github.com/naltoma/java_intro2020/blob/master/ObjectOrientedProgramming.md#step2)の Member.name は、特に中身を確認すること無く設定可能なため、未入力時には ``null`` を保存してしまう。

```Java
// 変更前フィールド
public class Member {
    int id;
    String name;

    public Member(int _id, String _name){
        this.id = _id;
        this.name = _name;
    }

    public String getName(){
        return this.name;
    }
}

// ------------------------------------------

/** 変更例1：運用中にフィールドを変更したくなった状況に対する更新例。
 * まだアクセス制御はしていない点に注意。
 * 更新方針
 *   nameは近いうちに廃止する方針。
 *   しかし急に無くすとこれを利用しているユーザやアプリ全てに影響を及ぼすため、
 *   当面は name を残しつつ、並行して fullName を追加。
 *   少しずつこれを利用するように書き直し、
 *   完全にnameの利用が無くなった段階でフィールドを削除するものとする。
 */
public class Member {
    int id;
    String name; // 暫くは firstName + " " + lastName で対応。
    String fullName;
    String firstName;
    String lastName;

    /**
     * @deprecated nameを近いうちに廃止し、fllName, lastNameに移行します。{@link #Member(int, String, String)}
     */
    @Deprecated
    public Member(int _id, String _name){
        this.id = _id;
        this.name = _name;
        this.fullName = _name;
    }

    public Member(int _id, String _firstName, String _lastName){
        this.id = _id;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.fullName = this.firstName + " " + this.lastName;
        this.name = this.fullName;
    }

    public String getName(){
        return this.fullName;
    }
}
```

<hr>

## <a name="access_control_for_member">メンバに対するアクセス制御（13.2節）</a>
- メンバ＝フィールド＋メソッド
  - ここではフィールドとメソッドに対するアクセス制御のみを対象としている。
- 制御の種類
  - 授業的には private, public の違いがわかれば十分。

| 名称 | 指定方法 | アクセスを許可する範囲 |
| --- | --- | --- |
| private | private | 自分自身のクラスのみ |
| package private | (何も書かない) | 自分と同じパッケージに属するクラス |
| protected | protected | 上記もしくは自分を継承したクラス |
| public | public | すべてのクラス |

- 代表的なカプセル化の流れ。
  - フィールドはすべて private 指定。
  - メソッドはすべて public 指定。
  - このように設定した上で「これはまずい」と思う部分があればアクセス制御の変更を検討していく。
- アクセサ・メソッド（accessor method）の作成。
  - 原則としてフィールドは private にするため、フィールドを設定・参照するためにはその権限のあるメソッドを用意する必要がある。（設定させたくない、参照させたくないなら用意しなくても良い）
  - フィールドを設定・参照するためのメソッドをアクセサ・メソッドと呼ぶ。特に設定するためのメソッドをセッター（setter method）、参照するためのメソッドをゲッター（getter method）と呼ぶ。
  - どちらもアクセサであることを類推しやすくするための設定方法がある。
- setter (setter method)
  - 名称: ``set + フィールド名``。例えばMemberクラスのフィールドnameに対するセッターは ``public Member.setName``。
  - 引数: セッターはフィールドに値を設定するためのメソッドなので、そのフィールドと同じ型を指定する。例えば Member.name は String 型なので ``public Member.setName(String)`` になる。
  - 戻り値: メソッドなら何でも設定してよいはずだが、セッターメソッドはフィールドに値を設定するためのメソッドであるため戻り値は void にすることが一般的である。例えば Member.nameなら、``public void Member.setName(String)`` になる。
- getter (getter method)
  - 名称: ``get + フィールド名``。例えばMember.nameに対するゲッターは ``public Member.getName``。
  - 引数: ゲッターは指定したフィールドの値を参照することだけが目的なので、引数は設定しない。Member.nameに対する例なら ``public Member.getName()``。
  - 戻り値: 指定したフィールドの値を参照するために、フィールドの型を指定する必要がある。Member.nameに対する例なら ``public String Member.getName()``。

```Java
// ------------------------------------------

/** 変更例2：アクセス制御の例。
 * 更新方針
 *   全てのフィールドを private にする。
 *   更新したい場合にはアクセサを使う。
 */
public class Member {
    private int id;
    private String name; // 暫くは firstName + " " + lastName で対応。
    private String fullName;
    private String firstName;
    private String lastName;

    /**
     * @deprecated nameを近いうちに廃止し、fllName, lastNameに移行します。{@link #Member(int, String, String)}
     */
    @Deprecated
    public Member(int _id, String _name){
        this.setId(_id);
        this.setName(_name);
        this.setFullName(_name);
    }

    public Member(int _id, String _firstName, String _lastName){
        this.setId(_id);
        this.setFirstName(_firstName);
        this.setLastName(_lastName);
        this.setFullName(_firstName + " " + _lastName);
        this.setName(this.fullName);
    }

    public void setId(int _id){ this.id = _id; }
    public void setName(String _name){ this.name = _name; }
    public void setFullName(String _fullname){ this.fullName = _fullname; }
    public void setFirstName(String _firstname){ this.firstName = _firstname; }
    public void setLastName(String _lastname){ this.lastName = _lastname; }

    public String getName(){
        return this.fullName;
    }
}
```

<hr>

## <a name="access_control_for_class">クラスに対するアクセス制御（13.4節）</a>
- フィールドやメソッドではなく、クラスに対するアクセス制御である点に注意。

| 名称 | 指定方法 | アクセスを許可する範囲 |
| --- | --- | --- |
| public private | (何も書かない) | 自分と同じパッケージに属するクラス |
| public | public | すべてのクラス |

- これまでは「public class」を一つのソースファイルに定義し、そのファイル名を「クラス名.java」として設定した。これに対し「publicじゃないクラス」は複数何個でも記述することが可能だが、それらのクラスはパッケージ外からは直接のアクセスを許可されない。（publicクラス経由での間接的な利用だけが許可された状態になる）

<hr>

## <a name="background">カプセル化を支えている考え方（13.5節）</a>
- 特別な理由がない限り、フィールドは private として外部から隠し、getter/setter メソッド経由でアクセスする。メソッドというカプセルによりフィールドを保護する。
