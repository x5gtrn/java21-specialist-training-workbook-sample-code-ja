package com.x5gtrn.book.appendix.java21stw300.springapp;

import com.x5gtrn.book.appendix.java21stw300.domain.Department;
import com.x5gtrn.book.appendix.java21stw300.domain.Employee;
import com.x5gtrn.book.appendix.java21stw300.repo.DepartmentRepository;
import com.x5gtrn.book.appendix.java21stw300.repo.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** 起動時に部門・従業員の参照データを投入する（N+1 / Lazy デモ用、読み取り専用）。 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departments;
    private final EmployeeRepository employees;

    public DataSeeder(DepartmentRepository departments, EmployeeRepository employees) {
        this.departments = departments;
        this.employees = employees;
    }

    @Override
    public void run(String... args) {
        if (departments.count() > 0) {
            return;
        }
        seed("Engineering", List.of("Alice", "Bob", "Carol"));
        seed("Marketing", List.of("Dave", "Eve"));
        seed("Sales", List.of("Frank", "Grace", "Heidi", "Ivan"));
    }

    private void seed(String deptName, List<String> names) {
        Department dept = departments.save(new Department(deptName));
        double base = 60000;
        for (String name : names) {
            employees.save(new Employee(name, base, dept));
            base += 5000;
        }
    }
}
