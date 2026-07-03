enum JobStatus {
    NEW,
    RUNNING,
    FAILED,
    COMPLETED
}
@@BLOCK@@
sealed interface JobCommand
        permits StartCommand, RetryCommand, CancelCommand {}

record StartCommand(String userId) implements JobCommand {}
record RetryCommand(String jobId, String reason) implements JobCommand {}
record CancelCommand(String jobId, String requestedBy) implements JobCommand {}
@@BLOCK@@
class JobCommand {
    String type;
    String userId;
    String jobId;
    String reason;
    String requestedBy;
}
@@BLOCK@@
new JobCommand("RETRY", null, null, null, "admin");
@@BLOCK@@
String describe(JobCommand command) {
    return switch (command) {
        case StartCommand s ->
                "start requested by " + s.userId();
        case RetryCommand r ->
                "retry job " + r.jobId() + " because " + r.reason();
        case CancelCommand c ->
                "cancel job " + c.jobId() + " by " + c.requestedBy();
    };
}
@@BLOCK@@
record PauseCommand(String jobId) implements JobCommand {}
@@BLOCK@@
enum JobStatus {
    NEW, RUNNING, FAILED, COMPLETED
}

sealed interface JobCommand
        permits StartCommand, RetryCommand, CancelCommand {
}

record StartCommand(String userId) implements JobCommand {
    StartCommand {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId is required");
        }
    }
}

record RetryCommand(String jobId, String reason) implements JobCommand {
    RetryCommand {
        if (jobId == null || jobId.isBlank()) {
            throw new IllegalArgumentException("jobId is required");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason is required");
        }
    }
}

record CancelCommand(String jobId, String requestedBy) implements JobCommand {
    CancelCommand {
        if (jobId == null || jobId.isBlank()) {
            throw new IllegalArgumentException("jobId is required");
        }
        if (requestedBy == null || requestedBy.isBlank()) {
            throw new IllegalArgumentException("requestedBy is required");
        }
    }
}