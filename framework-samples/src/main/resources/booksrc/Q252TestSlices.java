// ■ @WebMvcTest — コントローラ層のみ
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;  // サービスはモック

    @Test
    void shouldReturnUser() throws Exception {
        when(userService.findById(1L)).thenReturn(new User(1L, "Alice"));
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alice"));
    }
}

// ■ @DataJpaTest — リポジトリ層のみ（インメモリ DB 使用）
@DataJpaTest
class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Autowired private TestEntityManager entityManager;

    @Test
    void shouldFindByEmail() {
        entityManager.persistAndFlush(new User("Alice", "alice@test.com"));
        Optional<User> found = userRepository.findByEmail("alice@test.com");
        assertThat(found).isPresent();
    }
}
// POINT: @DataJpaTest は Web サーバーを起動しない、トランザクション自動ロールバック