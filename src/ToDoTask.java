import java.util.Date;

//get set
public class ToDoTask {
    public ToDoTask(String title, String description, int priority, Date deadline) {
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

