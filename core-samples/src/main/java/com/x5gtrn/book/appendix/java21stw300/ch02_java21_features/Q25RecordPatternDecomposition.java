package com.x5gtrn.book.appendix.java21stw300.ch02_java21_features;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q25RecordPatternDecomposition implements Sample {
    record Address(String city, String zip) {}
    record Customer(String name, Address address) {}
    record Order(Customer customer, int amount) {}
    public String chapter(){return "02_Java21_Features";}
    public int problem(){return 25;}
    public String title(){return "レコードパターンと深い分解の可読性";}
    public void run(){
        Object o = new Order(new Customer("Alice", new Address("Tokyo", "100-0001")), 5000);
        // 深いネストを一度の record パターンで分解
        if (o instanceof Order(Customer(var name, Address(var city, var zip)), var amount)) {
            System.out.printf("%s / %s(%s) / %d円%n", name, city, zip, amount);
        }
    }
}
