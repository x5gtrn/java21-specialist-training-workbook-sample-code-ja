List<String> messages = List.of("  INFO: Started  ", "  WARN: Timeout  ");
List<String> cleaned = messages.stream()
    .map(String::strip)  // POINT: この行
    .toList();
@@BLOCK@@
// 4種類のメソッド参照

// 1. static メソッド参照: ClassName::staticMethod
Function<String, Integer> parser = Integer::parseInt;
// 等価: s -> Integer.parseInt(s)

// 2. バウンドインスタンスメソッド参照: instance::instanceMethod
String prefix = "LOG: ";
Function<String, String> adder = prefix::concat;
// 等価: s -> prefix.concat(s)
// POINT: 特定のインスタンス（prefix）に束縛されている

// 3. アンバウンドインスタンスメソッド参照: ClassName::instanceMethod ← 本問
Function<String, String> stripper = String::strip;
// 等価: s -> s.strip()
// POINT: 第1引数がレシーバになる

// 4. コンストラクタ参照: ClassName::new
Supplier<ArrayList<String>> listFactory = ArrayList::new;
// 等価: () -> new ArrayList<>()