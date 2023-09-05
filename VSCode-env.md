# VSCodeの環境構築

- ＜目次＞
  - <a href="#step0">想定環境</a>
  - <a href="#step1">Step 1. VSCodeを最新版に更新する。</a>
  - <a href="#step2">Step 2. VSCodeからOpenJDKのインストール。</a>
  - <a href="#step3">Step 3. VSCodeの設定（OpenJDKのための設定）。</a>

---
## <a name="step0">想定環境</a>
- 動作確認日: 2023/8/29
- MacBook Air (M1, 2020)
- macOS Ventura 13.5
- Apple Silicon版VSCodeインストール済み。
- JDK未インストール。
- 備考
  - 再履修生等、既にJDKを用いた開発できる環境構築済みならそれを利用して構わない。ただし授業ページでは VSCode, OpenJDK 16, Gradle, JUnit 5, Git, GitHub 前提で書いてあるため、適宜手順等を読み替えて対応すること。

---
## <a name="step1">Step 1. VSCodeを最新版に更新する。</a>
VSCodeを起動し、``Code``メニューから``更新を確認``し、最新版に更新しよう。

2023年8月29日時点での最新版は 1.81.1。

---
## <a name="step2">Step 2. VSCodeからOpenJDKのインストール。</a>
- Extension Pack for Javaのインストール
  - 左下の歯車アイコンクリックして、「拡張機能(Extension)」を選択。
  - 検索窓に「java」と入力して「Extension Pack for Java」を探す。見つかったら選択して、インストール。
  - インストール終わったらVSCodeを再起動。
- JDKのインストール
  - ``⌘ + Shift + P`` を押してコマンドパレットを開き、``java``と入力。そこから ``Java: Install New JDK`` を選ぼう。選んだらその時点での最新安定版を選択し、dmgファイルをダウンロード。ファイルサイズは約200MBあるのでダウンロード完了するまで待とう。
    - 2023年8月29日時点では 17.0.8+7 で、ダウンロードされるファイル名は ``OpenJDK17U-jdk_aarch64_mac_hotspot_17.0.8_7.dmg`` でした。
  - 上記dmgファイルをダブルクリックし、インストールする。指定手順通りにインストールをしよう。特に変更する箇所はなく、デフォルトのままで良い。
  - 正常終了すると ``Eclipse Temurin 17.0.8+7 to /Library/Java/JavaVirtualMachines/temurin-17.jdk`` にインストールしたという画面が出るはず。これでJDKインストールが終了。インストーラのウィンドウを閉じよう。
- JDK動作確認
  - インストールを終えたら、ターミナルを開いて ``java -version`` と実行してみよう。以下のようにバージョンを確認することができればOK。
```
oct:tnal% java -version
openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment Temurin-17.0.8+7 (build 17.0.8+7)
OpenJDK 64-Bit Server VM Temurin-17.0.8+7 (build 17.0.8+7, mixed mode)
```

---
## <a name="step3">Step 3. VSCodeの動作確認（OpenJDKをVSCodeから使ってみる）。</a>

OpenJDKをインストールし終えたら、VSCodeを再起動しよう。

次に、動作確認をしていこう。授業では ``~/prog2/`` をプログラミング2用のディレクトリとして利用するものとする。また、今回の動作確認では ``~/prog2/sample/`` というプロジェクトを用意し、その中にソースファイルを用意して実行するものとする。

**【注意点】**
- Pythonと異なり、Javaでは「プロジェクト」という単位で開発を行う。これは「課題1用のプロジェクト」「演習3用のプロジェクト」といったように、専用のプロジェクトを用意して作業することを意味している。プロジェクトごとに異なるディレクトリを用意することになるため、これらのプロジェクトがPC内のあちこちに散らばってしまうと不便だ。授業ではそれをまとめるための「プログラミング2用のディレクトリ」として ``~/prog2/`` を用意し、その中にプロジェクトごとのディレクトリを用意することとする。

それでは、作業ディレクトリを用意(step3.1)し、その中に新規プロジェクトを作成(step3.2)して、動作確認(step3.3)してみよう。

- step 3.1: 作業ディレクトリの作成。
  - ターミナルを開いて ``~/prog2/`` を作成して下さい。
  - この作業は、今回限りです。次回以降（例えば課題や演習をやるとき）にはこのディレクトリはすでに存在してるため、2回目以降はやる必要はありません。
- step 3.2: 新規プロジェクトの作成。
  - ここでは ``sample`` という名前のプロジェクトを作成しよう。以下の作業は、新規プロジェクトを作成する都度繰り返し行うことになります。
  - (1) vscodeを立ち上げる。
  - (2) ``⌘ + Shift + P`` を押してコマンドパレットを開き、``java``と入力。そこから``Java: Create Java Project`` を選ぼう。
  - (3) プロジェクトの種類として、``No build tools`` を選ぼう。
  - (4) プロジェクトを保存する場所として ``~/prog2/`` を選ぼう。ここは誤解しやすいですが、プロジェクト名をここで選択しないようにしてください。
  - (5) プロジェクト名として ``sample`` を入力し、Enterキーを押す。
  - (6) サンプルコードの確認。
    - 自動的に「Hello, World!」と出力するだけのサンプルプログラムが作成されます。このときのファイル構成は以下のとおりです。この中の ``src/App.java`` を開いてください。
```
oct:tnal% pwd
/Users/tnal/prog2/sample
oct:tnal% tree
.
├── README.md
├── bin
│   └── App.class
├── lib
└── src
    └── App.java

3 directories, 3 files
```
  - (7) 動作確認。
    - Python同様、右上の ▷ ボタンを押して実行してみてください。ターミナル画面が現れ、そこに ``Hello, World!`` と出力されるならOKです。

---

## FAQ
- ターミナルで ``java --version`` と実行してもバージョンを確認できない。
  - case 1: JAVA_HOME の設定が必要かもしれません。今回のようにJDK17をインストールした場合、次のようにしてJAVAHOMEを設定してみてから再度実行してみてください。
```
cd
echo JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home >> .zshrc
source .zshrc
echo $JAVA_HOME
```

- ``~/prog2/sample/src/App.java`` を実行できない。
  - case 1: Java Classpath の設定が必要かもしれません。
    - App.javaを開いている状態で ``⌘ + Shift + P`` を押してコマンドパレットを開き、``java``と入力。そこから``Java: Configure Classpath`` を選ぼう。
    - JDK Runtime内の ``Configure Java Runtime`` をクリックしよう。
    - Java Versionのところから 17 を選ぶか、もしくはペンアイコンをクリックし、``/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home`` を設定しよう。
    - 設定後、App.javaに戻り実行してみよう。
