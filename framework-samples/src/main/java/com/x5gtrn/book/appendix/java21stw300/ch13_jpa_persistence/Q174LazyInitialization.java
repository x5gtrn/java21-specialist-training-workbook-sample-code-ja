package com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence;

import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import com.x5gtrn.book.appendix.java21stw300.repo.DepartmentRepository;
import com.x5gtrn.book.appendix.java21stw300.springapp.FrameworkSample;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

/**
 * 問題174: LAZY フェッチの遅延初期化。
 * トランザクション（永続化コンテキスト）の外で LAZY 関連にアクセスすると
 * LazyInitializationException が発生する。JOIN FETCH なら事前に取得できる。
 * （application.yml で open-in-view=false にしているため再現する）
 */
@Component
public class Q174LazyInitialization implements FrameworkSample {

    private final DepartmentRepository departments;

    public Q174LazyInitialization(DepartmentRepository departments) {
        this.departments = departments;
    }

    public String chapter() { return "13_JPA_Persistence"; }
    public int problem()    { return 174; }
    public String title()   { return "LAZY フェッチと LazyInitializationException"; }

    public void run() {
        // findAll() の戻り値はメソッド終了時にデタッチされる
        Department dept = departments.findAll().get(0);
        System.out.println("部門: " + dept.getName());
        try {
            int n = dept.getEmployees().size(); // LAZY -> セッション外アクセス
            System.out.println("employees = " + n + "（想定外: 取得できた）");
        } catch (LazyInitializationException e) {
            System.out.println("LazyInitializationException: セッション外で LAZY 関連にアクセスしました");
        }

        // 解決策: JOIN FETCH で同一クエリ内に取得しておく
        Department withEmployees = departments.findAllWithEmployees().get(0);
        System.out.println("JOIN FETCH 版: " + withEmployees.getName()
                + " の従業員数 = " + withEmployees.getEmployees().size());
    }
}
