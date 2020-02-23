package duke.task;

import duke.DukeException;

public class Todo extends Task {
    public static final char TODO_ICON = 'T';

    public Todo(String description) {
        super(description);
    }

    @Override
    public String encodeTask() {
        return String.format("%s|%s|%s", TODO_ICON, isDone, description);
    }

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
