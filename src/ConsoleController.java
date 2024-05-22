import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ConsoleController {
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

    private static final HashMap<String, String> cmdDescriptions = new HashMap<>() {{
        put("help", "              Перечислить консольные команды");
        put("list", "              Перечислить задачи");
        put("new", "               Создать задачу");
        put("remove", "            Удалить задачу");
        put("complete", "          Завершить задачу");
        put("edit", "              Редактировать задачу");
    }};

    public String execute(String commandLine, ToDoList dataBase) {
        var words = parseCmdWords(commandLine.trim());
        var amount = words.size();
        var out = new StringBuilder();


        if(amount == 0) {
            return out.toString();
        }

        if(words.get(0).equals("help")) {
            if(amount == 1) {
                cmdDescriptions.forEach((k,v) -> out.append(k).append(v).append('\n'));
            }
            else if(cmdDescriptions.containsKey(words.get(1))) {
                var arg = words.get(1);
                out.append(arg).append(cmdDescriptions.get(arg)).append('\n');
                switch(words.get(1))
                {
                    case "list":
                        out.append(" -s new           Только новые\n");
                        out.append(" -s in_progress   Только текущие\n");
                        out.append(" -s done          Только завершенные\n");
                        break;
                    case "help":
                        out.append("help [cmd]        Помощь по команде\n");
                        break;
                    default:
                }
            }
            else {
                out.append("Помощь по команде ").append(words.get(1)).append(" не найдена\n");
            }

            return out.toString();
        }


        if(words.get(0).equals("list")) {
            var taskView = new ArrayList<ToDoTask>();
            if(amount == 1) {
                taskView.addAll(dataBase.Items);
            }
            else if(amount == 3 && words.get(1).equals("-s")) {
                var status = ToDoTask.Status.Invalid;
                switch(words.get(2)) {
                    case "new": status = ToDoTask.Status.New; break;
                    case "in_progress": status = ToDoTask.Status.InProgress; break;
                    case "done": status = ToDoTask.Status.Done; break;
                    default:
                }
                ToDoTask.Status finalStatus = status;
                dataBase.Items.forEach(task -> {
                    if(task.status.equals(finalStatus))
                        taskView.add(task);
                });
            }

            taskView.sort(Comparator.comparingInt(a -> a.priority));
            if(taskView.isEmpty())
                out.append("Задачи не найдены\n");

            taskView.forEach(task -> {
                out.append("[").append(task.id).append("] ");
                if(task.status.equals(ToDoTask.Status.Done)) {
                    out.append(" Выполнена ").append(task.completionDate);
                }
                out.append('\n');
            });

            return out.toString();
        }

        out.append("Неизвестная команда\n");
        return out.toString();
    }
}
