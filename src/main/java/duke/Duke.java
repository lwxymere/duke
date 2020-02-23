package duke;

import duke.commands.Command;

import java.io.FileNotFoundException;

/**
 * Contains main logic for Duke
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs Duke object and loads existing save data, if any.
     */
    public Duke() {
        this.storage = new Storage();
        this.ui = new Ui();
        try {
            tasks = storage.load();
        } catch (FileNotFoundException e) {
            // no save file found
            tasks = new TaskList();
        } catch (DukeException e) {
            ui.showToUser(e.toString());
            tasks = new TaskList();
        }
    }

    /**
     * Continuously read and execute Duke commands input by the user, until the
     * 'Bye' command is entered where Duke exits.
     */
    public void run() {
        ui.showWelcomeMessage();
        // Loop terminates on receiving a "bye" command, which calls System.exit(0)
        //noinspection InfiniteLoopStatement
        while (true) {
            String fullCommand = ui.getUserCommand();
            Command command = Parser.parseCommand(fullCommand);
            command.execute(tasks, ui, storage);
            ui.showDivider();
        }
    }

    /**
     * Main entry point into Duke's logic
     * @param args unused
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}
