package duke;

import duke.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added:");
        System.out.println("  " + task);
        System.out.println("You have " + tasks.size() + " task" +
                (tasks.size() == 1 ? "" : "s") + " in the list");
    }

    public void showTasks() {
        if (tasks.size() == 0) {
            System.out.println("Nice, nothing to see here");
        } else {
            System.out.println("Quite a few tasks you got there");
            for (int i = 0; i < tasks.size(); ++i) {
                System.out.printf("%3d. %s%n", i + 1, tasks.get(i));
            }
        }
    }

    public void markTaskAsDone(int index) {
        tasks.get(index).markAsDone();
        System.out.println("Well, that's one task down");
        System.out.println("  " + tasks.get(index));
    }
}
