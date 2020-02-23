package duke;

import duke.commands.Command;

import java.io.FileNotFoundException;

public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Duke().run();
    }
}
