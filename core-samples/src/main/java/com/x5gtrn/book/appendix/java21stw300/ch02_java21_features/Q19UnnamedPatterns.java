package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q19UnnamedPatterns implements Sample {
    record Point(int x, int y) {}
    record Box(Point topLeft, Point bottomRight) {}
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 19;}
    public String title(){return "Unnamed Patterns と Variables";}
    @Override public boolean preview(){return true;}
    public void run(){
        Object obj = new Box(new Point(0, 10), new Point(20, 0));
        // 使わない成分を _ で無視（Unnamed Pattern）
        if (obj instanceof Box(Point(var x1, _), Point(_, var y2))) {
            System.out.println("左上x=" + x1 + ", 右下y=" + y2);
        }
        for (Object o : List.of(new Point(1, 2), new Point(3, 4))) {
            String s = switch (o) {
                case Point(var x, _) -> "x=" + x + "（y は無視）";
                default -> "?";
            };
            System.out.println(s);
        }
    }
}
