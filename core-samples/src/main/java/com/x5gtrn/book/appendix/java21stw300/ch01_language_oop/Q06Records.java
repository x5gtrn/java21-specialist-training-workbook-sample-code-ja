package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題6: record の equals / hashCode / toString は全コンポーネントから自動生成される。
 *
 * コンパクトコンストラクタでは引数を正規化できるが、this.field への直接代入はできない
 * （コンパイラが末尾で自動代入するため）。
 */
public final class Q06Records implements Sample {

    public record TimeSeriesPoint(long timestamp, double value, String label) {
        // コンパクトコンストラクタ: 引数（label）を正規化してから自動代入される
        public TimeSeriesPoint {
            label = label.trim().toUpperCase();
            if (value < 0) {
                throw new IllegalArgumentException("value must be >= 0");
            }
            // this.timestamp = timestamp;  // ← これはコンパイルエラー: cannot assign to final variable
        }
    }

    public String chapter() { return "01_Language_OOP"; }
    public int problem()    { return 6; }
    public String title()   { return "record の equals / hashCode / toString"; }

    public void run() {
        var p1 = new TimeSeriesPoint(1000L, 3.14, "temp");
        var p2 = new TimeSeriesPoint(1000L, 3.14, "temp");

        System.out.println("toString : " + p1);
        System.out.println("equals   : " + p1.equals(p2) + "  (全コンポーネントが等しい)");
        System.out.println("hashCode : " + (p1.hashCode() == p2.hashCode()));
        System.out.println("label正規化: \"temp\" -> \"" + p1.label() + "\"");
    }
}
