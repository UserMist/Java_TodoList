package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;

import java.util.ArrayList;

public class CommandNew implements ControllerCommand {
    @Override
    public String getName() {
        return "new";
    }

    @Override
    public String getShortDescription() {
        return "Создать задачу";
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

            var taskId = Integer.parseInt(words.get(1));
            if(controller.dataBase.tasks.containsKey(taskId)) {
                out.append("Задача #").append(taskId).append(" уже существует\n");
                return null;
            }

            return new DialogueTaskNew(out, controller.dataBase, taskId);
        }
        catch(NumberFormatException e) {
            out.append("Команда указана неверно\n");
            return null;
        }
    }
}
