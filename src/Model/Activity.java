package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Activity extends Event
{
    private ArrayList<Task> tasks;
    private int duration;
    private int activityQuantity;
    private QuantityType type;

    // public

    // Getters:

    /**
     * Returns an array of Tasks this Activity relates to.
     *
     * @return array of Tasks
     */
    public Task[] getTasks()
    {
        return this.tasks.toArray(new Task[this.tasks.size()]);
    }

    public void markComplete()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    public Activity(LocalDate date)
    {
        super(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "T00:00:01Z");
    }

}
