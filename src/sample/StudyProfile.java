package sample;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.ArrayList;

/**
 * Created by bendickson on 4/27/17.
 */
public class StudyProfile
{
    // private data
    private ArrayList<Module> modules;
    private ArrayList<Milestone> milestones;
    private String name;
    private ArrayList<String> details;
    private int year;
    private int semesterNo;
    private int version;

    // public methods
    public int milestonesCompleted()
    {
        int r=0;


        return r;
    }
    public double milestonesProgress()
    {
        double r=0.0;
        
        return r;
    }

    // constructors
    StudyProfile()
    {

    }
    StudyProfile(HubFile initialHubFile)
    {

    }
}
