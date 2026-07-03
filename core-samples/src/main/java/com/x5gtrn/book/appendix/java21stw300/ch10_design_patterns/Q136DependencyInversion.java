package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
public final class Q136DependencyInversion implements Sample {
    interface MessageSender { String send(String to, String body); }          // 抽象
    static final class EmailSender implements MessageSender {                  // 詳細
        public String send(String to, String body){ return "EMAIL -> " + to + ": " + body; }
    }
    static final class SmsSender implements MessageSender {
        public String send(String to, String body){ return "SMS -> " + to + ": " + body; }
    }
    static final class Notifier { // 高水準モジュールは抽象に依存し、詳細に依存しない
        private final MessageSender sender;
        Notifier(MessageSender sender){ this.sender = sender; }
        String notifyUser(String user){ return sender.send(user, "Welcome!"); }
    }
    public String chapter(){return "10_Design_Patterns";}
    public int problem(){return 136;}
    public String title(){return "依存性逆転の原則（DIP）";}
    public void run(){
        System.out.println(new Notifier(new EmailSender()).notifyUser("alice@example.com"));
        System.out.println(new Notifier(new SmsSender()).notifyUser("+81-90-0000"));
    }
}
