package coursehub;

public final class Task06CourseImportException extends Exception {
    private final int recordNumber;
    public Task06CourseImportException(int recordNumber, Throwable cause) {
        super("Не удалось импортировать запись №" + recordNumber, cause);
        if (recordNumber < 1) throw new IllegalArgumentException("recordNumber должен быть положительным");
        this.recordNumber = recordNumber;
    }
    public int recordNumber() { return recordNumber; }
}
