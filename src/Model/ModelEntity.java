package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public abstract class ModelEntity {
    protected String name = "";
    protected MultilineString details;
    protected ArrayList<Note> notes;


    // getters
    public String getName()
    {
        return name;
    }
    public  MultilineString getDetails()
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
        details = new MultilineString((String[])newDetails.toArray());
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

    ModelEntity()
    {
        this("");
    }
    ModelEntity(String cName)
    {
        this(cName,"");
    }
    ModelEntity(String cName,String cDetails)
    {
        this(cName,cDetails.split("\n"));
    }
    ModelEntity(String cName, String[] cDetails)
    {
        setName(cName);
        setDetails(cDetails);
        notes = new ArrayList<>();
    }
    ModelEntity(String cName,String[] cDetails,ArrayList<Note> cNotes)
    {
        this(cName,cDetails);
        notes = (ArrayList<Note>)cNotes.clone();
    }
}
