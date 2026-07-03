String describe(Object value) {
    return switch (value) {
        case null -> "missing";
        case String s -> "string: " + s;
        case Integer i -> "int: " + i;
        default -> "other";
    };
}
@@BLOCK@@
switch (value) {
    case Object o -> "object";
}
@@BLOCK@@
return switch (value) {
    case String s -> s;
    case null, default -> "unknown";
};
@@BLOCK@@
return switch (value) {
    case null -> "missing";
    case String s -> s.strip();
    default -> value.toString();
};
@@BLOCK@@
Object nonNull = Objects.requireNonNull(value, "value");
return switch (nonNull) {
    case String s -> s;
    default -> nonNull.toString();
};
@@BLOCK@@
return switch (value) {
    case null -> Status.MISSING;
    case String s when s.isBlank() -> Status.BLANK;
    case String s -> Status.PRESENT;
    default -> Status.UNSUPPORTED;
};