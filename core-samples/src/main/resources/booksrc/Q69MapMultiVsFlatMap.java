List<Event> events = records.stream()
        .<Event>mapMulti((record, downstream) -> {
            if (record.isDeleted()) {
                downstream.accept(new DeleteEvent(record.id()));
            } else if (record.isUpdated()) {
                downstream.accept(new UpdateEvent(record.id()));
                downstream.accept(new AuditEvent(record.id()));
            }
        })
        .toList();
@@BLOCK@@
List<Event> events = records.stream()
        .flatMap(record -> {
            if (record.isDeleted()) {
                return Stream.of(new DeleteEvent(record.id()));
            } else if (record.isUpdated()) {
                return Stream.of(new UpdateEvent(record.id()), new AuditEvent(record.id()));
            }
            return Stream.empty();
        })
        .toList();
@@BLOCK@@
List<String> strings = values.stream()
        .<String>mapMulti((value, downstream) -> {
            if (value instanceof String s && !s.isBlank()) {
                downstream.accept(s);
            }
        })
        .toList();
@@BLOCK@@
List<LineItem> items = orders.stream()
        .flatMap(order -> order.items().stream())
        .toList();
@@BLOCK@@
stream.<Output>mapMulti((input, downstream) -> {
    if (shouldEmit(input)) {
        downstream.accept(toOutput(input));
    }
});
@@BLOCK@@
List<DomainEvent> events = commands.stream()
        .<DomainEvent>mapMulti((command, out) -> emit(command, out))
        .toList();