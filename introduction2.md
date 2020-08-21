# Java入門2（データ型と命名規則、演算子、代表的なライブラリ、条件式、while文、switch文）

＜目次＞
- Java入門2（データ型と命名規則、演算子、代表的なライブラリ、条件式、while文、switch文）
  - <a href="#c3">いろいろなデータ型</a>
    - <a href="#c3-1">データ型の種類</a>
    - <a href="#c3-2">命名規則</a>
    - <a href="#c3-3">自動型変換</a>
  - <a href="#c4">演算子と演算</a>
    - <a href="#c4-1">Java言語の演算子</a>
    - <a href="#c4-2">キャスト演算子</a>
    - <a href="#c4-3">文字列の連結</a>
  - <a href="#c5">基本ライブラリの利用</a>
    - <a href="#c5-1">Stringクラスのメソッド</a>
    - <a href="#c5-2">Mathクラスのクラスメソッド</a>
    - <a href="#c5-3">Scannerクラス</a>
    - <a href="#c5-4">Integer, Float, Double, Booleanクラスのメソッド</a>
  - <a href="#c8">条件を書くための演算子</a>
    - <a href="#c8-1">文字列の比較</a>
    - <a href="#c8-2">論理演算子</a>
    - <a href="#c8-3">条件演算子</a>
  - <a href="#c9">while文</a>
    - <a href="#c9-1">while文の例</a>
    - <a href="#c9-2">do-while文</a>
  - <a href="#c11">ジャンプによる制御</a>
    - <a href="#c11-1">switch文（pp.119-）</a>

<hr>

## <a name="c3">いろいろなデータ型</a>
### <a name="c3-1">データ型の種類</a>
- データ型
  - 基本型（型ではあるが、クラスではない）
    - 整数型: byte, short, int, long
      - 具体的な違いは **p.49** 参照。
    - 浮動小数点型: float, double
      - floatとdoubleの違いは調べてみよう。普段はdoubleだけで問題ない。
    - 文字型: char
      - char型（=1文字）の場合は ``char c = 'a';`` のように、**シングルクォート** で囲うこと。
    - 論理型: boolean
  - 参照型（クラス）
    - 文字列型: String
      - String型の場合は ``String str = "hoge";`` のように、**ダブルクォート** で囲うこと。
- リテラル
  - 具体的な値（e.g., int型の値5）
  - 整数リテラル
    - 任意の箇所に ``2_000_000`` のようにアンダースコアを挿入できる。大きな数値を読みやすくするための記法。
  - 浮動小数点リテラル
  - 文字リテラル
    - 1つの文字。シングルクォーテーションで囲う事。
    - ユニコード（Unicode）：世界標準の文字コード体系。
    - エスケープ文字：特殊文字。**教科書の円マークはバックスラッシュ**。
      - 改行: ``\n``
      - タブ文字: ``\t``
  - 論理値リテラル
    - ``true``, ``false``
      - ``"true"``と``true``は異なる点に注意。
- 扱える値の範囲
  - 型ごとに範囲があることに注意。
  - int型が32bit（-2^31 〜 +2^31-1）
    - その半分16bitがshort。
    - その倍64bitがlong。
  - char型は16bit。
- 演習例
  - (演習1-1) ``\n`` を用い、「文字列の間に改行を含む」文字列を出力せよ。文字列は自由に設定して構わない。

### <a name="c3-2">命名規則</a>
- 識別子: クラス名、メソッド名、変数名などのプログラマが自由につけて良い名前のこと。
  - 使える文字: ``英数字, _, $``
  - 数字から始まってはならない。
  - 大文字小文字は異なる文字として扱われる。
  - [予約語](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html)は使えない。
  - 名前の長さに事実上制限はない（理論的にはint型整数の最大値と同じ）。
- 慣習上の命名規則
  - クラス名の先頭文字は大文字にする。
  - パッケージ名、変数名、メソッド名は小文字にする。
  - 2つ以上の単語を連結して名前を作る時は、2つ目以降の単語の先頭文字だけを大文字にする。
- 演習例
  - (演習2-1) 「私の名前」を String 型変数に保存したいとする。適切な変数名を検討し、変数宣言せよ。
  - (演習2-2) 「プログラミングIIの点数」を int型変数に保存したいとする。適切な変数名を検討、変数宣言せよ。

