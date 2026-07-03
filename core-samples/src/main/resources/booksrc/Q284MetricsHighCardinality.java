Counter.builder("orders.created")
    .tag("userId", userId)
    .tag("requestId", requestId)
    .register(registry)
    .increment();
@@BLOCK@@
Counter.builder("orders.created")
    .tag("region", region)
    .tag("result", result)
    .register(registry)
    .increment();
@@BLOCK@@
Timer.builder("http.client.requests")
    .tag("client", "payment")
    .tag("outcome", "success")
    .register(registry)
    .record(duration);
@@BLOCK@@
registry.config().meterFilter(MeterFilter.deny(id ->
    id.getTags().stream().anyMatch(t -> t.getKey().equals("userId"))));