package Model;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Exam extends Assignment
{
    // private data
    private Exam resit;
    private ExamEvent timeSlot;

    // private methods
    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if(receivedVCE instanceof Exam)
        {
            Exam castedVCE = (Exam)receivedVCE;
            this.resit = castedVCE.getResit();
            this.timeSlot = castedVCE.getTimeSlot();
        }

        super.replace(receivedVCE);
    }

    // public methods

    // getters
    public Exam getResit()
    {
        return resit;
    }
    public ExamEvent getTimeSlot()
    {
        return timeSlot;
    }
}
