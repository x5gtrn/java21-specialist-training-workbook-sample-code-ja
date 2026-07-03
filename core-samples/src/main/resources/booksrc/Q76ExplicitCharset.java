String config = Files.readString(path, StandardCharsets.UTF_8);

Files.writeString(path, content, StandardCharsets.UTF_8,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING);
@@BLOCK@@
byte[] bytes = Files.readAllBytes(path);
// 悪い例: UTF-8 を無視して byte を char 扱い
for (byte b : bytes) {
    char c = (char) b;
}
@@BLOCK@@
String text = new String(bytes, StandardCharsets.UTF_8);
@@BLOCK@@
String text = Files.readString(path, StandardCharsets.UTF_8);
Files.writeString(path, text, StandardCharsets.UTF_8);
@@BLOCK@@
String text = "東京, café, 🚀";
Files.writeString(path, text, StandardCharsets.UTF_8);
String restored = Files.readString(path, StandardCharsets.UTF_8);
assertEquals(text, restored);
@@BLOCK@@
byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
String restored = new String(bytes, StandardCharsets.UTF_8);