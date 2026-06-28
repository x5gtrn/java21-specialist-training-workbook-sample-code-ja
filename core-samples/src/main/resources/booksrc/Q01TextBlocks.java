// 閉じ"""が左端にある場合 → 先頭空白はすべて保持される
String html1 = """
        <html>
            <body>Hello</body>
        </html>
""";
// 結果: "        <html>\n            <body>Hello</body>\n        </html>\n"

// 閉じ"""がコンテンツと同じインデントにある場合 → 共通空白が除去される
String html2 = """
        <html>
            <body>Hello</body>
        </html>
        """;
// 結果: "<html>\n    <body>Hello</body>\n</html>\n"

// 閉じ"""がコンテンツより右にある場合 → 末尾空白は影響しない（共通先頭空白のみ除去）