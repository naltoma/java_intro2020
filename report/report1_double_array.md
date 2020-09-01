# Javaの言語仕様に慣れよう

## <a name="abst">課題概要</a>
- APIドキュメントを参照して読む練習をしよう。
- 配列操作に慣れよう。
  - 行列を足し算するメソッドを書いてみよう。行列は二重配列で用意するものとする。具体的には<a name="#pre">前提</a>に書いてるように2行2列や2行3列の行列を ``double[][]`` で用意するとし、これらを引数として受け取って足し算するメソッドを書こう。

<hr>

### <a name="pre">前提</a>
- パッケージ名を ``jp.ac.uryukyu.ie.your_account`` 形式とする。
  - your_accountは自身のアカウント名に置き換えること。
- クラス名を ``Week1`` とする。
- printMatrixメソッドを以下の通り定義せよ。なお、インデントは適切に揃えること。

```Java
    public static void printMatrix(double[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                System.out.printf("%5.1f", matrix[i][j]);
            }
            System.out.println();
        }
    }
```

- mainメソッドを以下の通り記述し、動作確認せよ。

```Java
    public static void main(String[] args){
        double[][] matrix22_1 = {{0., 1.}, {2., 3.}};
        double[][] matrix22_2 = {{4., 5.}, {6., 7.}};
        double[][] matrix23_1 = {{0., 1.}, {2., 3.}, {4., 5.}};
        double[][] matrix23_2 = {{6., 7.}, {8., 9.}, {10., 11.}};

        System.out.println("\n# printMatrixの動作確認");
        printMatrix(matrix22_1);

        //以下は computeAddMatrix メソッドを実装したあとで実行する内容。
        System.out.println("\n# matrix22_1 + matrix22_2");
        double[][] level2_1 = computeAddMatrix(matrix22_1, matrix22_2);
        printMatrix(level2_1);

        System.out.println("\n# matrix23_1 + matrix23_2");
        double[][] level2_2 = computeAddMatrix(matrix23_1, matrix23_2);
        printMatrix(level2_2);

        System.out.println("\n# 行列サイズが異る場合");
        double[][] level2_3 = computeAddMatrix(matrix22_1, matrix23_1);
    }
```

- 実行結果イメージ

```
# printMatrixの動作確認
  0.0  1.0
  2.0  3.0

# matrix22_1 + matrix22_2
  4.0  5.0
  6.0  7.0

# matrix23_1 + matrix23_2
  6.0  8.0
 10.0 12.0
 14.0 16.0

# 行列サイズが異る場合
行数がおかしい
```

<hr>

### <a name="level1">Level 1: printMatrixメソッドについて調べよう。</a>
- Level 1.1, 二重配列の並びについて確認しよう。
  - 引数matrixは二重配列として宣言されている。同様にmainメソッドには2行2列の行列のつもりで二重配列で記述した matrix22_1, matrix22_2。2行3列の matrix_23_1, matrix23_2 が用意されている。このmainメソッドで ``printMatrix(matrix23_2);`` として呼び出された状況について、以下の問いに答えよ。なお配列サイズの範囲外を参照している場合には ``IndexOutOfBoundsException`` と答えよ。
    - (a) printMatrixメソッド内で ``matrix[0][0]`` に保存されている値は何か。
    - (b) printMatrixメソッド内で ``matrix[1][1]`` に保存されている値は何か。
    - (c) printMatrixメソッド内で ``matrix[2][2]`` に保存されている値は何か。
- printMatrixで用いられているメソッド System.out.printf について、APIドキュメントで調べよう。
  - 前提手順
    - まず、APIドキュメントトップページを開こう。次に System.out で検索し、該当ページを開こう。
    - System.out で見つかったページには ``System.out.println`` しか言及しておらず、printf は見当たらない。そこで、out自体の型のように見える ``PrintStream`` をクリックしてみよう。
    - ``Class PrintStream`` のページに飛ぶと、PrintStream についての概要、Field Summary、Constructor Summary、Method Summaryが記述されており、Method Summaryの中に利用可能なメソッド一覧が列挙されている。
  - Level 1.2, メソッド一覧から print, println について調べ、両者の違いを1〜数行で述べよ。
  - Level 1.3, printfメソッドについて報告せよ。
    - ここでは ``printf​(Locale l, String format, Object... args)`` について調べるものとする。
    - (a) メソッド概要文について、1〜数行で説明せよ。
    - (b) 3つの引数 ``Locale l``、``String format``, ``Object... args`` について、各々1〜数行で説明せよ。

<hr>

