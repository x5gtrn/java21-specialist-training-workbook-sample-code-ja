try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (Request request : requests) {
        executor.submit(() -> handle(request));
    }
}
@@BLOCK@@
Semaphore dbBulkhead = new Semaphore(50);

void handle(Request request) throws Exception {
    dbBulkhead.acquire();
    try {
        queryDatabase(request);
    } finally {
        dbBulkhead.release();
    }
}
@@BLOCK@@
ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
Semaphore externalApiLimit = new Semaphore(100);

Future<Response> future = executor.submit(() -> {
    externalApiLimit.acquire();
    try {
        return client.call();
    } finally {
        externalApiLimit.release();
    }
});