package com.x5gtrn.book.appendix.java21stw300.ch11_architecture;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.function.Supplier;
public final class Q152CircuitBreaker implements Sample {
    enum State { CLOSED, OPEN, HALF_OPEN }
    static final class CircuitBreaker {
        private State state = State.CLOSED; private int failures = 0; private final int threshold;
        CircuitBreaker(int threshold){ this.threshold = threshold; }
        <T> T call(Supplier<T> action, Supplier<T> fallback){
            if (state == State.OPEN) return fallback.get(); // 開いている間は即フォールバック
            try {
                T r = action.get(); failures = 0; state = State.CLOSED; return r;
            } catch (RuntimeException e) {
                if (++failures >= threshold) state = State.OPEN; // 連続失敗で回路を開く
                return fallback.get();
            }
        }
        State state(){ return state; }
    }
    public String chapter(){return "11_Architecture";}
    public int problem(){return 152;}
    public String title(){return "Circuit Breaker パターン";}
    public void run(){
        CircuitBreaker cb = new CircuitBreaker(3);
        for (int i=1;i<=5;i++){
            String r = cb.call(() -> { throw new RuntimeException("downstream down"); }, () -> "fallback");
            System.out.println("call " + i + " -> " + r + " (state=" + cb.state() + ")");
        }
        System.out.println("連続失敗で OPEN になり、以降は下流を叩かず即フォールバック（障害の連鎖を防ぐ）");
    }
}
