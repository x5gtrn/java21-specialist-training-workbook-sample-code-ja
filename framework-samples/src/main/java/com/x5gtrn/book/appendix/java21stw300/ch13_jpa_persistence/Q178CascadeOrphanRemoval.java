package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import com.x5gtrn.book.appendix.java21stw300.domain.Employee;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
@Component
public class Q178CascadeOrphanRemoval implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q178CascadeOrphanRemoval(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 178; }
    public String title(){ return "Cascade と orphanRemoval の違い"; }
    public void run(){
        // 本サンプルの Department/Employee は cascade 未設定のため、子は明示的に永続化する
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Department dept = new Department("Temp");
        em.persist(dept);                                    // cascade=PERSIST があれば子も自動保存される
        em.persist(new Employee("child-1", 50000, dept));
        em.getTransaction().commit();
        em.close();
        System.out.println("cascade=PERSIST : 親の永続化で子も保存（設定時）");
        System.out.println("cascade=REMOVE  : 親の削除で子も削除");
        System.out.println("orphanRemoval=true : コレクションから外した子を『孤児』として自動 DELETE");
        System.out.println("違い: cascade=REMOVE は親削除に連動、orphanRemoval は関連解除に連動して削除する");
    }
}
