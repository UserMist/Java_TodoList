package main.todolist.console;

import main.ConsoleCommandInfo;
import main.ConsoleController;

import java.util.ArrayList;

public interface ControllerCommand extends ConsoleCommandInfo {
    ConsoleController run(Controller controller, ArrayList<String> words, StringBuilder out);
}
