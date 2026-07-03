User detached = loadUserOutsideCurrentTransaction();
detached.setName("new name");

User managed = entityManager.merge(detached);

System.out.println(detached == managed);  // 別インスタンスのため false になり得る
@@BLOCK@@
User managed = entityManager.merge(detached);
managed.setEmail("new@example.com");  // ダーティチェックの対象
detached.setEmail("ignored@example.com");  // 追跡されないため反映されない
@@BLOCK@@
@Transactional
public User update(User detached) {
    User managed = entityManager.merge(detached);
    managed.setUpdatedAt(Instant.now());
    return managed;
}
@@BLOCK@@
@Transactional
public void changeEmail(long userId, ChangeEmailCommand command) {
    User user = entityManager.find(User.class, userId);
    user.changeEmail(command.email());
}