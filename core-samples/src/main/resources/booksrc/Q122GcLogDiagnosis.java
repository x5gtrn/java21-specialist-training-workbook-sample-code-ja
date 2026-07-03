// 避けたい: debug 無効でも巨大文字列を先に作る場合がある
log.debug("payload={}", payload.toPrettyJson());

// 改善: ログレベルや遅延評価を考慮する
if (log.isDebugEnabled()) {
    log.debug("payload={}", payload.toPrettyJson());
}