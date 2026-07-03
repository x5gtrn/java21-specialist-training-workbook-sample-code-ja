// ObjectInputFilter の使用例

// 1. ストリームごとのフィルタ設定
ObjectInputStream ois = new ObjectInputStream(inputStream);
ois.setObjectInputFilter(info -> {
    if (info.serialClass() != null) {
        String className = info.serialClass().getName();
        // ホワイトリスト方式: 許可されたクラスのみデシリアライズ
        if (className.startsWith("com.myapp.dto.")) {
            return ObjectInputFilter.Status.ALLOWED;
        }
        return ObjectInputFilter.Status.REJECTED;
    }
    // 配列の深さやサイズの制限
    if (info.depth() > 10) return ObjectInputFilter.Status.REJECTED;
    if (info.references() > 1000) return ObjectInputFilter.Status.REJECTED;
    return ObjectInputFilter.Status.UNDECIDED;
});

// 2. JVM ワイドのフィルタ設定（jdk.serialFilter は Java 9〜）
// jdk.serialFilter システムプロパティで設定
// java -Djdk.serialFilter="com.myapp.**;!*" -jar app.jar
// → com.myapp 配下のみ許可、他はすべて拒否
//
// ※注意1（評価順）: パターンは左から評価され、最初に一致したものが優先。
//   許可パターンを先に置き、全拒否の "!*" は最後に書く。
//   "!*;com.myapp.**" の順だと com.myapp のクラスすら拒否される。
// ※注意2（JDK 型）: DTO が内部で使う JDK 型（ArrayList, HashMap など）も
//   許可が必要。"com.myapp.**;!*" だけだと、ArrayList を持つ DTO の復元が
//   InvalidClassException で失敗する。必要な型を明示的に許可する。
//   例: "java.base/*;com.myapp.**;!*"

// 3. 推奨: 可能な限り Java シリアライゼーションを避ける
// JSON (Jackson/Gson) や Protocol Buffers を代替として使用