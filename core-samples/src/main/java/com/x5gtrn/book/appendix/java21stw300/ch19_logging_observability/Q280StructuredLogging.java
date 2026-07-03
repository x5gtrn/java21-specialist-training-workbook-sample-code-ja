package com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.LinkedHashMap;
import java.util.Map;
public final class Q280StructuredLogging implements Sample {
    public String chapter(){ return "19_Logging_Observability"; }
    public int problem(){ return 280; }
    public String title(){ return "構造化ロギング"; }
    public void run(){
        // 非構造(文字列連結)は機械処理が困難。構造化(キー/値=JSON)は集約基盤で検索・集計しやすい。
        String plain = "User alice placed order 12345 amount 5000";
        System.out.println("非構造: " + plain + "  <- 正規表現でしか抽出できず脆い");
        Map<String,Object> event = new LinkedHashMap<>();
        event.put("level", "INFO"); event.put("event", "order_placed");
        event.put("userId", "alice"); event.put("orderId", 12345); event.put("amount", 5000);
        System.out.println("構造化: " + toJson(event));
        System.out.println("利点: フィールド単位で検索/集計(例: amount>1000)、ダッシュボード化、アラート設定が容易");
        System.out.println("実装: Logback/Log4j2 の JSON エンコーダ、または SLF4J の Fluent API + キー付き");
    }
    private String toJson(Map<String,Object> m){
        StringBuilder sb = new StringBuilder("{");
        m.forEach((k,v) -> sb.append('"').append(k).append("\":")
                .append(v instanceof Number ? v.toString() : "\"" + v + "\"").append(','));
        if (sb.charAt(sb.length()-1)==',') sb.setLength(sb.length()-1);
        return sb.append('}').toString();
    }
}
