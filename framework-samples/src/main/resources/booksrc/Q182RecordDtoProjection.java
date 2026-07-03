public record UserListItem(Long id, String name, String email) {}

@Query("""
    select new com.example.api.UserListItem(u.id, u.name, u.email)
    from User u
    order by u.name
""")
List<UserListItem> findUserListItems();
@@BLOCK@@
public interface UserListProjection {
    Long getId();
    String getName();
    String getEmail();
}
@@BLOCK@@
public record UserSummaryResponse(
    Long id,
    String name,
    String email
) {}

@RestController
class UserController {
    @GetMapping("/users")
    List<UserSummaryResponse> list() {
        return userService.listUsers();
    }
}