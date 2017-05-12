package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Milestone implements Serializable
{
    // private data
    private ArrayList<Task> tasks;
    private Deadline deadline;
    private ArrayList<Note> notes;

    // public methods
    public boolean isComplete()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public double progressPercentage()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public int tasksCompleted()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public int size()
    {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public int totalWeighting()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    // getters
    public String getName()
    {
        // initial set up code below - check if this needs updating
        return deadline.getName();
    }

    // setters
    public void setName(String newName)
    {
        // initial set up code below - check if this needs updating
        deadline.setName(newName);
    }


}
