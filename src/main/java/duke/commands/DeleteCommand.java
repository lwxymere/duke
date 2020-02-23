package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Task;

/**
 * Deletes a Task from the TaskList.
 */
public class DeleteCommand extends Command {
    /** Word to be typed by the user to invoke this Command */
    public static final String DELETE_COMMAND_NAME = "delete";
    private final int deleteIndex;

    public DeleteCommand(int deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        int numTasks = tasks.getNumTasks();
        try {
            Task deletedTask = tasks.deleteTask(deleteIndex);
            numTasks -= 1;
            ui.showDeletedTaskMessage(deletedTask, numTasks);
        } catch (IndexOutOfBoundsException ioobe) {
            if (numTasks == 0) {
                ui.showToUser(Ui.NO_TASKS_MESSAGE);
            } else {
                ui.showToUser(
                        Ui.GENERIC_ERROR_MESSAGE_PREFIX
                                + Ui.TASK_INDEX_OUT_OF_RANGE_MESSAGE
                                + numTasks
                );
            }
        }
        attemptSave(tasks, ui, storage);
    }
}
