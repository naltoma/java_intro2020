# Git + GitHub 演習（演習2しながら）
- 関連資料
  - [Gitによるバージョン管理入門](https://github.com/naltoma/python_intro/blob/master/Git.md)
    - 主な用語: add, commit, push, working directory, staging area, local repository, bare repository
- ＜目次＞
  - <a href="#goal">達成目標</a>
  - <a href="#flow">全体の流れ</a>
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
- [演習2: クラス実装に慣れよう](https://github.com/naltoma/java_intro2020/blob/master/ex/ex2_classcoding.md)の演習2_8途中まで（アクセス制限加える所まで）やり終えた。ここからソースコードをバージョン管理し、ベアリポジトリとしてGitHubを利用する。

<hr>

## <a name="flow">全体の流れ</a>
- [演習2: クラス実装に慣れよう](https://github.com/naltoma/java_intro2020/blob/master/ex/ex2_classcoding.md)の演習2_8途中まで（アクセス制限加える所まで）やり終えた。この時点でのコードを初版としてバージョン管理下におき登録していきたい。今後の修正も動作確認できる都度登録していく。バージョン管理にはGitを使い、ベアリポジトリとしてGitHubを利用する。

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
- [演習2: クラス実装に慣れよう](https://github.com/naltoma/java_intro2020/blob/master/ex/ex2_classcoding.md)の演習2_8途中まで（アクセス制限加える所まで）やり終えた。
  - コード例
    - [Chatbot.java](./src/week4/src_before/Chatbot.java)
    - [Main.java](./src/week4/src_before/Main.java)
- この時点での状況。
  - 作業ディレクトリを ``~/prog2/week4/`` とすると、ファイル配置は以下のようになっているはずだ。作業ディレクトリは適宜読み替えること。

```shell
(base) oct:tnal% find .
.
./README.md
./lib
./src
./src/Main.java
./src/Chatbot.java
./src/App.java
```

<hr>

### <a name="step2">step 2: step 2: バージョン管理対象を決定する。</a>
- この時点でのプロジェクト全体をバージョン管理対象とする。App.javaはVScodeが勝手に作るものであり、今回は不要なので対象から除外する。同様にlibも現時点では未使用のため除外する。
- 決定した管理対象
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
Initialized empty Git repository in /Users/tnal/prog2/week4/.git/
(base) oct:tnal% ls -a
./		.git/		lib/
../		README.md	src/
```

<hr>

### <a name="step4">step 4: 管理対象ファイルを add, commit する。</a>
- README.md, src/{Chatbot.java,Main.java} の3つをadd, commitする。以下ではgit管理対象になっているかどうかを確認するため、add,commit前後で ``git st`` (git statusのエイリアス名による実行) している様子を示している。

```shell
(base) oct:tnal% git st
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)
	README.md
	src/

nothing added to commit but untracked files present (use "git add" to track)
(base) oct:tnal% git add README.md src/{Chatbot.java,Main.java}
(base) oct:tnal% git commit -m "1st commit"
[master (root-commit) 18abe12] 1st commit
 3 files changed, 41 insertions(+)
 create mode 100644 README.md
 create mode 100644 src/Chatbot.java
 create mode 100644 src/Main.java
(base) oct:tnal% git st
On branch master
Untracked files:
  (use "git add <file>..." to include in what will be committed)
	src/App.java

nothing added to commit but untracked files present (use "git add" to track)
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
  - Project name を ``prog2_week4`` とする。ローカルリポジトリを置いたディレクトリ名と違っていて構わない。
  - Project description に ``プログラミング2、クラスからオブジェクト生成する練習`` ぐらいの説明を書いておく。
  - 「public」にする。
  - 「Create project」をクリック。
  - ベアリポジトリが作成されたら、背景青色の Quick setup 欄にて ``SSH`` タブをクリックしよう。これでリポジトリ先が ``git@gitlab:アカウント名/リポジトリ名.git`` になっているはずだ。

<hr>

## ローカルPCでの作業
### <a name="step6">step 6: ベアリポジトリへ push する。</a>
- ローカルリポジトリにはまだどこをベアリポジトリとするかを伝えていないため、push先を把握していない。まずはベアリポジトリを設定し、その後で push しよう。
  - ベアリポジトリを設定するためには ``git remote add origin GitURL`` とする。具体的な中身はGitHubのベアリポジトリページ、下段3行の1行目に書かれている。以下は當間の例。
- 補足
  - ``git remote -v`` は、ベアリポジトリを確認している。1回目の実行では未設定のため何も出力されていない。
  - ``git remote add origin GitURL`` でベアリポジトリを設定。
  - その後もう一度 git remote -v で設定の反映を確認。
  - ``git push -u origin master`` は、初めてリモートのベアリポジトリへ push する際のコマンド。2回目以降は ``git push`` で良い。
  - git push -u origin master を実行した際にエラーがなければ、pushが成功しているはずだ。

```shell
# 以下は https を指定しているが、sshタブで指定されたURLを使おう。

(base) oct:tnal% git remote -v
(base) oct:tnal% git remote add origin https://github.com/naltoma/prog2_week4.git
(base) oct:tnal% git remote -v
origin	https://github.com/naltoma/prog2_week4.git (fetch)
origin	https://github.com/naltoma/prog2_week4.git (push)
(base) oct:tnal% git push -u origin master
Enumerating objects: 9, done.
Counting objects: 100% (9/9), done.
Delta compression using up to 12 threads
Compressing objects: 100% (8/8), done.
Writing objects: 100% (9/9), 1.40 KiB | 1.40 MiB/s, done.
Total 9 (delta 0), reused 0 (delta 0), pack-reused 0
To https://github.com/naltoma/prog2_week4.git
 * [new branch]      master -> master
Branch 'master' set up to track remote branch 'master' from 'origin'.
(base) oct:tnal%
```

<hr>

## GitHubでの作業
### <a name="step7">step 7: ベアリポジトリへの登録状況確認。</a>
- ブラウザでベアリポジトリを開く。
  - ページを再読み込みしよう。この時点で README.md, src/{Chatbot.java,Main.java}, .gitignore の4ファイルが登録されていることを確認しよう。何か登録漏れがあるならこの時点で改めて add, commit, push しなおそう。
- GitHubのベアリポジトリ上には「Getting Started」とか良く分からない文章が書かれているはず。これはREADME.mdを見やすい形で出力している。このように ``README.md`` というファイル名自体は、「あるディレクトリ上にその名前のファイルが存在している場合には、自動で中身を出力する」特別な意味合いを持つ。文字通りREADME（=私を読んで）として使われる名称であり、プロジェクト概要や各フォルダを説明するための文章を書くために使われることが多い。

<hr>

## ローカルPCでの作業
### <a name="step8">step 8: コードの修正。</a>
- [演習2: クラス実装に慣れよう](https://github.com/naltoma/java_intro2020/blob/master/ex/ex2_classcoding.md)の演習2_8の続きをやろう。

<hr>

### <a name="step9">step 9: 修正したコードを add, commit, push する。</a>
- 演習2_8の修正を行い動作確認できたら、修正したファイルを add, commit, push しよう。
- ブラウザ上で変更履歴を眺めてみよう。
- 補足
  - 以下では修正したファイルを add し、「done for ex2_8」というコメントを付けて commit している。その後 push。
  - ``git log -p -1`` は「現バージョンと一つ前のバージョンとの差分（違い）」を確認するためのコマンド。Chatbot.java, Main.javaのどこをどう修正したのかがわかりやすく出力されている。（このコマンドを覚えるのが面倒な人は、GitHubのブラウザで操作するほうが楽でしょう）

```shell
(base) oct:tnal% git add src/{Chatbot.java,Main.java}
(base) oct:tnal% git commit -m "done for ex2_8"
[master 1933a7e] done for ex2_8
 2 files changed, 8 insertions(+), 1 deletion(-)
(base) oct:tnal% git push
Enumerating objects: 9, done.
Counting objects: 100% (9/9), done.
Delta compression using up to 12 threads
Compressing objects: 100% (5/5), done.
Writing objects: 100% (5/5), 499 bytes | 499.00 KiB/s, done.
Total 5 (delta 3), reused 0 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (3/3), completed with 3 local objects.
To https://github.com/naltoma/prog2_week4.git
   2a81a2a..1933a7e  master -> master
(base) oct:tnal% git log -p -1
commit 1933a7eef3e1f71addef47f7985c61b226a7202b (HEAD -> master, origin/master)
Author: Naruaki TOMA <tnal@ie.u-ryukyu.ac.jp>
Date:   Wed Oct 28 15:38:45 2020 +0900

    done for ex2_8

diff --git a/src/Chatbot.java b/src/Chatbot.java
index f9c3b01..01e3d91 100644
--- a/src/Chatbot.java
+++ b/src/Chatbot.java
@@ -11,4 +11,11 @@ public class Chatbot{
     public void greeting(){
         System.out.println("はじめまして" + this.name + "です。よろ！");
     }
+
+    public void setName(String _name){
+        this.name = _name;
+    }
+    public String getName(){
+        return this.name;
+    }
 }
\ No newline at end of file
diff --git a/src/Main.java b/src/Main.java
index 2a58389..8b77fb0 100644
--- a/src/Main.java
+++ b/src/Main.java
@@ -6,7 +6,7 @@ public class Main {
         Chatbot bot2 = new Chatbot("naltoma");
         bot1.greeting();
         bot2.greeting();
-        bot2.name = "ほげ";
+        bot2.setName("ほげ");
         bot2.greeting();
     }

(base) oct:tnal%
```

<hr>

### <a name="step10">step 10: 演習2_9に対するコード修正。</a>
- [演習2: クラス実装に慣れよう](https://github.com/naltoma/java_intro2020/blob/master/ex/ex2_classcoding.md)の演習2_9をやろう。

<hr>

### <a name="step11">step 11: 修正したコードを add, commit, push する。</a>
- 演習2_9の修正を行い動作確認できたら、修正したファイルを add, commit, push しよう。
- ブラウザ上で変更履歴を眺めてみよう。

<hr>

### <a name="option3">おまけ: GitHub向けにpushする都度パスワードを聞かれる場合</a>
- [githubでユーザー名とパスワードを毎回聞かれる問題解消](https://qiita.com/non0311/items/03e3e7a042f70f072286)を参考に ``git remote set-url origin`` を調整しよう。
  - 當間自身はhttpsのままで問題ないが一部（全員？）学生からはパスワードを毎回求められるという話が届いています。

<hr>

## <a name="sources">演習2の回答コード例</a>
- [Chatbot.java](./src/week4/src/Chatbot.java)
- [Main.java](./src/week4/src/Main.java)
