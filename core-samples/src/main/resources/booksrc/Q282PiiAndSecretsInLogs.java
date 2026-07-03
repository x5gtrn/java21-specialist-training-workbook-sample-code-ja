log.info("request body={}", requestBody);
log.info("認可={}", authorizationHeader);
@@BLOCK@@
log.info("payment request userId={} amount={} requestId={}",
    userId, amount, requestId);
@@BLOCK@@
String tokenFingerprint = sha256(token).substring(0, 12);
@@BLOCK@@
log.info("user profile updated userId={} requestId={} changedFields={}",
    userId, requestId, changedFields);