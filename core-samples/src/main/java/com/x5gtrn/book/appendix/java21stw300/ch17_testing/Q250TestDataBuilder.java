package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q250TestDataBuilder implements Sample {
    record User(String name, String email, int age, boolean active) {}
    // テストデータビルダ: 妥当なデフォルトを持ち、必要な属性だけ上書きしてテストの意図を明確化
    static final class UserBuilder {
        private String name="Test User", email="test@example.com"; private int age=30; private boolean active=true;
        UserBuilder name(String n){ this.name=n; return this; }
        UserBuilder age(int a){ this.age=a; return this; }
        UserBuilder inactive(){ this.active=false; return this; }
        User build(){ return new User(name,email,age,active); }
    }
    public String chapter(){return "17_Testing";}
    public int problem(){return 250;}
    public String title(){return "Test Data Builder";}
    public void run(){
        System.out.println("デフォルト   : " + new UserBuilder().build());
        System.out.println("年齢だけ変更 : " + new UserBuilder().age(17).build() + "（テストの関心事が一目で分かる）");
        System.out.println("非アクティブ : " + new UserBuilder().name("Bob").inactive().build());
    }
}
