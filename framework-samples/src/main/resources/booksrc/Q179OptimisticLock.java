@Entity
public class Product {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private BigDecimal price;

    @Version  // POINT: 楽観的ロック用バージョン列
    private Long version;
}

// POINT: JPA が自動生成する UPDATE 文
// UPDATE products SET name=?, price=?, version=version+1
// WHERE id=? AND version=?
// ^^^^^^^^^^ 読み取り時のバージョンと照合
// → 行が更新されない(version 不一致) → OptimisticLockException

// POINT: 楽観的 vs 悲観的ロック
// 楽観的（@Version）: 読み取り時はロックなし、更新時に競合検出
// → 読み取り頻度 >> 書き込み頻度 のシナリオに適していることが多い
// 悲観的（@Lock(PESSIMISTIC_WRITE)）: 読み取り時に SELECT FOR UPDATE
// → 競合が頻繁なシナリオ向け、スループット低下リスク