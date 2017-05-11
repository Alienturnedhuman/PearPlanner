package Model;

import Controller.MainController;
import View.UIManager;

import java.io.IOException;
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
    private int year;
    private int semesterNo;
    private boolean current;

    // public methods

    // getters:
    public Module[] getModules()
    {
        Module[] m = new Module[this.modules.size()];
        m = this.modules.toArray(m);
        return m;
    }

    public Milestone[] getMilestones()
    {
        Milestone[] m = new Milestone[this.milestones.size()];
        m = this.milestones.toArray(m);
        return m;
    }

    public ExtensionApplication[] getExtensions()
    {
        ExtensionApplication[] e = new ExtensionApplication[this.extensions.size()];
        e = this.extensions.toArray(e);
        return e;
    }

    public ArrayList<Task> getTasks()
    {
        ArrayList<Task> tasks = new ArrayList<>();
        this.modules.forEach(e -> e.getAssignments().forEach(ee -> tasks.addAll(ee.getTasks())));
        return tasks;
    }

    public boolean isCurrent()
    {
        return current;
    }

    public void setCurrent(boolean current)
    {
        this.current = current;
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

    public int getSemesterNo()
    {
        return semesterNo;
    }

    public boolean matches(int mYear, int mSemesterNo)
    {
        return mYear == year && mSemesterNo == semesterNo;
    }

    @Override
    public void open()
    {
        try
        {
            MainController.ui.studyProfileDetails(this);
        } catch (IOException e)
        {
            UIManager.reportError("Unable to open View file");
        }
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

        this.current = false;
    }
}
