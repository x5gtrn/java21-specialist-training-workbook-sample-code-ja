@Bean
PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
@@BLOCK@@
boolean ok = passwordEncoder.matches(rawPassword, storedHash);
@@BLOCK@@
public void register(RegisterRequest request) {
    String hash = passwordEncoder.encode(request.password());
    userRepository.save(new User(request.email(), hash));
}

public boolean login(String email, String password) {
    User user = userRepository.findByEmail(email).orElseThrow();
    return passwordEncoder.matches(password, user.passwordHash());
}