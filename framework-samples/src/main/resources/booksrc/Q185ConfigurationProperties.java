@Validated
@ConfigurationProperties(prefix = "payment")
public record PaymentProperties(
    @NotBlank String endpoint,
    @NotNull Duration timeout,
    @Min(0) int maxRetries,
    boolean enabled
) {}
@@BLOCK@@
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {}
@@BLOCK@@
@Validated
@ConfigurationProperties("client.catalog")
public record CatalogClientProperties(
    @NotNull URI baseUrl,
    @NotNull Duration connectTimeout,
    @NotNull Duration readTimeout,
    @Min(1) int maxConnections
) {}
@@BLOCK@@
@Service
class CatalogClient {
    private final CatalogClientProperties properties;

    CatalogClient(CatalogClientProperties properties) {
        this.properties = properties;
    }
}