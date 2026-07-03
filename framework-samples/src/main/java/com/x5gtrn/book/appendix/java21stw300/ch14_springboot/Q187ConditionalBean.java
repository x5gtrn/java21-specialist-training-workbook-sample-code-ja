package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
public class Q187ConditionalBean implements FrameworkSample {
    private final Environment env;
    public Q187ConditionalBean(Environment env){ this.env = env; }
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 187; }
    public String title(){ return "自動構成と条件付き Bean"; }
    public void run(){
        boolean featureOn = "true".equals(env.getProperty("app.feature.enabled", "false"));
        System.out.println("@ConditionalOnProperty(\"app.feature.enabled\") 相当 -> Bean登録 = " + featureOn);
        System.out.println("@ConditionalOnMissingBean: ユーザー定義Beanが無い時だけ既定を登録（自動構成の要）");
        System.out.println("@ConditionalOnClass: クラスパスに特定クラスがある時だけ構成を有効化");
        System.out.println("これらにより Spring Boot は『あるものを賢く自動構成、無ければ既定』を実現する");
    }
}
