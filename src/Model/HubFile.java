package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class HubFile
{
    // private data
    private ArrayList<VersionControlEntity> assets = new ArrayList<VersionControlEntity>();
    private ArrayList<Module> modules = new ArrayList<Module>();
    private ArrayList<ExtensionApplication> extensions = new ArrayList<ExtensionApplication>();
    private ArrayList<VersionControlEntity> updates = new ArrayList<VersionControlEntity>();
    private int version;
    private int semester;
    private int year;
    boolean updateFile;

    // public methods


    // getters
    public ArrayList<Module> getModules()
    {
        // initial set up code below - check if this needs updating
        return modules;
    }
    public ArrayList<ExtensionApplication> getExtensions()
    {
        // initial set up code below - check if this needs updating
        return extensions;
    }
    public ArrayList<VersionControlEntity> getUpdates()
    {
        // initial set up code below - check if this needs updating
        return updates;
    }
    public int getVersion()
    {
        // initial set up code below - check if this needs updating
        return version;
    }
    public int getSemester()
    {
        // initial set up code below - check if this needs updating
        return semester;
    }
    public int getYear()
    {
        // initial set up code below - check if this needs updating
        return year;
    }

    public boolean isUpdate()
    {
        return updateFile;
    }

    // setters


    // constructors

    /**
     * Constructor for new Study Profile
     * @param v
     * @param y
     * @param s
     * @param m
     * @param a
     */
    public HubFile(int v , int y , int s , ArrayList<Module> m , ArrayList<VersionControlEntity>  a)
    {
        version = v;
        year = y;
        semester = s;
        modules = (ArrayList<Module>)m.clone();
        assets = (ArrayList<VersionControlEntity>)a.clone();
        updateFile = false;
    }

    /**
     * Constructor for update
     * @param v
     * @param e
     * @param u
     */
    public HubFile(int v , ArrayList<ExtensionApplication> e , ArrayList<VersionControlEntity>  u)
    {
        version = v;
        extensions = (ArrayList<ExtensionApplication>)e.clone();
        updates = (ArrayList<VersionControlEntity>)u.clone();
        updateFile = true;
    }

}
