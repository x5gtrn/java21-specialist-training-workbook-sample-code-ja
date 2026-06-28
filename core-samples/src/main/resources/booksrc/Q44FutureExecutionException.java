try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    Future<Report> future = executor.submit(() -> generateReport(request));
    Report report = future.get();
}
@@BLOCK@@
try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    Future<Report> future = executor.submit(() -> generateReport(request));

    try {
        Report report = future.get();
        publish(report);
    } catch (ExecutionException e) {
        Throwable cause = e.getCause();
        throw new ReportGenerationException("failed to generate report", cause);
    }
}
@@BLOCK@@
Future<?> future = executor.submit(() -> {
    throw new IllegalStateException("broken");
});

future.get();  // ExecutionException がスローされる
@@BLOCK@@
try {
    return future.get();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    throw new TaskCancelledException("waiting for report was interrupted", e);
}
@@BLOCK@@
try {
    return future.get();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    throw new TaskCancelledException("interrupted while waiting", e);
} catch (ExecutionException e) {
    throw new TaskFailedException("task failed", e.getCause());
}