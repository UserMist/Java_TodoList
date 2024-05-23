package main.todolist;
import main.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Перечень задач с поддержкой сохранения на диск.
 * @see TodoTask
 */
public class TodoList {
    public final HashMap<Integer, TodoTask> tasks = new HashMap<>();
    protected Path savePath;
    protected RepresentationConverter<TodoList> converter;

    public Path getSavePath() {
        return savePath;
    }

    public void setSavePath(Path savePath) {
        this.savePath = savePath;
    }

    public RepresentationConverter<TodoList> getConverter() {
        return converter;
    }

    public void setConverter(RepresentationConverter<TodoList> converter) {
        this.converter = converter;
    }

    public TodoList(Path savePath, RepresentationConverter<TodoList> converter) throws Exception {
        this.savePath = savePath;
        this.converter = converter;
        reload();
    }
    public boolean createTask(int id, String title, String description, int priority, Date deadline) {
        if(tasks.containsKey(id))
            return false;

        tasks.put(id, new TodoTask(title, description, priority, deadline));
        return true;
    }

    public boolean removeTask(int id) {
        if(!tasks.containsKey(id))
            return false;

        tasks.remove(id);
        return true;
    }

    public void reload() throws Exception {
        converter.fromRepresentation(Files.readString(savePath), this);
    }

    public void saveChanges() throws Exception {
        var sb = new StringBuilder();
        converter.toRepresentation(this, sb);
        Files.writeString(savePath, sb);
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
            if(task.getStatus().equals(matchStatus))
                taskView.add(new AbstractMap.SimpleEntry<>(id,task));
        });
        return taskView;
    }
}
