package com.x5gtrn.book.appendix.java21stw300.framework;

/**
 * 書籍「Java 21 スペシャリスト育成問題集」のサンプルコード 1 件を表す共通インターフェース。
 *
 * <p>各実装クラスは、対応する問題のコードを {@link #run()} の中で実行し、
 * 結果を {@code System.out} に出力する。出力は {@link SampleRunner} が捕捉して
 * ブラウザ画面に表示する。</p>
 */
public interface Sample {

    /** 章の識別子（例: {@code "01_Language_OOP"}）。原稿のファイル名に対応する。 */
    String chapter();

    /** 問題番号（例: {@code 1}）。 */
    int problem();

    /** サンプルの短いタイトル。 */
    String title();

    /**
     * Java 21 の Preview 機能を使うサンプルかどうか。
     * {@code true} の場合、コンパイル・実行ともに {@code --enable-preview} が必要。
     */
    default boolean preview() {
        return false;
    }

    /** サンプルコード本体。標準出力に結果を書き出す。 */
    void run() throws Exception;

    /** 一覧・API で使う安定した ID（クラスの単純名）。 */
    default String id() {
        return getClass().getSimpleName();
    }
}
