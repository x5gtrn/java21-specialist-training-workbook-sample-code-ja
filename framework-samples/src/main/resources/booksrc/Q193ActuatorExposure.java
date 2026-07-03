http.securityMatcher(EndpointRequest.toAnyEndpoint())
    .authorizeHttpRequests(auth -> auth
        .requestMatchers(EndpointRequest.to("health", "info")).permitAll()
        .anyRequest().hasRole("OPS"));