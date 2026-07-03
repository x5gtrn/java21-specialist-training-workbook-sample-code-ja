package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class Q182RecordDtoProjection implements FrameworkSample {
    private final EntityManagerFactory emf;
    public Q182RecordDtoProjection(EntityManagerFactory emf){ this.emf = emf; }
    public String chapter(){ return "13_JPA_Persistence"; }
    public int problem(){ return 182; }
    public String title(){ return "Record DTO Projection と Entity 公開"; }
    public void run(){
        EntityManager em = emf.createEntityManager();
        // JPQL コンストラクタ式で必要な列だけを record DTO へ射影（Entity を API に晒さない）
        List<EmployeeSalaryDto> dtos = em.createQuery(
            "select new com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence.EmployeeSalaryDto(e.name, e.salary) "
            + "from Employee e order by e.salary desc", EmployeeSalaryDto.class)
            .setMaxResults(3).getResultList();
        System.out.println("上位3名の射影(record DTO):");
        dtos.forEach(d -> System.out.println("  " + d));
        System.out.println("利点: 遅延初期化を避け、必要列のみ取得し、Entity の内部構造を外部に漏らさない");
        em.close();
    }
}
