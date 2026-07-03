// POINT: @MockBean — Spring コンテキスト内の Bean をモックで置換
@WebMvcTest(UserController.class)
class ControllerTest {
    @MockBean  // POINT: Spring DI コンテナ内の User サービス Bean をモックに置換
    private UserService userService;
    // → UserController に注入される UserService がこのモックになる
}

// POINT: @Mock — 純粋な Mockito モック（Spring 無関係）
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock  // POINT: Spring コンテキストとは無関係の単体モック
    private UserRepository userRepository;
    @InjectMocks  // POINT: モックを手動注入
    private UserService userService;
}

// 使い分け:
// @MockBean → Spring 統合テスト（@WebMvcTest, @SpringBootTest 等）
// @Mock     → 純粋なユニットテスト（Spring コンテキスト不要、高速）