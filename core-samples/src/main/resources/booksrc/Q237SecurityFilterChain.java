@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())  // 3. CSRF 無効化
        .cors(Customizer.withDefaults())  // 2. CORS 設定
        .sessionManagement(s ->
            s.sessionCreationPolicy(STATELESS))  // セッション無効化
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/public/**").permitAll()
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated())  // 7. 認可ルール
        .oauth2ResourceServer(oauth2 ->
            oauth2.jwt(Customizer.withDefaults()))  // 5. JWT 認証
        .build();
}