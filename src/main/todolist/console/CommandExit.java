package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;

import java.util.ArrayList;

public class CommandExit implements ControllerCommand {
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getShortDescription() {
        return "Выйти из программы";
    }

    @Override
    public void printHelp(StringBuilder out) {
        ConsoleCommandInfo.printStandardHelp(this, "", getShortDescription(), out);
    }

    @Override
    public ConsoleController run(Controller controller, ArrayList<String> words, StringBuilder out) {
        controller.needsDispose = true;
        return null;
    }
}
