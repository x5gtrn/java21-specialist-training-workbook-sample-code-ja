SequencedSet<String> names = new LinkedHashSet<>();
names.add("alice");
names.add("bob");
names.add("alice");

System.out.println(names);  // [alice, bob]
System.out.println(names.getFirst());  // alice
System.out.println(names.getLast());  // bob
@@BLOCK@@
SequencedSet<String> tags = new LinkedHashSet<>();
tags.add("java");
tags.add("spring");
tags.add("java");

String first = tags.getFirst();
String last = tags.getLast();
SequencedSet<String> reversed = tags.reversed();