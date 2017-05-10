package Model;

import java.io.Serializable;
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
    private ArrayList<ExtensionApplication> extensions;
    private String name;
    private MultilineString details;
    private int year;
    private int semesterNo;
    private int version;

    // public methods

    // getters:
    public Module[] getModules()
    {
        Module[] m = new Module[this.modules.size()];
        m = this.modules.toArray(m);
        return m;
    }

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

    public String getName()
    {
        return name;
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
        return mYear == year && mSemesterNo == semesterNo;
    }

    // constructors
    public StudyProfile(HubFile initialHubFile)
    {
        // initial set up code below - check if this needs updating
        this.milestones = new ArrayList<>();

        this.modules = initialHubFile.getModules();
        this.extensions = initialHubFile.getExtensions();

        this.year = initialHubFile.getYear();
        this.semesterNo = initialHubFile.getSemester();
        this.version = initialHubFile.getVersion();
        this.name = initialHubFile.getSemesterName();
        this.details = initialHubFile.getSemesterDetails();
    }
}
