package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.domain.Employee;
import com.x5gtrn.book.appendix.java21stw300.repo.EmployeeRepository;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class Q189SpringDataRepository implements FrameworkSample {
    private final EmployeeRepository employees;
    public Q189SpringDataRepository(EmployeeRepository employees){ this.employees = employees; }
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 189; }
    public String title(){ return "Spring Data JPA の Repository パターン"; }
    public void run(){
        // インターフェースを宣言するだけで CRUD 実装が生成される（findAll/count/save/findById...）
        long count = employees.count();
        List<Employee> all = employees.findAll();
        System.out.println("employees.count() = " + count);
        System.out.println("先頭の従業員 = " + (all.isEmpty() ? "(none)" : all.get(0).getName()));
        employees.findById(all.get(0).getId())
                .ifPresent(e -> System.out.println("findById -> " + e.getName() + " / salary=" + e.getSalary()));
        System.out.println("メソッド名からクエリを自動生成（findByNameAndSalaryGreaterThan 等）。実装コード不要");
    }
}
