import java.util.*;

public class ToDoList {
    public HashMap<Integer,ToDoTask> tasks = new HashMap<>();

    public boolean createTask(int id, String title, String description, int priority, Date deadline) {
        if(tasks.containsKey(id))
            return false;

        tasks.put(id, new ToDoTask(title, description, priority, deadline));
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

    public ArrayList<HashMap.Entry<Integer,ToDoTask>> getTaskView() {
        var taskView = new ArrayList<HashMap.Entry<Integer,ToDoTask>>();
        tasks.forEach((id,task) -> {
            taskView.add(new AbstractMap.SimpleEntry<>(id,task));
        });
        return taskView;
    }

    public ArrayList<HashMap.Entry<Integer,ToDoTask>> getTaskView(ToDoTask.Status matchStatus) {
        var taskView = new ArrayList<HashMap.Entry<Integer,ToDoTask>>();
        tasks.forEach((id,task) -> {
            if(task.status.equals(matchStatus))
                taskView.add(new AbstractMap.SimpleEntry<>(id,task));
        });
        return taskView;
    }
}
