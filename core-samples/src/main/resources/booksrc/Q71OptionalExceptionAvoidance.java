// OK: E: 正しい Optional チェーン
public String getDisplayName(String email) {
    return userRepository.findByEmail(email)  // Optional<User>
        .map(User::getDisplayName)  // Optional<String>
        .orElse("Anonymous");  // String
}
// ユーザー存在: Optional[User] → map → Optional["Alice"] → orElse → "Alice"
// ユーザー不在: Optional.empty() → map → Optional.empty() → orElse → "Anonymous"

// NG: A: get() は値がない場合 NoSuchElementException をスロー
userRepository.findByEmail(email).get();  // POINT: 危険！例外が発生する可能性

// NG: B: Optional に対する null 比較は不適切
// findByEmail() は Optional を返す → Optional 自体が null になることはない
// Optional.empty() と null は異なる概念

// NG: D: Optional.of() に Optional を渡すと Optional<Optional<User>> になる
// Optional.of(Optional.empty()) → NullPointerException ではないが意味がない

// NG: C: orElseThrow() は値がない場合に例外をスロー → "Anonymous"返却と矛盾

// ■ Optional の実務パターン
// フィルタ: opt.filter(user -> user.isActive())
// flatMap: opt.flatMap(user -> user.getAddress()) ← getAddress()が Optional を返す場合
// or():    opt.or(() -> findInLegacySystem(email)) ← 代替ソースの遅延評価
// ifPresentOrElse(): opt.ifPresentOrElse(user -> greet(user), () -> logMissing())