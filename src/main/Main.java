package main;

import main.todolist.TodoList;
import main.todolist.console.Controller;
import main.todolist.serialization.XmlConverter;

import java.io.*;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var consoleReader = new BufferedReader(new InputStreamReader(System.in));
        TodoList dataBase;
        try {
            dataBase = new TodoList(Paths.get("runtime_resources","TodoList.xml"), new XmlConverter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var controller = new Controller(dataBase);
        while(!controller.needsDispose) {
            String cmdLine = "";
            try { cmdLine = consoleReader.readLine(); }
            catch(IOException e) { System.out.println(e.getMessage()); }

            var feedBack = new StringBuilder();
            controller.execute(cmdLine, feedBack);
            System.out.print(feedBack);
        }
    }
}