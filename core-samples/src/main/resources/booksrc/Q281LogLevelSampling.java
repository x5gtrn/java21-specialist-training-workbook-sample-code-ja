log.info("order created orderId={} userId={}", orderId, userId);
log.warn("payment retry scheduled orderId={} attempt={}", orderId, attempt);
log.error("payment failed orderId={} reason={}", orderId, reason, ex);