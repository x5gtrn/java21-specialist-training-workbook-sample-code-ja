try (Arena arena = Arena.ofConfined()) {
    MemorySegment segment = arena.allocate(1024);
    segment.set(ValueLayout.JAVA_INT, 0, 42);
}
// ここで segment へアクセスしてはいけない
@@BLOCK@@
try (Arena arena = Arena.ofConfined()) {
    MemorySegment segment = arena.allocate(ValueLayout.JAVA_INT);
    segment.set(ValueLayout.JAVA_INT, 0, 123);
}
@@BLOCK@@
// 擬似例: ネイティブシグネチャの設計確認が必要
// int strlen(const char* s)