### <a name="level2">Level 2: 行列演算メソッドを書いてみよう。</a>
- mainメソッドには2行2列の行列 matrix22_1, matrix22_2、2行3列の行列 matrix23_1, matrix23_2 を二重配列として宣言している。これらの行列の和を求めるメソッド ``computeAddMatrix`` を作成せよ。
- 条件
  - computeAddMatrixに与える行列のサイズは、事前にはわからないものとすること。
    - computeAddMatrix(matrix22_1, matrix22_2) のときは2行2列の足し算をするし、computeAddMatrix(matrix23_1, matrix23_2) のときは2行3列の足し算をするようにすること。
  - 行列演算であるため行列のサイズが異なる場合には処理できない。そのため computeAddMatrix では行列サイズを確認し、行数や列数が異なる場合には、標準エラー出力に「行数がおかしい」「列数がおかしい」と出力し、プログラムの実行を直ちに終了すること。
    - 列のサイズを確認する際には、1行目の要素数だけで判断しても構わない。（全ての行で確認しても構わない）
  - レポートには ``computeAddMatrix(matrix22_1, matrix22_2)`` の結果と、computeAddMatrix(matrix23_1, matrix23_2)`` の結果を printMatrix で出力するためのコードと、実行結果を記載すること。
- ヒント
  - 二重配列を引数として受け取り、返り値として設定するためには次のように宣言しよう。
    - ``public static double[][] computeAddMatrix(double[][] matrix1, double[][] matrix2){}``
    - これで引数と戻りの型を double 型の二重配列として宣言していることになる。
    - 引数名は自由に変更して構わない。
    - ``{}`` は適切に改行＆インデントし、中に具体的な処理を書くこと。
  - 二重配列が良く分からない場合。
    - まずは通常の配列を用いて横ベクトルを記述してみよう。2つの同じ長さの横ベクトルを足してその結果を返すメソッドを書いてみよう。
    - 次に縦ベクトルを二重配列で記述してみよう。2つの同じ長さの縦ベクトルを足してその結果を返すメソッドを書いてみよう。
  - 配列の用意の仕方。
    - 配列は予め要素数を決めて用意する必要がある。例えば2行3列の配列を用意するには以下の方法がある。
      - case 1: 初期値を列挙することで要素数を指定。
        - ``double[][] matrix22_1 = {{0., 1.}, {2., 3.}};``
      - case 2: 要素数だけを先に宣言し、後で値を設定。
        - ``double[][] matrix22_1 = new double[2][2];`` //2行2列の要素を確保
        - ``matrix22_1[0][0] = 0.;``
        - ``matrix22_1[0][1] = 1.;``
        - ``matrix22_1[1][0] = 2.;``
        - ``matrix22_1[1][1] = 3.;``
      - computeAddMatrixの計算結果は計算後にしかわからないため、case 2で二重配列用の変数を用意しておく必要がある。なお、ここでは直接 ``double[2][2]`` のように行数列数を数字で書いているが、2行3列のように異なるサイズが入ってくることもあるため、引数の行列サイズに応じて指定するようにしよう。
  - System.out.* は標準出力へ出力する。標準エラー出力のためには System.err.* を使おう。
  - プログラムの実行を終了するためには [System.exit](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/System.html#exit\(int\)) を使おう。

<hr>

### <a name="option">オプション例</a>
- printMatrixをインデックスを使わない方法に書き直してみよ。
- 【高難易度】通常の配列（``int[] data = {1, 2};`` とか）や今回のような二重配列を保存した変数を、直接 System.out.println で出力してみよ。Pythonのリストならばリスト内の要素が列挙されるが、Javaの配列はそうならないはずだ。要素が出力されないことについて System.out.println のドキュメントを参照しつつ考察せよ。
  - System.out.println の See Also には複数の関連メソッドが並んでいる。今回のケースではどれが実行されているだろうか？
  - それを選んだ理由を述べよ。
- 行列演算をサポートするライブラリの一つに [Commons Math](http://commons.apache.org/proper/commons-math/) がある。実際に導入して行列演算を実行してみよ。
  - そもそも外部ライブラリを使うために何をどう用意するとよいのだろうか？
  - ヒント
    - ライブラリをダウンロードして展開するだけでは使えない。どこに置くか検討し、置いた場所をJDKが参照できるようにパスを設定しよう。

<hr>

## <a name="report">取り組み方</a>
- ペアや友人らと話し合って取り組んで構わないが、コード解説を加えるなど「自分自身の報告書」となるように取り組むこと。試して分かったこと、自身で解決できなかった部分等についてどう取り組んだか、といった過程がわかるように示すこと。（考えを図表や文章を駆使して表現して報告する練習です）
- レポートには下記内容を含めること。
  - タイトル
    - 今回は「**プログラミング2、レポート課題1: 「Javaの言語仕様に慣れよう」**」。
  - 提出日: yyyy-mm-dd（4桁年-2桁月-2桁日）
  - 報告者: 学籍番号、氏名
    - 複数人で相談しながらやった場合、相談者らを「**協力者: 学籍番号、氏名**」として示そう。
  - Level 1, 2の結果（あれば考察も）
  - その他
    - 通常は感想等をレポートには含めませんが、練習なので課題に取り組みながら何か感じたこと、悩んでいること等、書きたいことがあれば自由に書いてください。（なければ省略OK）

<hr>

## <a name="submit">提出方法</a>
- 提出物は「レポート」、「作成したソースファイル」の2点である。
- レポートは電子ファイルで提出するものとする。Wordでも何でも構わない。
- 提出先:
  - 別途指定するGoogleドキュメントのreport1。
- 締切: 2週間後の授業開始前。
