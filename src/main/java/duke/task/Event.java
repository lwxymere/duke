package duke.task;

public class Event extends Task {
    protected static final String EVENT_ICON = "E";

    protected String time;

    public Event(String description, String time) {
        super(description);
        this.time = time;
    }

    @Override
    public String encodeTask() {
        return String.format("%s | %s | %s | %s", EVENT_ICON, isDone, description, time);
    }

    public static Event decodeTask(String encodedTask) {
        String[] tokens = encodedTask.split("\\" + DELIMITER);
        boolean isDone = Boolean.parseBoolean(tokens[1]);
        String description = tokens[2];
        String time = tokens[3];
        Event event = new Event(description, time);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }

    @Override
    public String toString() {
        return "[" + EVENT_ICON + "]" + super.toString() + " (at: " + time + ")";
    }
}
