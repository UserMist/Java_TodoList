package main.todolist.console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskNewTest {
    @Test
    void getQuestionAmount() {
        var v = new TaskNew(new StringBuilder(), null, -1);
        assertNotEquals(v.getQuestionAmount(), 0);
    }
}