# 演習3: Git, GitHubに慣れよう
- 参考
  - [Git Book](https://git-scm.com/book/ja/v2)　＊公式本
  - [Grogate](https://prog-8.com/languages/git)　＊演習
  - [サル先生のGit入門](http://www.backlog.jp/git-guide/)
  - [An Intro to Git and GitHub for Beginners (Tutorial)](https://product.hubspot.com/blog/git-and-github-tutorial-for-beginners)
- ＜目次＞
  - <a href="#goal">達成目標</a>
  - <a href="#ex3_1">演習3_1: GitHubにペアプロ用リポジトリを用意しよう</a>
  - <a href="#ex3_2">演習3_2: Gradleテンプレートを用意。</a>
  - <a href="#ex3_3">演習3_3: branchを切って作業してみよう1</a>
  - <a href="#ex3_4">演習3_4: branchを切って作業してみよう2</a>
  - <a href="#ex3_5">演習3_5: コードを整理してみよう</a>
  - <a href="#ex3_6">演習3_6: マージしてみよう</a>

<hr>

## <a name="goal">達成目標</a>
- Gitによるバージョン管理と、GitHubを通したリモートリポジトリの利用に慣れよう。
- モブプロ目標
  - まず作業者（ドライバー）を決めよう。ドライバーは画面共有して作業しよう。
  - ドライバー以外はナビゲーターとして「今何をするか」を相談しあい、伝えよう。
  - 考えてることを説明できるようになろう。
  - 分からない時には作業を止めて質問できるようになろう。
  - **branchが出てくる演習からは、「今どのブランチで作業しているか」を常に意識しよう。**

<hr>

## <a name="ex3_1">演習3_1: GitHubにペアプロ用リポジトリを用意しよう</a>
- 誰か代表一人決め、[GitHub](https://github.com/)にログインし、ペアプロ用リポジトリを新規作成しよう。
  - リポジトリ名: prog2-ex3
  - publicにすること。
  - Settingsをクリックし、「Collaborators」をクリック。
  - パスワードを要求されるので、入力。
  - Add collaboratorの欄に、パートナーのGitHubのusernameを入力し、Add collaboratorをクリック。
    - 協力者全員分の username を入力しよう。
    - 正しく入力できていれば、「xxx has invited you to collaborate on the yyy/prog2-ex3 repository」といったタイトルのメールが、協力者に届いているはず。
      - 協力者は、該当メールを開き、「View invitation」をクリック。
        - 続けて、「Accept invitaion」をクリック。
        - これで、グループメンバ全員がcommit&pushできる、共用リポジトリを準備できたはず。
- 報告内容
  - 共用リポジトリの作業用URLを報告すること。

<hr>

## <a name="ex3_2">演習3_2: Gradleテンプレートを用意。</a>
- **演習3_1の代表者が以下の作業をすること。**
  - gradleで初期化したディレクトリを用意する。パッケージは指定してもしなくても良い。
  - VSCodeを起動し、gradleとvscodeの初期設定をする。
  - App.javaを VSCodeとgradle（run, test）から実行できることを確認する。
  - 確認できたら、README.mdを編集して ``- 初期動作を確認終了。`` という1行だけを書き、保存する。
  - 用意したベアリポジトリに、README.mdとApp.javaの動作確認に必要なファイル一式をpushする。
  - push漏れが無いか念の為に確認する。以下、確認手順。
    - (a) Git URL をコピー。
    - (b) 今回のプロジェクトとダブらないようにcloneを作成。例えば ``~/prog2/ex3/`` としてリポジトリを作成しているなら、それとは被らない名前として ex3-temp でクローンするなら、``git clone URL ex3-temp`` と実行しよう。
    - (c) 用意したクローンに対し、VSCodeとgradleで実行できることを確認する。この時点で実行できないなら何かを push し忘れていることになる。追加すべきファイルは[gradle演習のstep8](https://github.com/naltoma/java_intro2020/blob/master/Gradle.md#step8)を参考にしよう。
- 報告内容
  - VSCode, gradle両方の実行結果を報告すること。

<hr>

## <a name="ex3_3">演習3_3: branchを切って作業してみよう1</a>
- 作業前の用語説明
  - ブランチ・枝（branch）
    - [3.1 Git のブランチ機能 - ブランチとは](https://git-scm.com/book/ja/v2/Git-のブランチ機能-ブランチとは)
    - グループ開発をしていると、知らぬ間に協力者によるpushが行われており、自身のローカルリポジトリとは整合が取れていない状況になることがある。これを避けるための工夫として branch と呼ばれる機能が用意されている。branchとは1つのリポジトリ内でオリジナルへ影響を与えることなく作業を続けるための機能である。
    - 例えばグループ開発しているソフトがバージョン1.0だとしよう。これをベースにAさんはクラスAを作成し、BさんはクラスBを作成し、、、といったように個々人で異なるコードを書いていく状況を想像すると、それぞれのファイルがブッキングしない範疇であれば全員が同じリポジトリ内で作業してても不都合は起きない。しかし「クラスAを開発している最中にクラスBの一部の機能が必要だから仮実装しておく」みたいな状況がよく起きる。こうなると誰がどのファイルを編集しているのが正しいのかも良く分からなくなる。このような状況を避けるためにブランチを利用する。ブランチを使うと、AさんはAさんが自由に作業できるAさんのブランチ内で作業をし、BさんはAさんが自由に作業できるAさんのブランチ内で作業をし、各々がそれぞれ仕上がった段階で統合して確認するという手順を踏むということができるようになる。
  - 枝を切る（branching, create branch, cut branch）
    - オリジナルから分岐するという視点では「枝を生やす（create branch）」という呼び方もするが、より本質的には「オリジナルに影響を与えない」という側面にあるため「枝を切る（branching, cut branch）」と呼ぶこともある。枝を切るといっても伐採するのではなく枝を生やす方だと考えよう。
  - ブランチ名（branch name）
    - 枝を切る祭には枝の名前（ブランチ名）が必要である。枝名は他と重複せず、協力者間で区別できるものなら何でも良い。
    - なお、gitリポジトリとして初期化した時点でメインとなるリポジトリは「main」という名前が割り振られている（gitのバージョンが古いとmasterになっているかも）。これもブランチなので新規にブランチを作成する際にはこの名前を避ける必要がある。
- **ドライバーを変更して以下の作業をしよう。**
  - 演習3_2で代表者が用意したGit URLからクローンを作成する。
  - VSCode, gradleで App.java を動作確認する。
    - この時点で動作しないなら演習3_2に戻って代表者のpush漏れがないか確認しよう。
  - ブランチを切る。
    - ブランチ名を「自身のアカウント名」としよう。e205700ならe205700。
    - ``git checkout -b ブランチ名``
    - 上記コマンドは「今の状態に対してブランチを作成し、作業領域をそこに切り替える」という意味。
  - 今いるブランチを確認する。
    - ``git branch`` を実行しよう。main と今作成したブランチの2つが出力され、作成した方に ``*`` が付いているならOK。そうでないならブランチを作成できていないか、そこに移動できていない。もう一度ブランチ作成をやり直そう。
  - 以下のクラスを実装しよう。
    - クラス名: Dice
      - フィールド
        - private int value;
      - コンストラクタ
        - 引数: なし
        - 処理内容: this.valueを後述する play() により設定。
      - メソッド
        - ``int play()``
          - 引数: なし
          - 処理内容: ランダムに1〜6の値を算出し、返す。
          - 戻り値の型: int
        - ``int getValue()``
          - 引数: なし
          - 処理内容: フィールドvalueを返す。
          - 戻り値の型: int
    - クラス名: Main
      - mainメソッド: 下記内容をコピペ。
  - 実装し終えたら、README.md に ``- Diceクラスを追加。`` を追加。

```java
public static void main(String[] args){
    Dice dice = new Dice();
    System.out.println(dice.getValue());
}
```

- 動作確認（PC上での確認）
  - Mainクラスが正しく動作するなら1〜6の範囲で値一つが出力されるはずだ。
  - 確認できたら Dice.java, Main.java, README.md を git add, commit までやる。
    - commit messageは "add Dice class"
  - 試しにpushしてみよう。次のようなエラーが返ってくるはずだ。以下はhogeブランチで作業しているときの例である。このブランチがupstream branch、つまりpush先のGitHubリポジトリにはない初めてのpushであることを報告している。初めてのpushの場合にはpush先を明示する必要がある。ここではアドバイスされたとおり ``git push --set-upstream origin hoge`` と実行しよう。これでpush先にブランチを作成し、そこにpushすることができる。2回目以降はgit pushするだけで良い。

```
fatal: The current branch hoge has no upstream branch.
To push the current branch and set the remote as upstream, use

    git push --set-upstream origin hoge
```

- 動作確認（GitHub上での確認）
  - リポジトリページを再読み込みしよう。この時点ではDice.java, Main.javaは見当たらないはずだ。
  - ``branches`` が2になっていることを確認しよう。なっていない場合には push が失敗している。
  - branchesをクリックするとブランチ一覧が表示される。そこから今回作成したブランチ名をクリックしよう。そこにDice.java, Main.javaが追加されていることを確認できるはずだ。mainブランチには影響を与えていないことを確認できた。

<hr>

## <a name="ex3_4">演習3_4: branchを切って作業してみよう2</a>
- **ドライバーを変更して以下の作業をしよう。**
- 事前確認
  - 演習3_2で代表者が用意したGit URLからクローンを作成する。
  - VSCode, gradleで App.java を動作確認する。
    - この時点で動作しないなら演習3_2に戻って代表者のpush漏れがないか確認しよう。
  - 演習3_3で協力者が作成したブランチに移動する。
    - ブランチ名をhogeだとすると、``git checkout hoge`` で移動できる。
    - どのようなブランチがあるかは ``git branch -a`` で確認できる。
    - VSCode, gradleで Main.java を動作確認する。
- 編集方針
  - ブランチhogeをベースにしつつ、新しい自分用のブランチfugaを作成しそこで新たな実装を行う。
    - hogeは演習3_3で作成したブランチ。
    - fugaは今回新しく作成するブランチ。それぞれ作業者のアカウント名に読み替えよう。
- 編集作業
  - hogeをベースにブランチを切る。
  - 念の為に新規ブランチに移動したことを確認する。
  - 以下のクラスを実装しよう。
    - クラス名: ExDice extends Dice
      - フィールド
        - private int maxValue;
      - コンストラクタ
        - 引数: int _maxValue
        - 処理内容: this.maxValueを引数で設定し、後述するplay()により value を設定。
      - メソッド
        - ``@override int play()``
          - 引数: なし
          - 処理内容: ランダムに1〜maxValueの値を算出し、返す。
          - 戻り値の型: int
    - クラス名: Main
      - mainメソッド: 下記内容をコピペ。
  - 実装し終えたら、README.md に ``- ExDiceクラスを追加。`` を追加。

```java
public static void main(String[] args){
    Dice dice = new Dice();
    System.out.println(dice.getValue());
    ExDice exdice = new ExDice(100);
    System.out.println(exdice.getValue());
}
```

- 動作確認（PC上での確認）
  - Mainクラスが正しく動作するならdiceオブジェクトについては1〜6の乱数、exdiceオブジェクトについては1〜100の乱数が一つ出力されるはずだ。
  - 確認できたら ExDice.java, Main.java, README.md をpushまでやる。
    - commit messageは "add ExDice class"
- 動作確認（GitHub上での確認）
  - pushしたリポジトリ上にブランチが3つあり、今回作成したブランチ上に ExDice.java と、再編集したMain.javaがあることを確認しよう。

<hr>

## <a name="ex3_5">演習3_5: コードを整理してみよう</a>
- ここまでの振り返り
  - 演習3_2: 代表者がテンプレートを用意した。
  - 演習3_3: 協力者1がDiceクラス実装した。
  - 演習3_4: 協力者2がそれをベースにExDiceクラスを実装した。
  - ExDiceはmaxValueを設定することで任意の乱数を返すクラス。これで8面ダイスとか100面ダイスとかをオブジェクトとして用意することができる。機能自体は良いが、スーパークラスのDiceを継承したクラスとして妥当だろうか？　継承関係ならば is-a 関係として説明できるはずだが、今回の「ExDiceはDiceの一種」になっているだろうか？　そうはなっておらず、あくまでも Dice.value やアクセサメソッドを使い回したいために継承している。これはよろしくない。（理由は教科書や授業資料を振り返ろう）
- 新しい編集方針
  - Diceクラス自体を編集し直すことで maxValue を設定できるようにする。継承は使わない。ExDiceクラスは不要なので削除。
- **ドライバーを変更して以下の作業をしよう。**
- 事前確認
  - 演習3_2で代表者が用意したGit URLからクローンを作成する。
  - VSCode, gradleで App.java を動作確認する。
    - この時点で動作しないなら演習3_2に戻って代表者のpush漏れがないか確認しよう。
  - 演習3_3で協力者が作成したブランチに移動する。
    - VSCode, gradleで Main.java を動作確認する。
  - 演習3_4で協力者が作成したブランチに移動する。
    - VSCode, gradleで Main.java を動作確認する。
- 編集作業
  - fugaをベースにブランチを切る。
  - 念の為に新規ブランチに移動したことを確認する。
  - 以下のクラスを実装しよう。
    - クラス名: Dice
      - フィールド
        - private int maxValue;
        - private int value;
      - コンストラクタ
        - 引数: int _maxValue
        - 処理内容: this.maxValueを引数で設定し、後述するplay()により this.value を設定。
      - メソッド
        - ``int play()``
          - 引数: なし
          - 処理内容: ランダムに1〜maxValueの値を算出し、返す。
          - 戻り値の型: int
    - クラス名: Main
      - mainメソッド: 下記内容をコピペ。
  - 実装し終えたら、README.md に ``- Diceクラスを追加。`` を追加。

```java
public static void main(String[] args){
  Dice dice1 = new Dice(6);
  System.out.println(dice1.getValue());
  Dice dice2 = new Dice(100);
  System.out.println(dice2.getValue());
}
```

- 動作確認（PC上での確認）
  - Mainクラスが正しく動作するならdice1オブジェクトについては1〜6の乱数、dice2オブジェクトについては1〜100の乱数が一つ出力されるはずだ。
  - 確認できたら Dice.java, Main.java, README.md をpushまでやる。
    - commit messageは "fix Dice class"
- 動作確認（GitHub上での確認）
  - pushしたリポジトリ上にブランチが3つあり、今回作成したブランチ上に Dice.java と、再編集したMain.javaがあることを確認しよう。

<hr>

## <a name="ex3_6">演習3_6: マージしてみよう</a>
- ここまでの振り返り
  - 演習3_2: 代表者がテンプレートを用意した。
  - 演習3_3: 協力者1がDiceクラス実装した。
  - 演習3_4: 協力者2がそれをベースにExDiceクラスを実装した。
  - 演習3_5: 協力者3がこれらをベースにDiceクラスを実装し直した。
  - 現時点ではグループ開発をしているため複数の実装が混在しています。どの実装が良いのか前回開発者らが話し合ったところ、演習
  3_5の実装が最も良いと判断されたとします。
- 編集方針
  - 演習3_5をmainブランチに統合する。演習3_3, 3_4のブランチは削除する。
  - 統合方法はいくつか種類があるが、ここでは merge を使う。merge では「指定ブランチの編集をmainに取り込む（上書きする）」形で修正する。
- **ドライバーを変更して以下の作業をしよう。**
- 事前確認
  - 演習3_2で代表者が用意したGit URLからクローンを作成する。
  - VSCode, gradleで App.java を動作確認する。
    - この時点で動作しないなら演習3_2に戻って代表者のpush漏れがないか確認しよう。
  - 演習3_5で協力者が作成したブランチに移動する。
    - VSCode, gradleで Main.java を動作確認する。
    - ソースコードも演習3_5のものであることを確認しよう。（実行結果だけだと演習3_3と同じなので判断できない）
- 編集作業
  - mainブランチに移動する。
  - 念の為にmainブランチに移動したことを確認する。
  - 演習3_5のブランチ名を確認する。ここでは piyo だとしよう。
  - mainブランチ上で以下のコマンドを実行し、piyoブランチのコードをmainにマージする。
    - ``git merge piyo``
  - マージが終わると、piyoブランチにて実行した add, commit をした状態になる。まだpushはされていない。
  - 念の為変更のあったソースコードの中身を確認し、VScode, gradleでの動作も確認しよう。
  - 問題なければ ``git push``。これでmainブランチとしてpushされる。
  - 演習3_3, 3_4のブランチを削除しよう。削除する際には ``git branch -d ブランチ名`` を実行する。

<hr>

以上のことを代表者を変えて何度か実行してみよう。
