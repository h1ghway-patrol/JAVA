package coursehub;

public final class Task06Importer {
    private Task06Importer() { }
    public static int parseHours(String text, int recordNumber) throws Task06CourseImportException {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException cause) {
            throw new Task06CourseImportException(recordNumber, cause);
        }
    }
}
