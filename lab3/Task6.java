import java.util.*;

public class Task6 {
    static class Result<T> {
        private final T value;
        private final String error;

        private Result(T value, String error) {
            this.value = value;
            this.error = error;
        }

        public static <T> Result<T> success(T value) {
            return new Result<>(Objects.requireNonNull(value), null);
        }

        public static <T> Result<T> failure(String error) {
            return new Result<>(null, Objects.requireNonNull(error));
        }

        public boolean isSuccess() {
            return error == null;
        }

        public T getValue() {
            if (!isSuccess()) {
                throw new IllegalStateException(error);
            }
            return value;
        }

        public String getError() {
            if (isSuccess()) {
                throw new IllegalStateException("Result has no error");
            }
            return error;
        }
    }

    public static void main(String[] args) {
        Result<String> name = Result.success("Java");
        Result<Integer> count = Result.success(10);
        Result<String> failed = Result.failure("Not found");

        System.out.println(name.getValue());
        System.out.println(count.getValue());

        if (!failed.isSuccess()) {
            System.out.println(failed.getError());
        }
    }
}
