module com.example.library {
    requires static com.example.annotations;

    exports com.example.library.api;
}
@@BLOCK@@
@GenerateMapper
public interface OrderMapper {
    OrderDto toDto(Order order);
}
@@BLOCK@@
boolean present = clazz.isAnnotationPresent(GenerateMapper.class);
@@BLOCK@@
module com.example.orders {
    requires static com.example.codegen.annotations;
    exports com.example.orders.api;
}
@@BLOCK@@
// API に依存型が出るなら利用者への影響を考える
public Optional<GeneratedMetadata> metadata();
@@BLOCK@@
// 実行時に Class.forName するなら requires static では足りない可能性
Class.forName("com.example.annotations.Generated");