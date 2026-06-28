package com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * 問題48: TreeSet は「比較器が 0 を返す要素」を重複とみなす。
 * 給与のみで比較すると同額の従業員が消える。副次キー（ID）を加えると区別できる。
 */
public final class Q48TreeSetComparator implements Sample {

    record Employee(int id, String name, double salary) {}

    public String chapter() { return "04_Collections_Generics"; }
    public int problem()    { return 48; }
    public String title()   { return "TreeSet の比較器と重複判定"; }

    public void run() {
        TreeSet<Employee> bySalary = new TreeSet<>(Comparator.comparingDouble(Employee::salary));
        bySalary.add(new Employee(1, "Alice", 80000));
        bySalary.add(new Employee(2, "Bob", 80000)); // compare == 0 -> 重複扱い
        System.out.println("給与のみで比較   : size=" + bySalary.size() + "  <- Bob が格納されない");

        TreeSet<Employee> correct = new TreeSet<>(
                Comparator.comparingDouble(Employee::salary).thenComparingInt(Employee::id));
        correct.add(new Employee(1, "Alice", 80000));
        correct.add(new Employee(2, "Bob", 80000));
        System.out.println("給与 + ID で比較 : size=" + correct.size() + "  <- 両方格納される");
    }
}
