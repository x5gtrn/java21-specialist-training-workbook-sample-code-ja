// ■ G1 GC のリージョンベースアーキテクチャ
//
// ヒープ = [E][E][S][O][O][H][O][E][O][S][O][O][E][  ][O][O]
// E=Eden, S=Survivor, O=Old, H=Humongous, [ ]=空きリージョン
//
// Mixed GC: ガベージが多い Old リージョンを選択
// [O: 20%生存] → POINT: 選択（80%回収可能）
// [O: 90%生存] → 今回はスキップ（回収効率が低い）
// [O: 30%生存] → POINT: 選択（70%回収可能）
//
// エバキュエーション: 生存オブジェクトを別リージョンにコピー
// → 元のリージョンは完全に空になる → フラグメンテーション解消

// ■ G1 GC の主要 JVM フラグ
// -XX:+UseG1GC                              Java 9+ でデフォルト
// -XX:MaxGCPauseMillis=200                  目標ポーズ時間（努力目標）
// -XX:G1HeapRegionSize=4m リージョンサイズ（1MB〜32MB、2の冪乗）
// -XX:InitiatingHeapOccupancyPercent=45     この占有率で Concurrent Marking 開始
// -XX:G1MixedGCCountTarget=8               Mixed GC の目標回数

// ■ G1 の動作フロー
// 1. Young GC (STW): Eden→Survivor のコピー（短いポーズ）
// 2. Concurrent Marking: Old リージョンの生存オブジェクトを並行マーキング
// （アプリケーションスレッドと並行実行 → ポーズは最小限）
// 3. Remark (STW): マーキング結果の確定（短いポーズ）
// 4. Cleanup: 完全に空の Old リージョンを即座に回収
// 5. Mixed GC (STW): Young + 選択された Old リージョンを同時回収・コンパクション
// → POINT: ガベージが多いリージョンを優先選択（Garbage-First の由来）
// → POINT: コピーによるコンパクション → フラグメンテーション解消

// ■ Full GC（最後の手段）
// Mixed GC でも追いつかない場合 → Full GC（単一スレッド、長い STW）
// → 本番で Full GC が発生する場合はチューニングが必要