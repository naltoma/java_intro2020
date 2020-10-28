//演習2_8の途中。
public class Chatbot{
    private String name;
    public Chatbot(){
        System.out.println("チャットボットの原型を作成します。");
    }
    public Chatbot(String _name){
        this.name = _name;
        System.out.println("チャットボット名" + this.name + "を作成します。");
    }
    public void greeting(){
        System.out.println("はじめまして" + this.name + "です。よろ！");
    }

    public void setName(String _name){
        if (_name.equals("差別用語")){
            System.out.println("禁止されています");
        }else{
            this.name = _name;
        }
    }
    public String getName(){
        return this.name;
    }
}