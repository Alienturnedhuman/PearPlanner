package Model;

import java.io.Serializable;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class ExtensionApplication implements Serializable
{
    // private data
    private Extension extension;
    private String moduleCode;
    private String assignmentUID;
    private Account account;

    // public methods


    // getters
    public Extension getExtension()
    {
        // initial set up code below - check if this needs updating
        return extension;
    }

    public String getModuleCode()
    {
        // initial set up code below - check if this needs updating
        return moduleCode;
    }

    public String getAssignmentUID()
    {
        // initial set up code below - check if this needs updating
        return assignmentUID;
    }


    // constructors
}
