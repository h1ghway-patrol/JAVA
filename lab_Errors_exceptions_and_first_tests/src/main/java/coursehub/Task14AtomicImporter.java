package coursehub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public final class Task14AtomicImporter {
    private Task14AtomicImporter() { }
    public record ImportResult(List<Integer> hours, int processedRecords) { }
    public static final class ImportFailure extends Exception {
        private final int processedRecords;
        public ImportFailure(String message, Throwable cause, int processedRecords) {
            super(message, cause); this.processedRecords = processedRecords;
        }
        public int processedRecords() { return processedRecords; }
    }
    public static ImportResult importHours(Reader source) throws ImportFailure {
        List<Integer> temporary = new ArrayList<>();
        int processed = 0;
        try (BufferedReader reader = new BufferedReader(source)) {
            String line;
            while ((line = reader.readLine()) != null) {
                processed++;
                try {
                    temporary.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException cause) {
                    throw new ImportFailure("Некорректная запись №" + processed, cause, processed);
                }
            }
        } catch (ImportFailure failure) {
            throw failure;
        } catch (IOException failure) {
            throw new ImportFailure("Ошибка чтения или закрытия источника", failure, processed);
        }
        return new ImportResult(List.copyOf(temporary), processed);
    }
}
