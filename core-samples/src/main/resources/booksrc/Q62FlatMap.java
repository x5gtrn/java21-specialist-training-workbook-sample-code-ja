record Course(String title, List<String> students) {}
List<Course> courses = getCourses();
@@BLOCK@@
// B: flatMap による平坦化（最も一般的なアプローチ）
List<String> allStudents = courses.stream()
    .flatMap(c -> c.students().stream())  // Stream<List<String>> → Stream<String>
    .distinct()  // 重複除去
    .toList();  // 不変リストに収集

// D: mapMulti による平坦化（Java 16+、中間ストリーム不要）
List<String> allStudents2 = courses.stream()
    .<String>mapMulti((Course c, Consumer<String> consumer) ->
        c.students().forEach(consumer))  // 各学生名を直接出力
    .distinct()
    .toList();