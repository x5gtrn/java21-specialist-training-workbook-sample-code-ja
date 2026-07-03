package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q149HexagonalArchitecture implements Sample {
    // ポート（ドメインが依存する抽象）
    interface NotificationPort { void notify(String user, String msg); }
    // アダプタ（外側の実装）。差し替え可能。
    static final class ConsoleAdapter implements NotificationPort {
        public void notify(String user, String msg){ System.out.println("  [console] " + user + ": " + msg); }
    }
    // ドメインサービスは具体実装でなくポートに依存する
    static final class RegistrationService {
        private final NotificationPort port;
        RegistrationService(NotificationPort port){ this.port = port; }
        void register(String user){ /* ドメインロジック */ port.notify(user, "登録が完了しました"); }
    }
    public String chapter(){return "11_Architecture";}
    public int problem(){return 149;}
    public String title(){return "Clean Architecture / Hexagonal Architecture";}
    public void run(){
        new RegistrationService(new ConsoleAdapter()).register("alice");
        System.out.println("依存は 外側→内側(ドメイン) の一方向。ドメインはフレームワークを知らない");
    }
}
