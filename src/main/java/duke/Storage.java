package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Handles local storage of the Duke TaskList
 */
public class Storage {
    private static final String DEFAULT_FILE_PATH = "./dukeData.txt";
    private String filePath;

    /**
     * Constructs Storage object with a default file path
     */
    public Storage() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Constructs the Storage object with the given filePath.
     * @param path desired location for the storage file
     */
    public Storage(String path) {
        this.filePath = path;
    }

    /**
     * Retrieves stored TaskList information from previous Duke sessions.
     * @return TaskList constructed using the stored information
     * @throws DukeException if storage file contents cannot be deciphered
     * @throws FileNotFoundException if no existing load file is found
     */
    public TaskList load() throws DukeException, FileNotFoundException {
        File f = new File(filePath);
        Scanner scanner = new Scanner(f);
        TaskList taskList = new TaskList();

        while (scanner.hasNextLine()) {
            String taskLine = scanner.nextLine();
            switch (taskLine.charAt(0)) {
            case Todo.TODO_ICON:
                // todo
                taskList.addTask(Todo.decodeTask(taskLine));
                break;
            case Deadline.DEADLINE_ICON:
                // deadline
                taskList.addTask(Deadline.decodeTask(taskLine));
                break;
            case Event.EVENT_ICON:
                // event
                taskList.addTask(Event.decodeTask(taskLine));
                break;
            default:
                throw new DukeException(Ui.UNKNOWN_STORAGE_FORMAT_MESSAGE);
            }
        }
        return taskList;
    }

    /**
     * Saves the current state of the TaskList into the local storage file.
     * @param taskList the TaskList to be saved
     * @throws IOException if error is encountered when writing to the file
     */
    public void save(TaskList taskList) throws IOException {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (Task task : taskList.getTasks()) {
            sj.add(task.encodeTask());
        }

        FileWriter fw = new FileWriter(filePath); // throws IOException
        fw.write(sj.toString()); // throws IOException
        fw.close();
    }
}
