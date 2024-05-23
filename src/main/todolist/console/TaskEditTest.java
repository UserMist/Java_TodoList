package main.todolist.console;

import main.todolist.TodoList;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskEditTest {
    @Test
    void getQuestionAmount() throws Exception {
        var db = new TodoList();
        db.createTask(0, "test", "test", 0, new Date());
        var subj = new TaskEdit(new StringBuilder(), db, 0);
        var out = new StringBuilder();

        subj.execute("-", out);
        for(var i = 1; i < subj.getQuestionAmount(); i++) {
            assertFalse(subj.needsDispose);
            subj.execute("-", out);
        }
        assertTrue(subj.needsDispose);
    }
}