package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.repo.EmployeeRepository;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
@Component
public class Q195TransactionIsolation implements FrameworkSample {
    private final PlatformTransactionManager txm;
    private final EmployeeRepository employees;
    public Q195TransactionIsolation(PlatformTransactionManager txm, EmployeeRepository employees){
        this.txm = txm; this.employees = employees;
    }
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 195; }
    public String title(){ return "@Transactional の分離レベル"; }
    public void run(){
        TransactionTemplate tx = new TransactionTemplate(txm);
        long count = tx.execute(status -> employees.count());  // トランザクション内で実行
        System.out.println("トランザクション内で count = " + count);
        System.out.println("READ_UNCOMMITTED: ダーティリードを許容（最速・危険）");
        System.out.println("READ_COMMITTED  : コミット済みのみ可視（多くのDBの既定）");
        System.out.println("REPEATABLE_READ : 同一Txの再読込で同値を保証（MySQL InnoDB 既定）");
        System.out.println("SERIALIZABLE    : 完全直列化（最も安全・最も低速）");
    }
}
