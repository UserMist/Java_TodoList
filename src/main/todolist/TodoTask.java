package main.todolist;

import java.util.Date;

/**
 * Задача, хранимая в TodoList.<br/><br/>
 * Ограничения:<ul>
 * <li>title может занимать максимально 50 символов.</li>
 * <li>priority находится в диапазоне от 0 до 10 включительно.</li>
 * <li>при status != New, completionDate равно null.</li>
 * </ul>
 * @see TodoList
 */
public class TodoTask {
    public TodoTask(String title, String description, int priority, Date deadline) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = Status.New;
        this.completionDate = null;
    }

    public enum Status
    {
        Invalid,
        New,
        InProgress,
        Done,
    }

    public String title;
    public String description; //length <= 50
    public int priority;  //importance ascends on [0;10]
    public Date deadline;
    public Status status;
    public Date completionDate; //null when status != .Done
}

