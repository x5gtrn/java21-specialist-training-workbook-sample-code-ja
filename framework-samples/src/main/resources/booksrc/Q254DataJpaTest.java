@DataJpaTest  // POINT: JPA 関連のみロード、自動ロールバック
class OrderRepositoryTest {
    @Autowired private TestEntityManager em;  // テスト用 EntityManager
    @Autowired private OrderRepository orderRepository;

    @Test
    void shouldFindRecentOrders() {
        // 準備
        em.persistAndFlush(new Order("SKU-001", LocalDateTime.now().minusHours(1)));
        em.persistAndFlush(new Order("SKU-002", LocalDateTime.now().minusDays(2)));

        // 実行
        List<Order> recent = orderRepository.findByCreatedAtAfter(
            LocalDateTime.now().minusDays(1));

        // 検証
        assertThat(recent).hasSize(1);
        assertThat(recent.get(0).getSku()).isEqualTo("SKU-001");
    }
    // POINT: テスト終了後に自動ロールバック → 他テストに影響しない
}

// POINT: @DataJpaTest がロードするもの:
// OK: @Entity クラス
// OK: Spring Data JPA リポジトリ
// OK: TestEntityManager
// OK: インメモリ DB (H2) の自動設定
// NG: @Service, @Controller, @Component → ロードしない