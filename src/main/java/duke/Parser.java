package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

public class Parser {

    public static void parseCommand(String fullCommand, Ui ui, TaskList tasks) throws DukeException {
        String[] commandTokens = fullCommand.split(" ");
        String commandType = commandTokens[0].toLowerCase();

        switch (commandType) {
        case "bye":
            // exit duke
            ui.showByeMessage();
            System.exit(0);
            // Fallthrough

        case "list":
            // show all tasks in the list
            tasks.showTasks();
            break;

        case "delete":
            // delete a task by its displayed list index
            try {
                int deleteIndex = Integer.parseInt(commandTokens[1]) - 1;
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
                int taskIndex = Integer.parseInt(commandTokens[1]) - 1;
                tasks.markTaskAsDone(taskIndex);
            } catch (IndexOutOfBoundsException aioobe) {
                // taskIndex is not specified
                throw new DukeException("Please specify a valid task number to be marked as done");
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
            String eventName = eventInfo[0].trim();
            String eventDate = eventInfo[1].trim();
            tasks.addTaskWithMessage(new Event(eventName, eventDate));
            break;

        default:
            throw new DukeException("I'm not very sure what that means...");
        }
    }}
