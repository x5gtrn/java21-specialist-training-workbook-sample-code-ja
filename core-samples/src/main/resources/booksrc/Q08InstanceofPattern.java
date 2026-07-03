public String describe(Object obj) {
    if (obj instanceof String) {
        String s = (String) obj;
        return "String of length " + s.length();
    } else if (obj instanceof Integer) {
        Integer i = (Integer) obj;
        return "Integer value " + i;
    } else if (obj instanceof String) {
        return "duplicate";
    }
    return "unknown";
}
@@BLOCK@@
public String describe(Object obj) {
    if (obj instanceof String s) {  // POINT: パターン変数を使用
        return "String of length " + s.length();  // キャスト不要
    } else if (obj instanceof Integer i) {  // POINT: パターン変数を使用
        return "Integer value " + i;  // キャスト不要
    }
    return "unknown";
}