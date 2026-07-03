// 例: 高頻度 API で不要な中間 Map を作る
Map<String, Object> body = orders.stream()
    .collect(Collectors.toMap(Order::id, o -> Map.of(
        "id", o.id(),
        "total", o.total(),
        "items", o.items().stream().map(ItemDto::from).toList()
    )));