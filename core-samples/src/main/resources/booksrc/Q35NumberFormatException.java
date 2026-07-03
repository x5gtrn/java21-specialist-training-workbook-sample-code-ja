public List<SalesRecord> importCsv(Path csvFile) throws IOException {
    List<SalesRecord> records = new ArrayList<>();
    List<String> errors = new ArrayList<>();
    List<String> lines = Files.readAllLines(csvFile);

    for (int i = 1; i < lines.size(); i++) {  // ヘッダースキップ
        String[] fields = lines.get(i).split(",");
        try {
            int quantity = Integer.parseInt(fields[2].trim());
            double price = Double.parseDouble(fields[3].trim());
            records.add(new SalesRecord(fields[0], fields[1], quantity, price));
        } catch (NumberFormatException e) {
            // POINT: フィールド/レコード単位でキャッチ → 処理は継続
            errors.add("Line %d: Invalid number in '%s' - %s"
                .formatted(i + 1, fields[2], e.getMessage()));
            // → 不正レコードはスキップ、残りは正常にインポート
        } catch (ArrayIndexOutOfBoundsException e) {
            errors.add("Line %d: Insufficient columns".formatted(i + 1));
        }
    }

    if (!errors.isEmpty()) {
        log.warn("Import completed with {} errors out of {} records",
                 errors.size(), lines.size() - 1);
        errors.forEach(log::warn);
    }
    return records;
}

// POINT: Java 21 での代替アプローチ: Optional 的なパースユーティリティ
public static OptionalInt tryParseInt(String s) {
    try {
        return OptionalInt.of(Integer.parseInt(s.trim()));
    } catch (NumberFormatException e) {
        return OptionalInt.empty();
    }
}
// 使用: tryParseInt(field).orElse(0)