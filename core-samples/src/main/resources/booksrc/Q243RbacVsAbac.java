// RBAC: ロール単位のアクセス制御
class UserAdminController {
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId) {
        // 管理者専用処理
    }
}

// ABAC: 属性ベースの細粒度制御
class DocumentController {
    @PreAuthorize("@accessPolicy.canAccess(#document, authentication)")
    public Document viewDocument(Document document) {
        return document;
    }
}

@Component
class AccessPolicy {
    boolean canAccess(Document doc, Authentication auth) {
        User user = (User) auth.getPrincipal();
        // ユーザー属性 + リソース属性 + 環境属性で判定
        return doc.departmentId().equals(user.departmentId())  // 同部署
            && doc.classification() != Classification.TOP_SECRET  // 機密レベル
            && isBusinessHours();  // 業務時間内のみ
    }
}