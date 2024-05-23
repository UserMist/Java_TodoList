package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;
import main.todolist.TodoTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class CommandComplete implements ControllerCommand {

    @Override
    public String getName() {
        return "complete";
    }

    @Override
    public String getShortDescription() {
        return "Завершить задачу";
    }

    @Override
    public void printHelp(StringBuilder out) {
        ConsoleCommandInfo.printStandardHelp(this, " <идентиф.>", getShortDescription(), out);
    }

    @Override
    public ConsoleController run(Controller controller, ArrayList<String> words, StringBuilder out) {
        try {
            if(words.size() == 1) {
                out.append("Необходимо сперва указать идентификатор\n");
                return null;
            }

            var id = Integer.parseInt(words.get(1));
            out.append("Задача #").append(id);
            if(controller.dataBase.tasks.containsKey(id)) {
                var task = controller.dataBase.tasks.get(id);
                if(task.getStatus() == TodoTask.Status.Done) {
                    out.append(" уже помечена как выполненная\n");
                }
                else {
                    task.setStatus(TodoTask.Status.Done);
                    var calendar = Calendar.getInstance();
                    calendar.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                    task.setCompletionDate(calendar.getTime());
                    out.append(" была помечена как выполненная\n");

                    try { controller.dataBase.saveChanges(); }
                    catch (Exception e) { out.append("но изменения не могут быть сохранены на диск:\n").append(e).append('\n'); }
                }
            }
            else
                out.append(" не была найдена\n");
            return null;
        }
        catch(NumberFormatException e) {
            out.append("Команда указана неверно\n");
            return null;
        }
    }
}
