@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private OrderService orderService;

    @Test
    void createOrderShouldReturn201() throws Exception {
        when(orderService.create(any())).thenReturn(new Order(1L, "OK"));

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"productId": "SKU-001", "quantity": 2}
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(header().exists("Location"));
    }

    @Test
    void invalidRequestShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"productId": "", "quantity": -1}
                """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }
}
// POINT: サーバー起動なし、ネットワーク通信なし → 高速（ミリ秒単位）
// POINT: Spring Security のフィルタも適用される（@WithMockUser で認証テスト可能）