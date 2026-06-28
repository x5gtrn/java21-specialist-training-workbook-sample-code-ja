// POINT: B + C + E: アノテーション定義
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)  // B: バリデータ指定
@Target({ElementType.FIELD, ElementType.PARAMETER,
         ElementType.METHOD, ElementType.ANNOTATION_TYPE})  // E: 使用箇所
@Retention(RetentionPolicy.RUNTIME)  // E: 実行時保持
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number format";  // C: 必須属性
    Class<?>[] groups() default {};  // C: 必須属性
    Class<? extends Payload>[] payload() default {};  // C: 必須属性

    // カスタム属性の追加も可能
    String region() default "JP";
}

// POINT: D: バリデータ実装
public class PhoneNumberValidator
        implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final Map<String, Pattern> PATTERNS = Map.of(
        "JP", Pattern.compile("^0[0-9]{9,10}$"),
        "US", Pattern.compile("^\\+1[0-9]{10}$"),
        "INTL", Pattern.compile("^\\+[1-9][0-9]{1,14}$")
    );
    private Pattern pattern;

    @Override
    public void initialize(ValidPhoneNumber annotation) {
        // POINT: アノテーションのカスタム属性を読み取り
        this.pattern = PATTERNS.getOrDefault(annotation.region(), PATTERNS.get("INTL"));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;  // POINT: @NotNull と組み合わせて使用
        return pattern.matcher(value).matches();
    }
}

// POINT: 使用例
public record ContactRequest(
    @NotBlank String name,
    @ValidPhoneNumber(region = "JP") @NotNull String phone,
    @Email String email
) {}

// コントローラ
@PostMapping("/contacts")
public ResponseEntity<Contact> create(@Valid @RequestBody ContactRequest req) {
    return ResponseEntity.ok(contactService.create(req));
}
// → phone が "090-1234-5678" のような不正な形式なら
// MethodArgumentNotValidException → 400 Bad Request