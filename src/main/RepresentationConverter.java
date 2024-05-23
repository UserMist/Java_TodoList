package main;

public interface RepresentationConverter<T> {

    public void toRepresentation(T in, StringBuilder out) throws Exception;
    public void fromRepresentation(String in, T out) throws Exception;
}

