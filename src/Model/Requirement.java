package Model;

import Controller.MainController;
import Controller.MenuController;
import View.UIManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Requirement extends ModelEntity
{
    protected boolean checkedCompleted;
    protected double estimatedTimeInHours;
    protected ArrayList<Activity> activityLog = new ArrayList<>();
    protected int initialQuantity;
    protected int remainingQuantity;
    protected QuantityType quantityType; // TODO custom Quantity and Task types

    // public methods

    // Getters:
    public boolean isComplete()
    {
        return this.checkedCompleted;
    }

    /**
     * Returns the QuantityType of this Requirement.
     *
     * @return
     */
    public QuantityType getQuantityType()
    {
        return this.quantityType;
    }

    /**
     * Returns the estimated time of this Requirement (in hours)
     *
     * @return
     */
    public double getEstimatedTimeInHours()
    {
        return estimatedTimeInHours;
    }

    /**
     * Returns the initial quantity of this Requirement
     *
     * @return
     */
    public int getInitialQuantity()
    {
        return initialQuantity;
    }

    /**
     * Returns the remaining quantity of this Requirement
     */
    public int getRemainingQuantity()
    {
        return remainingQuantity;
    }

    /**
     * Returns an array of ActivityEvents that are associated with this Requirement
     *
     * @return
     */
    public Activity[] getActivityLog()
    {
        return this.activityLog.toArray(new Activity[this.activityLog.size()]);
    }

    public double requirementProgress()
    {
        // TODO calculate progress
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    // Setters:
    public void setEstimatedTimeInHours(double estimatedTimeInHours)
    {
        this.estimatedTimeInHours = estimatedTimeInHours;
    }

    /**
     * Change the initial quantity. This will update the progress of this Requirement to reflect the change.
     *
     * @param initialQuantity
     */
    public void setInitialQuantity(int initialQuantity)
    {
        if (this.initialQuantity == this.remainingQuantity)
            this.initialQuantity = this.remainingQuantity = initialQuantity;
        else
        {
            this.initialQuantity = initialQuantity;
            this.update();
        }
    }

    public void setQuantityType(String quantityType)
    {
        this.quantityType = QuantityType.get(quantityType);
    }

    /**
     * Add an Activity to the current Requirement and update the progress of this Requirement accordingly.
     *
     * @param activity Activity to be added.
     */
    public void addActivity(Activity activity)
    {
        this.activityLog.add(activity);
        this.remainingQuantity -= activity.getActivityQuantity();
        if (remainingQuantity <= 0)
        {
            this.remainingQuantity = 0;
            this.checkedCompleted = true;
        }
    }

    /**
     * Update the current Requirement to reflect newly added activities
     * @return whether any changes were made
     */
    public boolean update()
    {
        // TODO update
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Returns the Name of the Requirement (used for JavaFX)
     *
     * @return Name of the task
     */
    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public void open(MenuController.Window current)
    {
        try
        {
            MainController.ui.requirementDetails(this);
        } catch (IOException e)
        {
            UIManager.reportError("Unable to open View file");
        }
    }

    // Constructors:
    public Requirement(String name, String details, double time, int quantity, String type)
    {
        super(name, details);
        this.estimatedTimeInHours = time;
        this.initialQuantity = this.remainingQuantity = quantity;
        this.quantityType = QuantityType.get(type);
    }
}
