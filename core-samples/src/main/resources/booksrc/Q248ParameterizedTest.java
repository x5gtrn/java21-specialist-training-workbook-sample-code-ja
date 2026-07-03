// @CsvSource によるパラメータ化テスト
class EmailValidatorTest {

    @ParameterizedTest(name = "[{index}] {0} → valid={1}")
    @CsvSource({
        "alice@example.com,     true",  // 正常なメールアドレス
        "bob@company.co.jp,     true",  // 日本のドメイン
        "invalid-email,         false",  // @がない
        "@no-local-part.com,    false",  // ローカルパートがない
        "spaces in@email.com,   false",  // スペースを含む
        "'',                    false"  // 空文字列
    })
    void shouldValidateEmail(String email, boolean expected) {
        assertThat(EmailValidator.isValid(email)).isEqualTo(expected);
    }
}

// その他のパラメータソース

// @ValueSource — 単一パラメータ
@ParameterizedTest
@ValueSource(strings = {"alice@example.com", "bob@test.org", "carol@company.jp"})
void shouldAcceptValidEmails(String email) {
    assertThat(EmailValidator.isValid(email)).isTrue();
}

// @EnumSource — Enum の値
@ParameterizedTest
@EnumSource(value = Status.class, names = {"ACTIVE", "PENDING"})
void shouldAllowNonTerminalStatuses(Status status) {
    assertThat(status.isTerminal()).isFalse();
}

// @MethodSource — メソッドからパラメータを生成
@ParameterizedTest
@MethodSource("provideOrderData")
void shouldCalculateTotal(List<Item> items, BigDecimal expectedTotal) {
    assertThat(OrderCalculator.calculateTotal(items)).isEqualByComparingTo(expectedTotal);
}

static Stream<Arguments> provideOrderData() {
    return Stream.of(
        Arguments.of(List.of(new Item("A", 10.0), new Item("B", 20.0)), BigDecimal.valueOf(30.0)),
        Arguments.of(List.of(), BigDecimal.ZERO),
        Arguments.of(List.of(new Item("C", 99.99)), BigDecimal.valueOf(99.99))
    );
}

// @CsvFileSource — 外部 CSV ファイルから読み込み
@ParameterizedTest
@CsvFileSource(resources = "/test-data/emails.csv", numLinesToSkip = 1)
void shouldValidateFromFile(String email, boolean expected) {
    assertThat(EmailValidator.isValid(email)).isEqualTo(expected);
}