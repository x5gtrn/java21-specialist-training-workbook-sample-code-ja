try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
    MappedByteBuffer buffer = channel.map(
            FileChannel.MapMode.READ_ONLY,
            0,
            channel.size());

    byte first = buffer.get(0);
}
@@BLOCK@@
long offset = 0;
long size = Math.min(chunkSize, channel.size() - offset);
MappedByteBuffer chunk = channel.map(FileChannel.MapMode.READ_ONLY, offset, size);
@@BLOCK@@
try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
    MappedByteBuffer buffer = channel.map(
            FileChannel.MapMode.READ_ONLY, 0, channel.size());
    process(buffer);
}
@@BLOCK@@
try (InputStream in = Files.newInputStream(path);
     BufferedInputStream buffered = new BufferedInputStream(in)) {
    process(buffered);
}