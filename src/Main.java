import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        var consoleReader = new BufferedReader(new InputStreamReader(System.in));
        var dataBase = new ToDoList();
        var testItem = new ToDoTask();
        testItem.id = 5;
        testItem.status = ToDoTask.Status.Done;
        testItem.completionDate = new Date();
        dataBase.Items.add(testItem);

        var controller = new ConsoleController();
        while(true)
        {
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