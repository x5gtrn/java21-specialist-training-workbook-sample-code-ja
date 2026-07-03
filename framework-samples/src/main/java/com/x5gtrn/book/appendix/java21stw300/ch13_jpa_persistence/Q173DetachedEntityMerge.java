package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.domain.Product;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
@Component
public class Q173DetachedEntityMerge implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q173DetachedEntityMerge(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 173; }
    public String title(){ return "Detached Entity と merge の意味"; }
    public void run(){
        EntityManager em1 = emf.createEntityManager();
        em1.getTransaction().begin();
        Product p = new Product("Detach-Demo", 10);
        em1.persist(p);
        em1.getTransaction().commit();
        Long id = p.getId();
        em1.close();                                          // p は detached になる
        p.setStock(99);                                       // detached なので DB へは反映されない
        System.out.println("detached のまま変更しても DB は更新されない（自動フラッシュ対象外）");
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Product mergedManaged = em2.merge(p);                 // merge: detached の状態を managed コピーへ反映
        em2.getTransaction().commit();
        em2.close();
        EntityManager em3 = emf.createEntityManager();
        Product reloaded = em3.find(Product.class, id);
        System.out.println("merge 後の在庫 = " + reloaded.getStock() + "（99 が反映）");
        System.out.println("注意: merge は引数を managed にせず、managed な別インスタンスを返す");
        em3.close();
    }
}
