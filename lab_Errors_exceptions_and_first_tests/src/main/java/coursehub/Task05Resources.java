package coursehub;

import java.io.IOException;

public final class Task05Resources {
    private Task05Resources() { }
    public static final class LoggingResource implements AutoCloseable {
        private final StringBuilder log;
        private final boolean failOnClose;
        public LoggingResource(StringBuilder log, boolean failOnClose) {
            this.log = log; this.failOnClose = failOnClose;
            log.append("open; ");
        }
        public void use(boolean fail) throws IOException {
            log.append("use; ");
            if (fail) throw new IOException("ошибка use");
        }
        @Override public void close() throws IOException {
            log.append("close; ");
            if (failOnClose) throw new IOException("ошибка close");
        }
    }
    public static void run(StringBuilder log, boolean failUse, boolean failClose) throws IOException {
        try (LoggingResource resource = new LoggingResource(log, failClose)) {
            resource.use(failUse);
        }
    }
}
