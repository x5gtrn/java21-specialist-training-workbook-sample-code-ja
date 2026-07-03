record AppConfig(String endpoint, Duration timeout, int maxRetries) {}
@@BLOCK@@
private volatile AppConfig config;

void reload() {
    AppConfig loaded = loadConfig();
    config = loaded;  // volatile 書き込み
}

AppConfig currentConfig() {
    return config;  // volatile 読み取り
}
@@BLOCK@@
class BadConfig {
    static BadConfig latest;
    final String endpoint;

    BadConfig(String endpoint) {
        latest = this;  // this のエスケープ
        this.endpoint = endpoint;
    }
}
@@BLOCK@@
record Config(String endpoint, Duration timeout) {}

private volatile Config config = loadInitialConfig();

Config config() {
    return config;
}
@@BLOCK@@
record Config(List<String> allowedOrigins) {
    Config {
        allowedOrigins = List.copyOf(allowedOrigins);
    }
}
@@BLOCK@@
void reload() {
    Config next = loadAndValidate();
    this.config = next;
}