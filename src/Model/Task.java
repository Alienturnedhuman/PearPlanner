package Model;

import java.util.ArrayList;

/**
 * Created by bendickson on 4/27/17.
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
        boolean r = false;

        return r;
    }
    boolean hasDependencies()
    {
        boolean r = false;

        return r;
    }
    boolean isComplete()
    {
        boolean r = false;

        return r;
    }
    boolean canCheckComplete()
    {
        boolean r = false;

        return r;
    }


}
