package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.Map;
@Component
public class Q218JacksonDynamicSerialization implements FrameworkSample {
    public String chapter(){ return "15_REST_API_HTTP"; }
    public int problem(){ return 218; }
    public String title(){ return "Jackson による動的レスポンスシリアライズ"; }
    public void run() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // 権限やクエリに応じて出力フィールドを動的に増減する（null 項目は出さない等）
        System.out.println("一般ユーザー向け : " + mapper.writeValueAsString(view("Alice", false)));
        System.out.println("管理者向け       : " + mapper.writeValueAsString(view("Alice", true)));
        System.out.println("手段: @JsonInclude(NON_NULL) / @JsonView / Map ベースの動的組み立て / MixIn");
    }
    private Map<String,Object> view(String name, boolean admin){
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("name", name);
        if (admin) { m.put("internalId", 12345); m.put("salary", 800000); } // 管理者にだけ露出
        return m;
    }
}
