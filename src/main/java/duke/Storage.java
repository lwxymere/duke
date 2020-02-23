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

public class Storage {
    private static final String DEFAULT_FILE_PATH = "./dukeData.txt";
    private String filePath;

    public Storage() {
        this(DEFAULT_FILE_PATH);
    }

    public Storage(String path) {
        this.filePath = path;
    }

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
