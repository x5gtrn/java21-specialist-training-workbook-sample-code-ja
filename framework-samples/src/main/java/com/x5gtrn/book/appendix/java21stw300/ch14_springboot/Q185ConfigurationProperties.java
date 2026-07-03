package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
public class Q185ConfigurationProperties implements FrameworkSample {
    private final Environment env;
    public Q185ConfigurationProperties(Environment env){ this.env = env; }
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 185; }
    public String title(){ return "@ConfigurationProperties と型安全な設定"; }
    public void run(){
        // @ConfigurationProperties は prefix 配下の設定を型付きオブジェクトへ束縛する（本来は専用クラスへ）
        String port = env.getProperty("server.port", "8081");
        String ddl = env.getProperty("spring.jpa.hibernate.ddl-auto", "(未設定)");
        System.out.println("server.port = " + port + " / ddl-auto = " + ddl + "（Environment 直読み）");
        System.out.println("@ConfigurationProperties(prefix=\"app\") + record で app.* を型安全に束縛し、@Value 散在を防ぐ");
        System.out.println("利点: 型検証・IDE補完・関連設定の集約・起動時バインド検証");
    }
}
