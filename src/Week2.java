import java.util.Scanner;

public class Week2 {
    public static void main(String[] args){
        //演習1
        System.out.println("# ex1");
        System.out.println("これが\nJavaなの？");   //演習1-1

        //演習2
        System.out.println("\n# ex2");
        String myName = "Naruaki TOMA"; //演習2-1
        System.out.println("myName is " + myName + ".");
        int scoreOfProgramming2 = 50;   //演習2-2
        System.out.println(scoreOfProgramming2);

        //演習3
        System.out.println("\n# ex3");
        int a = 1;
        double b = a/2;
        System.out.println("b = " + b); //演習3-1
        b = a/2.0;
        System.out.println("b = " + b); //演習3-2

        //演習4
        System.out.println("\n# ex4");
        System.out.println((int)10.9);  //演習4-1
        char ex4 = 'a';
        System.out.println((char)98);    //演習4-2

        //演習5
        System.out.println("\n# ex5");
        String[] text = {"This", "is", "test."};
        String result = ""; //初期化。
        for(String word: text){
            result += word;
        }
        System.out.println(result);

        //演習6
        System.out.println("\n# ex6");
        String target = "This is test.";
        String ex6_1 = target.toLowerCase();
        System.out.println(ex6_1);  //演習6-1

        //演習6-2
        String[] ex6_2 = target.split(" ");
        for(int i=0; i<ex6_2.length; i++){
            System.out.println(i + "番目の単語: " + ex6_2[i]);
        }

        //演習7
        System.out.println("\n# ex7");
        String input;
        Scanner in = new Scanner(System.in); // 標準入力から読み込むスキャナを用意

        while(true){
            System.out.println("何かごようですか？"); // 入力を促す文を出力しているだけ。
            input = in.nextLine(); // inputにユーザ入力を保存する。
            if (input.equals("bye.")){
                System.out.println("ご利用いただきありがとうございました");
                break;
            }else if(input.contains("クーラー")){
                System.out.println("クーラーをONにしました");
            }else{
                System.out.println(input + "とは何ですか？");
            }
        }
        in.close(); // スキャナを閉じる。

        //演習8
        System.out.println("\n# ex8");
        String data = "3.14";
        double ex8 = Double.parseDouble(data);
        System.out.println(ex8);
    }
    
}