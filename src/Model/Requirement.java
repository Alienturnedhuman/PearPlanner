package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Requirement extends ModelEntity
{
    protected boolean checkedCompleted;
    protected double estimatedTimeInHours;
    protected ArrayList<ActivityEvent> activityLog;
    protected int initialQuantity;
    protected int remainingQuantity;
    protected QuantityType quantityType;

    // public methods
    public boolean isComplete()
    {
        boolean r = false;

        return r;
    }
    public double requirementProgress()
    {
        double r = 0.0;

        return r;
    }
}
