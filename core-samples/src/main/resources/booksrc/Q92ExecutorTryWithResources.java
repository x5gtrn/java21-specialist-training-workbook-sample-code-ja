try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    Future<Result> a = executor.submit(this::taskA);
    Future<Result> b = executor.submit(this::taskB);

    use(a.get(), b.get());
}
@@BLOCK@@
executor.shutdown();
if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
    executor.shutdownNow();
}
@@BLOCK@@
try {
    queue.take();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    return;
}
@@BLOCK@@
try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
    tasks.forEach(task -> executor.submit(task::run));
}
@@BLOCK@@
class ReportRunner implements AutoCloseable {
    private final ExecutorService executor =
            Executors.newVirtualThreadPerTaskExecutor();

    Future<Report> submit(Job job) {
        return executor.submit(() -> run(job));
    }

    @Override
    public void close() {
        executor.close();
    }
}