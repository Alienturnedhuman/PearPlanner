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
        int i = -1;
        int ii = dependencies.size();
        while(++i<ii)
        {
            if(!dependencies.get(i).isComplete())
            {
                return false;
            }
        }
        return true;
    }
    public boolean hasDependencies()
    {
        return dependencies.size()>0;
    }
    public boolean isComplete()
    {
        return checkedComplete&&canCheckComplete();
    }
    public boolean canCheckComplete()
    {
        int i = -1;
        int ii = requirements.size();
        while(++i<ii)
        {
            if(!requirements.get(i).isComplete())
            {
                return false;
            }
        }
        return true;
    }

    public Task(String name)
    {
        super(name);
    }
}
