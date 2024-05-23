package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;

import java.util.ArrayList;

public class CommandEdit implements ControllerCommand {
    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getShortDescription() {
        return "Редактировать задачу";
    }

    @Override
    public void printHelp(StringBuilder out) {
        ConsoleCommandInfo.printStandardHelp(this, " <идентиф.>", getShortDescription(), out);
        out.append("Примечание:  Прочерк (-) оставляет поле без изменений\n");
    }

    @Override
    public ConsoleController run(Controller controller, ArrayList<String> words, StringBuilder out) {
        try {
            if(words.size() == 1) {
                out.append("Необходимо сперва указать идентификатор\n");
                return null;
            }

            var taskId = Integer.parseInt(words.get(1));
            if(!controller.dataBase.tasks.containsKey(taskId)) {
                out.append("Задача #").append(taskId).append(" не существует\n");
                return null;
            }

            return new DialogueEdit(out, controller.dataBase, taskId);
        }
        catch(NumberFormatException e) {
            out.append("Команда указана неверно\n");
            return null;
        }
    }
}
