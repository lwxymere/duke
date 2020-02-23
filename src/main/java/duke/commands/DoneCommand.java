package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Task;

public class DoneCommand extends Command {
    public static final String DONE_COMMAND_NAME = "done";
    protected final int doneIndex;

    public DoneCommand(int doneIndex) {
        this.doneIndex = doneIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        int numTasks = tasks.getNumTasks();
        try {
            Task doneTask = tasks.markTaskAsDone(doneIndex);
            ui.showToUser(Ui.TASK_MARKED_AS_DONE_MESSAGE + doneTask);
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
        } catch (DukeException de) {
            ui.showToUser(de.toString());
        }
        attemptSave(tasks, ui, storage);
    }
}
