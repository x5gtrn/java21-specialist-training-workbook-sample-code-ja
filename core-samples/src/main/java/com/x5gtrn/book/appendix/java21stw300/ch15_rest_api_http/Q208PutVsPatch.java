package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.HashMap;
import java.util.Map;
public final class Q208PutVsPatch implements Sample {
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 208;}
    public String title(){return "PUT と PATCH の更新の意味";}
    public void run(){
        Map<String,Object> resource = new HashMap<>(Map.of("name","Alice","age",30,"city","Tokyo"));
        System.out.println("初期      : " + resource);
        // PUT: リソース全体を置換（送らなかった項目は消える/デフォルトになる）
        Map<String,Object> put = new HashMap<>(Map.of("name","Bob","age",25));
        System.out.println("PUT 後    : " + put + "（city は無くなる=全置換）");
        // PATCH: 送った項目だけ部分更新
        Map<String,Object> patched = new HashMap<>(resource);
        patched.putAll(Map.of("age",31));
        System.out.println("PATCH 後  : " + patched + "（age のみ更新、他は保持）");
    }
}
