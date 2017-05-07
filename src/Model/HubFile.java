package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.*;

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



    // schemas

    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_ROOT;
    static
    {
        SCHEMA_ROOT = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_ROOT.put("hubfile",XMLcontroller.ImportAs.NODELIST);
    }
    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_NEW_STUDYPROFILE;
    static
    {
        SCHEMA_NEW_STUDYPROFILE = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_NEW_STUDYPROFILE.put("version",XMLcontroller.ImportAs.INTEGER);
        SCHEMA_NEW_STUDYPROFILE.put("assets",XMLcontroller.ImportAs.NODELIST);
        SCHEMA_NEW_STUDYPROFILE.put("studyProfile",XMLcontroller.ImportAs.NODELIST);
    }
    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_UPDATE_FILE;
    static
    {
        SCHEMA_UPDATE_FILE = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_UPDATE_FILE.put("version",XMLcontroller.ImportAs.INTEGER);
        SCHEMA_UPDATE_FILE.put("extensions",XMLcontroller.ImportAs.NODELIST);
        SCHEMA_UPDATE_FILE.put("updates",XMLcontroller.ImportAs.NODELIST);
    }

    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_ASSETS;
    static
    {
        SCHEMA_ASSETS = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_ASSETS.put("persons",XMLcontroller.ImportAs.NODELIST);
        SCHEMA_ASSETS.put("buildings",XMLcontroller.ImportAs.NODELIST);
        SCHEMA_ASSETS.put("rooms",XMLcontroller.ImportAs.NODELIST);
        SCHEMA_ASSETS.put("timetableEventTypes",XMLcontroller.ImportAs.NODELIST);
    }
    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_STUDYPROFILE;
    static
    {
        SCHEMA_STUDYPROFILE = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_STUDYPROFILE.put("year",XMLcontroller.ImportAs.NODELIST);
        SCHEMA_STUDYPROFILE.put("semester",XMLcontroller.ImportAs.NODELIST);
        SCHEMA_STUDYPROFILE.put("modules",XMLcontroller.ImportAs.NODELIST);
    }
    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_PERSON;
    static
    {
        SCHEMA_PERSON = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_PERSON.put("name",XMLcontroller.ImportAs.STRING);
        SCHEMA_PERSON.put("details",XMLcontroller.ImportAs.MULTILINESTRING);
        SCHEMA_PERSON.put("version",XMLcontroller.ImportAs.INTEGER);
        SCHEMA_PERSON.put("uid",XMLcontroller.ImportAs.STRING);
        SCHEMA_PERSON.put("givenNames",XMLcontroller.ImportAs.STRING);
        SCHEMA_PERSON.put("familyName",XMLcontroller.ImportAs.STRING);
        SCHEMA_PERSON.put("salutation",XMLcontroller.ImportAs.STRING);
        SCHEMA_PERSON.put("email",XMLcontroller.ImportAs.STRING);
        SCHEMA_PERSON.put("familyNameLast",XMLcontroller.ImportAs.BOOLEAN);
    }
    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_BUILDING;
    static
    {
        SCHEMA_BUILDING = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_BUILDING.put("name",XMLcontroller.ImportAs.STRING);
        SCHEMA_BUILDING.put("details",XMLcontroller.ImportAs.MULTILINESTRING);
        SCHEMA_BUILDING.put("version",XMLcontroller.ImportAs.INTEGER);
        SCHEMA_BUILDING.put("uid",XMLcontroller.ImportAs.STRING);
        SCHEMA_BUILDING.put("code",XMLcontroller.ImportAs.STRING);
        SCHEMA_BUILDING.put("latitude",XMLcontroller.ImportAs.DOUBLE);
        SCHEMA_BUILDING.put("longitude",XMLcontroller.ImportAs.DOUBLE);
    }
    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_ROOM;
    static
    {
        SCHEMA_ROOM = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_ROOM.put("name",XMLcontroller.ImportAs.STRING);
        SCHEMA_ROOM.put("details",XMLcontroller.ImportAs.MULTILINESTRING);
        SCHEMA_ROOM.put("version",XMLcontroller.ImportAs.INTEGER);
        SCHEMA_ROOM.put("uid",XMLcontroller.ImportAs.STRING);
        SCHEMA_ROOM.put("building",XMLcontroller.ImportAs.STRING);
        SCHEMA_ROOM.put("roomNumber",XMLcontroller.ImportAs.STRING);
    }
    public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_TIMETABLE_EVENT_TYPE;
    static
    {
        SCHEMA_TIMETABLE_EVENT_TYPE = new HashMap<String, XMLcontroller.ImportAs>();
        SCHEMA_TIMETABLE_EVENT_TYPE.put("name",XMLcontroller.ImportAs.STRING);
        SCHEMA_TIMETABLE_EVENT_TYPE.put("details",XMLcontroller.ImportAs.MULTILINESTRING);
        SCHEMA_TIMETABLE_EVENT_TYPE.put("version",XMLcontroller.ImportAs.INTEGER);
        SCHEMA_TIMETABLE_EVENT_TYPE.put("uid",XMLcontroller.ImportAs.STRING);
    }

}
