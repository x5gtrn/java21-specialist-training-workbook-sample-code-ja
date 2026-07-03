package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q36CatchOrder implements Sample {
    public String chapter(){ return "03_Exception_Handling"; }
    public int problem(){ return 36; }
    public String title(){ return "try-catch の例外捕捉順序"; }
    public void run(){
        // catch は上から順に評価される。サブクラスを先に、スーパークラスを後に書く必要がある。
        try {
            throw new IllegalArgumentException("不正な引数");
        } catch (IllegalArgumentException e) {          // より特定的な型を先に
            System.out.println("捕捉(特定): IllegalArgumentException -> " + e.getMessage());
        } catch (RuntimeException e) {                  // 一般的な型を後に
            System.out.println("捕捉(一般): RuntimeException");
        }
        // もし RuntimeException を先に書くと、後続の IllegalArgumentException は到達不能でコンパイルエラーになる
        System.out.println("順序を逆にすると『到達不能な catch』としてコンパイルエラーになる");
    }
}
