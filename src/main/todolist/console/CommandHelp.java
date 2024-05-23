package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandHelp implements ControllerCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getShortDescription() {
        return "Перечислить консольные команды";
    }

    @Override
    public void printHelp(StringBuilder out) {
        ConsoleCommandInfo.printStandardHelp(this, "", getShortDescription(), out);
        ConsoleCommandInfo.printStandardHelp(this, " <команда>", "Показать подробности команды", out);
    }

    @Override
    public ConsoleController run(Controller controller, ArrayList<String> words, StringBuilder out) {
        var commands = Controller.getCommands();
        if(words.size() == 1) {
            var maxCmdLength = new AtomicInteger();
            commands.forEach(cmd -> maxCmdLength.set(Math.max(maxCmdLength.get(), cmd.getName().length())));

            var offset = maxCmdLength.addAndGet(2);
            commands.forEach(cmd -> {
                var cmdName = cmd.getName();
                out.append(cmdName).append(" ".repeat(offset-cmdName.length()));
                out.append(cmd.getShortDescription()).append('\n');
            });
            return null;
        }

        var cmdName = words.get(1);
        var cmd = Controller.getCommand(cmdName);
        if(cmd == null) {
            out.append("Помощь по команде ").append(cmdName).append(" не найдена\n");
            return null;
        }

        cmd.printHelp(out);
        return null;
    }
}
