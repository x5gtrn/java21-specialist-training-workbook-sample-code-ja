void waitForSignal(BlockingQueue<String> queue) {
    try {
        String message = queue.take();
        process(message);
    } catch (InterruptedException e) {
        // ここで何をすべきか？
    }
}
@@BLOCK@@
void waitForSignal(BlockingQueue<String> queue) throws InterruptedException {
    String message = queue.take();
    process(message);
}
@@BLOCK@@
void waitForSignal(BlockingQueue<String> queue) {
    try {
        String message = queue.take();
        process(message);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new TaskCancelledException("Worker was interrupted", e);
    }
}
@@BLOCK@@
class Worker implements Runnable {
    private final BlockingQueue<String> queue;

    Worker(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String message = queue.take();
                process(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        cleanup();
    }
}
@@BLOCK@@
try {
    blockingOperation();
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    throw new TaskCancelledException("interrupted while waiting", e);
}