package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public abstract class ModelEntity {
    protected String name;
    protected MultilineString details;
    protected ArrayList<Note> notes;

    protected void replace(ModelEntity newModel)
    {
        name = newModel.getName();
        details = newModel.getDetails();
    }


    // getters
    public String getName()
    {
        return name;
    }
    public  MultilineString getDetails()
    {
        return details;
    }

    abstract void replace(VersionControlEntity receivedVCE);
}
