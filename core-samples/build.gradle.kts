plugins {
    application
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "com.x5gtrn.book.appendix.java21stw300.framework.WorkbookServer"
    // Preview 機能（String Templates / Scoped Values / Structured Concurrency / 無名変数）を使用
    applicationDefaultJvmArgs = listOf("--enable-preview")
}

// ブラウザ UI で表示する本文コードは src/main/resources/booksrc/ に置く（標準のリソースとして同梱される）。

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = 21
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs("--enable-preview")
}

// 依存が無いため、Main-Class を持つ通常の jar で `java --enable-preview -jar` 実行できる
tasks.jar {
    archiveFileName = "core-samples.jar"
    manifest {
        attributes["Main-Class"] = "com.x5gtrn.book.appendix.java21stw300.framework.WorkbookServer"
    }
}
