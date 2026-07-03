@PutMapping("/users/{id}")
UserResponse replace(@PathVariable long id,
                     @Valid @RequestBody ReplaceUserRequest request) {
    return service.replace(id, request);
}

@PatchMapping("/users/{id}")
UserResponse patch(@PathVariable long id,
                   @RequestBody PatchUserRequest request) {
    return service.patch(id, request);
}