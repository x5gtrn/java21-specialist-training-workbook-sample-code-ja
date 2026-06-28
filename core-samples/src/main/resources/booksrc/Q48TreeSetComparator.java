record Employee(int id, String name, double salary) {}

// NG: 給与のみで比較 → 同一給与の従業員が重複扱い
Comparator<Employee> bySalary = Comparator.comparingDouble(Employee::salary);
TreeSet<Employee> set = new TreeSet<>(bySalary);

set.add(new Employee(1, "Alice", 80000));
set.add(new Employee(2, "Bob",   80000));  // POINT: compare returns 0 → 「重複」扱い

System.out.println(set.size());  // 1 POINT: Bob は格納されない！

// OK: 副次キーを追加 → 同一給与でも ID で区別
Comparator<Employee> correct = Comparator
    .comparingDouble(Employee::salary)
    .thenComparingInt(Employee::id);  // POINT: 給与同一時は ID で区別
TreeSet<Employee> fixedSet = new TreeSet<>(correct);
fixedSet.add(new Employee(1, "Alice", 80000));
fixedSet.add(new Employee(2, "Bob",   80000));
System.out.println(fixedSet.size());  // 2 POINT: 両方格納される

// ■ TreeSet vs HashSet の一意性判定
// TreeSet: Comparator.compare() == 0 → 重複 （equals/hashCode 無関係）
// HashSet: hashCode() 一致 && equals() == true → 重複 （Comparator 無関係）

// ■ Comparator の一貫性（consistent with equals）
// compare(a,b)==0 ⟺ a.equals(b) であることが推奨（Javadoc）
// 不一致だと Set/Map の契約違反になり得る