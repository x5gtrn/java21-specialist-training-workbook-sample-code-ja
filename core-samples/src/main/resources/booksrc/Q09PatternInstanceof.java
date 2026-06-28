void process(Object value) {
    if (value instanceof String s && s.length() > 10) {
        System.out.println(s.toUpperCase());
    } else {
        // ここで s を使えるか？
    }
}
@@BLOCK@@
// ケース1: value が String ではない
value instanceof String s  // false

// ケース2: value は String だが length <= 10
value instanceof String s  // true
s.length() > 10  // false
@@BLOCK@@
if (value instanceof String s && s.length() > 10) {
    // OK: ここでは s が使える
}
@@BLOCK@@
if (value instanceof String s || s.length() > 10) {
    // NG 例
}
@@BLOCK@@
if (!(value instanceof String s)) {
    return;
}

// ここに到達するのは value が String の場合だけ
System.out.println(s.toUpperCase());  // OK 例
@@BLOCK@@
Object value = null;
if (value instanceof String s) {
    // ここには入らない
}
@@BLOCK@@
void render(Object value) {
    if (!(value instanceof String text)) {
        throw new IllegalArgumentException("String value is required");
    }

    // ここから下では text が確実に String として束縛されている
    String normalized = text.strip();
    if (normalized.isEmpty()) {
        throw new IllegalArgumentException("blank text");
    }

    System.out.println(normalized.toUpperCase());
}