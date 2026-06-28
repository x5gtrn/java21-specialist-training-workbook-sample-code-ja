record Employee(String name, String department, double salary) {}
List<Employee> employees = getEmployees();
@@BLOCK@@
// A: 正解 → Map<String, Double>（部門名 → 平均給与）
Map<String, Double> avgByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::department,  // キー: 部門名
        Collectors.averagingDouble(Employee::salary)  // 下流: 平均値
    ));
// {Engineering=95000.0, Marketing=72000.0, Sales=68000.0}