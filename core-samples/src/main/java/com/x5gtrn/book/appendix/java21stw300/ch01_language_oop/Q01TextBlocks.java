package com.x5gtrn.book.appendix.java21stw300.ch01_language_oop;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題1: テキストブロックの付随的空白（incidental whitespace）の除去規則。
 *
 * 閉じ """ の位置によって、各行の先頭空白がどう扱われるかが変わる。
 */
public final class Q01TextBlocks implements Sample {

    public String chapter() { return "01_Language_OOP"; }
    public int problem()    { return 1; }
    public String title()   { return "テキストブロックと付随的空白の除去"; }

    public void run() {
        // 閉じ """ が左端にある場合 → 先頭空白はすべて保持される
        String html1 = """
                <html>
                    <body>Hello</body>
                </html>
""";

        // 閉じ """ がコンテンツと同じインデントにある場合 → 共通の先頭空白が除去される
        String html2 = """
                <html>
                    <body>Hello</body>
                </html>
                """;

        System.out.println("html1 (閉じ \"\"\" が左端 → 空白保持):");
        System.out.println(html1.replace("\n", "\\n"));
        System.out.println();
        System.out.println("html2 (閉じ \"\"\" が整列 → 共通空白除去):");
        System.out.println(html2.replace("\n", "\\n"));
    }
}
