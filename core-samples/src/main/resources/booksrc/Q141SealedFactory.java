// ■ sealed 型階層の定義
sealed interface Notification
    permits EmailNotification, SmsNotification, PushNotification {}

record EmailNotification(String to, String subject, String body)
    implements Notification {}
record SmsNotification(String phone, String message)
    implements Notification {}
record PushNotification(String deviceToken, String payload)
    implements Notification {}

// ■ Factory + sealed + pattern matching switch
class NotificationFactory {
    static Notification create(NotificationRequest req) {
        return switch (req.channel()) {
            case EMAIL -> new EmailNotification(
                req.recipient(), req.subject(), req.body());
            case SMS -> new SmsNotification(
                req.recipient(), req.body());
            case PUSH -> new PushNotification(
                req.deviceToken(), req.body());
            // POINT: Channel enum に新しい値を追加すると
            // ここがコンパイルエラーになり修正を強制
        };
    }
}

// ■ 送信処理でも sealed + switch の恩恵
class NotificationSender {
    void send(Notification notification) {
        switch (notification) {
            case EmailNotification e -> emailService.send(e.to(), e.subject(), e.body());
            case SmsNotification s   -> smsService.send(s.phone(), s.message());
            case PushNotification p  -> pushService.send(p.deviceToken(), p.payload());
            // POINT: default 不要（sealed の網羅性保証）
            // POINT: 新サブタイプ追加時にコンパイルエラーで検出
        }
    }
}