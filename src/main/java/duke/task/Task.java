package duke.task;

/**
 * Task that a user can keep of track of.
 */
public abstract class Task {
    protected static final String DELIMITER = "|";
    protected static final String TASK_DONE_ICON = "O";
    protected static final String TASK_NOT_DONE_ICON = "X";

    protected String description;
    protected boolean isDone;

    /**
     * Constructs a task using a given description. Task is marked as not done
     * by default.
     * @param description Task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a status icon that show whether the Task is done.
     * @return string representing the status icon
     */
    public String getStatusIcon() {
        return (isDone ? TASK_DONE_ICON : TASK_NOT_DONE_ICON);
    }

    /**
     * Returns whether a Task is currently done.
     * @return true if Task is done, false otherwise
     */
    public Boolean isDone() {
        return isDone;
    }

    /**
     * Returns the description of the Task.
     * @return Task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the status of the Task to done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Formats the Task information as a string that can be stored and decoded.
     * @return the encoded Task string
     */
    public abstract String encodeTask();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
