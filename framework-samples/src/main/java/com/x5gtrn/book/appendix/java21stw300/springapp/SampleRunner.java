package com.x5gtrn.book.appendix.java21stw300.springapp;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * {@link FrameworkSample} を実行し、標準出力／標準エラーを文字列として捕捉する。
 * {@code System.setOut} はプロセス共有のため実行は直列化している（デモ用途として十分）。
 */
@Component
public class SampleRunner {

    public record Result(boolean error, long millis, String output) {}

    private static final Object LOCK = new Object();

    public Result run(FrameworkSample sample) {
        synchronized (LOCK) {
            PrintStream originalOut = System.out;
            PrintStream originalErr = System.err;
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8);

            boolean error = false;
            long start = System.nanoTime();
            System.setOut(capture);
            System.setErr(capture);
            try {
                sample.run();
            } catch (Throwable t) {
                error = true;
                t.printStackTrace();
            } finally {
                System.setOut(originalOut);
                System.setErr(originalErr);
                capture.flush();
            }
            long millis = (System.nanoTime() - start) / 1_000_000;
            String output = buffer.toString(StandardCharsets.UTF_8);
            if (output.isEmpty()) {
                output = "(no output)";
            }
            return new Result(error, millis, output);
        }
    }
}
