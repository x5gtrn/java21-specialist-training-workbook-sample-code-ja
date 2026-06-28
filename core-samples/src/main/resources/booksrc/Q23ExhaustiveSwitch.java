// 変更前: Circle と Rectangle のみ
sealed interface Shape permits Circle, Rectangle {}
record Circle(double radius) implements Shape {}
record Rectangle(double width, double height) implements Shape {}

double area(Shape shape) {
    return switch (shape) {
        case Circle c -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.width() * r.height();
    };  // OK: 網羅的 → コンパイル OK
}

// POINT: Triangle を追加
sealed interface Shape permits Circle, Rectangle, Triangle {}
record Triangle(double base, double height) implements Shape {}

// → 上記の area() メソッドがコンパイルエラーに:
// エラー: the switch expression does not cover all possible input values
// → Triangle の case を追加するまでコンパイルが通らない

// 修正後
double area(Shape shape) {
    return switch (shape) {
        case Circle c -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.width() * r.height();
        case Triangle t -> 0.5 * t.base() * t.height();  // POINT: 追加
    };
}

// ⚠ default がある場合 → 問題の温床
double areaDangerous(Shape shape) {
    return switch (shape) {
        case Circle c -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.width() * r.height();
        default -> 0.0;  // Triangle 追加時もコンパイルエラーにならない！
    };  // → Triangle が黙って 0.0 を返すバグ
}