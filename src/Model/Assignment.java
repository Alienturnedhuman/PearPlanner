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
public abstract class Assignment extends VersionControlEntity
{

    protected ArrayList<Task> tasks = new ArrayList<>();
    protected ArrayList<Requirement> requirements = new ArrayList<>();
    protected int weighting;
    protected Person setBy;
    protected Person markedBy;
    protected Person reviewedBy;
    protected int marks;
    protected StateType state;  // this may not be needed as we can work it out

    // private methods
    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if(receivedVCE instanceof Assignment)
        {
            Assignment castedVCE = (Assignment)receivedVCE;
            // this.tasks = castedVCE.getTasks();
            // this.requirements = castedVCE.getRequirements();
            this.weighting = castedVCE.getWeighting();
            this.setBy = castedVCE.getSetBy();
            this.markedBy = castedVCE.getMarkedBy();
            this.reviewedBy = castedVCE.getReviewedBy();
            this.marks = castedVCE.getMarks();
            // this.state = castedVCE.getState();
        }
        super.replace(receivedVCE);
    }


    // public enums
    public enum StateType {IN_PROGRESS,DEADLINE_PASSED,NOT_STARTED}

    // public methods
    @Override
    public String toString()
    {
        return "Assignment '"+name+"'";
    }
    public String toString(boolean verbose)
    {
        if(verbose)
        {
            StringBuilder r = new StringBuilder();
            r.append(toString());
            r.append("\n");
            r.append("Total marks: "+Integer.toString(marks));
            r.append("\n");
            r.append("Total weighting: "+Integer.toString(weighting));

            r.append("\n");
            r.append("Set By: "+setBy.toString());
            r.append("\n");
            r.append("Marked By: "+markedBy.toString());
            r.append("\n");
            r.append("Reviewed By: "+reviewedBy.toString());

            return r.toString();
        }
        else
        {
            return toString();
        }
    }
    // getters
    public ArrayList<Task> getTasks()
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

    @Override
    public void open(MenuController.Window current)
    {
        try
        {
            MainController.ui.assignmentDetails(this, current);
        } catch (IOException e)
        {
            UIManager.reportError("Unable to open View file");
        }
    }

    // Constructor
    public Assignment(int cWeighting, Person cSetBy, Person cMarkedBy, Person cReviewedBy, int cMarks)
    {
        weighting = cWeighting;
        setBy = cSetBy;
        markedBy = cMarkedBy;
        reviewedBy = cReviewedBy;
        marks = cMarks;
    }
}
