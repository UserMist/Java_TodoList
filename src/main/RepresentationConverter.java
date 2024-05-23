package main;

/**
 * Интерфейс, используемый для обновления значения T в соответствие с хранимыми в строке данными и для переноса данных в строку.
 * @see main.todolist.serialization.XmlConverter
 */
public interface RepresentationConverter<T> {

    public void toRepresentation(T in, StringBuilder out) throws Exception;
    public void fromRepresentation(String in, T out) throws Exception;
}

