@Service
class ExchangeRateClient {
    private final HttpClient httpClient;
    private final ExchangeProperties properties;

    ExchangeRateClient(HttpClient httpClient, ExchangeProperties properties) {
        this.httpClient = httpClient;
        this.properties = properties;
    }
}
@@BLOCK@@
class ExchangeRateClient {
    private static final ExchangeRateClient INSTANCE = new ExchangeRateClient();

    static ExchangeRateClient getInstance() {
        return INSTANCE;
    }
}
@@BLOCK@@
@Component
class TokenSigner {
    private final KeyProvider keyProvider;
    private final Clock clock;

    TokenSigner(KeyProvider keyProvider, Clock clock) {
        this.keyProvider = keyProvider;
        this.clock = clock;
    }
}