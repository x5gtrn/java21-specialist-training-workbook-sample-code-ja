package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q26SealedInstanceofExhaustive implements Sample {
    sealed interface Event permits Click, Scroll, KeyPress {}
    record Click(int x, int y) implements Event {}
    record Scroll(int delta) implements Event {}
    record KeyPress(char key) implements Event {}
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 26;}
    public String title(){return "sealed クラスと instanceof パターン漏れ";}
    public void run(){
        for (Event e : List.of(new Click(10,20), new Scroll(-3), new KeyPress('A')))
            System.out.println(handle(e));
        // instanceof の連鎖では網羅性がコンパイラに保証されない（switch なら保証される）
    }
    private String handle(Event e){
        return switch (e) {
            case Click c -> "click at (" + c.x() + "," + c.y() + ")";
            case Scroll s -> "scroll " + s.delta();
            case KeyPress k -> "key '" + k.key() + "'";
        };
    }
}
