package Model;

import java.util.HashMap;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class VersionControlEntity extends ModelEntity
{
    protected int version;
    protected String uid;
    protected boolean sealed;
    private static HashMap<String, VersionControlEntity> library = new HashMap<>();
    protected boolean importer = false; // used for VCEs created during XML import
    // private methods

    /**
     * This method overwrites the data in the received object with that received
     * This method will need to overrode in every class that extends it
     *
     * @param receivedVCE
     */
    protected void replace(VersionControlEntity receivedVCE)
    {
        name = receivedVCE.getName();
        details = receivedVCE.getDetails();
        version = receivedVCE.getVersion();
        // super.replace(receivedVCE);
    }

    // public methods
    public void update(VersionControlEntity receivedVCE)
    {
        // initial set up code below - check if this needs
        if (uid.equals(receivedVCE.getUID()) && version < receivedVCE.getVersion())
        {
            replace(receivedVCE);
        }

    }

    public static boolean findAndUpdate(VersionControlEntity receivedVCE)
    {
        String UID = receivedVCE.getUID();
        if (inLibrary(UID))
        {
            library.get(UID).update(receivedVCE);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean makeImporter()
    {
        if (!sealed)
        {
            importer = true;
            return true;
        } else
        {
            return false;
        }
    }

    public boolean isImporter()
    {
        return importer;
    }

    public boolean addToLibrary()
    {
        if (importer)
        {
            if (inLibrary(uid))
            {
                return false;
            } else
            {
                library.put(uid, this);
                return true;
            }
        } else
        {
            return false;
        }
    }

    public static VersionControlEntity get(String UID)
    {
        if (inLibrary(UID))
        {
            return library.get(UID);
        } else
        {
            return null;
        }
    }

    public static boolean inLibrary(String UID)
    {
        return library.containsKey(UID);
    }

    // getters
    public int getVersion()
    {
        // initial set up code below - check if this needs updating
        return version;
    }

    public String getUID()
    {
        // initial set up code below - check if this needs updating
        return uid;
    }

    public static String libraryReport()
    {
        return "Total Entries: " + library.size();
    }

    public boolean setUID(String newUID, int newVersion)
    {
//        setUID(newUID);
        if (importer)
        {
            setUID(newUID);
            version = newVersion;
            return true;
        } else if (sealed || library.containsKey(newUID))
        {
            return false;
        } else
        {
            setUID(newUID);
            version = newVersion;
            return true;
        }
    }

    public boolean setUID(String newUID)
    {
        if (importer)
        {
            uid = newUID;
            return true;
        } else if (sealed || library.containsKey(newUID))
        {
            return false;
        } else
        {
            uid = newUID;
            library.put(newUID, this);
            return true;
        }
    }


    // Constructors
    public VersionControlEntity(boolean leaveUnsealed)
    {
        super();
        sealed = !leaveUnsealed;
    }

    public VersionControlEntity()
    {
        super();
        sealed = false;
    }

    public VersionControlEntity(String UID)
    {
        super();
        sealed = setUID(UID);
    }

}
