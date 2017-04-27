package Model;


import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Assignment extends VersionControlEntity
{

    protected ArrayList<Assignment> tasks;
    protected ArrayList<Requirement> requirements;
    protected int weighting;
    protected Person setBy;
    protected Person markedBy;
    protected Person reviewedBy;
    protected int marks;
    protected StateType state;


    // public enums
    public enum StateType {IN_PROGRESS,DEADLINE_PASSED,NOT_STARTED}
}
