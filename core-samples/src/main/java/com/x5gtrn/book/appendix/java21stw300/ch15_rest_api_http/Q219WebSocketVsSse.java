package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q219WebSocketVsSse implements Sample {
    public String chapter(){return "15_REST_API_HTTP";}
    public int problem(){return 219;}
    public String title(){return "WebSocket と SSE";}
    public void run(){
        // SSE: サーバ→クライアントの単方向。text/event-stream で "data:" 行を push する
        System.out.println("Content-Type: text/event-stream");
        for (int i=1;i<=3;i++) System.out.print("data: {\"tick\":" + i + "}\n\n");
        System.out.println("--- SSE は単方向・HTTP上・自動再接続。ブラウザは EventSource で購読 ---");
        System.out.println("WebSocket は双方向・独自プロトコル(ws://)。チャット等の相互通信向け");
    }
}
