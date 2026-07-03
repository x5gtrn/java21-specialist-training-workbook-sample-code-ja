package com.x5gtrn.book.appendix.java21stw300.ch16_security_auth;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Map;
public final class Q244ObjectLevelAuthorization implements Sample {
    record Document(String id, String ownerId) {}
    public String chapter(){return "16_Security_Auth";}
    public int problem(){return 244;}
    public String title(){return "オブジェクト単位の認可";}
    public void run(){
        Map<String,Document> docs = Map.of("d1", new Document("d1","alice"), "d2", new Document("d2","bob"));
        // ロールが十分でも「その特定リソースの所有者か」を必ず確認する（IDOR 対策）
        System.out.println("alice が d1 を取得 -> " + access(docs, "alice", "d1"));
        System.out.println("alice が d2 を取得 -> " + access(docs, "alice", "d2"));
        System.out.println("URL の ID を差し替えただけで他人のデータが見えてはならない（水平権限昇格の防止）");
    }
    private String access(Map<String,Document> docs, String userId, String docId){
        Document d = docs.get(docId);
        if (d == null) return "404 Not Found";
        return d.ownerId().equals(userId) ? "200 OK (所有者)" : "403 Forbidden (他人の所有物)";
    }
}
