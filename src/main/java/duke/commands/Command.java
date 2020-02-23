package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

import java.io.IOException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    protected void attemptSave(TaskList tasks, Ui ui, Storage storage) {
        try {
            storage.save(tasks);
        } catch (IOException ioe) {
            ui.showToUser(ioe.getMessage());
        }
    }
}
