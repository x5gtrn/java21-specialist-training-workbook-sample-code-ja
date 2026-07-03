// NG: Records は JPA Entity として使えない
// @Entity
// public record User(Long id, String name) {}
// → コンパイルは通るが、Hibernate が正常に動作しない
// 1. 引数なし（no-arg）コンストラクタがない（JPA 仕様で必須）
// 2. フィールドが final（ダーティチェックには mutable フィールドが必要）
// 3. final クラス（プロキシ生成不可 → LAZY ロード不可）

// OK: Entity は従来のクラス
@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
    // no-arg constructor + getter/setter（JPA 要件）
}

// OK: Record は DTO / Projection として使用
public record UserResponse(Long id, String name, String email) {}

// POINT: Spring Data JPA の Interface Projection
public interface UserSummary {
    String getName();
    String getEmail();
}

// POINT: JPQL での Record DTO 直接マッピング
@Query("SELECT new com.example.dto.UserResponse(u.id, u.name, u.email) FROM User u")
List<UserResponse> findAllAsDto();

// POINT: 推奨アーキテクチャ
// Controller ←→ DTO(Record) ←→ Service ←→ Entity(Class) ←→ Repository