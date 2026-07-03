@AutoConfiguration
@EnableConfigurationProperties(PaymentProperties.class)
class PaymentAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    PaymentClient paymentClient(PaymentProperties properties) {
        return new DefaultPaymentClient(properties.endpoint());
    }
}
@@BLOCK@@
@Configuration
class CustomPaymentConfig {
    @Bean
    PaymentClient paymentClient() {
        return new CustomPaymentClient();
    }
}
@@BLOCK@@
@ConditionalOnProperty(prefix = "payment", name = "enabled", havingValue = "true")
@@BLOCK@@
@AutoConfiguration
class ClientAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(Client.class)
    Client defaultClient(ClientProperties properties) {
        return new Client(properties.baseUrl(), properties.timeout());
    }
}