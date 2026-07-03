@GetMapping("/users/{id}")
public UserResponse get(@PathVariable long id) {
    User user = userService.findUser(id);  // transaction は service 内で終了
    return mapper.toResponse(user);  // ここで user.getOrders()に触ると危険
}
@@BLOCK@@
@Transactional(readOnly = true)
public UserResponse findUser(long id) {
    User user = repository.findByIdWithOrders(id)
        .orElseThrow();
    return UserResponse.from(user);
}
@@BLOCK@@
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "orders")
    Optional<User> findWithOrdersById(Long id);
}

@Transactional(readOnly = true)
public UserResponse getUser(long id) {
    User user = repository.findWithOrdersById(id).orElseThrow();
    return new UserResponse(
        user.getId(),
        user.getName(),
        user.getOrders().stream().map(OrderResponse::from).toList()
    );
}