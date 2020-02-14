package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Duke {
    private static final String DUKE_LOGO =
            " ____        _        \n"
                    + "|  _ \\ _   _| | _____ \n"
                    + "| | | | | | | |/ / _ \\\n"
                    + "| |_| | |_| |   <  __/\n"
                    + "|____/ \\__,_|_|\\_\\___|\n";

    private static Storage storage = new Storage();
    private static TaskList tasks;

    public static void main(String[] args) {
        printWelcomeBanner();

        Scanner scanner = new Scanner(System.in);
        try {
            tasks = storage.load();
        } catch (FileNotFoundException e) {
            // no save file found
            tasks = new TaskList();
        } catch (DukeException e) {
            System.out.println(e.toString());
            tasks = new TaskList();
        }

        // Loop terminates on receiving a "bye" command, which calls System.exit(0)
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.print("> ");
            String fullCommand = scanner.nextLine();

            try {
                parseCommand(fullCommand);
            } catch (DukeException e) {
                System.out.println(e.toString());
            } finally {
                printDividerLine();
            }

            try {
                storage.save(tasks);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void parseCommand(String fullCommand) throws DukeException {
        String[] commandTokens = fullCommand.split(" ");
        String commandType = commandTokens[0].toLowerCase();

        switch(commandType) {
        case "bye":
            // exit duke
            printByeMessage();
            System.exit(0);

        case "list":
            // show all tasks in the list
            tasks.showTasks();
            break;

        case "delete":
            // delete a task by its displayed list index
            try {
                int deleteIndex = Integer.parseInt(commandTokens[1]);
                tasks.deleteTask(deleteIndex);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                // deleteIndex is not specified
                throw new DukeException("Please specify a task number to be deleted");
            } catch (NumberFormatException nfe) {
                throw new DukeException("Please specify an integer for the task number to be deleted");
            }
            break;

        case "done":
            // mark a task as done
            try {
                int taskIndex = Character.getNumericValue(fullCommand.charAt(5)) - 1;
                tasks.markTaskAsDone(taskIndex);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                // deleteIndex is not specified
                throw new DukeException("Please specify a task number to be marked as done");
            } catch (NumberFormatException nfe) {
                throw new DukeException("Please specify an integer for the task number to be marked as done");
            }
            break;

        case "todo":
            // add to`do to tasks
            // input follows format <taskType> <taskName>
            String description = null;
            try {
                description = fullCommand.substring(5);
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeException("The description of a todo cannot be empty");
            }

            tasks.addTaskWithMessage(new Todo(description));
            break;

        case "deadline":
            // add deadline to tasks
            // input follows format <taskType> <taskName> /<date>
            String[] deadlineInfo = null;
            try {
                deadlineInfo = fullCommand.substring(9).trim().split(" /by ");
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeException("The description and date of a deadline cannot be empty");
            }

            if (deadlineInfo.length != 2) {
                throw new DukeException("The description and date of a deadline cannot be empty");
            }
            String deadlineName = deadlineInfo[0].trim();
            String deadlineDate = deadlineInfo[1].trim();
            tasks.addTaskWithMessage(new Deadline(deadlineName, deadlineDate));
            break;

        case "event":
            // add event to tasks
            // input follows format <taskType> <taskName> /<date>
            String[] eventInfo = null;
            try {
                eventInfo = fullCommand.substring(6).trim().split(" /at ");
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeException("The description and date of an event cannot be empty");
            }

            if (eventInfo.length != 2) {
                throw new DukeException("The description and date of an event cannot be empty");
            }
            String eventName =eventInfo[0].trim();
            String eventDate = eventInfo[1].trim();
            tasks.addTaskWithMessage(new Event(eventName, eventDate));
            break;

        default:
            throw new DukeException("I'm not very sure what that means...");
        }
    }

    private static void printDividerLine() {
        System.out.println("_________________________________________________");
    }

    private static void printWelcomeBanner() {
        printDividerLine();
        System.out.println("This is" + System.lineSeparator() + DUKE_LOGO);
        System.out.println("How can I help you today?");
        printDividerLine();
    }

    private static void printByeMessage() {
        System.out.println("Goodbye");
        printDividerLine();
    }
}