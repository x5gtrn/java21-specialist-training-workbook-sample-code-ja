package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;

import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 問題176: N+1 SELECT 問題。
 * 部門を取得後に各部門の LAZY な従業員へアクセスすると、部門数 N の分だけ追加 SELECT が走る。
 * JOIN FETCH なら 1 クエリで済む。Hibernate Statistics で実際の発行クエリ数を比較する。
 */
@Component
public class Q176NPlusOne implements FrameworkSample {

    private final EntityManagerFactory emf;

    public Q176NPlusOne(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public String chapter() { return "13_JPA_Persistence"; }
    public int problem()    { return 176; }
    public String title()   { return "N+1 SELECT 問題と JOIN FETCH"; }

    public void run() {
        SessionFactory sf = emf.unwrap(SessionFactory.class);
        Statistics stats = sf.getStatistics();

        // --- N+1 が起きる版 ---
        stats.clear();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Department> depts = em.createQuery("select d from Department d", Department.class).getResultList();
        for (Department d : depts) {
            d.getEmployees().size(); // 部門ごとに追加 SELECT
        }
        em.getTransaction().commit();
        em.close();
        long nPlusOne = stats.getPrepareStatementCount();
        System.out.println("N+1 版      : 部門数 " + depts.size() + " / 発行クエリ数 " + nPlusOne);

        // --- JOIN FETCH 版 ---
        stats.clear();
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        List<Department> joined = em2.createQuery(
                "select distinct d from Department d join fetch d.employees", Department.class).getResultList();
        for (Department d : joined) {
            d.getEmployees().size();
        }
        em2.getTransaction().commit();
        em2.close();
        long withFetch = stats.getPrepareStatementCount();
        System.out.println("JOIN FETCH 版: 部門数 " + joined.size() + " / 発行クエリ数 " + withFetch);
    }
}
