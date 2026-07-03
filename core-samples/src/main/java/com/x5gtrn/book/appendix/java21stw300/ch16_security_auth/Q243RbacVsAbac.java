package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Map;
import java.util.Set;
public final class Q243RbacVsAbac implements Sample {
    record User(String name, Set<String> roles, String department) {}
    record Resource(String name, String ownerDepartment) {}
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 243;}
    public String title(){return "RBAC vs ABAC";}
    public void run(){
        User alice = new User("Alice", Set.of("EDITOR"), "sales");
        Resource salesDoc = new Resource("sales-report", "sales");
        Resource hrDoc = new Resource("hr-policy", "hr");
        // RBAC: ロールで判定（粗い粒度）
        System.out.println("RBAC alice EDITOR? " + rbac(alice, "EDITOR"));
        // ABAC: 属性（部署一致など）で判定（きめ細かい文脈依存）
        System.out.println("ABAC alice -> sales-report : " + abac(alice, salesDoc));
        System.out.println("ABAC alice -> hr-policy    : " + abac(alice, hrDoc));
    }
    private boolean rbac(User u, String role){ return u.roles().contains(role); }
    private boolean abac(User u, Resource r){
        return u.roles().contains("EDITOR") && u.department().equals(r.ownerDepartment());
    }
}
