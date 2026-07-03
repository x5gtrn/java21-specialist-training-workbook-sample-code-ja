public class ExpensiveService {
    private ExpensiveService() { /* 高コストな初期化処理 */ }

    // POINT: Holder は ExpensiveService.getInstance() が初めて呼ばれるまでロードされない
    private static class Holder {
        private static final ExpensiveService INSTANCE = new ExpensiveService();
    }

    public static ExpensiveService getInstance() {
        return Holder.INSTANCE;
        // POINT: 初回呼び出し → Holder クラスロード → INSTANCE 初期化
        // POINT: JVM のクラス初期化はスレッドセーフ保証（JLS §12.4.2）
        // POINT: 2回目以降 → 既にロード済みの INSTANCE を返すだけ
    }
}
// synchronized も volatile も不要 → オーバーヘッドゼロ
// Double-Checked Locking の代替として推奨