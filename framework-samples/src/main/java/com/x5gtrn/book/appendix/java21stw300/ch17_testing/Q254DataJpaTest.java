package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import com.x5gtrn.book.appendix.java21stw300.domain.Employee;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class Q254DataJpaTest implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q254DataJpaTest(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "17_Testing"; }
    public int problem(){ return 254; }
    public String title(){ return "@DataJpaTest によるリポジトリテスト"; }
    public void run(){
        // @DataJpaTest 相当: JPA 層のみで永続化・検索を検証し、最後にロールバックして隔離する
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Department dept = em.createQuery("select d from Department d", Department.class).setMaxResults(1).getResultList().get(0);
        long before = em.createQuery("select count(e) from Employee e", Long.class).getSingleResult();
        em.persist(new Employee("TmpTester", 55000, dept));
        em.flush();
        long after = em.createQuery("select count(e) from Employee e", Long.class).getSingleResult();
        System.out.println("追加前 count=" + before + " / flush 後 count=" + after);
        List<Employee> found = em.createQuery(
            "select e from Employee e where e.name = 'TmpTester'", Employee.class).getResultList();
        System.out.println("検索できた件数 = " + found.size());
        em.getTransaction().rollback();   // テストは既定でロールバックし、DBを汚さない
        em.close();
        System.out.println("ロールバックで隔離 -> 各テストは独立（@DataJpaTest の既定挙動）");
    }
}
