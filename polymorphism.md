# 多態性（ポリモーフィズム; Polymorphism）
- 教科書12章。
  - <a href="#term">用語説明と前提知識</a>
  - <a href="#example">ポリモーフィズムがあると嬉しい状況？</a>
  - <a href="#howto">どう実現するのか？</a>

<hr>

## <a name="term">用語説明と前提知識</a>
- 多態性, 多様性, ポリモーフィズム, Polymorphism
  - 継承関係にあるクラスから生成した異なる複数のオブジェクトを同一視して、スーパークラスとして扱うこと。その際の振る舞いは、オブジェクトを生成したクラス自体の定義に従うため異なる動作となる。
  - 同じ型の変数でも、どんなオブジェクトが入っているかで動作が変わってしまうこと。
  - 同じ型の変数でも、違う型のオブジェクトを入れることで様々な機能に変身させられるということ。
- 継承関係にある型同士のオブジェクトは、同じ種類の型と看做して利用できる。
  - スーパークラス型への代入は、、、
    - 代入可能。
    - **オブジェクトは代入によって変化しない。**
    - スーパークラス型変数では、サブクラスで拡張したメンバにはアクセスできない。スーパークラスからアクセス出来ないだけで、保持したままであることに注意。
  - サブクラス型への代入は、、、
    - コンパイルエラー。実行すらできない。
  - [Object.getClass()](https://docs.oracle.com/javase/9/docs/api/java/lang/Object.html#getClass--)による **実行時のクラス** 確認（Returns **the runtime class of this Object**. ）。
    - Objectクラスは、全てのクラスの親クラス（教科書, pp.401-）。クラス型なら全てgetClass()で「実行時のクラス」を確認できる。
    - int, double等の「基本データ型」は、クラスとしては実装されていないため、getClass()で確認できない。

```java
// 親子関係にあるオブジェクトの代入例
class Member{
    private String name;
    public Member(String _name){
        this.name = _name;
    }
    public String getName(){ return this.name; }
    public void sayHello(){
        System.out.println("いらっしゃいませ" + getName() + "さん");
    }
}

class StudentMember extends Member{
    public StudentMember(String _name){
        super(_name);
    }
    @Override
    public void sayHello(){
        System.out.println(getName() + "様、お久しぶりです。");
    }
}


public class Main {
    public static void main(String[] args){
        //通常のオブジェクト。
        Member mem1 = new Member("member1");
        mem1.sayHello();

        //サブクラスのオブジェクトをスーパークラス型変数に代入。
        Member mem2 = new StudentMember("stu1");
        mem2.sayHello(); // case 1: 動作はどうなっている？
        StudentMember stu = (StudentMember)mem2;
        stu.sayHello();  // case 2: 動作はどうなっている？
    }
}
```


<hr>

## <a name="example">ポリモーフィズムがあると嬉しい状況？</a>
- オブジェクトを大まかに扱うことで、プログラマが楽をする。
  - 例えば、道路交通シミュレーションすることを想像してみよう。
    - 道路を利用する（道路上を移動する）対象は、車（バス、ダンプカー、中型トラック、自家用車）、バイク（自動二輪、原付き）、自転車、歩行者（一般人、子供、不注意者）、、、
    - これらを「道路上を移動するオブジェクト Vehicle」というスーパークラスを用意した上で、各々継承したサブクラスとして実装する。スーパークラスには「1秒間動く move()メソッド」を用意しておき、具体的な実装は各サブクラスで用意する。
      - ![クラス図の例](./figs/Polymorphism.svg)
    - シミュレータのmainメソッドで、各クラスのインスタンスを用意する。
    - 用意した全オブジェクトに対して「1秒間動く move()メソッド」を実行させる際に、ポリモーフィズムがない状況だと「クラス毎にmove()メソッドを呼び出す」必要がある。これは、クラス数が多いと大変だし、無駄でもある。
      - ポリモーフィズムがある状況だと、全クラスのスーパークラスが持つメソッド Vehicle.move() を実行するだけで良い。

```java
// 擬似コード1（ポリモーフィズムがない状況）
Bus[] buses = new Bus[3];
Bus[0] = new Bus();
Bus[1] = new Bus();
Bus[2] = new Bus();
DumpCar[] dumpCars = new DumpCar[2];
DumpCar[0] = new DumpCar();
DumpCar[1] = new DumpCar();

for(Bus bus: buses){
  bus.move();
}
for(DumpCar dumpCar: dumpCars){
  dumpCar.move();
}
// 以下、サブクラス毎に同じfor文が続く。
```

```java
// 擬似コード2（ポリモーフィズムがある状況）
Vehicle[] vehicles = new Vehicle[5];
vehicles[0] = new Bus();
vehicles[1] = new Bus();
vehicles[2] = new Bus();
vehicles[3] = new DumpCar();
vehicles[4] = new DumpCar();

// どれだけサブクラスが増えたとしても、
// シミュレータ側では下記ループ文だけで全オブジェクトを操作できる。
for(Vehicle vehicle: vehicles){
  vehicle.move();
  //オーバーライドにより、オブジェクト自身のメソッドが実行される。
}
```

<hr>

## <a name="howto">どう実現するのか？</a>
- スーパークラスへの代入とオーバーライド
  - 授業としては知識の片隅に置いておくぐらいで十分。自身の直感と異なる妙な動作をすると感じたら、デバッグ実行で動作を確認しよう。
  - 最初からうまくクラス設計できる訳でもないので、まずは継承を意識してクラス設計してみよう。その後の実装を通して、「まとめて処理したい」「同一視したいクラス」等が出てきた際に、設計を見直そう。
