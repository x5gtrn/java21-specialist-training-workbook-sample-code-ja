package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.domain.Product;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PessimisticLockException;
import org.springframework.stereotype.Component;
@Component
public class Q180PessimisticLockRetry implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q180PessimisticLockRetry(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 180; }
    public String title(){ return "Pessimistic Lock と Deadlock Retry"; }
    public void run(){
        EntityManager em0 = emf.createEntityManager();
        em0.getTransaction().begin();
        Product p = new Product("LockItem", 100);
        em0.persist(p);
        em0.getTransaction().commit();
        Long id = p.getId();
        em0.close();
        // 悲観ロック: SELECT ... FOR UPDATE で行を掴み、他トランザクションを待たせる
        int attempts = 0; boolean done = false;
        while (!done && attempts < 3) {
            attempts++;
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                Product locked = em.find(Product.class, id, LockModeType.PESSIMISTIC_WRITE);
                locked.setStock(locked.getStock() - 1);
                em.getTransaction().commit();
                done = true;
                System.out.println("試行" + attempts + ": ロック取得・更新に成功 stock=" + locked.getStock());
            } catch (PessimisticLockException | jakarta.persistence.LockTimeoutException e) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                System.out.println("試行" + attempts + ": ロック競合/タイムアウト -> リトライ");
            } finally { em.close(); }
        }
        System.out.println("デッドロック/ロックタイムアウトは指数バックオフ等でリトライ設計する");
    }
}
