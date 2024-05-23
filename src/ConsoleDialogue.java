import java.util.HashMap;

public abstract class ConsoleDialogue extends ConsoleController {
    public final HashMap<Integer, Object> answers = new HashMap<>(getQuestionAmount());
    private int currentId = 0;

    public ConsoleDialogue(StringBuilder out) {
        printQuestion(0, out);
    }

    public abstract int getQuestionAmount(); // > 0

    public abstract void printQuestion(int id, StringBuilder out);

    public abstract void submitAnswer(int id, String line, StringBuilder out) throws Exception;

    public abstract void finish(StringBuilder out);

    @Override
    public void execute(String line, StringBuilder out) {
        try
        {
            submitAnswer(currentId, line, out);
            currentId++;
        }
        catch(Exception e) {
            out.append("Ошибка типа ").append(e).append('\n');
        }

        if(currentId < getQuestionAmount()) {
            printQuestion(currentId, out);
        }
        else {
            finish(out);
            finished = true;
        }
    }
}
