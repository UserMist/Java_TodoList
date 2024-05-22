import java.util.*;

public class ConsoleController {
    private String dialogueType = "";
    private List<Object> dialogueItems = new ArrayList<>();
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

    private void executeHelp(ArrayList<String> words, StringBuilder out, ToDoList dataBase) {
        if(words.size() == 1) {
            cmdDescriptions.forEach((k,v) -> out.append(k).append(v).append('\n'));
            return;
        }

        if(cmdDescriptions.containsKey(words.get(1))) {
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
                case "edit":
                    out.append("Примечание:  Поля, чьи значения не были указаны, останутся без изменений\n");
                    break;
                default:
            }
        }
        else {
            out.append("Помощь по команде ").append(words.get(1)).append(" не найдена\n");
        }
    }

    private void executeList(ArrayList<String> words, StringBuilder out, ToDoList dataBase) {
        ArrayList<HashMap.Entry<Integer,ToDoTask>> taskView = null;
        if(words.size() == 1) {
            taskView = dataBase.getTaskView();
        }
        else if(words.size() == 3 && words.get(1).equals("-s")) {
            var status = ToDoTask.Status.Invalid;
            switch(words.get(2)) {
                case "new": status = ToDoTask.Status.New; break;
                case "in_progress": status = ToDoTask.Status.InProgress; break;
                case "done": status = ToDoTask.Status.Done; break;
                default:
            }
            taskView = dataBase.getTaskView(status);
        }
        else {
            out.append("Команда указана неверно\n");
            return;
        }

        taskView.sort(Comparator.comparingInt(a -> -a.getValue().priority));
        taskView.forEach(pair -> {
            var id = pair.getKey();
            var task = pair.getValue();

            var statusWord = "приоритет "+task.priority+", ";
            switch(task.status) {
                case New: statusWord += "новая"; break;
                case InProgress: statusWord += "текущая"; break;
                case Done: statusWord += "завершена " + task.completionDate; break;
                default: statusWord += "invalid"; break;
            }
            out.append('[').append(id).append("] ").append(task.title).append(" (").append(statusWord).append(')');
            out.append('\n');
        });

        if(taskView.isEmpty())
            out.append("Задачи не найдены\n");
    }

    private void executeNew(ArrayList<String> words, StringBuilder out, ToDoList dataBase) {
        try {
            if(words.size() == 1) {
                out.append("Необходимо указать идентификатор\n");
                return;
            }

            var id = Integer.parseInt(words.get(1));
            if(dataBase.tasks.containsKey(id)) {
                out.append("Задача #").append(id).append(" уже существует\n");
                return;
            }

            dialogueItems.add(id);
            dialogueType = "new";
            out.append("Заголовок: ");
        }
        catch(NumberFormatException e) {
            out.append("Команда указана неверно\n");
        }
    }
    private void dialogueNew(String line, StringBuilder out, ToDoList dataBase)
    {
        switch(dialogueItems.size())
        {
            case 1:
                if(line.isEmpty()) {
                    dialogueItems.add(null);
                }
                else {
                    dialogueItems.add(line);
                }

                out.append("Описание: wip");
                break;

            default:
                var id = (int) dialogueItems.get(0);
                dataBase.createTask(id, (String) dialogueItems.get(1), "", 5, null);
                out.append("Задача #").append(id).append(" была успешно создана\n");

                dialogueItems.clear();
                dialogueType = "";
        }
    }

    private void executeRemove(ArrayList<String> words, StringBuilder out, ToDoList dataBase) {
        try {
            if(words.size() == 1) {
                out.append("Необходимо указать идентификатор\n");
                return;
            }

            var id = Integer.parseInt(words.get(1));
            out.append("Задача #").append(id);
            if(dataBase.removeTask(id))
                out.append(" успешно удалена\n");
            else
                out.append(" не была найдена\n");
        }
        catch(NumberFormatException e) {
            out.append("Команда указана неверно\n");
        }
    }

    public String execute(String line, ToDoList dataBase) {
        var out = new StringBuilder();
        switch(dialogueType) {
            case "new":
                dialogueNew(line, out, dataBase);
                break;

            default:
                var words = parseCmdWords(line.trim());
                if(words.isEmpty())
                    return "";

                switch(words.get(0)) {
                    case "help": executeHelp(words, out, dataBase); break;
                    case "list": executeList(words, out, dataBase); break;
                    case "new": executeNew(words, out, dataBase); break;
                    case "remove": executeRemove(words, out, dataBase); break;
                    default: out.append("Неизвестная команда\n");
                }
        }
        return out.toString();
    }
}
