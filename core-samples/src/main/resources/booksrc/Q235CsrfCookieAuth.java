http
    .csrf(csrf -> csrf
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
    .authorizeHttpRequests(auth -> auth
        .anyRequest().authenticated());