try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
    List<String> errors = lines
            .filter(line -> line.contains("ERROR"))
            .limit(100)
            .toList();
}
@@BLOCK@@
Stream<String> bad(Path path) throws IOException {
    return Files.lines(path);  // 呼び出し側が close 責務を知らない可能性
}
@@BLOCK@@
List<String> findErrors(Path path) throws IOException {
    try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
        return lines.filter(line -> line.contains("ERROR")).toList();
    }
}
@@BLOCK@@
try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
    return lines.filter(this::matches).toList();
}
@@BLOCK@@
try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
    long errorCount = lines.filter(line -> line.contains("ERROR")).count();
}
@@BLOCK@@
// close 責務が見えにくい
Stream<String> openLines(Path path) throws IOException;

// 結果だけ返す
List<String> readMatchingLines(Path path) throws IOException;