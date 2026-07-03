package com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q89FinalFieldSafePublication implements Sample {
    static final class Config {
        private final String host; private final int port;
        Config(String host, int port){ this.host = host; this.port = port; }
        // final フィールドはコンストラクタ完了時点で他スレッドから確実に可視（安全な公開）
        public String toString(){ return host + ":" + port; }
    }
    public String chapter(){return "07_Concurrency_Threading";}
    public int problem(){return 89;}
    public String title(){return "final フィールドと安全な公開";}
    public void run() throws InterruptedException {
        final Config[] shared = new Config[1];
        Thread writer = new Thread(() -> shared[0] = new Config("localhost", 8080));
        writer.start(); writer.join();
        System.out.println("公開された Config = " + shared[0]);
        System.out.println("final フィールドは初期化安全性により、部分構築状態が見えることはない");
    }
}
