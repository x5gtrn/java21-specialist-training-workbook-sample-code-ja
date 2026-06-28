package com.x5gtrn.book.appendix.java21stw300.springapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

/**
 * フロントエンド（static/index.html）に対する API。
 * GET /api/samples … サンプル一覧＋ソース、GET /api/run?id=X … 実行して出力を返す。
 */
@RestController
@RequestMapping("/api")
public class SampleController {

    private final List<FrameworkSample> samples;
    private final SampleRunner runner;

    public SampleController(List<FrameworkSample> samples, SampleRunner runner) {
        this.samples = samples.stream()
                .sorted(Comparator.comparing(FrameworkSample::chapter)
                        .thenComparingInt(FrameworkSample::problem))
                .toList();
        this.runner = runner;
    }

    public record SampleDto(String id, String chapter, int problem, String title,
                            boolean preview, String source) {}

    @GetMapping("/samples")
    public List<SampleDto> samples() {
        return samples.stream()
                .map(s -> new SampleDto(s.id(), s.chapter(), s.problem(), s.title(), s.preview(), source(s)))
                .toList();
    }

    @GetMapping("/run")
    public SampleRunner.Result run(@RequestParam("id") String id) {
        FrameworkSample target = samples.stream()
                .filter(s -> s.id().equals(id))
                .findFirst()
                .orElse(null);
        if (target == null) {
            return new SampleRunner.Result(true, 0, "Unknown sample id: " + id);
        }
        return runner.run(target);
    }

    private String source(FrameworkSample s) {
        String path = "/booksrc/" + s.id() + ".java";
        try (InputStream in = getClass().getResourceAsStream(path)) {
            if (in == null) {
                return "// (本文コード未登録)";
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "// failed to read source: " + e.getMessage();
        }
    }
}
