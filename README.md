# Java 21 Specialist Training Workbook - Sample Code

書籍『**Java 21 スペシャリスト育成問題集**』（Java 21 Specialist Training Workbook）掲載コードを、
実際にコンパイル・実行できる形にまとめたサンプルプロジェクトです。**Gradle のマルチモジュール構成**で、
2 つのアプリを Docker コンテナで起動でき、いずれもブラウザ上でソース閲覧と実行・出力確認ができます。


| モジュール            | 内容                                                                                                                                                                                                                                                | 技術                                                              | ポート |
| --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------- | ------ |
| **core-samples**      | 第1〜11章・第14〜20章のうち純 JDK 21 だけで動くコア例（言語機能・Java 21 新機能・例外・コレクション・ストリーム・並行処理・メモリ・JPMS・デザインパターン・アーキテクチャ・REST・セキュリティ・テスト・ロギング・デバッグ ほか）。全 203 サンプル。 | 外部依存なしの純 JDK 21（内蔵 HTTP サーバ）。Preview 機能を使用。 | 8080   |
| **framework-samples** | 第12〜19章のフレームワーク例（JDBC・JPA/Hibernate・Spring Boot・REST・セキュリティ・ロギング・テスト）。実際に動かせるものを収録。全 54 サンプル。                                                                                                  | Spring Boot 3.3 / Hibernate 6 / H2 インメモリ DB。                | 8081   |

---

## 必要なもの

- **Docker** のみ（推奨）。ローカルに JDK / Gradle は不要です。
- ローカルビルドする場合は **JDK 21**（Gradle Wrapper を同梱しているため Gradle 本体のインストールは不要）。

---

## 起動方法

### Docker Compose（両方を一度に起動）

```bash
docker compose up --build
```

