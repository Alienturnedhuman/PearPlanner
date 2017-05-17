package Model;

import Controller.MenuController;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class ModelEntity implements Serializable
{
    protected String name = "";
    protected MultilineString details = null;
    protected ArrayList<Note> notes;


    // getters
    public String getName()
    {
        return name;
    }

    public MultilineString getDetails()
    {
        return details;
    }

    public void setName(String newName)
    {
        name = newName;
    }

    public void setDetails(String newDetails)
    {
        details = new MultilineString(newDetails);
    }

    public void setDetails(String[] newDetails)
    {
        details = new MultilineString(newDetails);
    }

    public void setDetails(ArrayList<String> newDetails)
    {
        details = new MultilineString(newDetails.toArray(new String[newDetails.size()]));
    }

    public void setDetails(MultilineString newDetails)
    {
        details = newDetails;
    }


    public void addProperties(String aName, MultilineString aDetails)
    {
        setName(aName);
        setDetails(aDetails.clone());
    }

    public void addProperties(String aName, String aDetails)
    {
        setName(aName);
        setDetails(aDetails);
    }

    /**
     * Open the appropriate UI window for this class
     * To be overridden by children.
     */
    public void open(MenuController.Window current)
    {
    }

    public ModelEntity()
    {
        this("");
    }

    public ModelEntity(String cName)
    {
        this(cName, "");
    }

    public ModelEntity(String cName, String cDetails)
    {
        this(cName, cDetails.split("\n"));
    }

    public ModelEntity(String cName, String[] cDetails)
    {
        setName(cName);
        setDetails(cDetails);
        notes = new ArrayList<>();
    }

    public ModelEntity(String cName, String[] cDetails, ArrayList<Note> cNotes)
    {
        this(cName, cDetails);
        notes = (ArrayList<Note>) cNotes.clone();
    }
}
