// E: String.intern() によるメモリ削減
String city1 = new String("Tokyo");
String city2 = new String("Tokyo");
System.out.println(city1 == city2);  // false（異なるオブジェクト）

String interned1 = city1.intern();
String interned2 = city2.intern();
System.out.println(interned1 == interned2);  // true（同じプールインスタンス）

// A: JVM レベルの文字列重複排除
// GC が自動的に重複文字列の内部 byte[]を共有
// java -XX:+UseZGC -XX:+UseStringDeduplication -jar app.jar