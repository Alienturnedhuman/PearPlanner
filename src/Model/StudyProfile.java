package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class StudyProfile extends VersionControlEntity
{
    // private data
    private ArrayList<Module> modules;
    private ArrayList<Milestone> milestones;
    private int year;
    private int semesterNo;

    // public methods
    public int milestonesCompleted()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    public double milestonesProgress()
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    public int getYear()
    {
        return year;
    }
    public int getSemester()
    {
        return semesterNo;
    }

    public boolean matches(int mYear, int mSemesterNo)
    {
        return mYear==year&&mSemesterNo==semesterNo;
    }
    // constructors
    StudyProfile()
    {
        // initial set up code below - check if this needs updating

    }
    StudyProfile(HubFile initialHubFile)
    {
        // initial set up code below - check if this needs updating
        ArrayList<Module> hfModules = initialHubFile.getModules();
        int i = -1;
        int ii = hfModules.size();
        while(++i<ii)
        {
            modules.add(hfModules.get(i));
        }
        milestones = new ArrayList<>();
        year = initialHubFile.getYear();
        semesterNo = initialHubFile.getSemester();



    }
}
