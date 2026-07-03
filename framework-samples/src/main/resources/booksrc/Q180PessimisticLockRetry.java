@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select i from Inventory i where i.itemId = :itemId")
Optional<Inventory> findForUpdate(String itemId);
@@BLOCK@@
@Transactional
public void reserve(List<String> itemIds) {
    List<String> sortedIds = itemIds.stream().sorted().toList();
    for (String itemId : sortedIds) {
        Inventory inventory = repository.findForUpdate(itemId)
            .orElseThrow();
        inventory.decrement();
    }
}