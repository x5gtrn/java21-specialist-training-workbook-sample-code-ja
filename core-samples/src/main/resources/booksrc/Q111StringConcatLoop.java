StringBuilder builder = new StringBuilder();
for (Order order : orders) {
    builder.append(order.id())
            .append(',')
            .append(order.amount())
            .append(',')
            .append(order.status())
            .append('\n');
}
String csv = builder.toString();
@@BLOCK@@
void writeCsv(List<Order> orders, Writer writer) throws IOException {
    for (Order order : orders) {
        writer.write(order.id());
        writer.write(',');
        writer.write(order.amount().toString());
        writer.write(',');
        writer.write(order.status().name());
        writer.write('\n');
    }
}
@@BLOCK@@
result = result + value;
@@BLOCK@@
String label = user.firstName() + " " + user.lastName();
@@BLOCK@@
StringBuilder builder = new StringBuilder(estimatedSize);
for (Item item : items) {
    builder.append(item.value()).append('\n');
}
return builder.toString();
@@BLOCK@@
void streamCsv(Stream<Order> orders, Writer writer) {
    orders.forEach(order -> {
        try {
            writer.write(order.id());
            writer.write(',');
            writer.write(order.status().name());
            writer.write('\n');
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    });
}
@@BLOCK@@
if (log.isDebugEnabled()) {
    log.debug("csv preview={}", preview(csv));
}