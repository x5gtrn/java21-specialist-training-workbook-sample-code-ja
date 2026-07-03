package com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http;
import com.x5gtrn.book.appendix.java21stw300.domain.Employee;
import com.x5gtrn.book.appendix.java21stw300.repo.EmployeeRepository;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
@Component
public class Q213Pagination implements FrameworkSample {
    private final EmployeeRepository employees;
    public Q213Pagination(EmployeeRepository employees){ this.employees = employees; }
    public String chapter(){ return "15_REST_API_HTTP"; }
    public int problem(){ return 213; }
    public String title(){ return "ページネーション実装"; }
    public void run(){
        // Pageable を渡すと LIMIT/OFFSET + 総件数カウントが行われ、Page で返る（全件ロードを避ける）
        Page<Employee> page0 = employees.findAll(PageRequest.of(0, 2));
        System.out.println("総件数 = " + page0.getTotalElements() + " / 総ページ数 = " + page0.getTotalPages());
        System.out.println("page0 (size=2): " + page0.getContent().stream().map(Employee::getName).toList());
        Page<Employee> page1 = employees.findAll(PageRequest.of(1, 2));
        System.out.println("page1 (size=2): " + page1.getContent().stream().map(Employee::getName).toList()
                + " / hasNext=" + page1.hasNext());
        System.out.println("大量データは offset ページングの深いページで劣化 -> keyset(seek)ページングも検討");
    }
}
