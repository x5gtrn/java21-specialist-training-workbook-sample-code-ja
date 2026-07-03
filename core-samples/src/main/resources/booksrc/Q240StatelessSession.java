// Spring Boot でのステートレス設定
http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

// 各リクエスト: Authorization: Bearer eyJhbGciOi...
// どのインスタンスでも公開鍵で署名検証可能 → DB アクセス不要