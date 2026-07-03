@RestController
public class UserController {
    @GetMapping(value = "/users/{id}",
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}

// クライアント側
// curl -H "Accept: application/json" http://localhost:8080/users/1  → JSON
// curl -H "Accept: application/xml" http://localhost:8080/users/1   → XML