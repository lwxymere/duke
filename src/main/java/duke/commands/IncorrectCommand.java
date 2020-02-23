package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

/**
 * An invalid or non-existent Command.
 */
public class IncorrectCommand extends Command {
    /** Error information to be shown to the user */
    public final String errorMessage;

    public IncorrectCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showToUser(Ui.GENERIC_ERROR_MESSAGE_PREFIX + errorMessage);
    }
}

