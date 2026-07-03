package com.x5gtrn.book.appendix.java21stw300.ch14_springboot;
import com.x5gtrn.book.appendix.java21stw300.repo.EmployeeRepository;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
@Component
public class Q196TransactionPropagation implements FrameworkSample {
    private final PlatformTransactionManager txm;
    private final EmployeeRepository employees;
    public Q196TransactionPropagation(PlatformTransactionManager txm, EmployeeRepository employees){
        this.txm = txm; this.employees = employees;
    }
    public String chapter(){ return "14_SpringBoot"; }
    public int problem(){ return 196; }
    public String title(){ return "@Transactional の伝播レベル"; }
    public void run(){
        TransactionTemplate outer = new TransactionTemplate(txm);
        outer.execute(status -> {
            long c = employees.count();                        // 呼び出し側トランザクション
            System.out.println("外側トランザクション内 count = " + c);
            return null;
        });
        System.out.println("REQUIRED    : 既存Txがあれば参加、無ければ新規（既定）");
        System.out.println("REQUIRES_NEW: 常に新規Txを開始（親を一時中断）。監査ログ等を親のロールバックから独立させる");
        System.out.println("NESTED      : セーブポイントで部分ロールバック");
        System.out.println("注意: REQUIRES_NEW 内の失敗は親に伝播しうる。境界設計を誤ると意図せぬコミット/巻き戻しになる");
    }
}
