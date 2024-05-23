import java.io.*;
import java.util.Calendar;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) {
        var consoleReader = new BufferedReader(new InputStreamReader(System.in));
        var dataBase = new TodoList();
        var calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"));
        calendar.set(Calendar.HOUR_OF_DAY, 5);

        var testItem = new TodoTask("Задание 1", "Описание 1", 5, calendar.getTime());
        dataBase.tasks.put(5, testItem);

        var testItemB = new TodoTask("Задание 2", "Описание 2", 2, calendar.getTime());
        dataBase.tasks.put(204, testItemB);

        var controller = new TodoListConsoleController(dataBase);
        while(true) {
            String cmdLine = "";
            try {
                cmdLine = consoleReader.readLine();
            }
            catch(IOException e) {
                System.out.println(e.getMessage());
            }

            if(cmdLine.equals("exit"))
                return;

            var feedBack = new StringBuilder();
            controller.execute(cmdLine, feedBack);
            System.out.print(feedBack);
        }
    }
}