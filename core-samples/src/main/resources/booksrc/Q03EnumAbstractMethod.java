public enum TaskStatus {
    PENDING {
        @Override
        public TaskStatus next() { return IN_PROGRESS; }
    },
    IN_PROGRESS {
        @Override
        public TaskStatus next() { return COMPLETED; }
    },
    COMPLETED {
        @Override
        public TaskStatus next() { return COMPLETED; }
    };

    public abstract TaskStatus next();
}
@@BLOCK@@
// コンパイル後のイメージ（概念的な等価コード）
// TaskStatus$1 extends TaskStatus（PENDING に対応）
// TaskStatus$2 extends TaskStatus（IN_PROGRESS に対応）
// TaskStatus$3 extends TaskStatus（COMPLETED に対応）

TaskStatus status = TaskStatus.PENDING;
System.out.println(status.next());  // IN_PROGRESS
System.out.println(status.next().next());  // COMPLETED
System.out.println(status.getClass().getName());  // TaskStatus$1（匿名サブクラス）