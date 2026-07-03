// ■ カスタム HealthIndicator
@Component
public class PaymentGatewayHealthIndicator implements HealthIndicator {
    private final PaymentGateway gateway;

    @Override
    public Health health() {
        try {
            gateway.ping();
            return Health.up()
                .withDetail("gateway", "reachable")
                .withDetail("responseTime", "45ms")
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("gateway", "unreachable")
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}
// → GET /actuator/health
// {
// "status": "UP",
// "components": {
// "paymentGateway": { "status": "UP", "details": {"gateway":"reachable"} },
// "db": { "status": "UP" },
// "diskSpace": { "status": "UP" }
// }
// }

// ■ Actuator の設定
// 設定ファイル: application.properties
// management.endpoints.web.exposure.include=health,metrics,info,loggers
// management.endpoint.health.show-details=when-authorized
// management.server.port=9090  # 管理ポートの分離（推奨）