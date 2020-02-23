package duke.task;

import duke.Parser;

import java.time.LocalDateTime;

/**
 * Type of Task that takes place at a specified date.
 */
public class Event extends Task {
    /** Icon used to represent an Event */
    public static final char EVENT_ICON = 'E';

    /** Date at which the Event will be held */
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

    /**
     * Returns the object representation of an encoded Event.
     * @param encodedTask a string returned by method Task.encodeTask()
     * @return an Event object whose information was stored in encodedTask
     */
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
