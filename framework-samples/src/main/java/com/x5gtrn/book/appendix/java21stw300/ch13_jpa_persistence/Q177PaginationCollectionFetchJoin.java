package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class Q177PaginationCollectionFetchJoin implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q177PaginationCollectionFetchJoin(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 177; }
    public String title(){ return "Pagination と Collection Fetch Join"; }
    public void run(){
        EntityManager em = emf.createEntityManager();
        // 罠: コレクションの JOIN FETCH と setFirstResult/setMaxResults を併用すると、
        // DB ではページングできず「全件取得してメモリ上でページング」される（Hibernate 警告 HHH000104）。
        List<Department> paged = em.createQuery(
                "select distinct d from Department d join fetch d.employees", Department.class)
            .setFirstResult(0).setMaxResults(2)   // ← コレクションfetchと併用は危険
            .getResultList();
        System.out.println("JOIN FETCH + ページング指定の結果件数 = " + paged.size());
        System.out.println("問題: JOIN で行が重複するため LIMIT が意図通り効かず、全件をメモリでページングし OOM リスク");
        System.out.println("対策1: まず ID を通常クエリでページング取得 → その ID 集合で JOIN FETCH（2段階）");
        System.out.println("対策2: @BatchSize / fetch=SUBSELECT でN+1を抑えつつ、ページングは親側だけで行う");
        em.close();
    }
}
