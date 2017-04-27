package Model;

import java.sql.Time;
import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class ActivityEvent extends Event
{
    private ArrayList<Task> tasks;
    private boolean checkedComplete;
    private Time start;
    private int duration;
    private String description;
    private int activityQuantity;
    private QuantityType type;

    // public methods
    public void markComplete()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");

    }
    public boolean isComplete()
    {
        // initial set up code below - check if this needs updating


        throw new UnsupportedOperationException("This method is not implemented yet");
    }


}
