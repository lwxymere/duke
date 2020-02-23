package duke;

import duke.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getNumTasks() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void showTasks() {
        for (int i = 0; i < tasks.size(); ++i) {
            System.out.printf("%3d. %s%n", i + 1, tasks.get(i));
        }
    }

    public Task deleteTask(int index) throws IndexOutOfBoundsException {
        Task deletedTask = tasks.get(index);
        tasks.remove(index);
        return deletedTask;
    }

    public Task markTaskAsDone(int index) throws IndexOutOfBoundsException, DukeException {
        Task selectedTask = tasks.get(index);
        if (selectedTask.isDone()) {
            throw new DukeException(Ui.TASK_ALREADY_DONE_MESSAGE);
        }
        selectedTask.markAsDone();
        return selectedTask;
    }
}
