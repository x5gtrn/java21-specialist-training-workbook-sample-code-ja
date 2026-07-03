class EventBus {
    private static final List<Listener> listeners = new ArrayList<>();

    static void register(Listener listener) {
        listeners.add(listener);
    }
}
@@BLOCK@@
void handle(Request request) {
    EventBus.register(event -> {
        audit(request.userId(), event);  // request をキャプチャ
    });
}
@@BLOCK@@
Registration registration = eventBus.register(listener);
try {
    process();
} finally {
    registration.close();  // listener を解除
}
@@BLOCK@@
interface Registration extends AutoCloseable {
    @Override void close();
}

try (Registration ignored = eventBus.register(listener)) {
    processRequest();
}
@@BLOCK@@
class RequestHandler {
    void handle(RequestDto dto) {
        scheduler.add(() -> processLater(dto));  // dto を長寿命キューへ捕捉
    }
}
@@BLOCK@@
String requestId = dto.id();
scheduler.add(() -> processLater(requestId));