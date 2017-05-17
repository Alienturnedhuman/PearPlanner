package Model;

import Controller.MainController;
import Controller.MenuController;
import View.UIManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;
/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Activity extends Event
{
    private ArrayList<Task> tasks = new ArrayList<>();
    private int duration;
    private int activityQuantity;
    private QuantityType type;

    // Getters:

    /**
     * Returns an array of Tasks this Activity relates to.
     *
     * @return array of Tasks.
     */
    public Task[] getTasks()
    {
        return this.tasks.toArray(new Task[this.tasks.size()]);
    }

    /**
     * Returns the Duration of this Activity.
     *
     * @return integer representation of the Duration.
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * Returns the Quantity of this Activity.
     *
     * @return integer representation of the Activity Quantity.
     */
    public int getActivityQuantity()
    {
        return activityQuantity;
    }

    /**
     * Returns the Quantity Type of this Activity.
     *
     * @return QuantityType object.
     */
    public QuantityType getType()
    {
        return type;
    }

    /**
     * Returns a formatted date of this Activity
     *
     * @return string representation of a Date.
     */
    public String getDateString()
    {
        return this.date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + " " +
                this.date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " +
                this.date.get(Calendar.DAY_OF_MONTH);
    }

    // Setters:

    /**
     * Add a single Task to this Activity.
     *
     * @param task Task to be added.
     * @return whether the provided Task was added successfully.
     */
    public boolean addTask(Task task)
    {
        if (this.tasks.contains(task))
            return false;

        this.tasks.add(task);
        return true;
    }

    /**
     * Add all given Tasks to this Activity.
     *
     * @param tasks a Collection of Tasks to be added.
     * @return whether the provided Tasks were added successfully.
     */
    public boolean addTasks(Collection<Task> tasks)
    {
        if (this.tasks.contains(tasks))
            return false;

        this.tasks.addAll(tasks);
        return true;
    }

    /**
     * Replace the current list of Tasks with the provided Tasks.
     *
     * @param tasks Collection of Tasks.
     */
    public void replaceTasks(Collection<Task> tasks)
    {
        this.tasks.clear();
        this.tasks.addAll(tasks);
    }

    /**
     * Remove a given Task from this Activity.
     *
     * @param task Task to be removed.
     */
    public void removeTask(Task task)
    {
        this.tasks.remove(task);
    }

    /**
     * Set the Duration of this Activity.
     *
     * @param duration integer value of the Duration.
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    /**
     * Set the Quantity of this Activity.
     *
     * @param activityQuantity integer value of the Quantity.
     */
    public void setActivityQuantity(int activityQuantity)
    {
        this.activityQuantity = activityQuantity;
    }

    /**
     * Set the QuantityType of this Activity.
     *
     * @param type String representation of the QuantityType.
     */
    public void setType(String type)
    {
        if (QuantityType.exists(type))
            this.type = QuantityType.get(type);
    }

    @Override
    public void open(MenuController.Window current)
    {
        try
        {
            MainController.ui.activityDetails(this);
        } catch (IOException e)
        {
            UIManager.reportError("Unable to open View file");
        }
    }

    // Constructors:
    public Activity(String name, String details, LocalDate date, int duration, int activityQuantity, String type)
    {
        super(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "T00:00:01Z");
        this.setName(name);
        this.setDetails(details);
        this.duration = duration;
        this.activityQuantity = activityQuantity;
        this.type = QuantityType.get(type);
    }

    /**
     * Creates a copy of the given Activity.
     *
     * @param activity Activity object to be copied.
     */
    public Activity(Activity activity)
    {
        super();
        this.name = activity.name;
        this.details = activity.details;
        this.date = activity.date;
        this.duration = activity.duration;
        this.activityQuantity = activity.activityQuantity;
        this.type = activity.type;
        this.tasks = activity.tasks;
    }
}