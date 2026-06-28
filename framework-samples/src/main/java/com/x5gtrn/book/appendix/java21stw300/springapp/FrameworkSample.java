package com.x5gtrn.book.appendix.java21stw300.springapp;

/**
 * フレームワーク編サンプルの共通インターフェース。各実装は Spring Bean（@Component）として登録し、
 * 必要な依存（EntityManagerFactory / JdbcTemplate / Repository 等）はコンストラクタ注入で受け取る。
 */
public interface FrameworkSample {

    /** 章の識別子（例: "13_JPA_Persistence"）。 */
    String chapter();

    /** 問題番号。 */
    int problem();

    /** 短いタイトル。 */
    String title();

    /** Preview 機能を使うか（フレームワーク編は基本的に false）。 */
    default boolean preview() {
        return false;
    }

    /** サンプル本体。標準出力に結果を書き出す。 */
    void run() throws Exception;

    /** API 用の安定 ID（クラス単純名）。 */
    default String id() {
        return getClass().getSimpleName();
    }
}
