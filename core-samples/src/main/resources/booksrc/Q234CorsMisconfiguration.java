// NG: 危険な設定
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*")  // POINT: 全オリジン許可
            .allowCredentials(true)  // POINT: クッキー送信許可
            .allowedMethods("GET", "POST");
        // → ブラウザがエラーを返す or 認証情報付きリクエストが任意のサイトから可能に
    }
}

// OK: 安全な設定
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://app.example.com")  // POINT: 特定オリジンのみ
            .allowCredentials(true)  // OK: 特定オリジンと組み合わせ
            .allowedMethods("GET", "POST")
            .allowedHeaders("Content-Type", "Authorization")
            .maxAge(3600);
    }
}