### <a name="c3-3">自動型変換</a>
- 変数に代入するリテラルは同じ型である必要があるが、厳密にしすぎるとプログラムが書きづらくなってしまう。
  - ``double x = 15;``
    - 右辺は整数型(int)リテラルだが、15.0に自動型変換されて代入される。
  - ``double y = x + 2``
    - 上記は、xが浮動小数点型ならば自動変換される。
    - 逆に言えば、「xが浮動小数点型でない」ならば、自動変換されない点に注意。
      - ``int a = 1;``
      - ``double b = a / 2;``
        - 上記のケースでは、変数aとリテラル2は整数型なので、割り算は整数型で行う。
        - 1を2で割った答えは0（整数型）。この値が浮動小数点型に自動型変換され、その結果である0.0が変数bに代入される。
        - 浮動小数点型で処理して欲しい時には、演算対象を明示的に記述しよう。
          - 明示方法1: 数値を直接コードに書いてるなら、 ``2.`` や ``2.0`` のように小数点付きで書くと浮動小数点型と解釈される。（「2.」はタイプミスではなく、正しい書き方）。
          - 明示方法2: キャストする。
- 自動型変換できないケース
  - ``int n = 15.432;``
    - システム側では「切り捨ててよいのか四捨五入すべきか」どうか判断できない。そのためコンパイルエラー（文法エラー）となる。
- 演習例
  - (演習3-1) ``int a=1; double b=a/2;`` の結果、変数bの値がどのようになるのか標準出力して確認せよ。その結果から、変数bの値がどのように算出されているか考察せよ。
  - (演習3-2) 一般的な算数としての「1÷2の結果は0.5」という結果を得たいものとする。ただし変数aと変数bの型を変更してしまうとその影響が他にも及ぼしてしまうため、変更しないものとする。変数の型を変更せずに演算結果が0.5となるためには、どのようにコード修正するとよいか検討せよ。コードを追加修正し、bの値が0.5となっていることを標準出力で確認せよ。

<hr>

## <a name="c4">演算子と演算</a>
### <a name="c4-1">Java言語の演算子</a>
- よく使うものは覚えておこう
  - メソッド呼び出し
  - 配列要素の参照
  - オブジェクトのメンバ参照
  - インクリメント、デクリメント
    - ``int i=0; i++;``
      - ``i += 1;`` と同等。主に配列において先頭から順繰りに処理していく場合にインデックスを1つずつ増やすことが多いため、専用の演算子として ``++`` が用意されている。
    - i--
      - ``i -= 1;`` と同等。
    - i++ と ++i は動作が異なる。（pp.74-76）
      - 特別な理由がなければ i++ だけ使おう。
  - 論理否定
  - オブジェクトの生成
  - 四則演算
  - 関係演算子
  - 論理演算子
- 優先順位も覚えておくべきだが、それ以上に「誤読されにくいコードを書く」方が良い。
  - 優先順位は()で変更（丸括弧付きを優先的に処理させる）できる。
  - 丸括弧が多くなりすぎて分かりづらくなるようなら、一度に複数の条件式を書きすぎているかも。部分的に関数化したり、個別判定したり、別表記できないかも検討してみよう。

### <a name="c4-2">キャスト演算子</a>
- プログラマの責任で強制的に型変換する操作のこと。
  - ``int number = (int)10.5;`` // 小数点以下を切り捨ててint型に変換。
  - ``float x = (float)1.234;`` // 小数点型はデフォルトでdoubleになるが、float精度(1.234f)で代入される。
- char型とint型の間での型変換（pp.81-）
  - char型は厳密にはユニコードであるため、int型との型変換が可能。特段の理由がなければ普段は使わない。
- 演習例
  - (演習4-1) 10.9をint型にキャストした結果を標準出力し、確認せよ。
  - (演習4-2) char型で'a'を保存し、その変数をint型にキャストした結果を標準出力し、確認せよ。
  - (演習4-3) 先程の結果は「97」となるはずだ。これを踏まえ、その次の値となる「98」をchar型にキャストした結果を標準出力し、確認せよ。

### <a name="c4-3">文字列の連結</a>
- 例1
```Java
String s = "こんにちは" + "太郎"+ "さん";
```

