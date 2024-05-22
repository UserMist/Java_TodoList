import java.util.Date;

//get set
public class ToDoTask {
    public enum Status
    {
        Invalid,
        New,
        InProgress,
        Done,
    }

    public int id;


    public String description; //length <= 50
    public int priority;  //importance ascends on [0;10]
    public Date deadline;
    public Status status;
    public Date completionDate;
}

