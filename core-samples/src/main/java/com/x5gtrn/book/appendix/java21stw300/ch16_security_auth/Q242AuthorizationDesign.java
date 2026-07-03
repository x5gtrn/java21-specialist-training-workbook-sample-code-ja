package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Set;
public final class Q242AuthorizationDesign implements Sample {
    record User(String name, Set<String> authorities) {}
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 242;}
    public String title(){return "Spring Security による認可設計";}
    public void run(){
        User admin = new User("admin", Set.of("ROLE_ADMIN","SCOPE_reports:write"));
        User viewer = new User("viewer", Set.of("ROLE_USER","SCOPE_reports:read"));
        // エンドポイントごとに必要な権限（authority/scope）を要求する
        for (User u : new User[]{admin, viewer}){
            System.out.printf("%-7s reports:write -> %s%n", u.name(), has(u,"SCOPE_reports:write"));
            System.out.printf("%-7s ADMIN専用    -> %s%n", u.name(), has(u,"ROLE_ADMIN"));
        }
    }
    private String has(User u, String required){ return u.authorities().contains(required) ? "許可" : "拒否(403)"; }
}
