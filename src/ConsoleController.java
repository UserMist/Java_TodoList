public abstract class ConsoleController {
    public boolean finished = false;
    public abstract void execute(String line, StringBuilder out);
}
