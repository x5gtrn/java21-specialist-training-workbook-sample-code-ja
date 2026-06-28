// ■ N+1 問題の発生
@Entity
public class Department {
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)  // LAZY でも EAGER でも発生し得る
    private List<Employee> employees;
}

List<Department> depts = departmentRepository.findAll();
// → SELECT * FROM departments  (1クエリ)
for (Department d : depts) {
    d.getEmployees().size();  // 各部門で1クエリ
    // → SELECT * FROM employees WHERE department_id = 1  (N 回)
    // → SELECT * FROM employees WHERE department_id = 2
    // → ...
}
// 合計: 1 + 100 = 101 クエリ（N+1 問題）

// ■ 解決策1: JOIN FETCH（最も一般的）
@Query("SELECT d FROM Department d JOIN FETCH d.employees")
List<Department> findAllWithEmployees();
// → 1クエリで全データ取得（ただしカルテシアン積に注意）

// ■ 解決策2: @EntityGraph
@EntityGraph(attributePaths = {"employees"})
List<Department> findAll();
// → LEFT JOIN で1クエリ

// ■ 解決策3: @BatchSize（Hibernate 固有）
@OneToMany(mappedBy = "department")
@BatchSize(size = 25)  // 25部門分の従業員を IN 句で一括取得
private List<Employee> employees;
// → SELECT * FROM employees WHERE department_id IN (1,2,...,25)
// → 100部門 / 25 = 4クエリ → 合計5クエリに削減

// ■ 解決策4: Projection / DTO
@Query("SELECT new com.example.DeptSummary(d.name, COUNT(e)) " +
       "FROM Department d LEFT JOIN d.employees e GROUP BY d.name")
List<DeptSummary> findDeptSummaries();
// → 1クエリ、エンティティではなく DTO で取得