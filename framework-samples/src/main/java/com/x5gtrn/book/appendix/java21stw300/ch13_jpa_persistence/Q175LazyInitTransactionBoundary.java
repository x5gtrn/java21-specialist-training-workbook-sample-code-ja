package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class Q175LazyInitTransactionBoundary implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q175LazyInitTransactionBoundary(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 175; }
    public String title(){ return "LazyInitializationException と Transaction 境界"; }
    public void run(){
        // 永続化コンテキスト（＝トランザクション境界）が開いている間は LAZY 初期化が可能
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Department> depts = em.createQuery("select d from Department d", Department.class).getResultList();
        Department d = depts.get(0);
        int within = d.getEmployees().size();                 // セッション内なので初期化できる
        System.out.println("トランザクション内: employees=" + within);
        em.getTransaction().commit();
        em.close();                                           // 境界を抜ける
        try {
            d.getEmployees().size();                          // セッション外の LAZY アクセス
            System.out.println("セッション外でも取得できた（想定外）");
        } catch (LazyInitializationException e) {
            System.out.println("セッション外アクセス -> LazyInitializationException");
        }
        System.out.println("対策: 境界内で必要分を初期化 / JOIN FETCH / DTO 射影 / @EntityGraph");
    }
}
