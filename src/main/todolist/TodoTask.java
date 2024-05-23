package main.todolist;

import java.util.Date;

/**
 * Задача, хранимая в TodoList.<br/><br/>
 * Ограничения:<ul>
 * <li>title может занимать максимально 50 символов.</li>
 * <li>priority находится в диапазоне от 0 до 10 включительно.</li>
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCompletionDate() throws IllegalAccessException {
        if(status != Status.Done)
            throw new IllegalAccessException("Задача не помечена как завершенная и поэтому у неё нет даты");
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public enum Status
    {
        Invalid,
        New,
        InProgress,
        Done,
    }

    private String title;
    private String description;
    private int priority;
    private Date deadline;
    private Status status;
    private Date completionDate;
}

