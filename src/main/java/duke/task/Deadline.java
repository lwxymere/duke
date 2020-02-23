package duke.task;

import duke.Parser;

import java.time.LocalDateTime;

/**
 * Type of Task that should be done before a specified date.
 */
public class Deadline extends Task {
    /** Icon used to represent a Deadline */
    public static final char DEADLINE_ICON = 'D';

    /** Date before which the Deadline should be done */
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

    /**
     * Returns the object representation of an encoded Deadline.
     * @param encodedTask a string returned by method Task.encodeTask()
     * @return a Deadline object whose information was stored in encodedTask
     */
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
