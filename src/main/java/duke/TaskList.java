package duke;

import duke.task.Task;

import java.util.ArrayList;

/**
 * List of Task objects
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Returns the list of Tasks
     * @return the list of Tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the TaskList
     * @return number of tasks in the TaskList
     */
    public int getNumTasks() {
        return tasks.size();
    }

    /**
     * Adds a task to the TaskList
     * @param task task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Prints all tasks in the TaskList. Printed tasks may no longer by well
     * aligned if the list contains more than 999 tasks.
     */
    public void showTasks() {
        for (int i = 0; i < tasks.size(); ++i) {
            System.out.printf("%3d. %s%n", i + 1, tasks.get(i));
        }
    }

    /**
     * Deletes a task from the TaskList.
     * @param index list index of task to be deleted
     * @return the deleted task
     * @throws IndexOutOfBoundsException if given index falls outside the list
     */
    public Task deleteTask(int index) throws IndexOutOfBoundsException {
        Task deletedTask = tasks.get(index);
        tasks.remove(index);
        return deletedTask;
    }

    /**
     * Changes the status of a task from not done to done.
     * @param index list index of task to be marked as done
     * @return the task that was marked as done
     * @throws IndexOutOfBoundsException if given index falls outside the list
     * @throws DukeException if task is already done
     */
    public Task markTaskAsDone(int index) throws IndexOutOfBoundsException, DukeException {
        Task selectedTask = tasks.get(index);
        if (selectedTask.isDone()) {
            throw new DukeException(Ui.TASK_ALREADY_DONE_MESSAGE);
        }
        selectedTask.markAsDone();
        return selectedTask;
    }
}
