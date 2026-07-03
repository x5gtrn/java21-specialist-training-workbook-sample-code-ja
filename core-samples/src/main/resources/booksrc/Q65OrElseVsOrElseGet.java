UserProfile profile = cache.find(userId)
        .orElse(remoteClient.fetchProfile(userId));
@@BLOCK@@
UserProfile profile = cache.find(userId)
        .orElse(remoteClient.fetchProfile(userId));
@@BLOCK@@
UserProfile profile = cache.find(userId)
        .orElseGet(() -> remoteClient.fetchProfile(userId));
@@BLOCK@@
Optional<String> present = Optional.of("cached");

String a = present.orElse(expensive("orElse"));
// expensive("orElse") は呼ばれる

String b = present.orElseGet(() -> expensive("orElseGet"));
// expensive("orElseGet") は呼ばれない
@@BLOCK@@
String displayName = user.nickname().orElse("anonymous");
@@BLOCK@@
UserProfile profile = cache.find(userId)
        .orElseGet(() -> remoteClient.fetchProfile(userId));
@@BLOCK@@
UserProfile profile = cache.find(userId)
        .orElseGet(() -> {
            metrics.increment("profile.cache.miss");
            return remoteClient.fetchProfile(userId);
        });
@@BLOCK@@
UserProfile profile = cache.find(userId)
        .orElseGet(() -> loadProfileFromRemote(userId));