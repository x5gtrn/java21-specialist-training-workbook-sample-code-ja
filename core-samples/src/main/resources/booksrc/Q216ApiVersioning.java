// D: URL パスバージョニング（最も一般的）
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    @GetMapping("/{id}")
    public UserV1 getUser(@PathVariable Long id) {
        return findUserV1(id);
    }
}

@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {
    @GetMapping("/{id}")
    public UserV2 getUser(@PathVariable Long id) {
        return findUserV2(id);
    }
}

// A: カスタムヘッダーバージョニング
@RestController
@RequestMapping("/api/users")
public class HeaderVersionedUserController {
    @GetMapping(value = "/{id}", headers = "X-API-Version=2")
    public UserV2 getUserV2(@PathVariable Long id) {
        return findUserV2(id);
    }
}

// B: メディアタイプバージョニング
@RestController
@RequestMapping("/api/users")
public class MediaTypeVersionedUserController {
    @GetMapping(value = "/{id}",
                produces = "application/vnd.company.v2+json")
    public UserV2 getUserV2(@PathVariable Long id) {
        return findUserV2(id);
    }
}