- core-samples … [http://localhost:8080](http://localhost:8080)
- framework-samples … [http://localhost:8081](http://localhost:8081)

### 個別に Docker で起動

```bash
# コア編
docker build -f core-samples/Dockerfile -t java21stw300-core-samples .
docker run --rm -p 8080:8080 java21stw300-core-samples

# フレームワーク編
docker build -f framework-samples/Dockerfile -t java21stw300-framework-samples .
docker run --rm -p 8081:8081 java21stw300-framework-samples
```

### ローカルで Gradle 実行（JDK 21 が必要）

```bash
# コア編（Preview 有効で起動）
./gradlew :core-samples:run

# フレームワーク編（Spring Boot 起動）
./gradlew :framework-samples:bootRun

# 第17章のテスト（Mockito / Awaitility）
./gradlew :framework-samples:test
```

> Windows は `gradlew.bat` を使用します。

---

## 画面の使い方（両アプリ共通）

1. 左サイドバーに章ごとのサンプル一覧が並びます。
2. 選ぶとソースコードが表示されます。
3. 「▶ 実行する」でその場で実行し、標準出力の内容と所要時間が表示されます。
   例外を意図的に発生させるサンプル（LazyInitializationException・OptimisticLockException・
   UnsupportedOperationException など）は、捕捉した例外の内容が出力に表示されます。

framework-samples では、REST 系サンプル（問題211・214）が**アプリ自身のエンドポイントを呼び出して**
400 Bad Request や 304 Not Modified を実演します。これらの REST エンドポイントは直接 curl でも確認できます。

```bash
# バリデーション失敗（400）
curl -i -X POST http://localhost:8081/api/demo/users \
  -H 'Content-Type: application/json' \
  -d '{"name":"","email":"bad","age":-1}'

# ETag による条件付き GET（2回目は 304）
curl -i http://localhost:8081/api/demo/config
curl -i http://localhost:8081/api/demo/config -H 'If-None-Match: "config-v1"'
```

H2 コンソールは [http://localhost:8081/h2-console](http://localhost:8081/h2-console)（JDBC URL: `jdbc:h2:mem:workbook`, user: `sa`）。

---

## プロジェクト構成

```
java21-specialist-workbook-sample/       (Gradle ルート)
├── settings.gradle.kts                  … core-samples / framework-samples を include
├── build.gradle.kts                     … 共通設定（group / version, repositories）
├── gradlew / gradlew.bat / gradle/       … Gradle Wrapper（8.10.2）
├── docker-compose.yml                    … 2 サービスを定義
├── LICENSE                               … MIT
│
├── core-samples/                         … 純 JDK モジュール（Preview 有効, ポート 8080, 全 203 サンプル）
│   ├── build.gradle.kts
│   ├── Dockerfile
│   ├── src/main/java/com/x5gtrn/book/appendix/java21stw300/
│   │   ├── framework/                    … Sample / SampleRunner / SampleRegistry / WorkbookServer
│   │   ├── ch01_language_oop/ 〜 ch11_architecture/
│   │   └── ch14_springboot/ 〜 ch20_debugging_profiling/
│   └── src/main/resources/
│       ├── web/index.html                … ブラウザ UI
│       ├── booksrc/                       … UI 表示用の本文ソース
│       └── META-INF/services/            … 第9章 ServiceLoader 用の登録ファイル
│
└── framework-samples/                    … Spring Boot モジュール（ポート 8081, 全 54 サンプル）
    ├── build.gradle.kts
    ├── Dockerfile
    ├── src/main/java/com/x5gtrn/book/appendix/java21stw300/
    │   ├── Application.java   … Spring Boot 起動クラス（@SpringBootApplication, 最上位パッケージ）
    │   ├── springapp/   … FrameworkSample / SampleRunner / SampleController / DataSeeder
    │   ├── domain/      … Department / Employee / Product（JPA エンティティ）
    │   ├── repo/        … Spring Data リポジトリ
    │   ├── web/         … バリデーション・ETag のデモ用 REST
    │   ├── ch12_database_jdbc/        … 問題161〜171
    │   ├── ch13_jpa_persistence/      … 問題172〜183
    │   ├── ch14_springboot/           … 問題184〜204
    │   ├── ch15_rest_api_http/        … 問題206〜218
    │   ├── ch16_security_auth/        … 問題226 / 238
    │   ├── ch17_testing/              … 問題202 / 252〜255
    │   └── ch19_logging_observability/… 問題278
    ├── src/main/resources/
    │   ├── application.yml             … H2 / JPA 設定（open-in-view=false 等）
    │   └── static/index.html           … ブラウザ UI
    └── src/test/java/.../ch17_testing/ … 問題249（Mockito）/ 261（Awaitility）
```

パッケージは `com.x5gtrn.book.appendix.java21stw300` を起点に、章名のサブパッケージ＋問題番号入りクラス
（例: `com.x5gtrn.book.appendix.java21stw300.ch13_jpa_persistence.Q176NPlusOne`）。

---

## 収録範囲について

- **core-samples** … 第1〜11章および第14〜20章から、純 JDK 21 だけで動作するサンプルを収録（全 203）。
- **framework-samples** … 第12〜19章から、Spring Boot / Hibernate / H2 で実際に挙動を確認できるサンプルを収録（全 54）。

各サンプルは本書の問題番号・タイトルに対応しています。コードとして「実行」になじまない概念中心の項目
（純粋な設計論など）は、対応するサンプルを設けていない場合があります。

### framework-samples が実演する主な挙動

- **問題174**: OSIV を無効化した状態での `LazyInitializationException` と JOIN FETCH での回避
- **問題176**: N+1 SELECT を Hibernate Statistics の発行クエリ数で可視化（N+1 版 vs JOIN FETCH 版）
- **問題179**: `@Version` による楽観的ロックと `OptimisticLockException`
- **問題194**: `@Cacheable` のキャッシュヒット（DB アクセス回数で確認）
- **問題211 / 214**: バリデーション 400・ETag/304 を実際の HTTP 呼び出しで確認

---

## 技術メモ

- **Gradle マルチモジュール**。ルートの `settings.gradle.kts` が 2 サブプロジェクトを束ねます。
- core-samples は **外部依存ゼロ**。Web サーバは JDK 内蔵の `com.sun.net.httpserver.HttpServer`。
  Preview 機能（String Templates / Scoped Values / Structured Concurrency / 無名変数）を使うため、
  コンパイル・実行とも `--enable-preview` を有効化しています（JDK 21 固定）。
- framework-samples は H2 **インメモリ DB** で完結し、外部 DB は不要です。
  `spring.jpa.open-in-view=false`（問題174 の再現）と `hibernate.generate_statistics=true`
  （問題176 の計測）を設定しています。
- Docker ビルドは `gradle:8.10.2-jdk21` イメージでビルドし、実行は `eclipse-temurin:21-jre` を使います。
  ビルド時に Maven Central から依存を解決します（インターネット接続が必要）。

---

## ライセンス

MIT License（[LICENSE](./LICENSE) を参照）。本サンプルコードは上記書籍の付録として公開しています。
