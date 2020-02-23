package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Task;

/**
 * Searches for Tasks with descriptions that match a query string.
 */
public class FindCommand extends Command {
    /** Word to be typed by the user to invoke this Command */
    public static final String FIND_COMMAND_NAME = "find";
    private final String searchString;

    public FindCommand(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = new TaskList();
        for (Task task : tasks.getTasks()) {
            String description = task.getDescription();
            if (description.contains(searchString)) {
                matchingTasks.addTask(task);
            }
        }

        if (matchingTasks.getNumTasks() == 0) {
            ui.showToUser(Ui.NO_MATCHING_TASKS_MESSAGE);
        } else {
            ui.showToUser(Ui.FOUND_MATCHING_TASKS_MESSAGE);
            matchingTasks.showTasks();
        }
    }
}
