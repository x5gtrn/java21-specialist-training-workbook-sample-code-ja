package com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 問題66: groupingBy + averagingDouble で「部門ごとの平均給与」を求める。
 * 結果は Map<部門, 平均給与>。
 */
public final class Q66GroupingByAveraging implements Sample {

    record Employee(String name, String department, double salary) {}

    public String chapter() { return "05_Streams_Lambda_Functional"; }
    public int problem()    { return 66; }
    public String title()   { return "groupingBy + averagingDouble"; }

    public void run() {
        List<Employee> employees = List.of(
            new Employee("Alice", "Engineering", 100000),
            new Employee("Bob",   "Engineering", 90000),
            new Employee("Carol", "Marketing",   72000),
            new Employee("Dave",  "Sales",       70000),
            new Employee("Eve",   "Sales",       66000)
        );

        Map<String, Double> avgByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.averagingDouble(Employee::salary)));

        // 表示順を安定させるため TreeMap でソート
        System.out.println(new TreeMap<>(avgByDept));
    }
}
