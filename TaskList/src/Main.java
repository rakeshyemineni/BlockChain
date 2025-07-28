import java.util.*;
import java.lang.*;

class Task {
    int taskId;
    String taskName;
    String taskType;
    Task prev, next;

    Task(int id, String name, String type) {
        this.taskId = id;
        this.taskName = name;
        this.taskType = type;
    }

    @Override
    public String toString() {
        return "[ID: " + taskId + ", Name: " + taskName + ", Type: " + taskType + "]";
    }
}

class TaskList {
    Task head, tail;

    public void addTask(Task task) {
        if (head == null) {
            head = tail = task;
        } else {
            tail.next = task;
            task.prev = tail;
            tail = task;
        }
        System.out.println("Task Added: " + task);
    }

    public Task removeTask(int id) {
        Task current = head;
        while (current != null) {
            if (current.taskId == id) {
                if (current.prev != null) current.prev.next = current.next;
                else head = current.next;
                if (current.next != null) current.next.prev = current.prev;
                else tail = current.prev;
                System.out.println("Task Removed: " + current);
                return current;
            }
            current = current.next;
        }
        System.out.println("Task ID " + id + " not found!");
        return null;
    }

    public void printTasks() {
        Task current = head;
        if (current == null) {
            System.out.println("No tasks available!");
            return;
        }
        System.out.println("Tasks in order:");
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    public void printReverse() {
        Task current = tail;
        if (current == null) {
            System.out.println("No tasks available!");
            return;
        }
        System.out.println("Tasks in reverse order:");
        while (current != null) {
            System.out.println(current);
            current = current.prev;
        }
    }

    public void sortByName() {
        List<Task> tempList = toArrayList();
        tempList.sort(Comparator.comparing(t -> t.taskName.toLowerCase()));
        System.out.println("Tasks Sorted by Name:");
        tempList.forEach(System.out::println);
    }

    public void sortByType() {
        List<Task> tempList = toArrayList();
        tempList.sort(Comparator.comparing(t -> t.taskType.toLowerCase()));
        System.out.println("Tasks Sorted by Type:");
        tempList.forEach(System.out::println);
    }

    private List<Task> toArrayList() {
        List<Task> list = new ArrayList<>();
        Task current = head;
        while (current != null) {
            list.add(current);
            current = current.next;
        }
        return list;
    }
}

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskList taskList = new TaskList();
        Stack<Task> deletedTasks = new Stack<>();
        System.out.println("Enter commands (A: Add, R: Remove, P: Print, REV: Reverse, SN: Sort Name, ST: Sort Type, U: Undo, Q: Quit)");
        while (true) {
            String input = sc.nextLine().trim();
            String[] parts = input.split("\\s+");
            String command = parts[0].toUpperCase();
            switch (command) {
                case "A":
                    if (parts.length < 4) {
                        System.out.println("Usage: A <TaskID> <TaskName> <TaskType>");
                        break;
                    }
                    int id = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    String type = parts[3];
                    taskList.addTask(new Task(id, name, type));
                    break;
                case "R":
                    if (parts.length < 2) {
                        System.out.println("Usage: R <TaskID>");
                        break;
                    }
                    int removeId = Integer.parseInt(parts[1]);
                    Task removed = taskList.removeTask(removeId);
                    if (removed != null) deletedTasks.push(removed);
                    break;
                case "P":
                    taskList.printTasks();
                    break;
                case "REV":
                    taskList.printReverse();
                    break;
                case "SN":
                    taskList.sortByName();
                    break;
                case "ST":
                    taskList.sortByType();
                    break;
                case "U":
                    if (!deletedTasks.isEmpty()) {
                        Task undoTask = deletedTasks.pop();
                        taskList.addTask(new Task(undoTask.taskId, undoTask.taskName, undoTask.taskType));
                        System.out.println("Undo Successful!");
                    } else {
                        System.out.println("Nothing to undo!");
                    }
                    break;
                case "Q":
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }
}
