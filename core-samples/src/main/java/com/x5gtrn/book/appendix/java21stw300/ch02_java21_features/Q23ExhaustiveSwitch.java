package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;

/**
 * 問題23: sealed 型に対する switch 式は全サブタイプを網羅する必要がある。
 * permits にサブタイプを追加すると、対応する case を足すまでコンパイルエラーになり、
 * 処理漏れがコンパイル時に検出される（default を置くとこの恩恵が失われる）。
 */
public final class Q23ExhaustiveSwitch implements Sample {

    sealed interface Shape permits Circle, Rectangle, Triangle {}
    record Circle(double radius) implements Shape {}
    record Rectangle(double width, double height) implements Shape {}
    record Triangle(double base, double height) implements Shape {}

    public String chapter() { return "02_Java21_Features"; }
    public int problem()    { return 23; }
    public String title()   { return "網羅的 switch（sealed 型）"; }

    public void run() {
        for (Shape shape : List.of(new Circle(2), new Rectangle(3, 4), new Triangle(6, 5))) {
            double area = switch (shape) {
                case Circle c    -> Math.PI * c.radius() * c.radius();
                case Rectangle r -> r.width() * r.height();
                case Triangle t  -> 0.5 * t.base() * t.height();
            };
            System.out.printf("%-40s area=%.3f%n", shape, area);
        }
    }
}
