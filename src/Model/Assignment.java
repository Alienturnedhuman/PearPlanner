package Model;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public abstract class Assignment extends VersionControlEntity
{

    protected ArrayList<Assignment> tasks;
    protected ArrayList<Requirement> requirements;
    protected int weighting;
    protected Person setBy;
    protected Person markedBy;
    protected Person reviewedBy;
    protected int marks;
    protected StateType state;

    // private methods
    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if(receivedVCE instanceof Assignment)
        {
            Assignment castedVCE = (Assignment)receivedVCE;
            this.tasks = castedVCE.getTasks();
            this.requirements = castedVCE.getRequirements();
            this.weighting = castedVCE.getWeighting();
            this.setBy = castedVCE.getSetBy();
            this.markedBy = castedVCE.getMarkedBy();
            this.reviewedBy = castedVCE.getReviewedBy();
            this.marks = castedVCE.getMarks();
            this.state = castedVCE.getState();
        }
        super.replace(receivedVCE);
    }


    // public enums
    public static enum StateType {IN_PROGRESS,DEADLINE_PASSED,NOT_STARTED};

    // public methods

    // getters
    public ArrayList<Assignment> getTasks()
    {
        return tasks;
    }
    public ArrayList<Requirement> getRequirements()
    {
        return requirements;
    }
    public int getWeighting()
    {
        return weighting;
    }
    public Person getSetBy()
    {
        return setBy;
    }
    public Person getMarkedBy()
    {
        return markedBy;
    }
    public Person getReviewedBy()
    {
        return reviewedBy;
    }
    public int getMarks()
    {
        return marks;
    }
    public StateType getState()
    {
        return state;
    }
}
