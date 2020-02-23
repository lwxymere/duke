package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class ListCommand extends Command {
    public static final String LIST_COMMAND_NAME = "list";

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.getNumTasks() == 0) {
            ui.showToUser(Ui.NO_TASKS_MESSAGE);
        } else {
            ui.showToUser(Ui.HAVE_TASKS_MESSAGE);
            tasks.showTasks();
        }
    }
}
