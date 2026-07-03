package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q13SealedSwitchResilience implements Sample {
    sealed interface Expr permits Num, Add, Mul {}
    record Num(int value) implements Expr {}
    record Add(Expr left, Expr right) implements Expr {}
    record Mul(Expr left, Expr right) implements Expr {}
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 13;}
    public String title(){return "sealed 階層と switch 式の変更耐性";}
    public void run(){
        // (1 + 2) * 3
        Expr e = new Mul(new Add(new Num(1), new Num(2)), new Num(3));
        System.out.println("式の評価結果 = " + eval(e));
        // Expr に新種を permits 追加すると、この switch が未対応でコンパイルエラーになり漏れを検知できる
    }
    private int eval(Expr e){
        return switch (e) {
            case Num n -> n.value();
            case Add(var l, var r) -> eval(l) + eval(r);
            case Mul(var l, var r) -> eval(l) * eval(r);
        };
    }
}
