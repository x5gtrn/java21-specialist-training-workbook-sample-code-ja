// レコード宣言
public record TimeSeriesPoint(long timestamp, double value, String label) {}

// 上記は以下と等価なクラスを自動生成する:
// - private final long timestamp;
// - private final double value;
// - private final String label;
// - 全引数コンストラクタ
// - timestamp(), value(), label() アクセサメソッド
// - equals(), hashCode(), toString()

TimeSeriesPoint p1 = new TimeSeriesPoint(1000L, 3.14, "temp");
TimeSeriesPoint p2 = new TimeSeriesPoint(1000L, 3.14, "temp");
System.out.println(p1.equals(p2));  // true（全コンポーネントが等しいため）
System.out.println(p1.hashCode() == p2.hashCode());  // true
System.out.println(p1);  // TimeSeriesPoint[timestamp=1000, value=3.14, label=temp]
@@BLOCK@@
public record TimeSeriesPoint(long timestamp, double value, String label) {
    // コンパクトコンストラクタ
    public TimeSeriesPoint {
        // OK: パラメータを正規化（OK）
        label = label.trim().toUpperCase();
        // OK: バリデーション（OK）
        if (value < 0) throw new IllegalArgumentException("value must be non-negative");
        // NG: this.timestamp = timestamp; は書けない（コンパイラが末尾で自動代入する）
    }
}