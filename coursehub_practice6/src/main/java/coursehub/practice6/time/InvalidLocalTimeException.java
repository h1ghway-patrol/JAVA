package coursehub.practice6.time;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InvalidLocalTimeException extends IllegalArgumentException {
    public InvalidLocalTimeException(LocalDateTime localDateTime, ZoneId zone) {
        super("Local time " + localDateTime + " does not exist in zone " + zone);
    }
}
