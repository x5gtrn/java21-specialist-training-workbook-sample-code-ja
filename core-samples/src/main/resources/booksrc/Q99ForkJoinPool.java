// ForkJoinPool + RecursiveTask の例: 巨大配列の合計
class SumTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10_000;
    private final long[] array;
    private final int lo, hi;

    SumTask(long[] array, int lo, int hi) {
        this.array = array; this.lo = lo; this.hi = hi;
    }

    @Override
    protected Long compute() {
        if (hi - lo <= THRESHOLD) {
            // ベースケース: 直接計算
            long sum = 0;
            for (int i = lo; i < hi; i++) sum += array[i];
            return sum;
        }
        // 分割ケース: 2つのサブタスクに分割
        int mid = (lo + hi) / 2;
        SumTask left = new SumTask(array, lo, mid);
        SumTask right = new SumTask(array, mid, hi);

        left.fork();  // POINT: 左タスクを自分のデックに追加
        long rightResult = right.compute();  // 右タスクを現在のスレッドで実行
        long leftResult = left.join();  // 左タスクの結果を待機

        return leftResult + rightResult;
    }
}

// 使用
ForkJoinPool pool = new ForkJoinPool();  // デフォルト: Runtime.getRuntime().availableProcessors()
long[] bigArray = new long[10_000_000];
long total = pool.invoke(new SumTask(bigArray, 0, bigArray.length));

// POINT: work-stealing の動作イメージ
// Thread-1 のデック: [Task-A, Task-B, Task-C]  ← 自分のタスク
// Thread-2 のデック: []  ← アイドル
//
// Thread-2 が Thread-1 のデックの先頭（Task-A）を盗む
// Thread-1: [Task-B, Task-C] → Task-C を末尾から取得して実行
// Thread-2: [Task-A] → 盗んだタスクを実行
//
// POINT: 先頭から盗む理由: 大きなタスク（分割前）が先頭にあるため、
// 盗んだ側でさらに分割→並列化が期待できる

// Java 21 との関連:
// ForkJoinPool.commonPool() → parallelStream() のデフォルトプール
// Virtual Threads のスケジューラも内部的に ForkJoinPool を使用