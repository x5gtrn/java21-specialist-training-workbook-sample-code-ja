package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Optional;
public final class Q64OptionalChaining implements Sample {
    record Address(String city) {}
    record User(String name, Address address) {}
    public String chapter(){ return "05_Streams_Lambda_Functional"; }
    public int problem(){ return 64; }
    public String title(){ return "Optional のチェーンパターン"; }
    public void run(){
        User withCity = new User("Alice", new Address("Tokyo"));
        User noAddr = new User("Bob", null);
        // map/flatMap/filter/orElseGet で null チェックの入れ子を平坦化
        System.out.println("Alice の市: " + cityOf(withCity));
        System.out.println("Bob の市  : " + cityOf(noAddr));
        System.out.println("アンチパターン: isPresent()+get() の多用。get() は例外源で null チェックと大差ない");
    }
    private String cityOf(User u){
        return Optional.ofNullable(u)
                .map(User::address)              // null なら空へ短絡
                .map(Address::city)
                .filter(c -> !c.isBlank())
                .orElseGet(() -> "(不明)");       // 遅延評価のデフォルト
    }
}
