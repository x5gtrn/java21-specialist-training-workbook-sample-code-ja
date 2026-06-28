plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // H2 インメモリ DB（このモジュールは外部 DB 不要で完結する）
    runtimeOnly("com.h2database:h2")

    // 第17章 テストの例（JUnit 5 / Mockito は starter-test に同梱、Awaitility を追加）
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.awaitility:awaitility")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = 21
}

// ブラウザ UI で表示する本文コードは src/main/resources/booksrc/ に置く（標準のリソースとして同梱される）。

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

// 実行可能 jar の名前を固定（Dockerfile から参照しやすくする）
tasks.bootJar {
    archiveFileName = "framework-samples.jar"
}
