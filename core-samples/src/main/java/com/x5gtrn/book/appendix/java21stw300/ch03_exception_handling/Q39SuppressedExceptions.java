package com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling;

import com.x5gtrn.book.appendix.java21stw300.framework.Sample;

/**
 * 問題39: try-with-resources の抑制例外（suppressed exception）。
 * try 本体で例外が発生し、close() でも例外が発生した場合、本体の例外が主例外となり、
 * close() の例外は getSuppressed() に格納される。
 */
public final class Q39SuppressedExceptions implements Sample {

    static class ConnectionCloseException extends RuntimeException {
        ConnectionCloseException(String m) { super(m); }
    }
    static class DataProcessingException extends RuntimeException {
        DataProcessingException(String m) { super(m); }
    }
    static class Connection implements AutoCloseable {
        public void close() { throw new ConnectionCloseException("close failed"); }
    }

    public String chapter() { return "03_Exception_Handling"; }
    public int problem()    { return 39; }
    public String title()   { return "try-with-resources の抑制例外"; }

    public void run() {
        try (Connection c = new Connection()) {
            throw new DataProcessingException("processing failed");
        } catch (Exception e) {
            System.out.println("Primary    : " + e.getClass().getSimpleName() + " - " + e.getMessage());
            for (Throwable s : e.getSuppressed()) {
                System.out.println("Suppressed : " + s.getClass().getSimpleName() + " - " + s.getMessage());
            }
        }
    }
}
