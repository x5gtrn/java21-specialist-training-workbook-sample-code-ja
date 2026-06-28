// B: Predicate 合成
Predicate<String> notEmpty = s -> !s.isEmpty();
Predicate<String> startsWithA = s -> s.startsWith("A");
Predicate<String> combined = notEmpty.and(startsWithA).negate();
// 等価: s -> !((!s.isEmpty()) && s.startsWith("A"))

// C: Function 合成
Function<String, String> trim = String::trim;
Function<String, String> toUpper = String::toUpperCase;
Function<String, Integer> length = String::length;

Function<String, Integer> pipeline = trim.andThen(toUpper).andThen(length);
// " hello " → "hello" → "HELLO" → 5

Function<String, Integer> reversed = length.compose(toUpper).compose(trim);
// compose は逆順: trim → toUpper → length

// E: Consumer 合成
Consumer<String> log = s -> System.out.println("Log: " + s);
Consumer<String> save = s -> database.save(s);
Consumer<String> combined = log.andThen(save);
// log を実行してから save を実行