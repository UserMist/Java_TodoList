public interface RepresentationConverter<T> {

    public StringBuilder ToRepresentation(T in);

    public T FromRepresentation(String in);
}

