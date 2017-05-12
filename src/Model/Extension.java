package Model;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Extension extends VersionControlEntity
{
    // private data
    private Deadline newDeadline;
    private MultilineString circumstances;
    private ApprovalStatus approvalStatus;


    // private methods
    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if(receivedVCE instanceof Extension)
        {
            Extension castedVCE = (Extension)receivedVCE;
            this.newDeadline = castedVCE.getNewDeadline();
            this.circumstances = castedVCE.getCircumstances();
            this.approvalStatus = castedVCE.getApprovalStatus();
        }

        super.replace(receivedVCE);
    }

    // public enums
    public enum ApprovalStatus{PENDING,APPROVED,DECLINED}

    // public methods

    // getters
    public Deadline getNewDeadline()
    {
        // initial set up code below - check if this needs updating
        return newDeadline;
    }
    public MultilineString getCircumstances()
    {
        // initial set up code below - check if this needs updating
        return circumstances;
    }
    public ApprovalStatus getApprovalStatus()
    {
        // initial set up code below - check if this needs updating
        return approvalStatus;
    }

    // setters
    public void setCircumstances(MultilineString newCircumstances)
    {
        // initial set up code below - check if this needs updating
        circumstances = newCircumstances;
    }
    public void setNewDeadline(Deadline newNewDeadline)
    {
        // initial set up code below - check if this needs updating
        newDeadline = newNewDeadline;
    }
    public void setApprovalStatus(ApprovalStatus newApprovalStatus)
    {
        // initial set up code below - check if this needs updating
        approvalStatus = newApprovalStatus;
    }


    // constructor
}
