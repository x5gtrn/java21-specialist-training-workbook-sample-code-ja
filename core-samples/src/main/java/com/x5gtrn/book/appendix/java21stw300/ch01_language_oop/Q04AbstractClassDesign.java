package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
public final class Q04AbstractClassDesign implements Sample {
    static abstract class Shape {
        abstract double area();
        // テンプレート的な共通実装（抽象クラスは状態と実装を持てる）
        String describe(){ return getClass().getSimpleName() + " area=" + String.format("%.2f", area()); }
    }
    static final class Circle extends Shape { final double r; Circle(double r){this.r=r;} double area(){return Math.PI*r*r;} }
    static final class Rectangle extends Shape { final double w,h; Rectangle(double w,double h){this.w=w;this.h=h;} double area(){return w*h;} }
    public String chapter(){return "01_Language_OOP";}
    public int problem(){return 4;}
    public String title(){return "抽象クラスの設計";}
    public void run(){
        for (Shape s : List.of(new Circle(2), new Rectangle(3,4)))
            System.out.println(s.describe());
    }
}
