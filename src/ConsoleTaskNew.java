public class ConsoleTaskNew extends ConsoleDialogue {
    public TodoList dataBase;
    public int taskId;

    public ConsoleTaskNew(StringBuilder out, TodoList dataBase, int taskId) {
        super(out);
        this.dataBase = dataBase;
        this.taskId = taskId;
    }

    @Override
    public int getQuestionAmount() {
        return 4;
    }

    @Override
    public void printQuestion(int id, StringBuilder out) {
        switch(id) {
            case 0: out.append("Заголовок: "); break;
            case 1: out.append("Описание: "); break;
            case 2: out.append("Важность: "); break;
            case 3: out.append("Срок: "); break;
        }
    }

    @Override
    public void submitAnswer(int id, String line, StringBuilder out) throws Exception {
        switch(id) {
            case 0:
                if(line.length() > 50) {
                    answers.put(id, line.substring(0, 50));
                    out.append("Заголовок был урезан до 50 символов\n");
                }
                else {
                    answers.put(id, line);
                }
                break;
            case 1:
                answers.put(id, line);
                break;
            case 2:
                answers.put(id, Integer.parseInt(line));
                break;
            case 3:
                answers.put(id, null);
                break;
                //throw new ExecutionControl.NotImplementedException("Не реализовано!!!!");
        }
    }

    @Override
    public void finish(StringBuilder out) {
        dataBase.createTask(taskId, (String)answers.get(0), (String)answers.get(1), (int)answers.get(2), null);
        out.append("Задача #").append(taskId).append(" была успешно создана\n");
    }
}