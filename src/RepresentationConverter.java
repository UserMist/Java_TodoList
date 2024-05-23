public interface RepresentationConverter<T> {

    public void toRepresentation(TodoList in, StringBuilder out);
    public void fromRepresentation(String in, T out) throws Exception;
}

