package com.x5gtrn.book.appendix.java21stw300.repo;

import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /** JOIN FETCH で employees をまとめて取得（N+1 を回避する版）。 */
    @Query("select distinct d from Department d join fetch d.employees")
    List<Department> findAllWithEmployees();
}
