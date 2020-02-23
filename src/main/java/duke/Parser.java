package duke;

import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.DeleteCommand;
import duke.commands.DeadlineCommand;
import duke.commands.DoneCommand;
import duke.commands.EventCommand;
import duke.commands.FindCommand;
import duke.commands.IncorrectCommand;
import duke.commands.ListCommand;
import duke.commands.TodoCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy HHmm");
    public static final DateTimeFormatter PRINT_DATE_FORMAT = DateTimeFormatter.ofPattern("EEE dd MMM yyyy HH':'mm");

    public static Command parseCommand(String fullCommand) {
        String[] commandTokens = fullCommand.split(" ");
        String commandType = commandTokens[0].toLowerCase();

        switch (commandType) {
        case ByeCommand.BYE_COMMAND_NAME:
            // exit duke
            return new ByeCommand();
        case ListCommand.LIST_COMMAND_NAME:
            // show all tasks in the list
            return new ListCommand();
        case DeleteCommand.DELETE_COMMAND_NAME:
            // delete a task by its displayed list index
            return prepareDeleteCommand(commandTokens);
        case DoneCommand.DONE_COMMAND_NAME:
            // mark a task as done
            return prepareDoneCommand(commandTokens);
        case FindCommand.FIND_COMMAND_NAME:
            // find tasks with descriptions that match a query string
            return prepareFindCommand(fullCommand);
        case TodoCommand.TODO_COMMAND_NAME:
            // add to`do to tasks
            return prepareTodoCommand(fullCommand);
        case DeadlineCommand.DEADLINE_COMMAND_NAME:
            // add deadline to tasks
            return prepareDeadlineCommand(fullCommand);
        case EventCommand.EVENT_COMMAND_NAME:
            // add event to tasks
            return prepareEventCommand(fullCommand);
        default:
            return new IncorrectCommand(Ui.UNKNOWN_COMMAND_NAME_MESSAGE);
        }
    }

    public static LocalDateTime parseDate(String dateTimeString) throws DateTimeParseException {
        // handle issue where there are multiple spaces between the date and the time
        String[] dateAndTime = dateTimeString.split("\\s+", 2);
        String formattedDateTimeString = dateAndTime[0] + " " + dateAndTime[1];
        return LocalDateTime.parse(formattedDateTimeString, INPUT_DATE_FORMAT);
    }

    public static void main(String[] args) {
        String d1 = "12/3/2020 1800";
        String d2 = "2020-3-12T18:00:00";
        String d3 = "12/03/2020     1830";
        String[] d3split = d3.split("\\s+", 2);
        String d4 = d3split[0] + " " + d3split[1];
        System.out.println(d4);
        String d5 = "12/03/2020 1830";
        DateTimeFormatter output = DateTimeFormatter.ofPattern("EEE dd MMM yyyy");

        try {
//            System.out.println(parseDate(d1).toString());
//            System.out.println(parseDate(d4).format(output));
            System.out.println(parseDate(d4).toString());
        } catch (DateTimeParseException e){
            System.out.println(e);
        }
    }

    private static Command prepareDeadlineCommand(String fullCommand) {
        // deadline command follows format <taskType> <taskName> /<date>
        String[] deadlineInfo = null;
        try {
            deadlineInfo = fullCommand
                    .substring(DeadlineCommand.DEADLINE_COMMAND_NAME.length() + 1)
                    .trim()
                    .split(DeadlineCommand.COMMAND_DATE_TIME_DELIMITER, 2);
        } catch (StringIndexOutOfBoundsException e) {
            return new IncorrectCommand(Ui.DEADLINE_INSUFFICIENT_ARGS_MESSAGE);
        }

        if (deadlineInfo.length != 2) {
            return new IncorrectCommand(Ui.DEADLINE_INSUFFICIENT_ARGS_MESSAGE);
        }

        String deadlineName = deadlineInfo[0].trim();
        LocalDateTime deadlineDate;
        try {
            deadlineDate = parseDate(deadlineInfo[1].trim());
        } catch (DateTimeParseException dtpe) {
            return new IncorrectCommand(Ui.INVALID_DATE_FORMAT_MESSAGE);
        }
        return new DeadlineCommand(deadlineName, deadlineDate);
    }

    private static Command prepareDeleteCommand(String[] commandTokens) {
        try {
            int deleteIndex = Integer.parseInt(commandTokens[1]) - 1;
            return new DeleteCommand(deleteIndex);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // deleteIndex is not specified
            return new IncorrectCommand(Ui.TASK_NUMBER_NOT_SPECIFIED_MESSAGE);
        } catch (NumberFormatException nfe) {
            // commandTokens[1] is unable to be parsed as an int
            return new IncorrectCommand(Ui.TASK_NUMBER_NOT_INTEGER_MESSAGE);
        }
    }

    private static Command prepareDoneCommand(String[] commandTokens) {
        try {
            int doneIndex = Integer.parseInt(commandTokens[1]) - 1;
            return new DoneCommand(doneIndex);
        } catch (IndexOutOfBoundsException aioobe) {
            // taskIndex is not specified
            return new IncorrectCommand(Ui.TASK_NUMBER_NOT_SPECIFIED_MESSAGE);
        } catch (NumberFormatException nfe) {
            return new IncorrectCommand(Ui.TASK_NUMBER_NOT_INTEGER_MESSAGE);
        }
    }

    private static Command prepareEventCommand(String fullCommand) {
        // event command follows format <taskType> <taskName> /<date>
        String[] eventInfo = null;
        try {
            eventInfo = fullCommand
                    .substring(EventCommand.EVENT_COMMAND_NAME.length() + 1)
                    .trim()
                    .split(EventCommand.COMMAND_DATE_TIME_DELIMITER, 2);
        } catch (StringIndexOutOfBoundsException e) {
            return new IncorrectCommand(Ui.EVENT_INSUFFICIENT_ARGS_MESSAGE);
        }

        if (eventInfo.length != 2) {
            return new IncorrectCommand(Ui.EVENT_INSUFFICIENT_ARGS_MESSAGE);
        }
        String eventName = eventInfo[0].trim();
        LocalDateTime eventDate;
        try {
            eventDate = parseDate(eventInfo[1].trim());
        } catch (DateTimeParseException dtpe) {
            return new IncorrectCommand(Ui.INVALID_DATE_FORMAT_MESSAGE);
        }
        return new EventCommand(eventName, eventDate);
    }

    private static Command prepareFindCommand(String fullCommand) {
        try {
            String searchString = fullCommand.substring(FindCommand.FIND_COMMAND_NAME.length() + 1).trim();
            return new FindCommand(searchString);
        } catch (StringIndexOutOfBoundsException sioobe) {
            return new IncorrectCommand(Ui.FIND_INSUFFICIENT_ARGS_MESSAGE);
        }
    }

    private static Command prepareTodoCommand(String fullCommand) {
        // to`do command follows format <taskType> <taskName>
        try {
            String description = fullCommand.substring(TodoCommand.TODO_COMMAND_NAME.length() + 1).trim();
            return new TodoCommand(description);
        } catch (StringIndexOutOfBoundsException e) {
            return new IncorrectCommand(Ui.TODO_INSUFFICIENT_ARGS_MESSAGE);
        }
    }
}
