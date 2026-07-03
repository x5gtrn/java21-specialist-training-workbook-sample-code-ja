package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q14EnumVsSealedModeling implements Sample {
    // 固定の定数集合 + 各要素が同じ属性を持つ -> enum
    enum Planet {
        EARTH(5.976e+24, 6.37814e6), MARS(6.421e+23, 3.3972e6);
        final double mass, radius;
        Planet(double m, double r){ mass = m; radius = r; }
        double gravity(){ return 6.67300e-11 * mass / (radius * radius); }
    }
    // 種類ごとに異なるデータを持つ -> sealed
    sealed interface Shape permits Circle, Rectangle {}
    record Circle(double r) implements Shape {}
    record Rectangle(double w, double h) implements Shape {}
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 14;}
    public String title(){return "enum と sealed 型のモデリング判断";}
    public void run(){
        for (Planet p : Planet.values())
            System.out.printf("%-6s 表面重力 = %.2f m/s^2%n", p, p.gravity());
        for (Shape s : List.of(new Circle(2), new Rectangle(3,4)))
            System.out.println(s + " area=" + String.format("%.2f", area(s)));
    }
    private double area(Shape s){
        return switch (s) { case Circle c -> Math.PI*c.r()*c.r(); case Rectangle r -> r.w()*r.h(); };
    }
}
