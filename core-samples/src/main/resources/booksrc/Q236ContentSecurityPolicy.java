http.headers(headers -> headers
    .contentSecurityPolicy(csp -> csp
        .policyDirectives("default-src 'self'; script-src 'self'; object-src 'none'")));
@@BLOCK@@
http.headers(headers -> headers
    .contentSecurityPolicy(csp -> csp.policyDirectives("""
        default-src 'self';
        script-src 'self';
        style-src 'self';
        img-src 'self' data:;
        object-src 'none';
        base-uri 'self';
        frame-ancestors 'none'
        """.replace("\n", " "))));