public record EmailAddress(String value) {
    // 実装はここに記述
}
@@BLOCK@@
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record EmailAddress(String value) {
    private static final Pattern SIMPLE_EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
                    Pattern.CASE_INSENSITIVE);

    public EmailAddress {
        Objects.requireNonNull(value, "value");

        value = value.trim().toLowerCase(Locale.ROOT);

        if (value.isBlank()) {
            throw new IllegalArgumentException("email must not be blank");
        }
        if (!SIMPLE_EMAIL.matcher(value).matches()) {
            throw new IllegalArgumentException("invalid email address: " + value);
        }
    }
}
@@BLOCK@@
public record EmailAddress(String value) {
    public EmailAddress(String value) {
        this.value = value;
    }
}
@@BLOCK@@
public record EmailAddress(String value) {
    public EmailAddress {
        value = value.trim();
        // コンストラクタ末尾で、コンパイラが this.value = value 相当を行う
    }
}
@@BLOCK@@
public record EmailAddress(String value) {
    public EmailAddress(String value) {
        Objects.requireNonNull(value, "value");
        value = value.trim().toLowerCase(Locale.ROOT);
        if (value.isBlank()) {
            throw new IllegalArgumentException("email must not be blank");
        }
        this.value = value;  // 明示的 canonical constructor では代入を書く
    }
}
@@BLOCK@@
public record EmailAddress(String value) {
    @Override
    public String value() {
        return value.trim().toLowerCase(Locale.ROOT);
    }
}