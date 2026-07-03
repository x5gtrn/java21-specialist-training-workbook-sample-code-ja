package com.x5gtrn.book.appendix.java21stw300.ch17_testing;
import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
public final class Q251ClockTesting implements Sample {
    // System.currentTimeMillis を直接使わず Clock を注入 → テストで時刻を固定できる
    static final class Token {
        private final Instant expiry;
        Token(Instant expiry){ this.expiry = expiry; }
        boolean isExpired(Clock clock){ return Instant.now(clock).isAfter(expiry); }
    }
    public String chapter(){return "17_Testing";}
    public int problem(){return 251;}
    public String title(){return "時刻依存コードのテスト";}
    public void run(){
        Instant expiry = Instant.parse("2026-01-01T00:00:00Z");
        Token token = new Token(expiry);
        Clock before = Clock.fixed(Instant.parse("2025-12-31T23:59:59Z"), ZoneOffset.UTC);
        Clock after  = Clock.fixed(Instant.parse("2026-01-01T00:00:01Z"), ZoneOffset.UTC);
        System.out.println("期限前の固定時刻 -> isExpired=" + token.isExpired(before) + "（決定的にテスト可能）");
        System.out.println("期限後の固定時刻 -> isExpired=" + token.isExpired(after));
    }
}
