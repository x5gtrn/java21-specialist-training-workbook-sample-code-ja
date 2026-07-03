Path base = Path.of("/srv/files").toAbsolutePath().normalize();
Path requested = base.resolve(userInput).normalize();

if (!requested.startsWith(base)) {
    throw new SecurityException("Path traversal attempt");
}

return Files.readAllBytes(requested);
@@BLOCK@@
Path realBase = base.toRealPath();
Path realRequested = requested.toRealPath();
if (!realRequested.startsWith(realBase)) {
    throw new SecurityException("Outside base directory");
}
@@BLOCK@@
Path base = configuredBase.toAbsolutePath().normalize();
Path target = base.resolve(userInput).normalize();
if (!target.startsWith(base)) {
    throw new SecurityException("invalid path");
}
@@BLOCK@@
final class FileStore {
    private final Path base;

    FileStore(Path configuredBase) {
        this.base = configuredBase.toAbsolutePath().normalize();
    }

    Path resolve(String input) {
        Path target = base.resolve(input).normalize();
        if (!target.startsWith(base)) {
            throw new SecurityException("outside base");
        }
        return target;
    }
}
@@BLOCK@@
Path temp = Files.createTempFile(base, "upload-", ".tmp");