import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) {
        var consoleReader = new BufferedReader(new InputStreamReader(System.in));
        var dataBase = new ToDoList();
        var calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"));
        calendar.set(Calendar.HOUR_OF_DAY, 5);

        var testItem = new ToDoTask("Задание 1", "Описание 1", 5, calendar.getTime());
        dataBase.tasks.put(5, testItem);

        var testItemB = new ToDoTask("Задание 2", "Описание 2", 2, calendar.getTime());
        dataBase.tasks.put(204, testItemB);

        var controller = new ConsoleController();
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

            var feedback = controller.execute(cmdLine, dataBase);
            System.out.print(feedback);
        }
    }
}