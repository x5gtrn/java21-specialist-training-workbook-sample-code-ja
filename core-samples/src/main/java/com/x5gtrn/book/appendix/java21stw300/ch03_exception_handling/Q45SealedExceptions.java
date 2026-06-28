package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;
import java.util.List;

/**
 * 問題45: sealed な例外階層 + パターンマッチング switch。
 * 例外の種類ごとの分岐を網羅的に書ける（RetryableException / FatalException）。
 */
public final class Q45SealedExceptions implements Sample {

    sealed static abstract class ProcessingException extends RuntimeException
            permits RetryableException, FatalException {
        ProcessingException(String msg) { super(msg); }
    }
    static final class RetryableException extends ProcessingException {
        RetryableException(String msg) { super(msg); }
    }
    static final class FatalException extends ProcessingException {
        FatalException(String msg) { super(msg); }
    }

    public String chapter() { return "03_Exception_Handling"; }
    public int problem()    { return 45; }
    public String title()   { return "sealed 例外階層とパターンマッチング"; }

    public void run() {
        List<ProcessingException> errors = List.of(
            new RetryableException("temporary network glitch"),
            new FatalException("data corrupted")
        );
        for (ProcessingException e : errors) {
            String action = switch (e) {
                case RetryableException r -> "retry queue へ: " + r.getMessage();
                case FatalException f     -> "即時停止: " + f.getMessage();
            };
            System.out.println(action);
        }
    }
}
