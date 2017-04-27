package sample;

import java.util.ArrayList;

/**
 * Created by bendickson on 4/27/17.
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
        // initial set up code below - check if this needs updating
        boolean r = false;

        return r;
    }
    public double requirementProgress()
    {
        // initial set up code below - check if this needs updating
        double r = 0.0;

        return r;
    }
}
