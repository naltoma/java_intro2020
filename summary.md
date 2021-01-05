# 振り返り
## 主要キーワード
- オブジェクト指向プログラミング
  - クラス、オブジェクト、フィールド、コンストラクタ、メソッド、メンバ、静的メンバ
  - カプセル化、継承、多態性
- 開発周り
  - ユニットテスト: JUnit5
  - バージョン管理: git + リポジトリ共有GitHub
  - ビルドツール: Gradle

<hr>

## カプセル化、継承、多態性を利用したコード例
- 関連
  - [System.out](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/System.html#out)
  - [String.valueOf(x)](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/String.html#valueOf(java.lang.Object))
  - [Object.toString()](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/Object.html#toString())
    - ``getClass().getName() + '@' + Integer.toHexString(hashCode())``
- コード例

```Java
//通常のクラス実装。extendsしていないが、Objectクラスを継承している。
class Member {
    private String name;
    private int id;
    public Member(String _name, int _id){
        setName(_name);
        setId(_id);
    }
    public String getName(){ return this.name; }
    public void setName(String _name){ this.name = _name; }
    public int getId(){ return this.id; }
    public void setId(int _id){ this.id = _id; }
}

//toStringをオーバーライドしたクラス
class Member2 {
    private String name;
    private int id;
    public Member2(String _name, int _id){
        setName(_name);
        setId(_id);
    }
    public String getName(){ return this.name; }
    public void setName(String _name){ this.name = _name; }
    public int getId(){ return this.id; }
    public void setId(int _id){ this.id = _id; }
    @Override
    public String toString(){
        String result = "Member2: ";
        result += getName() + ", " + Integer.toString(getId());
        return result;
    }
}

public class Sample {
    public static void main(String[] args) throws Exception {
        //case1: 通常クラスのオブジェクトを直接printした場合。
        Member mem1 = new Member("naltoma", 1);
        System.out.println(mem1); // Member@78308db1

        //case2: toStringメソッドをオーバーライドした場合。
        Member2 mem2 = new Member2("naltoma", 1);
        System.out.println(mem2); // Member2: naltoma, 1
    }
}
```
