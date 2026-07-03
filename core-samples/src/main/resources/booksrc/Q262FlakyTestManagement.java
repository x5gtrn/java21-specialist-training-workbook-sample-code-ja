@TempDir Path tempDir;
Random random = new Random(1234);
Clock fixedClock = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);