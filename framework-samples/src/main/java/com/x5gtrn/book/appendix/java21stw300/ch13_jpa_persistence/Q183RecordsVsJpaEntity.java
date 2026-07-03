package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.domain.Employee;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
@Component
public class Q183RecordsVsJpaEntity implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q183RecordsVsJpaEntity(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 183; }
    public String title(){ return "Records vs JPA Entity の設計トレードオフ"; }
    public void run(){
        EntityManager em = emf.createEntityManager();
        Employee e = em.createQuery("select e from Employee e", Employee.class).setMaxResults(1).getResultList().get(0);
        // Entity は「引数なしコンストラクタ・可変・プロキシ化」が前提 → record にはできない
        EmployeeSalaryDto dto = new EmployeeSalaryDto(e.getName(), e.getSalary());
        System.out.println("Entity(可変・遅延プロキシ)     : " + e.getName());
        System.out.println("record DTO(不変・境界外へ公開) : " + dto);
        System.out.println("JPA Entity を record にできない理由: 無引数コンストラクタ不可・final フィールド・遅延プロキシ非対応");
        System.out.println("推奨: 永続化は Entity、API/レイヤ境界の受け渡しは immutable な record DTO");
        em.close();
    }
}
