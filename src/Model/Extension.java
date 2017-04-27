package Model;

/**
 * Created by bendickson on 4/27/17.
 */
public class Extension extends VersionControlEntity
{
    // private data
    private Deadline newDeadline;
    private MultilineString circumstances;
    private ApprovalStatus approvalStatus;

    // public enums
    public enum ApprovalStatus{PENDING,APPROVED,DECLINED};

    // public methods

    // getters
    public Deadline getDeadline()
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
