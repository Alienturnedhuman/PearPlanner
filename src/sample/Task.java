package sample;

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
        // initial set up code below - check if this needs updating
        boolean r = false;

        return r;
    }
    boolean hasDependencies()
    {
        // initial set up code below - check if this needs updating
        boolean r = false;

        return r;
    }
    boolean isComplete()
    {
        // initial set up code below - check if this needs updating
        boolean r = false;

        return r;
    }
    boolean canCheckComplete()
    {
        // initial set up code below - check if this needs updating
        boolean r = false;

        return r;
    }


}
