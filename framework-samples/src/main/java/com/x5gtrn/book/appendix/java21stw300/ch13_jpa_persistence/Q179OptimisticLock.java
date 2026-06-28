package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;

import com.x5gtrn.book.appendix.java21stw300.domain.Product;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Component;

/**
 * 問題179: 楽観的ロック（@Version）。
 * 2 つの永続化コンテキストが同じ行をロードし、後勝ちで更新しようとすると、
 * version 不一致により OptimisticLockException が発生する。
 */
@Component
public class Q179OptimisticLock implements FrameworkSample {

    private final EntityManagerFactory emf;

    public Q179OptimisticLock(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public String chapter() { return "13_JPA_Persistence"; }
    public int problem()    { return 179; }
    public String title()   { return "楽観的ロック（@Version）と OptimisticLockException"; }

    public void run() {
        // 商品を作成（version=0）
        EntityManager em0 = emf.createEntityManager();
        em0.getTransaction().begin();
        Product p = new Product("Widget", 100);
        em0.persist(p);
        em0.getTransaction().commit();
        Long id = p.getId();
        em0.close();
        System.out.println("作成: id=" + id + ", version=" + p.getVersion());

        // EM1 が古い状態をロード（version=0）
        EntityManager em1 = emf.createEntityManager();
        em1.getTransaction().begin();
        Product stale = em1.find(Product.class, id);

        // 別トランザクション(EM2)が先に更新 → version=1
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Product fresh = em2.find(Product.class, id);
        fresh.setStock(80);
        em2.getTransaction().commit();
        em2.close();
        System.out.println("EM2 が先に更新 -> version=" + fresh.getVersion());

        // EM1 が古い version のまま更新 → 競合
        try {
            stale.setStock(50);
            em1.flush(); // ここで UPDATE ... WHERE version=0 が 0 件 -> 例外
            em1.getTransaction().commit();
            System.out.println("EM1 のコミットが成功（想定外）");
        } catch (OptimisticLockException e) {
            System.out.println("OptimisticLockException を検出: 楽観的ロックが上書きを防ぎました");
        } catch (RuntimeException e) {
            System.out.println("ロック競合例外を検出: " + e.getClass().getName());
        } finally {
            if (em1.getTransaction().isActive()) {
                try { em1.getTransaction().rollback(); } catch (RuntimeException ignored) { }
            }
            em1.close();
        }
    }
}
