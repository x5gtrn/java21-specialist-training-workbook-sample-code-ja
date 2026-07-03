package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.domain.Product;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
@Component
public class Q172EntityLifecycle implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q172EntityLifecycle(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 172; }
    public String title(){ return "JPA エンティティのライフサイクル"; }
    public void run(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product p = new Product("Gadget", 5);                 // new/transient
        System.out.println("new 直後 managed? " + em.contains(p) + "（transient）");
        em.persist(p);                                        // managed
        System.out.println("persist 後 managed? " + em.contains(p) + "（managed）");
        em.getTransaction().commit();
        Long id = p.getId();
        em.close();                                           // detached
        System.out.println("em.close() 後は detached（永続化コンテキスト外）");
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Product managed = em2.find(Product.class, id);
        em2.remove(managed);                                  // removed
        System.out.println("remove 後 managed? " + em2.contains(managed) + "（removed）");
        em2.getTransaction().commit();
        em2.close();
        System.out.println("状態遷移: transient -> managed -> detached / managed -> removed");
    }
}
