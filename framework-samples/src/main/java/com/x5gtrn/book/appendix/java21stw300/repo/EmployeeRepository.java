package com.x5gtrn.book.appendix.java21stw300.repo;

import com.x5gtrn.book.appendix.java21stw300.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
