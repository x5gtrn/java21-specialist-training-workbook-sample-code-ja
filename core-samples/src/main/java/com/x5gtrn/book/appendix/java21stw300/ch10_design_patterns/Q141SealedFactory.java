package com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;

/**
 * 問題141: Factory + sealed 型 + パターンマッチング switch。
 * enum に新しい値を追加すると switch がコンパイルエラーになり、対応の追加を強制できる。
 */
public final class Q141SealedFactory implements Sample {

    enum Channel { EMAIL, SMS, PUSH }

    sealed interface Notification permits EmailNotification, SmsNotification, PushNotification {}
    record EmailNotification(String to, String body) implements Notification {}
    record SmsNotification(String phone, String body) implements Notification {}
    record PushNotification(String deviceToken, String body) implements Notification {}

    record NotificationRequest(Channel channel, String recipient, String body) {}

    public String chapter() { return "10_Design_Patterns"; }
    public int problem()    { return 141; }
    public String title()   { return "Factory + sealed + パターンマッチング"; }

    public void run() {
        List<NotificationRequest> requests = List.of(
            new NotificationRequest(Channel.EMAIL, "a@example.com", "Welcome"),
            new NotificationRequest(Channel.SMS,   "+81-90-0000",   "Code: 1234"),
            new NotificationRequest(Channel.PUSH,  "device-xyz",    "You have a message")
        );
        for (NotificationRequest req : requests) {
            Notification n = create(req);
            System.out.println(send(n));
        }
    }

    private Notification create(NotificationRequest req) {
        return switch (req.channel()) {
            case EMAIL -> new EmailNotification(req.recipient(), req.body());
            case SMS   -> new SmsNotification(req.recipient(), req.body());
            case PUSH  -> new PushNotification(req.recipient(), req.body());
        };
    }

    private String send(Notification n) {
        return switch (n) {
            case EmailNotification e -> "EMAIL -> " + e.to() + " : " + e.body();
            case SmsNotification s   -> "SMS   -> " + s.phone() + " : " + s.body();
            case PushNotification p  -> "PUSH  -> " + p.deviceToken() + " : " + p.body();
        };
    }
}
