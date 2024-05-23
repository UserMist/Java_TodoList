package main.todolist.console;

import main.todolist.TodoList;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskNewTest {
    @Test
    void getQuestionAmount() throws Exception {
        var db = new TodoList();
        var subj = new TaskNew(new StringBuilder(), db, 0);
        var out = new StringBuilder();

        //question 0
        subj.execute("", out); //title input, question 1
        assertFalse(subj.needsDispose);
        subj.execute("", out); //description, question 2
        assertFalse(subj.needsDispose);
        subj.execute("4", out); //priority, question 3
        assertFalse(subj.needsDispose);
        subj.execute("2000-01-20", out); //deadline, question 4
        assertTrue(subj.needsDispose);
    }
}