- 例2
```Java
String name = "太郎";
System.out.println("こんにちは" + name + "さん");
```
- Stringではない型の変数を連結する
- 演習例
  - (演習5-1) ``String[] text = {"This", "is", "test."};`` として保存されたString配列型に対し、全要素を結合した文字列（Thisistest.）を作り、標準出力せよ。　＊できればループ処理を使おう。
    - ヒント
      - String型変数を使うためには必ず初期値を与えてから利用する必要がある。
      - 例えば以下のコードは初期化されておらず、コンパイルエラーになる。これを避けるためには初期値を与えよう。今回の場合だと「空の文字列」を初期値として与えると良いだろう。

```Java
// コンパイルエラーになる例。

String result; //結合した文字列を保存するつもりで宣言だけした変数。
for(String word: text){
    result += word; //用意した変数に文字列を結合して保存。
}
```

<hr>

## <a name="c5">基本ライブラリの利用</a>
- APIドキュメント: [JDK 14 API Specification](https://docs.oracle.com/en/java/javase/14/docs/api/index.html)

### <a name="c5-1">Stringクラスのメソッド</a>
- [java.lang.String](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/String.html)
- 文字列の長さ
  - String.length()メソッド
- Stringクラスの代表的なAPI
  - boolean equals(String str): 文字列が、別の文字列strを等しい時trueを返す
    - Pythonのように、文字列が等しいことを判断するために比較演算子 ``==`` を使うのではない。専用メソッドを使おう。
  - char charAt(int i): 先頭からi番目の文字(char)を返す。
  - int length(): 文字列の長さ（文字数）を返す。
  - String toLowerCase(): 英字を小文字にした文字列を返す。
  - String toUpperCase(): 英字を大文字にした文字列を返す。
  - String trim(): 先頭と末尾の空白を削除した文字列を返す。
  - String[] split(String regex): 与えられた[正規表現regex](http://docs.oracle.com/javase/10/docs/api/java/util/regex/Pattern.html#sum)に基づき、文字列を分解した配列を返す。
    - 正規表現の例: [正規表現を使う](http://java-reference.com/java_string_regex.html)
- 演習例
  - 前提: ``String target = "This is test.";`` というString型変数を用意する。
  - (演習6-1) 変数targetに保存されている文字列を、toLowerCaseメソッドを使って全てを小文字に変換した文字列を作成し、標準出力せよ。
  - (演習6-2) 変数targetに保存されている文字列を、splitメソッドを使い、「スペース記号」を区切り文字として分割したString配列を作成し、標準出力せよ。（出力体裁は自由で構わない）

### <a name="c5-2">Mathクラスのクラスメソッド</a>
- [java.lang.Math](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/Math.html)
  - 絶対値、三角関数（ラジアン単位、角度単位）、自然対数、べき乗、max/min、0.0以上~1.0未満乱数、平方根、四捨五入、、、
- 平方根の計算
```
double x = Math.sqrt(2.0);
System.out.println("2.0の平方根=" + x);
```

### <a name="c5-3">Scannerクラス</a>
- [java.util.Scanner](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Scanner.html)
  - シンプルなテキスト読み込み機能を提供するクラス。
- ユーザからの入力を読み込む例。
  - ``java.util.Scanner`` は、``import java.util.Scanner;`` しておけば ``Scanner`` と省略して書ける。

```Java
//import無しで書いた例
String input; //読み込んだユーザ入力を格納するStringオブジェクトを用意。
java.util.Scanner in = new java.util.Scanner(System.in); // 標準入力から読み込むスキャナを用意

while(true){
  System.out.println("何かごようですか？"); // 入力を促す文を出力しているだけ。
    input = in.nextLine(); // inputにユーザ入力を保存する。
  if (input.equals("bye.")){
    System.out.println("ご利用いただきありがとうございました");
    break;
  }else{
    System.out.println(input + "とは何ですか？");
  }
}
in.close(); // スキャナを閉じる。
```
- 演習例
  - (演習7) 上記コード例を参考に、「クーラー」という文字列が含まれている場合には「クーラーをONにしました」と標準出力するコードを書け。
    - ヒント
      - 部分文字列としてクーラーが含まれているかどうかは [String.containsメソッド](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/String.html#contains(java.lang.CharSequence)) を使って判定できる。

### <a name="c5-4">Integer, Float, Double, Booleanクラスのメソッド</a>
- APIドキュメントからIntegerクラスを探してみよう。
- Integerクラスのフィールド変数
  - MAX_VALUE
  - MIN_VALUE
- Integerクラスの代表的なAPI
  - int max(int a, int b)
  - int min(int a, int b)
  - int parseInt(String s): 文字列sをint(符号付き10進数整数)型に解析し直す（パースする）。
    - 浮動小数点型、ブーリアン型へ変換するには下記を使おう。
      - ``Float.parseFloat(String s)``
      - ``Double.parseDouble(String s)``
      - ``Boolean.parseBoolean(String s)``
  - String toString(): Stringに変換。
- 演習例
  - (演習8) ``String data = "3.14";`` と保存されているとし、変数dataをdouble型変数に変換した結果をdouble型変数として保持せよ。

<hr>

## <a name="c8">条件を書くための演算子</a>
### <a name="c8-1">文字列の比較</a>
- 「関係演算子==」は、String型（参照型）には **適用できない**。意味が異なる。
  - 文字列がnullかどうか調べる場合だけ使える。
    - ``object == null`` //objectがnullである（実体がない）時にtrue
- 文字列そのもの値を比較するには、String.equals()メソッドを使う。

```Java
String str = "Java";
boolean answer = str.equals("Java");
System.out.println(answer)
answer = str.equals("Java2");
System.out.println(answer)
```

### <a name="c8-2">論理演算子</a>
- ``&&``: 論理積
- ``||``: 論理和
- ``!``: 否定

### <a name="c8-3">条件演算子</a>
- 3項演算子の例。
- 条件演算子
  - 条件部がtrueかfalseかで違う値になる式を作る。簡単なif文なら1行で書いてしまった方が全体を俯瞰しやすくなる。（使うことを推奨するわけではない）
  - 以下の例では「aの値が偶数ならnの値を100に、そうでないならnの値を0にする」。

```Java
int a = 5;
int n = a%2==0 ? 100 : 0;
```

<hr>

## <a name="c9">while文</a>
### <a name="c9-1">while文の例</a>
- 条件を満足している間、ループ処理を実行し続ける。

```Java
int i = 0;
while( i<3 ){
    System.out.println(i);
    i++;
}
```

### <a name="c9-2">do-while文</a>
- 最初に処理を実行し、その後で条件を確認する。（最低1回は処理を実行する）

```Java
int i = 0;
do {
    System.out.println(i);
    i++;
} while( i<3 );
```

<hr>

## <a name="c11">ジャンプによる制御</a>
### <a name="c11-1">switch文（pp.119-）</a>
- swich文は、非常に多くの分岐先がある時に有効な構文。if-else文でも同じ処理を書けるが、switch文の方が高速。ただし、(1)すべての条件式が「一致するか否か」を判定する条件式になっており、(2)比較する値が int, char, String である時のみ利用できる。また (3) caseにはリテラルだけが利用できる（caseに変数は書けない）。

```Java
// ref: https://www.tutorialspoint.com/java/switch_statement_in_java.htm
char grade = 'B';
switch (grade) {
    case 'A':
        System.out.println("Excellent!");
        break;
    case 'B':
    case 'C': // if (case == 'B' or case == 'C') と同等。
        System.out.println("Well done");
        break;
    case 'D':
        System.out.println("You passed");
        break;
    case 'F':
        System.out.println("Better try again");
        break;
    default:
        System.out.println("Invalid grade");
}
System.out.println("Your grade is " + grade);
```

- caseで指定された値と等しい箇所があれば、そこにジャンプする。
  - ジャンプ先にbreakがあれば、そのブロックを抜ける。（ここではswitch文を抜ける）。
  - **ジャンプ先にbreakがなければ、ブロックを抜けず、処理を継続する点に注意。**
    - 例えば、grade = 'B' 時に、上記コードで「case 'B'」にはbreakがない。この場合、case 'C'の確認をせずに素通しして case 'C' の処理を実行する。
    - default は全ての case に当てはまらなかった場合に実行される。
- Java 14 以降は以下の書き方も可能。

```Java
// Java14以降
char grade = 'B';
switch (grade) {
    case 'A' -> System.out.println("Excellent!");
    case 'B', 'C' -> System.out.println("Well done");
    case 'D' -> System.out.println("You passed");
    case 'F' -> System.out.println("Better try again");
    default  -> System.out.println("Invalid grade");
}
System.out.println("Your grade is " + grade);
```

<hr>

- 演習のコード例: [Week2.java](./src/Week2.java)
