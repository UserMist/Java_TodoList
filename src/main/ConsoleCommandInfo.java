package main;

import main.todolist.console.ControllerCommand;

public interface ConsoleCommandInfo {
    String getName();
    String getShortDescription();
    void printHelp(StringBuilder out);
    static void printStandardHelp(ControllerCommand cmd, String postfix, String shortDescription, StringBuilder out) {
        out.append(cmd.getName()).append(postfix).append("  --  ").append(shortDescription).append('\n');
    }
}
