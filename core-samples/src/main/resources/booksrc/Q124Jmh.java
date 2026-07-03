@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class StringBenchmark {
    @Benchmark
    public String concatPlus(Blackhole bh) {
        String s = "";
        for (int i = 0; i < 100; i++) s += "a";
        return s;
    }

    @Benchmark
    public String concatBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) sb.append("a");
        return sb.toString();
    }
}
// POINT: Blackhole.consume() → JIT によるデッドコード除去を防止