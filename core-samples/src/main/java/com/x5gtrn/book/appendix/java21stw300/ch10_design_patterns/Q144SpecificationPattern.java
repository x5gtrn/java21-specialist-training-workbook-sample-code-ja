package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public final class Q144SpecificationPattern implements Sample {
    record Product(String name, int price, boolean inStock) {}
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 144;}
    public String title(){return "Specification Pattern と複雑な業務条件";}
    public void run(){
        List<Product> products = List.of(
            new Product("A", 500, true), new Product("B", 1500, true),
            new Product("C", 800, false), new Product("D", 1200, true));
        // 仕様（Predicate）を and/or で合成し、複雑な業務条件を組み立てる
        Predicate<Product> affordable = p -> p.price() <= 1200;
        Predicate<Product> available = Product::inStock;
        Predicate<Product> spec = affordable.and(available);
        String matched = products.stream().filter(spec).map(Product::name).collect(Collectors.joining(", "));
        System.out.println("価格1200以下 かつ 在庫あり -> " + matched);
    }
}
