List<Record> records = paths.stream()
        .map(path -> {
            try {
                return parse(path);
            } catch (IOException e) {
                throw new ImportFileException("Failed to parse " + path, e);
            }
        })
        .toList();
@@BLOCK@@
List<Record> records = new ArrayList<>();
for (Path path : paths) {
    records.add(parse(path));  // parse throws IOException
}
@@BLOCK@@
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);  // throws IOException とは宣言されていない
}
@@BLOCK@@
paths.stream()
        .map(path -> parse(path))  // parse throws IOException
        .toList();
@@BLOCK@@
record ImportResult(Path path, Record record, Exception error) {
    static ImportResult success(Path path, Record record) {
        return new ImportResult(path, record, null);
    }
    static ImportResult failure(Path path, Exception error) {
        return new ImportResult(path, null, error);
    }
}
@@BLOCK@@
paths.stream()
        .map(path -> {
            try {
                return parse(path);
            } catch (IOException e) {
                throw new ImportFileException("failed: " + path, e);
            }
        })
        .toList();
@@BLOCK@@
throw new ImportFileException(
        "failed to parse import file: path=%s".formatted(path.getFileName()),
        e);