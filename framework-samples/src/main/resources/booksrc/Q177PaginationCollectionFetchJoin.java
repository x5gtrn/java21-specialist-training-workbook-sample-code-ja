@Query("""
    select o
    from Order o
    join fetch o.lines
    order by o.createdAt desc
""")
Page<Order> findPageWithLines(Pageable pageable);
@@BLOCK@@
Page<Long> ids = orderRepository.findOrderIds(pageable);
List<Order> orders = orderRepository.findByIdInWithLines(ids.getContent());
@@BLOCK@@
@Query("select o.id from Order o order by o.createdAt desc")
Page<Long> findOrderIds(Pageable pageable);

@Query("""
    select distinct o
    from Order o
    left join fetch o.lines
    where o.id in :ids
""")
List<Order> findByIdInWithLines(List<Long> ids);
@@BLOCK@@
Map<Long, Order> byId = orders.stream()
    .collect(Collectors.toMap(Order::getId, Function.identity()));
List<Order> sorted = ids.stream().map(byId::get).toList();
@@BLOCK@@
public record OrderSummary(long id, Instant createdAt, BigDecimal total, long lineCount) {}