@Bean
JwtDecoder jwtDecoder() {
    NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(issuer);
    decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
        JwtValidators.createDefaultWithIssuer(issuer),
        new AudienceValidator("orders-api")
    ));
    return decoder;
}
@@BLOCK@@
class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final String audience;

    AudienceValidator(String audience) {
        this.audience = audience;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        return jwt.getAudience().contains(audience)
            ? OAuth2TokenValidatorResult.success()
            : OAuth2TokenValidatorResult.failure(
                new OAuth2Error("invalid_token", "Invalid audience", null));
    }
}