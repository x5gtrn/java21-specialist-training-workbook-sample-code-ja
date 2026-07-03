package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q11RecordPattern implements Sample {
    record Point(int x, int y) {}
    record Line(Point start, Point end) {}
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 11;}
    public String title(){return "レコードパターン";}
    public void run(){
        Object obj = new Line(new Point(1, 2), new Point(4, 6));
        // ネストしたレコードパターンで一度に分解
        if (obj instanceof Line(Point(var x1, var y1), Point(var x2, var y2))) {
            System.out.printf("start=(%d,%d) end=(%d,%d)%n", x1, y1, x2, y2);
            System.out.println("length^2 = " + ((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
        }
        System.out.println(describe(new Point(0, 0)));
        System.out.println(describe(new Point(3, 0)));
    }
    private String describe(Object o){
        return switch (o) {
            case Point(var x, var y) when x == 0 && y == 0 -> "原点";
            case Point(var x, var y) -> "点(" + x + "," + y + ")";
            default -> "?";
        };
    }
}
