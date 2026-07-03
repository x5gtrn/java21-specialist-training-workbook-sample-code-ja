public record CreateUserRequest(
    @NotBlank String name,
    @Email @NotBlank String email
) {}

@PostMapping("/users")
ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
    return ResponseEntity.ok(userService.create(request));
}
@@BLOCK@@
@ExceptionHandler(MethodArgumentNotValidException.class)
ProblemDetail handle(MethodArgumentNotValidException ex) {
    return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
}
@@BLOCK@@
public record CreateOrderRequest(
    @NotEmpty List<@Valid OrderLineRequest> lines
) {}
@@BLOCK@@
public record CreateOrderRequest(
    @NotNull Long customerId,
    @NotEmpty List<@Valid CreateOrderLineRequest> lines
) {}

public record CreateOrderLineRequest(
    @NotNull Long itemId,
    @Min(1) int quantity
) {}