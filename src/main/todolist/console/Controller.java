package main.todolist.console;
import main.todolist.TodoList;
import main.ConsoleController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Консольный контроллер для работы с TodoList путём введения текстовых команд.<br/>
 * Некоторые команды могут временно перенаправлять пользователя на другие контроллеры.
 * @see main.ConsoleController
 * @see TodoList
 */
public class Controller extends main.ConsoleController {
    public static SimpleDateFormat defaultDateParser = new SimpleDateFormat("yyyy-MM-dd");
    public ConsoleController redirection = null; //Перенаправление на диалог ввода данных
    public TodoList dataBase;

    public Controller(TodoList dataBase) {
        this.dataBase = dataBase;
    }

    private ArrayList<String> parseCmdWords(String cmdLine) {
        var lineLen = cmdLine.length();
        var words = new ArrayList<String>();
        var wordOpener = '?';
        var wordStart = -1;

        for(int i = 0; i < lineLen; i++) {
            var ch = cmdLine.charAt(i);
            if(wordOpener != '?') {
                if(ch == wordOpener) {
                    words.add(cmdLine.substring(wordStart, i));
                    wordOpener = '?';
                }

                if(wordOpener != ' ' && ch == '\\' && i+1 < lineLen)
                    i++;
            }
            else if(ch == '"' || ch == '\'') {
                wordOpener = ch;
                wordStart = i+1;
            }
            else if(ch != ' ') {
                wordOpener = ' ';
                wordStart = i;
            }
        }

        if(wordOpener != '?')
            words.add(cmdLine.substring(wordStart));

        return words;
    }

    private static final ArrayList<ControllerCommand> commands = new ArrayList<>() {{
        add(new CommandComplete());
        add(new CommandEdit());
        add(new CommandExit());
        add(new CommandHelp());
        add(new CommandList());
        add(new CommandNew());
        add(new CommandRemove());
    }};

    public static ArrayList<ControllerCommand> getCommands() {
        return commands;
    }

    public static ControllerCommand getCommand(String cmdName) {
        ControllerCommand cmd = null;
        for(var c : commands) {
            if(c.getName().equals(cmdName)) {
                cmd = c;
                break;
            }
        }
        return cmd;
    }

    @Override
    public void execute(String line, StringBuilder out) {
        if(redirection != null) {
            redirection.execute(line, out);
            if(redirection.needsDispose)
                redirection = null;
            return;
        }

        var words = parseCmdWords(line.trim());
        if(words.isEmpty())
            return;

        var cmd = getCommand(words.get(0));
        if(cmd == null)
            out.append("Неизвестная команда\n");
        else
            redirection = cmd.run(this, words, out);
    }
}
