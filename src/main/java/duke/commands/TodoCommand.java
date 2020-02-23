package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.task.Task;
import duke.task.Todo;

public class TodoCommand extends Command {
    public static final String TODO_COMMAND_NAME = "todo";
    protected final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task addedTask = new Todo(description);
        tasks.addTask(addedTask);
        ui.showAddedTaskMessage(addedTask, tasks.getNumTasks());
        attemptSave(tasks, ui, storage);
    }
}
