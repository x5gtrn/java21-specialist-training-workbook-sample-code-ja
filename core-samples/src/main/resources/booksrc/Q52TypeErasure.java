public void process(List<String> strings) { /* ... */ }
public void process(List<Integer> integers) { /* ... */ }
@@BLOCK@@
// コンパイル前（ソースコード）
public void process(List<String> strings) { /* 省略 */ }
public void process(List<Integer> integers) { /* 省略 */ }

// 型消去後（バイトコードレベル）
public void process(List strings) { /* 省略 */ }  // ← 同一シグネチャ
public void process(List integers) { /* 省略 */ }  // ← 同一シグネチャ → コンパイルエラー

// ■ 型消去の具体的な影響
// 1. ジェネリック型によるオーバーロード不可
// 2. instanceof でジェネリック型チェック不可
if (list instanceof List<String>) { }  // NG: コンパイルエラー
if (list instanceof List<?>) { }  // OK: ワイルドカードは可

// 3. ジェネリック型の配列作成不可
// T[] array = new T[10];              // NG: コンパイルエラー
// List<String>[] lists = new List<String>[10]; // NG: コンパイルエラー

// ■ 解決策
// 方法1: メソッド名を変える
public void processStrings(List<String> strings) { /* 省略 */ }
public void processIntegers(List<Integer> integers) { /* 省略 */ }

// 方法2: ジェネリックメソッドに統一
public <T> void process(List<T> items, Class<T> type) { /* 省略 */ }

// 方法3: ワイルドカードで統一して instanceof で分岐
public void process(List<?> items) {
    if (!items.isEmpty() && items.get(0) instanceof String) { /* 省略 */ }
}