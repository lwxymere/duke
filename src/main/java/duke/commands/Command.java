package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

import java.io.IOException;

/**
 * A Duke Command given by a user to be executed.
 */
public abstract class Command {
    /**
     * Run the specified Command.
     * @param tasks TaskList containing the current user's Tasks
     * @param ui Ui object to handle user feedback
     * @param storage Storage object to save data if the TaskList is updated
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Saves the current state of the TaskList after a Command is executed.
     * @param tasks TaskList containing the current user's Tasks
     * @param ui Ui object to handle user feedback
     * @param storage Storage object to save data if the TaskList is updated
     */
    protected void attemptSave(TaskList tasks, Ui ui, Storage storage) {
        try {
            storage.save(tasks);
        } catch (IOException ioe) {
            ui.showToUser(ioe.getMessage());
        }
    }
}
