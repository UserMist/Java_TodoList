package main.todolist.console;

import main.todolist.TodoList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskEditTest {
    @Test
    void getQuestionAmount() throws Exception {
        var v = new TaskEdit(new StringBuilder(), null, -1);
        assertNotEquals(v.getQuestionAmount(), 0);
    }
}