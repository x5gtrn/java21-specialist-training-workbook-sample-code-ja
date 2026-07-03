package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Objects;
public final class Q147DddBuildingBlocks implements Sample {
    // 値オブジェクト: 不変・値で等価
    record Money(long amount, String currency) {}
    // エンティティ: 同一性(ID)で等価。属性が変わっても同じもの
    static final class Account {
        private final String id; private Money balance;
        Account(String id, Money b){ this.id = id; this.balance = b; }
        void deposit(long a){ balance = new Money(balance.amount()+a, balance.currency()); }
        public boolean equals(Object o){ return o instanceof Account x && x.id.equals(id); }
        public int hashCode(){ return Objects.hash(id); }
        Money balance(){ return balance; }
    }
    public String chapter(){return "11_Architecture";}
    public int problem(){return 147;}
    public String title(){return "DDD 基礎 — Entity / Value Object / Aggregate";}
    public void run(){
        System.out.println("値オブジェクトの等価: " + new Money(100,"JPY").equals(new Money(100,"JPY")));
        Account a = new Account("acc-1", new Money(1000,"JPY"));
        a.deposit(500);
        Account sameId = new Account("acc-1", new Money(0,"JPY"));
        System.out.println("エンティティは ID で等価: " + a.equals(sameId) + "、残高=" + a.balance());
        System.out.println("集約ルート(Account)経由でのみ不変条件を保ちつつ内部を変更する");
    }
}
