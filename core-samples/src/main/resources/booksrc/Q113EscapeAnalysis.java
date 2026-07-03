record Point(int x, int y) {}

int distanceSquared(int x, int y) {
    Point p = new Point(x, y);
    return p.x() * p.x() + p.y() * p.y();
}
@@BLOCK@@
Point create(int x, int y) {
    return new Point(x, y);  // 呼び出し元へ返るためエスケープする
}

void store(int x, int y) {
    this.lastPoint = new Point(x, y);  // フィールドへ保存されるためエスケープする
}

void publish(int x, int y) {
    queue.add(new Point(x, y));  // 共有コレクションへ渡るためエスケープする
}
@@BLOCK@@
long calculate(List<Order> orders) {
    long total = 0;
    for (Order order : orders) {
        MoneyParts parts = split(order.amount());
        total += parts.major() * 100 + parts.minor();
    }
    return total;
}
@@BLOCK@@
// 読みやすく、JIT が最適化しやすい可能性がある
for (Order order : orders) {
    TaxResult tax = calculator.calculate(order);
    total += tax.amount();
}

// 安易な再利用は状態バグを作りやすい
MutableTaxResult reusable = new MutableTaxResult();
for (Order order : orders) {
    calculator.calculateInto(order, reusable);
    total += reusable.amount();
}