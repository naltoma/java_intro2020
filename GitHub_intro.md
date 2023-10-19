# Git + GitHub 演習
- 関連資料
  - [Gitによるバージョン管理入門](https://github.com/naltoma/python_intro/blob/master/Git.md)
    - 主な用語: add, commit, push, working directory, staging area, local repository, bare repository
- ＜目次＞
  - <a href="#goal">達成目標</a>
  - <a href="#pre">前提</a>
  - <a href="#step0">step 0: GitHubのアカウント作成</a>
  - ローカルPCでの作業
    - <a href="#step1">step 1: 現状</a>
    - <a href="#step2">step 2: step 2: バージョン管理対象を決定する。</a>
    - <a href="#step3">step 3: プロジェクトを保存しているディレクトリをローカルリポジトリにする。</a>
    - <a href="#step4">step 4: 管理対象ファイルを add, commit する。</a>
    - <a href="#option">おまけ：.gitignore を追加しよう。</a>
  - GitHubでの作業
    - <a href="#step5">step 5: ベアリポジトリの準備。</a>
  - ローカルPCでの作業
    - <a href="#step6">step 6: ベアリポジトリへ push する。</a>
  - GitHubでの作業
    - <a href="#step7">step 7: ベアリポジトリへの登録状況確認。</a>
  - ローカルPCでの作業
    - <a href="#step8">step 8: コードの修正。</a>
    - <a href="#step9">step 9: 修正したコードを add, commit, push する。</a>
    - <a href="#step10">step 10: 演習2_9に対するコード修正。</a>
    - <a href="#step11">step 11: 修正したコードを add, commit, push する。</a>
    - <a href="#option3">おまけ: GitHub向けにpushする都度パスワードを聞かれる場合</a>
  - <a href="#sources">演習2の回答コード例</a>

<hr>

## <a name="goal">達成目標</a>
- ソースコードをバージョン管理し、ベアリポジトリとしてGitHubを利用する。

<hr>

## <a name="pre">前提</a>
- Gitを使える状態にあること。良く分からない人は、、、
  - (1) [Gitによるバージョン管理入門](https://github.com/naltoma/python_intro/blob/master/Git.md)の ``0. gitがインストールされてるかの確認``。
  - (2) ターミナル上で ``cat ~/.gitconfig`` を実行。Git設定ファイルにuser name, email, alias が設定（記述）されていることを確認。書かれていない場合、[Gitによるバージョン管理入門](https://github.com/naltoma/python_intro/blob/master/Git.md)の ``1. Git利用前の設定`` を実行。

<hr>

## <a name="step0">step 0: GitHubのアカウント作成</a>
- <a name="github-login">GitHubへのログイン</a>
  - ここでは[Github](https://github.com)の利用を想定している。[学科のgitlabサーバ](https://gitlab.ie.u-ryukyu.ac.jp/gitlab/users/sign_in)を利用したいのであればそちらでも構わないが、2020年10月時点での動作は不安定の可能性あり。
  - [Github](https://github.com)へアクセス。
    - Sign Up をクリック。
    - username, email address, password を入力して「Create an account」。
      - usernameは自由で構わないが、少なくともemail addressは学科のアドレスを指定すること。（そうしないと誰のリポジトリか判別できない）
      - Unlimited public repositories for free. を選択。
      - Tailor your experience は説明文読んで回答。
- <a name="ssh-key">ssh-keyの登録</a>
  - ブラウザ内、右上のアイコンをクリックして「Settings」を選択。
    - 左メニューの「SSH and GPG keys」を選択。
      - 「New SSH key」を選択。
      - ターミナルで、次のコマンドを実行。メールアドレスは自分自身のものに変更すること。
        - ``cd ~/.ssh``
        - ``ssh-keygen -t rsa -b 4096 -C "your_email@example.com" -f github``
          - パスフレーズを聞かれるが、何も入力せずにEnterを2回。
          - 想定通りに実行できていれば、次の2つのファイルが生成されているはず。
            - ~/.ssh/github # 秘密鍵
            - ~/.ssh/github.pub # 公開鍵（外部サービスへ登録するための鍵）
        - 生成した鍵を、以下のコマンドでキャッシュにコピーする。公開鍵の方である点に注意。
          - ``pbcopy < ~/.ssh/github.pub``
      - ターミナルで、``vim ~/.ssh/config`` を実行して、設定ファイルを編集する。

```
Host github.com
  IdentityFile ~/.ssh/github
```

- ssh-keyの登録続き
  - ブラウザで、SSH Keys の登録ページに戻る。
    - Title欄に「プログラミング2での登録」とか、どの鍵を登録したのか分かるようにコメントを書こう。自分がわかるコメントであれば何でもOK。
    - 先ほどコピーした公開鍵を、「Key」欄に貼り付ける。
    - 「Add SSH key」をクリック。
    - パスワードを要求されるので、Githubへログインするためのパスワードを入力。

<hr>

## ローカルPCでの作業
### <a name="step1">step 1: 現状</a>
ここではバージョン管理する対象として、VSCodeでプロジェクトを作成した際に自動で用意される Readme.md, src/App.java の2つを利用することにしよう。

- この時点での状況。
  - 作業ディレクトリを ``~/prog2/week4-20223/`` とすると、ファイル配置は以下のようになっているはずだ。作業ディレクトリは適宜読み替えること。

```shell
(base) oct2021:tnal% pwd
/Users/tnal/prog2/week4-2023
(base) oct2021:tnal% tree
.
├── README.md
├── lib
└── src
    └── App.java

3 directories, 2 files
```

なお、上記では表示されていないが ``.vscode`` という VSCode 向けの設定を記述するフォルダも生成されている。カスタマイズしたり、共同開発者間で共有したい場合にはこのフォルダもバージョン管理対象としたほうが良い。そうでない場合は自動生成される標準設定のみで十分であることが多いので、バージョン管理からは除外しても良い。今回は .vscode/settings.json も管理対象とすることにしよう。

<hr>

### <a name="step2">step 2: step 2: バージョン管理対象を決定する。</a>
- この時点でのプロジェクト全体をバージョン管理対象とする。
- 決定した管理対象
  - .vscode/settings.json
  - README.md
  - src/Main.java
  - src/Chatbot.java

<hr>

### <a name="step3">step 3: プロジェクトを保存しているディレクトリをローカルリポジトリにする。</a>
- 作業ディレクトリ上で ``git init`` しよう。特に問題なければGit管理用の初期化を実行できるはずだ。
- 確認方法
  - 実行前の前の時点では ``ls -a`` をしても特にgit管理用ファイルはみあたらない。git initで初期化後にもう一度 ls -a すると、``.git`` というgitが使うための管理用ファイルを保存しているディレクトリが作成されるはずだ。
- 確認する様子

```shell
(base) oct:tnal% ls -a
./		../		README.md	lib/		src/
(base) oct:tnal% git init
Initialized empty Git repository in /Users/tnal/prog2/week4-2023/.git/
(base) oct:tnal% ls -a
./		.git/		README.md	src/
../		.vscode/	lib/
```

<hr>

### <a name="step4">step 4: 管理対象ファイルを add, commit する。</a>
- README.md, src/App.java の2つをadd, commitする。以下ではgit管理対象になっているかどうかを確認するため、add,commit前後で ``git st`` (git statusのエイリアス名による実行) している様子を示している。

```shell
(base) oct:tnal% git st
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)
	.vscode/
	README.md
	src/

nothing added to commit but untracked files present (use "git add" to track)
(base) oct:tnal% git add .vscode README.md src/App.java
(base) oct:tnal% git commit -m "1st commit"
[master (root-commit) 18abe12] 1st commit
 3 files changed, 41 insertions(+)
 create mode 100644 README.md
 create mode 100644 src/Chatbot.java
 create mode 100644 src/Main.java
(base) oct:tnal% git st
On branch main
nothing to commit, working tree clean
(base) oct:tnal%
```

<hr>

### <a name="option">おまけ：.gitignore を追加しよう。</a>
- ``.gitignore`` とは、git対象外を明示的にしておくための設定ファイルのこと。例えば macOS では勝手に ``.DS_Store`` が生成されることが多い。このファイルは Finder が自動生成するファイルであるためバージョン管理する必要がない。これを除外するため、``echo ".DS_Store" >> .gitignore`` して、これもadd, commit しよう。
  - 参考: [.DS_Storeとは](https://discussionsjapan.apple.com/thread/10058768)
- よくある使い方としては、Javaの中間ファイル（``*.class``）や、Pythonでのキャッシュファイル（``__pycache__``）も自動生成されるため、.gitignore に記述することで対象外として明示することが多い。

```shell
(base) oct:tnal% ls -a
./		.git/		lib/
../		README.md	src/
(base) oct:tnal% echo ".DS_Store" >> .gitignore
(base) oct:tnal% ls -a
./		.git/		README.md	src/
../		.gitignore	lib/
(base) oct:tnal% cat .gitignore
.DS_Store
(base) oct:tnal% git add .gitignore
(base) oct:tnal% git commit -m "add ignore setting"
[master 2a81a2a] add ignore setting
 1 file changed, 1 insertion(+)
 create mode 100644 .gitignore
(base) oct:tnal%
```

<hr>

## GitHubでの作業
### <a name="step5">step 5: ベアリポジトリの準備。</a>
- ブラウザでGitHubを開く。
- 右上アイコン隣の「+」をクリックし、「New repository」を選択。
  - Repository name を ``prog2_week4`` とする。ローカルリポジトリを置いたディレクトリ名と違っていて構わない。
  - Project description に ``プログラミング2、GitHubにpushする練習`` ぐらいの説明を書いておく。
  - 「public」にする。
  - 「Create project」をクリック。
  - ベアリポジトリが作成されたら、背景青色の Quick setup 欄にて ``SSH`` タブをクリックしよう。これでリポジトリ先が ``git@github.com:アカウント名/リポジトリ名.git`` になっているはずだ。

<hr>

## ローカルPCでの作業
### <a name="step6">step 6: ベアリポジトリへ push する。</a>
- 基本的な方針
  - 既に Git 管理しているファイルがある場合と、まだ何もない場合（これからgit initする場合）とで手続きが異なる。今回は既に git init している（Git管理している）ものを GitHub 上のリポジトリに push したい。この場合にはリポジトリページ上の「…or push an existing repository from the command line」を参考にコマンドを実行することになる。
- ローカルリポジトリにはまだどこをベアリポジトリとするかを伝えていないため、push先を把握していない。まずはベアリポジトリを設定し、その後で push しよう。
  - ベアリポジトリを設定するためには ``git remote add origin ベアリポジトリ名`` とする。具体的な中身はGitHubのベアリポジトリページ、下段3行の1行目に書かれている。以下は當間の例。
- 補足
  - ``git remote -v`` は、ベアリポジトリを確認している。1回目の実行では未設定のため何も出力されていない。
  - ``git remote add origin ベアリポジトリ名`` でベアリポジトリを設定。
  - その後もう一度 git remote -v で設定の反映を確認。
  - ``git push -u origin main`` は、初めてリモートのベアリポジトリへ push する際のコマンド。2回目以降は ``git push`` で良い。
  - git push -u origin master を実行した際にエラーがなければ、pushが成功しているはずだ。

```shell
# 以下は https を指定しているが、sshタブで指定されたURLを使おう。

(base) oct:tnal% git remote -v
(base) oct:tnal% git remote add origin git@github.com:naltoma/prog2_week4_2023.git
(base) oct:tnal% git remote -v
origin	git@github.com:naltoma/prog2_week4_2023.git (fetch)
origin	git@github.com:naltoma/prog2_week4_2023.git (push)
(base) oct:tnal% git branch -M main
(base) oct:tnal% git push -u origin main
Enumerating objects: 10, done.
Counting objects: 100% (10/10), done.
Delta compression using up to 8 threads
Compressing objects: 100% (7/7), done.
Writing objects: 100% (10/10), 1.24 KiB | 633.00 KiB/s, done.
Total 10 (delta 1), reused 0 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (1/1), done.
To github.com:naltoma/prog2_week4_2023.git
 * [new branch]      main -> main
branch 'main' set up to track 'origin/main'.
(base) oct:tnal%
```

<hr>

## GitHubでの作業
### <a name="step7">step 7: ベアリポジトリへの登録状況確認。</a>
- ブラウザでベアリポジトリを開く。
  - ページを再読み込みしよう。この時点で README.md, src/App.java, .gitignore の3ファイルが登録されていることを確認しよう。何か登録漏れがあるならこの時点で改めて add, commit, push しなおそう。
- GitHubのベアリポジトリ上には「Getting Started」とか良く分からない文章が書かれているはず。これはREADME.mdを見やすい形で出力している。このように ``README.md`` というファイル名自体は、「あるディレクトリ上にその名前のファイルが存在している場合には、自動で中身を出力する」特別な意味合いを持つ。文字通りREADME（=私を読んで）として使われる名称であり、プロジェクト概要や各フォルダを説明するための文章を書くために使われることが多い。

<hr>

## ローカルPCでの作業
### <a name="step8">step 8: コードの修正。</a>
- (1) App.java の修正。
  - 何でも良いので動作することを確認すること。
- (2) .gitignore の修正。
  - App.javaを実行すると bin ディレクトリが作成され、そこにクラスファイル（バイトコード）が保存される。これはコンパイル時に自動生成されるので、特別な用途がなければバージョン管理しなくて良い。このため .gitignore を開き、 ``bin`` という行を追加しよう。これで bin フォルダ以下をバージョン管理対象外とすることができる。
- (3) README.md の修正。
  - デフォルトでは VSCode で生成された無意味なテンプレート文になっている。全て削除した上で以下のように記述してみよう。``# ``は見出し行。``- `` は箇条書き。より詳細なMarkdown記法についてはググってみよう。
```markdown
# GitHubをベアリポジトリとして使う練習
- 1st commit: プロジェクト生成した際に自動生成されたコードをそのまま登録。
- 2nd commit: App.javaを少し改変し、README.mdを修正。
```

<hr>

### <a name="step9">step 9: 修正したコードを add, commit, push する。</a>
- step 8の修正を行い動作確認できたら、修正したファイル（App.java, .gitignore, README.md）を add, commit, push しよう。
- ブラウザ上で変更履歴を眺めてみよう。
- 補足
  - 以下では修正したファイルを add し、「modification test」というコメントを付けて commit している。その後 push。
  - 一度目のpushと異なり、2回目以降は ``git push`` するだけで良い。
  - ``git log -p -1`` は「現バージョンと一つ前のバージョンとの差分（違い）」を確認するためのコマンド。どこをどう修正したのかがわかりやすく出力されている。（このコマンドを覚えるのが面倒な人は、GitHubのブラウザで操作するほうが楽でしょう）

```shell
(base) oct:tnal% git add src/App.java .gitignore README.md
(base) oct:tnal% git commit -m "modification test"
[main fce0c89] modification test
 3 files changed, 5 insertions(+), 19 deletions(-)
(base) oct:tnal% git push
Enumerating objects: 11, done.
Counting objects: 100% (11/11), done.
Delta compression using up to 8 threads
Compressing objects: 100% (4/4), done.
Writing objects: 100% (6/6), 702 bytes | 702.00 KiB/s, done.
Total 6 (delta 1), reused 0 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (1/1), completed with 1 local object.
To github.com:naltoma/prog2_week4_2023.git
   d548487..fce0c89  main -> main
(base) oct:tnal% git log -p -1
commit fce0c89a6bb93d8cb676b9a721edda2e937e4c16 (HEAD -> main, origin/main)
Author: Naruaki TOMA <tnal@ie.u-ryukyu.ac.jp>
Date:   Thu Oct 19 13:20:03 2023 +0900

    modification test

diff --git a/.gitignore b/.gitignore
index e43b0f9..3a5ba85 100644
--- a/.gitignore
+++ b/.gitignore
@@ -1 +1,2 @@
 .DS_Store
+bin
diff --git a/README.md b/README.md
index 7c03a53..2782e6b 100644
--- a/README.md
+++ b/README.md
@@ -1,18 +1,3 @@
-## Getting Started
-
-Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.
-
-## Folder Structure
-
-The workspace contains two folders by default, where:
-
-- `src`: the folder to maintain sources
-- `lib`: the folder to maintain dependencies
-
-Meanwhile, the compiled output files will be generated in the `bin` folder by default.
-
-> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.
-
-## Dependency Management
-
-The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
+# GitHubをベアリポジトリとして使う練習
+- 1st commit: プロジェクト生成した際に自動生成されたコードをそのまま登録。
+- 2nd commit: App.javaを少し改変し、README.mdを修正。
diff --git a/src/App.java b/src/App.java
index 0a839f9..67261e7 100644
--- a/src/App.java
+++ b/src/App.java
@@ -1,5 +1,5 @@
 public class App {
     public static void main(String[] args) throws Exception {
-        System.out.println("Hello, World!");
+        System.out.println("ルートビア飲みたい、、");
     }
 }
(base) oct:tnal%
```

<hr>

### <a name="step10">step 10: 演習2_9に対するコード修正。</a>
- 自由編集しよう。既存ファイルを編集しても良いし、新規ファイルを追加しても良い。

<hr>

### <a name="step11">step 11: 修正したコードを add, commit, push する。</a>
- step 10 の修正を行い動作確認できたら、修正したファイルを add, commit, push しよう。
- ブラウザ上で変更履歴を眺めてみよう。

<hr>

### <a name="option3">おまけ: GitHub向けにpushする都度パスワードを聞かれる場合</a>
- [githubでユーザー名とパスワードを毎回聞かれる問題解消](https://qiita.com/non0311/items/03e3e7a042f70f072286)を参考に ``git remote set-url origin`` を調整しよう。
  - 當間自身はhttpsのままで問題ないが一部（全員？）学生からはパスワードを毎回求められるという話が届いています。

<hr>

## <a name="sources">演習2の回答コード例（おまけ）</a>
- [Chatbot.java](./src/week4/src/Chatbot.java)
- [Main.java](./src/week4/src/Main.java)
