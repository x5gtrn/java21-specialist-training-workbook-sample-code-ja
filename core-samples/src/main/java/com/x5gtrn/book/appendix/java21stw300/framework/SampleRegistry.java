package com.x5gtrn.book.appendix.java21stw300.framework;

import com.x5gtrn.book.appendix.java21stw300.ch01_language_oop.*;
import com.x5gtrn.book.appendix.java21stw300.ch02_java21_features.*;
import com.x5gtrn.book.appendix.java21stw300.ch03_exception_handling.*;
import com.x5gtrn.book.appendix.java21stw300.ch04_collections_generics.*;
import com.x5gtrn.book.appendix.java21stw300.ch05_streams_lambda_functional.*;
import com.x5gtrn.book.appendix.java21stw300.ch06_io_nio.*;
import com.x5gtrn.book.appendix.java21stw300.ch07_concurrency_threading.*;
import com.x5gtrn.book.appendix.java21stw300.ch08_memory_gc_performance.*;
import com.x5gtrn.book.appendix.java21stw300.ch09_jpms_modularity.*;
import com.x5gtrn.book.appendix.java21stw300.ch10_design_patterns.*;
import com.x5gtrn.book.appendix.java21stw300.ch11_architecture.*;
import com.x5gtrn.book.appendix.java21stw300.ch14_springboot.*;
import com.x5gtrn.book.appendix.java21stw300.ch15_rest_api_http.*;
import com.x5gtrn.book.appendix.java21stw300.ch16_security_auth.*;
import com.x5gtrn.book.appendix.java21stw300.ch17_testing.*;
import com.x5gtrn.book.appendix.java21stw300.ch18_deploy_devops.*;
import com.x5gtrn.book.appendix.java21stw300.ch19_logging_observability.*;
import com.x5gtrn.book.appendix.java21stw300.ch20_debugging_profiling.*;

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
            new Q02VarTypeInference(),
            new Q03EnumAbstractMethod(),
            new Q04AbstractClassDesign(),
            new Q05InterfacePrivateMethod(),
            new Q06Records(),
            new Q07RecordCompactConstructor(),
            new Q08InstanceofPattern(),
            new Q09PatternInstanceof(),
            new Q10SwitchArrowSyntax(),
            new Q11RecordPattern(),
            new Q12SealedTypes(),
            new Q13SealedSwitchResilience(),
            new Q14EnumVsSealedModeling(),
            // 第2章 Java 21 Features
            new Q15SequencedCollections(),
            new Q16SequencedSetDuplicates(),
            new Q17SequencedMap(),
            new Q18ImmutableUnsupported(),
            new Q19UnnamedPatterns(),
            new Q20UnnamedVariables(),
            new Q21PatternSwitchNull(),
            new Q22PatternSwitchGuards(),
            new Q23ExhaustiveSwitch(),
            new Q24PatternSwitchDominance(),
            new Q25RecordPatternDecomposition(),
            new Q26SealedInstanceofExhaustive(),
            new Q27VirtualThreadSpringBoot(),
            new Q28VirtualThreadCpuBound(),
            new Q29VirtualThreadResourceLimit(),
            new Q30VirtualThreadThreadLocalMdc(),
            new Q31ForeignFunctionMemory(),
            new Q32ForeignMemoryApi(),
            new Q33StringTemplates(),
            new Q34StringTemplateSqlSafety(),
            // 第3章 Exception Handling
            new Q35NumberFormatException(),
            new Q36CatchOrder(),
            new Q37MultiCatchSpecificity(),
            new Q38TryWithResourcesEffectivelyFinal(),
            new Q39SuppressedExceptions(),
            new Q40ExceptionChaining(),
            new Q41CustomUncheckedHierarchy(),
            new Q42InterruptedExceptionHandling(),
            new Q43ExceptionTranslationBoundary(),
            new Q44FutureExecutionException(),
            new Q45SealedExceptions(),
            // 第4章 Collections & Generics
            new Q46ViewVsCopy(),
            new Q47SequencedMapViews(),
            new Q48TreeSetComparator(),
            new Q49BoundedTypeParameter(),
            new Q50InvarianceExtendsWildcard(),
            new Q51PecsPrinciple(),
            new Q52TypeErasure(),
            new Q53ComputeIfAbsentSideEffects(),
            new Q54EqualsHashCodeMutableKey(),
            new Q55BlockingQueue(),
            new Q56LongAdder(),
            // 第5章 Streams, Lambda & Functional
            new Q57LimitIterate(),
            new Q58MethodReferences(),
            new Q59PrimitiveStreams(),
            new Q60ToListVsCollectorsToList(),
            new Q61DropWhileTakeWhile(),
            new Q62FlatMap(),
            new Q63StreamReduceReturn(),
            new Q64OptionalChaining(),
            new Q65OrElseVsOrElseGet(),
            new Q66GroupingByAveraging(),
            new Q67StringNormalize(),
            new Q68PeekSideEffects(),
            new Q69MapMultiVsFlatMap(),
            new Q70CollectorsTeeing(),
            new Q71OptionalExceptionAvoidance(),
            new Q72CheckedExceptionLambda(),
            new Q73CustomCollector(),
            new Q74ParallelStreamPitfalls(),
            // 第6章 I/O & NIO
            new Q75FilesReadWriteString(),
            new Q76ExplicitCharset(),
            new Q77PathOperations(),
            new Q78PathTraversalPrevention(),
            new Q79FilesLinesClose(),
            new Q80ByteBufferChannel(),
            new Q81WatchService(),
            new Q82AsynchronousFileChannel(),
            new Q83WalkFileTree(),
            new Q84FileChannelMemoryMapped(),
            new Q85MemoryMappedIO(),
            new Q86SerializationSecurity(),
            // 第7章 Concurrency & Threading
            new Q87AtomicOperations(),
            new Q88VolatileCompoundLimit(),
            new Q89FinalFieldSafePublication(),
            new Q90BlockingQueueOverWaitNotify(),
            new Q91ExecutorShutdown(),
            new Q92ExecutorTryWithResources(),
            new Q93LatchVsBarrier(),
            new Q94ReentrantLockVsSynchronized(),
            new Q95ConcurrentHashMapAtomicUpdate(),
            new Q96RejectedExecution(),
            new Q97CompletableFutureChaining(),
            new Q98CompletableFutureExceptionTimeout(),
            new Q99ForkJoinPool(),
            new Q100StampedLockOptimisticRead(),
            new Q101StampedLockNonReentrant(),
            new Q102VirtualThreads(),
            new Q103VirtualThreadPinning(),
            new Q104VirtualThreadThreadLocal(),
            new Q105ScopedValues(),
            new Q106StructuredConcurrency(),
            new Q107StructuredConcurrencyApplicability(),
            // 第8章 Memory, GC & Performance
            new Q109CompactStrings(),
            new Q110StringIntern(),
            new Q111StringConcatLoop(),
            new Q112StringIdentity(),
            new Q113EscapeAnalysis(),
            new Q114ClosureReachability(),
            new Q115WeakSoftReference(),
            new Q116LazyInitialization(),
            new Q117StaticCollectionLeak(),
            new Q118CacheGrowthBound(),
            new Q119DirectByteBuffer(),
            new Q120G1Fragmentation(),
            new Q122GcLogDiagnosis(),
            new Q123ObjectPooling(),
            new Q124Jmh(),
            // 第9章 JPMS & Modularity
            new Q125ModuleEncapsulation(),
            new Q126TransitiveDependencies(),
            new Q127QualifiedExports(),
            new Q128RequiresStatic(),
            new Q129JpmsReflectionAccess(),
            new Q130OpensMinimization(),
            new Q131ServiceLoader(),
            new Q133AutomaticModule(),
            // 第10章 Design Patterns
            new Q136DependencyInversion(),
            new Q137Immutability(),
            new Q138BuilderWithRecords(),
            new Q139StrategyWithLambda(),
            new Q140TemplateMethodVsStrategy(),
            new Q141SealedFactory(),
            new Q142DecoratorCrossCutting(),
            new Q143AdapterFacade(),
            new Q144SpecificationPattern(),
            new Q145CommandOutbox(),
            new Q146SingletonVsDi(),
            // 第11章 Architecture
            new Q147DddBuildingBlocks(),
            new Q148BoundedContext(),
            new Q149HexagonalArchitecture(),
            new Q150ModularMonolithVsMicroservices(),
            new Q152CircuitBreaker(),
            new Q153EventDrivenBasics(),
            new Q155Cqrs(),
            new Q156Saga(),
            new Q158EventSourcing(),
            new Q160ArchitectureFitnessFunction(),
            // 第14章 Spring Boot
            new Q188ConstructorInjection(),
            new Q201ReactiveBasics(),
            // 第15章 REST API & HTTP
            new Q205JavaHttpClient(),
            new Q208PutVsPatch(),
            new Q209HttpStatusGranularity(),
            new Q215ProblemDetails(),
            new Q216ApiVersioning(),
            new Q219WebSocketVsSse(),
            new Q220AcceptedAsyncApi(),
            new Q221IdempotencyKey(),
            new Q222ApiRetryIdempotency(),
            new Q223IfMatchLostUpdate(),
            new Q224RateLimiting(),
            new Q225CorsVsAuth(),
            // 第16章 Security & Auth
            new Q227SqlInjection(),
            new Q228DirectoryTraversal(),
            new Q229SecureCodingBestPractices(),
            new Q230PasswordHashing(),
            new Q231SecureRandomCrypto(),
            new Q233SecretManagement(),
            new Q234CorsMisconfiguration(),
            new Q235CsrfCookieAuth(),
            new Q236ContentSecurityPolicy(),
            new Q237SecurityFilterChain(),
            new Q239JwtClaimsValidation(),
            new Q240StatelessSession(),
            new Q242AuthorizationDesign(),
            new Q243RbacVsAbac(),
            new Q244ObjectLevelAuthorization(),
            new Q245SsrfPrevention(),
            new Q246UnsafeDeserialization(),
            // 第17章 Testing
            new Q248ParameterizedTest(),
            new Q250TestDataBuilder(),
            new Q251ClockTesting(),
            new Q258PropertyBasedTesting(),
            new Q259MutationTesting(),
            new Q260ConcurrentCodeTesting(),
            new Q262FlakyTestManagement(),
            // 第18章 Deploy & DevOps
            new Q269GraalVmNativeImage(),
            new Q273ZeroDowntimeMigration(),
            // 第19章 Logging & Observability
            new Q279CorrelationId(),
            new Q280StructuredLogging(),
            new Q281LogLevelSampling(),
            new Q282PiiAndSecretsInLogs(),
            new Q283MicrometerMetrics(),
            new Q284MetricsHighCardinality(),
            // 第20章 Debugging & Profiling
            new Q289ThreadDumpDeadlock(),
            new Q292HeapDumpRetainedSize(),
            new Q293JavaFlightRecorder(),
            new Q297GcAllocationPressure(),
            new Q298DbLatencyProfiling(),
            new Q299LoggingDebugPitfalls()
    );

    private SampleRegistry() {
    }
}
