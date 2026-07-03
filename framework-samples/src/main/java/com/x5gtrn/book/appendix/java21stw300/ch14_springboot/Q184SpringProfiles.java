package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.Arrays;
@Component
public class Q184SpringProfiles implements FrameworkSample {
    private final Environment env;
    public Q184SpringProfiles(Environment env){ this.env = env; }
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 184; }
    public String title(){ return "Spring Profiles"; }
    public void run(){
        String[] active = env.getActiveProfiles();
        System.out.println("アクティブプロファイル = " + (active.length == 0 ? "(default)" : Arrays.toString(active)));
        System.out.println("application-{profile}.yml が上書き適用され、@Profile(\"dev\") 等でBeanを出し分ける");
        System.out.println("用途: dev/stg/prod で DataSource・ログ・外部連携先などを環境ごとに切り替える");
    }
}
