package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Deadline;
import duke.task.Task;

import java.time.LocalDateTime;

/**
 * Adds a Deadline to the TaskList.
 */
public class DeadlineCommand extends Command {
    /** Word to be typed by the user to invoke this Command */
    public static final String DEADLINE_COMMAND_NAME = "deadline";
    /** Delimiter that separates the description and date of the Command */
    public static final String COMMAND_DATE_TIME_DELIMITER = "\\s+/by\\s+";
    private final String description;
    private final LocalDateTime by;

    public DeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task addedTask = new Deadline(description, by);
        tasks.addTask(addedTask);
        ui.showAddedTaskMessage(addedTask, tasks.getNumTasks());
        attemptSave(tasks, ui, storage);
    }
}
