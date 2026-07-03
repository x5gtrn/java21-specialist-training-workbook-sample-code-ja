// カスタムフィールドの追加
import static net.logstash.logback.argument.StructuredArguments.*;
log.info("Order processed", kv("orderId", 42), kv("amount", 299.99));
// → JSON に orderId と amount フィールドが追加される