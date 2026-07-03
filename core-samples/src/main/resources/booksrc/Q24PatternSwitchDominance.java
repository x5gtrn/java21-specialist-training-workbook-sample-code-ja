String describe(Object value) {
    return switch (value) {
        case Object o -> "object";
        case String s -> "string";  // 支配されるため到達不能
    };
}
@@BLOCK@@
return switch (value) {
    case String s -> "string";
    case Object o -> "object";
};
@@BLOCK@@
switch (value) {
    case String s -> "any string";
    case String s when s.length() > 10 -> "long string";  // 到達不能
}
@@BLOCK@@
switch (value) {
    case String s when s.length() > 10 -> "long string";
    case String s -> "any string";
    default -> "other";
}
@@BLOCK@@
return switch (value) {
    case String s when s.isBlank() -> "blank";
    case String s -> "text";
    case Number n -> "number";
    case null -> "missing";
    default -> "other";
};
@@BLOCK@@
// 避けたい: 最初に広すぎる case
switch (value) {
    case Object o -> handleObject(o);
    case List<?> list -> handleList(list);
}
@@BLOCK@@
switch (status) {
    case String s when s.startsWith("ERR") -> "error";
    case String s -> "text";
    default -> "other";
}
@@BLOCK@@
sealed interface Payment permits CardPayment, BankPayment {}
record CardPayment(String cardNo) implements Payment {}
record BankPayment(String iban) implements Payment {}