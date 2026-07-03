static final ThreadLocal<RequestContext> CURRENT = new ThreadLocal<>();

void handle(Request request) {
    CURRENT.set(buildLargeContext(request));
    try {
        service.process();
    } finally {
        CURRENT.remove();
    }
}
@@BLOCK@@
void process(RequestContext context) {
    validator.validate(context);
    service.execute(context);
}
@@BLOCK@@
private static final ScopedValue<UserContext> USER = ScopedValue.newInstance();

ScopedValue.where(USER, user).run(() -> {
    service.process();
});
@@BLOCK@@
CURRENT.set(context);
try {
    process();
} finally {
    CURRENT.remove();
}
@@BLOCK@@
record RequestContext(String traceId, String userId) {}