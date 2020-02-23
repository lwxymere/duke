package duke.task;

import duke.DukeException;
import duke.Parser;

import java.time.LocalDateTime;

public class Event extends Task {
    public static final char EVENT_ICON = 'E';

    protected LocalDateTime time;

    public Event(String description, LocalDateTime time) {
        super(description);
        this.time = time;
    }

    @Override
    public String encodeTask() {
        return String.format("%s|%s|%s|%s",
                EVENT_ICON, isDone, description, time.format(Parser.INPUT_DATE_FORMAT));
    }

    public static Event decodeTask(String encodedTask) {
        String[] tokens = encodedTask.split("\\" + DELIMITER);
        boolean isDone = Boolean.parseBoolean(tokens[1]);
        String description = tokens[2];
        LocalDateTime time = Parser.parseDate(tokens[3]);
        Event event = new Event(description, time);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }

    @Override
    public String toString() {
        return "[" + EVENT_ICON + "]"
                + super.toString()
                + " (at: " + time.format(Parser.PRINT_DATE_FORMAT) + ")";
    }
}
