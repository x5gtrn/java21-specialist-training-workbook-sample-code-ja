int current = counter;  // 読み取り
int next = current + 1;  // 変更
counter = next;  // 書き込み
@@BLOCK@@
private volatile boolean shutdown;

void stop() {
    shutdown = true;
}

void runLoop() {
    while (!shutdown) {
        doWork();
    }
}
@@BLOCK@@
AtomicInteger counter = new AtomicInteger();
counter.incrementAndGet();
@@BLOCK@@
LongAdder requests = new LongAdder();
requests.increment();
long total = requests.sum();
@@BLOCK@@
private final LongAdder requests = new LongAdder();

void recordRequest() {
    requests.increment();
}
@@BLOCK@@
AtomicInteger success = new AtomicInteger();
AtomicInteger failure = new AtomicInteger();
@@BLOCK@@
record Stats(int success, int failure) {}

AtomicReference<Stats> stats = new AtomicReference<>(new Stats(0, 0));