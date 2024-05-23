import java.util.*;

public class TodoList {
    public final HashMap<Integer, TodoTask> tasks = new HashMap<>();

    public boolean createTask(int id, String title, String description, int priority, Date deadline) {
        if(tasks.containsKey(id))
            return false;

        tasks.put(id, new TodoTask(title, description, priority, deadline));
        saveChanges();
        return true;
    }

    public boolean removeTask(int id) {
        if(!tasks.containsKey(id))
            return false;

        tasks.remove(id);
        saveChanges();
        return true;
    }

    public void saveChanges() {

    }

    public void reload() {

    }

    public ArrayList<HashMap.Entry<Integer, TodoTask>> getTaskView() {
        var taskView = new ArrayList<HashMap.Entry<Integer, TodoTask>>();
        tasks.forEach((id,task) -> {
            taskView.add(new AbstractMap.SimpleEntry<>(id,task));
        });
        return taskView;
    }

    public ArrayList<HashMap.Entry<Integer, TodoTask>> getTaskView(TodoTask.Status matchStatus) {
        var taskView = new ArrayList<HashMap.Entry<Integer, TodoTask>>();
        tasks.forEach((id,task) -> {
            if(task.status.equals(matchStatus))
                taskView.add(new AbstractMap.SimpleEntry<>(id,task));
        });
        return taskView;
    }
}
