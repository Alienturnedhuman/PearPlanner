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
public class Assignment extends VersionControlEntity
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
        if (receivedVCE instanceof Assignment)
        {
            Assignment castedVCE = (Assignment) receivedVCE;
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
    public enum StateType
    {
        IN_PROGRESS, DEADLINE_PASSED, NOT_STARTED
    }

    // public methods
    // getters
    @Override
    public String toString()
    {
        return "Assignment '" + name + "'";
    }

    public String toString(boolean verbose)
    {
        if (verbose)
        {
            StringBuilder r = new StringBuilder();
            r.append(toString());
            r.append("\n");
            r.append("Total marks: " + Integer.toString(marks));
            r.append("\n");
            r.append("Total weighting: " + Integer.toString(weighting));

            r.append("\n");
            r.append("Set By: " + setBy.toString());
            r.append("\n");
            r.append("Marked By: " + markedBy.toString());
            r.append("\n");
            r.append("Reviewed By: " + reviewedBy.toString());

            return r.toString();
        } else
        {
            return toString();
        }
    }

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

    // Setters:

    /**
     * Add a Task to this Assignment.
     *
     * @param task Task to be added
     */
    public void addTask(Task task)
    {
        this.tasks.add(task);
        task.addAssignmentReference(this);
    }

    /**
     * Removes the given Task from this assignment.
     *
     * @param task Task to be removed
     * @return true if found and deleted, false otherwise
     */
    public boolean removeTask(Task task)
    {
        task.removeAssignmentReference(this);
        return this.tasks.remove(task);
    }

    /**
     * Add a Requirement to this Assignment.
     *
     * @param requirement Task to be added
     */
    public void addRequirement(Requirement requirement)
    {
        this.requirements.add(requirement);
    }

    /**
     * Removes the given Requirement from this Assignment.
     *
     * @param requirement Requirement to be removed
     * @return true if found and deleted, false otherwise
     */
    public boolean removeRequirement(Requirement requirement)
    {
        return this.requirements.remove(requirement);
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
        MainController.getSPC().getPlanner().getDeadlineNotifications().put(this, new boolean[2]);
    }
}
