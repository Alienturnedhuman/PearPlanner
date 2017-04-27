package Model;

import java.util.ArrayList;

/**
 * Created by bendickson on 4/27/17.
 */
public class Milestone
{
    // private data
    private ArrayList<Task> tasks;
    private Deadline deadline;
    private ArrayList<Note> notes;

    // public methods
    public boolean isComplete()
    {
        // initial set up code below - check if this needs updating
        boolean r = false;

        return r;
    }
    public double progressPercentage()
    {
        // initial set up code below - check if this needs updating
        double r = 0.0;

        return r;
    }
    public int tasksCompleted()
    {
        // initial set up code below - check if this needs updating
        int r = 0;

        return r;
    }
    public int size()
    {
        // initial set up code below - check if this needs updating
        int r = 0;

        return r;
    }
    public int totalWeighting()
    {
        // initial set up code below - check if this needs updating
        int r = 0;

        return r;
    }

    // getters
    public String getName()
    {
        // initial set up code below - check if this needs updating
        return deadline.getName();
    };

    // setters
    public void setName(String newName)
    {
        // initial set up code below - check if this needs updating
        deadline.setName(newName);
    }


}
