package Model;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public abstract class VersionControlEntity extends ModelEntity
{
    protected int version;
    protected String uid;

    // private methods

    /**
     * This method overwrites the data in the received object with that received
     * This method will need to overrode in every class that extends it
     * @param receivedVCE
     */
    protected void replace(VersionControlEntity receivedVCE)
    {
        this.version = receivedVCE.getVersion();
        // super.replace(receivedVCE);
    }

    // public methods
    public void update(VersionControlEntity receivedVCE)
    {
        // initial set up code below - check if this needs
        if(uid.equals(receivedVCE.getUID()) && version < receivedVCE.getVersion())
        {
            replace(receivedVCE);
        }

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

    // private methods
    abstract void replace(Assignment receivedVCE);
}
