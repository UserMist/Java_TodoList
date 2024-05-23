package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;
import main.todolist.TodoTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class CommandList implements ControllerCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getShortDescription() {
        return "Перечислить задачи";
    }

    @Override
    public void printHelp(StringBuilder out) {
        ConsoleCommandInfo.printStandardHelp(this, "", getShortDescription(), out);
        out.append(" -s new           Только новые\n");
        out.append(" -s in_progress   Только текущие\n");
        out.append(" -s done          Только завершенные\n");
    }

    @Override
    public ConsoleController run(Controller controller, ArrayList<String> words, StringBuilder out) {
        ArrayList<HashMap.Entry<Integer, TodoTask>> taskView;
        if(words.size() == 1) {
            taskView = controller.dataBase.getTaskView();
        }
        else if(words.size() == 3 && words.get(1).equals("-s")) {
            var status = TodoTask.Status.Invalid;
            switch(words.get(2)) {
                case "new": status = TodoTask.Status.New; break;
                case "in_progress": status = TodoTask.Status.InProgress; break;
                case "done": status = TodoTask.Status.Done; break;
                default: break;
            }
            taskView = controller.dataBase.getTaskView(status);
        }
        else {
            out.append("Команда указана неверно\n");
            return null;
        }

        taskView.sort(Comparator.comparingInt(a -> -a.getValue().getPriority()));
        taskView.forEach(pair -> {
            var id = pair.getKey();
            var task = pair.getValue();

            var statusWord = "важность "+task.getPriority()+", ";
            switch(task.getStatus()) {
                case New: statusWord += "новая"; break;
                case InProgress: statusWord += "текущая"; break;
                case Done:
                    try { statusWord += "завершена " + Controller.defaultDateParser.format(task.getCompletionDate()); }
                    catch (Exception e) { throw new RuntimeException(e); }
                    break;
                default: statusWord += "invalid"; break;
            }
            out.append('[').append(id).append("] ").append(task.getTitle()).append(" (").append(statusWord).append(")\n");
            out.append("Описание: ").append(task.getDescription()).append('\n');
            out.append("Крайний срок: ").append(Controller.defaultDateParser.format(task.getDeadline())).append('\n');
        });

        if(taskView.isEmpty())
            out.append("Задачи не найдены\n");
        return null;
    }
}
