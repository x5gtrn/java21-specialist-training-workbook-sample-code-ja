package com.x5gtrn.book.appendix.java21stw300.web;

import jakarta.validation.Valid;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * デモ用 REST エンドポイント。
 * - POST /api/demo/users : @Valid によるバリデーション（不正なら 400）
 * - GET  /api/demo/config: ETag による条件付き GET（一致すれば 304 Not Modified）
 */
@RestController
@RequestMapping("/api/demo")
public class DemoRestController {

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody CreateUserRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name", req.name());
        body.put("email", req.email());
        body.put("age", req.age());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> config(
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        String etag = "\"config-v1\"";
        if (etag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
        }
        return ResponseEntity.ok()
                .eTag(etag)
                .cacheControl(CacheControl.maxAge(Duration.ofHours(1)))
                .body(Map.of("feature", "new-dashboard", "enabled", true));
    }
}
