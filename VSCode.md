# Visual Studio Code での開発の流れ
- ＜目次＞
  - <a href="#ex1">動作確認</a>
  - <a href="#ex2">packageの設定</a>
  - <a href="#summary">振り返り</a>
  - <a href="#ref">参考文献</a>

<hr>

## <a name="ex1">動作確認</a>
- 用語
  - 作業スペース（Workspaces）
    - 複数プロジェクトを保管する場所。今回はプログラミング2では以下のように用意するとしよう。なお、既にプログラミング1でこれに該当するディレクトリを用意済みならば、それを利用してもらって構わない。
      - ``~/prog2/`` : vscodeで作成したプロジェクト一覧。
  - プロジェクト（Project）
    - プログラムを動作させるために必要なもの一式をプロジェクトとして管理する。ソースコードやテストコード、データセット等諸々をまとめたものの総称だが、まとめかたは様々。当面は「プロジェクト作成〜プログラム作成〜実行」の一連の流れを覚えるため、授業中の演習は毎週新しくプロジェクトを作成することにしよう。
- プロジェクトを作成する。
  - ``⌘⇧p`` を押して、検索窓に ``java`` と入力。
  - 検索結果から ``Java: Create Java Project...`` を選択。
- ビルド方法を選択。
  - 当面は ``No Build Tools`` を選択しよう。
- プロジェクトを保存する場所を選択。
  - **作業スペース** で用意したディレクトリを選択（select the project location）しよう。
- プロジェクト名を設定。
  - ``Input a Java project name (Press 'Enter' to confirm or 'Escape' to cancel)`` と促される。作成したいプロジェクト名を入力して、Enterを押そう。ここで入力するプロジェクト名はディレクトリとして作成されるため、既存プロジェクトと被らない名前にすること。今回は ``week1`` にしておこう。
    - 作成ミスしたり、お試し作成したり等の理由で不要になったプロジェクトがあれば、FinderなりTerminalなりで該当プロジェクトを削除しよう。削除する際には誤って本来は残しておきたかったものを削除しないように注意。
- 作成されたプロジェクトを確認。
  - 以下のようにディレクトリ、ファイルが作成されていればOK。
  - ``.vscode/`` は、VSCode設定関連の置き場所。
  - ``lib`` は library の略。
  - ``src`` は source (soucr file) の略で、ソースファイル一式を置く場所。
  - ``src/App.java`` は、プログラム初期作成時に自動で用意してくれたテンプレート。
  - ``README.md`` は、プロジェクト全体に関するドキュメントを **Markdown記法** で記述したテキストファイル。
    - [Markdownとは](http://www.markdown.jp/what-is-markdown/)
    - [基本的な書き方とフォーマットの構文 / GitHubでの執筆](https://docs.github.com/ja/github/writing-on-github/basic-writing-and-formatting-syntax)

```zsh
(base) oct:tnal% pwd
/Users/tnal/prog2/week1
(base) oct:tnal% ls -1
README.md
lib/
src/
(base) oct:tnal% find .
.
./README.md
./lib
./.vscode
./.vscode/settings.json
./src
./src/App.java
```

- プログラムの実行。
  - 実行したいソースファイルを開く。今回は ``src/App.java`` を開こう。
  - Run メニューから ``Run without debugging（デバッグなし実行）`` を選択。問題なければターミナルに ``Hello world!`` と出力されるはず。
- ソースコードの一部修正。
  - 3行目の ``System.out.println()`` の中身を変更して、動作確認してみよう。

<hr>

## <a name="ex2">packageの設定</a>
- package とは、ソースコードをまとめるための概念。これがあることで例えば「組織Aが開発した Hoge.java と組織Bが開発した Hoge.java」のように同名プログラムがあっても区別して利用することができるようになる。
- package の設定方法
  - パッケージ名を先に決める。
    - 授業では ``jp.ac.uryukyu.ie.tnal`` 形式で設定することにしよう。これはメールアドレスを逆順に書き、@マークやハイフンを削除したもの。こうしている理由は、メールアドレスなら学生個々人が被らないように設定できるから。
    - tnalは各自のアカウント名に修正すること。
  - パッケージ用フォルダを用意する。
    - srcディレクトリ上で ``ctrl + click`` してサブメニューを出す。
    - ``New Folder`` を選択。
    - 先程決めたパッケージ名を元に **ドット(.)をスラッシュ(/)に置換した一連のフォルダ階層** を入力する。今回の當間の例だと ``jp/ac/uryukyu/ie/tnal`` と入力することになる。
  - ソースファイルを作成する。
    - 考え方
      - パッケージ指定があるソースファイルはそのパッケージフォルダの中に置く必要がある。今回のケースだと、``jp.ac.uryukyu.ie.tnal`` に属するソースファイルを作成したいならば、``jp/ac/uryukyu/ie/tnal`` 以下に置く必要がある。
      - 今回は、用意したパッケージに属する ``Week1.java`` というファイルを作成するとしよう。
    - 作業手順
      - 作成したパッケージ名の右端（上記例だと ``tnal`` や、その右側）を ``ctrl + click`` してサブメニューを出す。
      - ``New File`` を選択。
      - ``Week1.java`` と入力して新規作成する。
        - この時点で自動的に ``package jp.ac.uryukyu.ie.tnal;`` というパッケージ設定文が入力されているはず。
- ソースファイルの編集。
  - ``Week1.java`` を以下のように編集しよう。1〜3行目は既に記述されているはずなので、4行目から始まるmain関数を手打ちで入力してみよう。
  - いろいろと補完機能がある。
    - ``public class Week1 {}`` の中で main と書いてみよう。
    - Java 21 では試験的に「void main()」と省略して書く機能が試験的に盛り込まれています。またクラス名を省略して書くこともできるようです。ただしこれらはまだプレビュー版なので、授業では従来通りの記法を用います。
      - 参考: [ [Java のpublic static void main なくなるってよ。](https://qiita.com/shupeluter/items/78a491fbaf84aaf119d9) | [JEP 445: Unnamed Classes and Instance Main Methods (Preview)](https://openjdk.org/jeps/445)] ]
  - 書き終えたら実行できることを確認しよう。

```Java
package jp.ac.uryukyu.ie.tnal;

public class Week1 {
    public static void main(String[] args) {
            System.out.println("Hello, World!");
    }    
}
```

<hr>

## <a name="summary">振り返り</a>
- Pythonでは基本的に「ソースファイルを編集して実行する」だけだった。
- これに対し、Javaでは「プロジェクト」というまとまりで開発を進めることになる。複数のプロジェクト群をまとめて置く場所を指定し、その後で新規プロジェクト作成し（もしくは過去に作成したプロジェクトを開き)、その上でソースコードを修正して実行するという流れを意識しよう。
- 注意
  - 「プロジェクト」単位でプログラムを書くため、VSCodeで「過去に書いたプログラム」を改めて操作（編集・実行等）する際には **「プロジェクト・ディレクトリ」を指定して開く** ようにしよう。
  - 例えば今回は「~/tnal/prog2/week1/src/jp/ac/uryukyu/ie/tnal/Week1.java」を作成した。VSCodeを一度閉じた後で改めて操作したい場合には、プロジェクト・ディレクトリ「~/tnal/prog2/week1」を開こう。

<hr>

## <a name="ref">参考文献</a>
- [Java in Visual Studio Code](https://code.visualstudio.com/docs/languages/java)
