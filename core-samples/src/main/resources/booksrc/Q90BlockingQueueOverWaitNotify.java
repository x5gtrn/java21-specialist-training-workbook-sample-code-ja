BlockingQueue<WorkItem> queue = new LinkedBlockingQueue<>(1000);

void produce(WorkItem item) throws InterruptedException {
    queue.put(item);
}

void consumeLoop() throws InterruptedException {
    while (!Thread.currentThread().isInterrupted()) {
        WorkItem item = queue.take();
        process(item);
    }
}
@@BLOCK@@
synchronized (lock) {
    while (list.isEmpty()) {
        lock.wait();
    }
    item = list.remove(0);
}
@@BLOCK@@
BlockingQueue<Task> queue = new ArrayBlockingQueue<>(capacity);

producer.submit(() -> queue.put(task));
consumer.submit(() -> process(queue.take()));
@@BLOCK@@
for (int i = 0; i < consumerCount; i++) {
    queue.put(Task.POISON);
}
@@BLOCK@@
try {
    process(task);
} catch (RecoverableException e) {
    retryQueue.put(task);
}