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
    boolean dependenciesComplete()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    boolean hasDependencies()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    boolean isComplete()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    boolean canCheckComplete()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }


}
