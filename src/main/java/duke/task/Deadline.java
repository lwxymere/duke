package duke.task;

import duke.DukeException;
import duke.Parser;

import java.time.LocalDateTime;

public class Deadline extends Task {
    public static final char DEADLINE_ICON = 'D';

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String encodeTask() {
        return String.format("%s|%s|%s|%s",
                DEADLINE_ICON, isDone, description, by.format(Parser.INPUT_DATE_FORMAT)
        );
    }

    public static Deadline decodeTask(String encodedTask) {
        String[] tokens = encodedTask.split("\\" + DELIMITER);
        boolean isDone = Boolean.parseBoolean(tokens[1]);
        String description = tokens[2];
        LocalDateTime by = Parser.parseDate(tokens[3]);
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }

    @Override
    public String toString() {
        return "[" + DEADLINE_ICON + "]"
                + super.toString()
                + " (by: " + by.format(Parser.PRINT_DATE_FORMAT) + ")";
    }
}
