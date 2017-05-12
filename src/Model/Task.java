package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Task extends ModelEntity
{
    // private data
    private ArrayList<Task> dependencies;
    private Deadline deadline;
    private ArrayList<Requirement> requirements;
    private ArrayList<Note> notes;
    private boolean checkedComplete;
    private int weighting;
    private TaskType type;


    // public methods

    // getters:

    public String getDeadline()
    {
        return this.deadline.toString();
    }

    public boolean isCheckedComplete()
    {
        return this.checkedComplete;
    }

    public boolean dependenciesComplete()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public boolean hasDependencies()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public boolean isComplete()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public boolean canCheckComplete()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
