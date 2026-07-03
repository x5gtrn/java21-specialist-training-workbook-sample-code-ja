package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q223IfMatchLostUpdate implements Sample {
    static final class Resource { String value = "v0"; int version = 1; String etag(){ return "\"" + version + "\""; } }
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 223;}
    public String title(){return "If-Match と Lost Update 対策";}
    public void run(){
        Resource res = new Resource();
        String clientEtag = res.etag(); // クライアントは GET 時の ETag を保持
        // 別クライアントが先に更新 → version/ETag が変わる
        res.value = "other"; res.version = 2;
        System.out.println("サーバ現在の ETag = " + res.etag() + " / クライアント保持 = " + clientEtag);
        // 条件付き更新: If-Match が現在の ETag と不一致なら 412 で拒否（上書き=ロストアップデート防止）
        System.out.println(conditionalUpdate(res, clientEtag, "mine"));
        System.out.println(conditionalUpdate(res, res.etag(), "mine")); // 最新 ETag なら成功
    }
    private String conditionalUpdate(Resource res, String ifMatch, String newValue){
        if (!ifMatch.equals(res.etag())) return "PUT (If-Match:" + ifMatch + ") -> 412 Precondition Failed";
        res.value = newValue; res.version++;
        return "PUT (If-Match:" + ifMatch + ") -> 200 OK, value=" + res.value;
    }
}
