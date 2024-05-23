import java.text.SimpleDateFormat;

public class TodoListXmlConverter implements RepresentationConverter<TodoList> {
    public static SimpleDateFormat defaultDateParser = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void toRepresentation(TodoList in, StringBuilder out) {
    }

    @Override
    public void fromRepresentation(String in, TodoList out) {
    }
}
