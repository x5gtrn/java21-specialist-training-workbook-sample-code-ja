package com.x5gtrn.book.appendix.java21stw300.framework;

import com.x5gtrn.book.appendix.java21stw300.ch01_language_oop.*;
import com.x5gtrn.book.appendix.java21stw300.ch02_java21_features.*;
import com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling.*;
import com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics.*;
import com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional.*;
import com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading.*;
import com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance.*;
import com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns.*;

import java.util.List;

/**
 * 収録している全サンプルの一覧（章・問題番号順）。
 *
 * <p>新しいサンプルを追加したら、対応する {@code Sample} 実装をこのリストに登録するだけでよい。</p>
 */
public final class SampleRegistry {

    public static final List<Sample> ALL = List.of(
            // 第1章 Language & OOP
            new Q01TextBlocks(),
            new Q06Records(),
            new Q09PatternInstanceof(),
            new Q12SealedTypes(),
            // 第2章 Java 21 Features
            new Q15SequencedCollections(),
            new Q17SequencedMap(),
            new Q18ImmutableUnsupported(),
            new Q20UnnamedVariables(),
            new Q22PatternSwitchGuards(),
            new Q23ExhaustiveSwitch(),
            new Q33StringTemplates(),
            // 第3章 Exception Handling
            new Q39SuppressedExceptions(),
            new Q44FutureExecutionException(),
            new Q45SealedExceptions(),
            // 第4章 Collections & Generics
            new Q46ViewVsCopy(),
            new Q47SequencedMapViews(),
            new Q48TreeSetComparator(),
            new Q56LongAdder(),
            // 第5章 Streams, Lambda & Functional
            new Q57LimitIterate(),
            new Q61DropWhileTakeWhile(),
            new Q66GroupingByAveraging(),
            new Q67StringNormalize(),
            // 第7章 Concurrency & Threading
            new Q96RejectedExecution(),
            new Q102VirtualThreads(),
            new Q105ScopedValues(),
            new Q106StructuredConcurrency(),
            // 第8章 Memory, GC & Performance
            new Q110StringIntern(),
            new Q112StringIdentity(),
            // 第10章 Design Patterns
            new Q141SealedFactory()
    );

    private SampleRegistry() {
    }
}
