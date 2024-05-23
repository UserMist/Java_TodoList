package main;

import java.util.HashMap;

/**
 * Консольный контроллер, используемый для введения набора данных в виде отдельных полей
 * Ограничение: getQuestionAmount() > 0
 * @see ConsoleController
 */
public abstract class ConsoleDialogue extends ConsoleController {
    protected final HashMap<Integer, Object> answers = new HashMap<>(getQuestionAmount());
    private int currentId = 0;

    public ConsoleDialogue(StringBuilder out) {
        printQuestion(0, out);
    }

    public abstract int getQuestionAmount();

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
            dispose = true;
        }
    }
}
