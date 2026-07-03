// POINT: B: 最も柔軟で正確な宣言（JDK 標準ライブラリで使用されるパターン）
public static <T extends Comparable<? super T>> T findMax(Collection<T> items) {
    return items.stream().max(Comparator.naturalOrder()).orElseThrow();
}

// ■ ? super T が必要な理由
// 例: Animal が Comparable<Animal> を実装、Dog extends Animal
class Animal implements Comparable<Animal> {
    @Override public int compareTo(Animal other) { /* ... */ }
}
class Dog extends Animal { }  // Dog 自身は Comparable を直接実装していない

// ? super T あり: findMax(List<Dog>) OK:
// → T = Dog, Comparable<? super Dog> → Comparable<Animal> にマッチ

// ? super T なし: <T extends Comparable<T>> → findMax(List<Dog>) NG:
// → T = Dog, Comparable<Dog> を要求 → Dog は Comparable<Dog> を実装していない

// ■ JDK 標準での使用例
// Collections.max() のシグネチャ:
// public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> c)
// ^^^^^^^^^^^^^^^^  ← 型消去時に Object を保証（バイナリ互換性）
// ^^^^^^^^^^^^^^^ ← 本質的な制約

// ■ 型パラメータ宣言のルール
// extends のみ使用可能（インターフェースでも extends）
// <T extends Comparable>           OK:
// <T extends Serializable & Comparable<T>>  OK: 複数境界（&で結合）
// <T super Comparable<T>>          NG: コンパイルエラー（super は型パラメータ宣言で不可）
// <T implements Comparable<T>>     NG: コンパイルエラー（implements は不可）