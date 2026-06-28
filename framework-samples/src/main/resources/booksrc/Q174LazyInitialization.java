@Entity
public class Employee {
    @ManyToOne(fetch = FetchType.LAZY)  // POINT: 推奨
    private Company company;
    // employee.getCompany() を呼ばなければ Company はロードされない
    // 呼んだ瞬間に SELECT * FROM companies WHERE id = ? が実行
}

// POINT: JPA のデフォルトフェッチタイプ
// @ManyToOne, @OneToOne  → EAGER（デフォルト）← 変更推奨
// @OneToMany, @ManyToMany → LAZY（デフォルト）

// POINT: ベストプラクティス: すべて LAZY にして、必要な時だけ JOIN FETCH
@ManyToOne(fetch = FetchType.LAZY)  // すべてのリレーションで LAZY 推奨
private Company company;

// LazyInitializationException への注意
// トランザクション外で LAZY プロキシにアクセスすると例外
// 解決: @Transactional 内でアクセスするか、JOIN FETCH で事前ロード