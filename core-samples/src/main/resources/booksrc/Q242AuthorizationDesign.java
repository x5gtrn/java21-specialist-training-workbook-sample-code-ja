// ■ B: SecurityFilterChain による URL ベース設定
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // POINT: E の @PreAuthorize を有効化するために必要
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()  // 認証不要
                .requestMatchers("/api/admin/**").hasRole("ADMIN")  // ADMIN ロール必須
                .anyRequest().authenticated())  // その他は認証必須
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults()))  // JWT 認証
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}

// ■ E: @PreAuthorize によるメソッドレベル細粒度認可
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // POINT: ロールチェック + ビジネスルール（自分自身の削除を禁止）
    @PreAuthorize("hasRole('ADMIN') and #userId != authentication.principal.id")
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }

    // POINT: 複数ロールの OR 条件
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/reports")
    public List<Report> getReports() {
        return List.of();
    }

    // POINT: カスタムメソッドの呼び出し
    @PreAuthorize("@accessPolicy.canAccess(#document, authentication)")
    @GetMapping("/documents/{id}")
    public Document getDocument(@PathVariable Long id) {
        return findDocument(id);
    }
}