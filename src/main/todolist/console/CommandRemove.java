package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;

import java.util.ArrayList;

public class CommandRemove implements ControllerCommand {
    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getShortDescription() {
        return "Удалить задачу";
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
            if(controller.dataBase.removeTask(id)) {
                out.append(" успешно удалена\n");

                try { controller.dataBase.saveChanges(); }
                catch (Exception e) { out.append("но изменения не могут быть сохранены на диск:\n").append(e).append('\n'); }
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
