package duke.task;

/**
 * Type of Task that is not tied to a date.
 */
public class Todo extends Task {
    /** Icon used to represent a Todo */
    public static final char TODO_ICON = 'T';

    public Todo(String description) {
        super(description);
    }

    @Override
    public String encodeTask() {
        return String.format("%s|%s|%s", TODO_ICON, isDone, description);
    }

    /**
     * Returns the object representation of an encoded Todo.
     * @param encodedTask a string returned by method Task.encodeTask()
     * @return a Todo object whose information was stored in encodedTask
     */
    public static Todo decodeTask(String encodedTask) {
        String[] tokens = encodedTask.split("\\" + DELIMITER);
        boolean isDone = Boolean.parseBoolean(tokens[1]);
        String description = tokens[2];
        Todo todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }

    @Override
    public String toString() {
        return "[" + TODO_ICON + "]" + super.toString();
    }
}
