package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
@Component
public class Q206JsonRecordIntegration implements FrameworkSample {
    record UserDto(String name, String email, int age) {}
    public String chapter(){ return "15_REST_API_HTTP"; }
    public int problem(){ return 206; }
    public String title(){ return "JSON とレコード型の統合"; }
    public void run() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDto dto = new UserDto("Alice", "alice@example.com", 30);
        String json = mapper.writeValueAsString(dto);        // record -> JSON（アクセサ名がキーになる）
        System.out.println("serialize   : " + json);
        UserDto back = mapper.readValue(json, UserDto.class); // JSON -> record（正準コンストラクタ）
        System.out.println("deserialize : " + back);
        System.out.println("Jackson 2.12+ は record を標準サポート。DTO を不変の record にできる");
    }
}
