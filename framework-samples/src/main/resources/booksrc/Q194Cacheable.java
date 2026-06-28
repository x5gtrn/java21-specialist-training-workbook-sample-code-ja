@EnableCaching  // POINT: 必須のアクティベーション
@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        // Caffeine（インメモリ、高性能）
        return new CaffeineCacheManager("products", "users");
    }
}

@Service
public class ProductService {
    // POINT: 同じ id での2回目以降の呼び出しは DB アクセスなし
    @Cacheable(value = "products", key = "#id")
    public Product findById(Long id) {
        log.info("DB query for product {}", id);  // 初回のみログ出力
        return productRepository.findById(id).orElseThrow();
    }

    // POINT: 更新時にキャッシュを無効化
    @CacheEvict(value = "products", key = "#product.id")
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    // POINT: 更新と同時にキャッシュも更新
    @CachePut(value = "products", key = "#product.id")
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // POINT: キャッシュ全クリア
    @CacheEvict(value = "products", allEntries = true)
    public void clearProductCache() { }

    // POINT: 条件付きキャッシュ
    @Cacheable(value = "products", key = "#id",
               condition = "#id > 0",  // この条件を満たす場合のみキャッシュ
               unless = "#result == null")  // null の場合はキャッシュしない
    public Product findByIdConditional(Long id) {
        throw new UnsupportedOperationException("example");
    }
}