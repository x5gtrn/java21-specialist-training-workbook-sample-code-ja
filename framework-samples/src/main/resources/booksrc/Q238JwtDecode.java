// Spring Boot Resource Server の設定
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("SCOPE_admin")
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter())))
            .build();
    }
}
// POINT: JWT 構造: Header.Payload.Signature
// {"alg":"RS256","typ":"JWT"}
// {"sub":"user123","iss":"auth.example.com","exp":1706097600,"roles":["USER"]}
// RSASHA256(base64(header)+"."+base64(payload), privateKey)
// POINT: Payload は Base64 デコード可能（暗号化ではない！署名のみ）