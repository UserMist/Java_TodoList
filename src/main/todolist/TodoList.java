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
    protected Path savePath; //Уточняет путь сохранения данных
    protected RepresentationConverter<TodoList> saveConverter; //Уточняет формат сохранения данных

    public Path getSavePath() {
        return savePath;
    }

    public void setSavePath(Path savePath) {
        this.savePath = savePath;
    }

    public RepresentationConverter<TodoList> getSaveConverter() {
        return saveConverter;
    }

    public void setSaveConverter(RepresentationConverter<TodoList> saveConverter) {
        this.saveConverter = saveConverter;
    }

    public TodoList() {
        this.savePath = null;
        this.saveConverter = null;
    }

    public TodoList(Path savePath, RepresentationConverter<TodoList> saveConverter) throws Exception {
        this.savePath = savePath;
        this.saveConverter = saveConverter;
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
        saveConverter.fromRepresentation(Files.readString(savePath), this);
    }

    public void saveChanges() throws Exception {
        var sb = new StringBuilder();
        saveConverter.toRepresentation(this, sb);
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
