// ■ フィルタでリクエストごとに MDC を設定
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                     FilterChain chain) throws IOException, ServletException {
        String correlationId = Optional.ofNullable(req.getHeader("X-Correlation-ID"))
            .orElseGet(() -> UUID.randomUUID().toString().substring(0, 8));
        String userId = Optional.ofNullable(req.getHeader("X-User-ID")).orElse("anonymous");

        MDC.put("correlationId", correlationId);
        MDC.put("userId", userId);
        try {
            res.setHeader("X-Correlation-ID", correlationId);
            chain.doFilter(req, res);
        } finally {
            MDC.clear();  // POINT: リクエスト完了後に必ずクリア（メモリリーク防止）
        }
    }
}

// 設定ファイル: logback-spring.xml
// <pattern>%d{HH:mm:ss.SSS} [%X{correlationId}] [%X{userId}] %-5level %logger{36} - %msg%n</pattern>

// ■ サービス層（MDC を明示的に参照する必要なし）
@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public Order processOrder(OrderRequest req) {
        log.info("Processing order for product: {}", req.productId());
        // 出力: 14:30:05.123 [abc12345] [user-789] INFO  OrderService - Processing order...
        // POINT: correlationId と userId が MDC から自動付加
        return orderRepository.save(new Order(req));
    }
}