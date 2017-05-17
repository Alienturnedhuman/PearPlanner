package Model;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Exam extends Assignment
{
    // private data
    private Exam resit = null;
    private ExamEvent timeSlot = null;

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof Exam)
        {
            Exam castedVCE = (Exam) receivedVCE;
            if (castedVCE.getResit() != null)
            {
                this.resit = castedVCE.getResit();
            }
            if (castedVCE.getTimeSlot() != null)
            {
                this.timeSlot = castedVCE.getTimeSlot();
            }
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

    // constructors
    public Exam(int cWeighting, Person cSetBy, Person cMarkedBy, Person cReviewedBy, int cMarks, ExamEvent cTimeSlot, Exam cResit)
    {
        super(cWeighting, cSetBy, cMarkedBy, cReviewedBy, cMarks);
        timeSlot = cTimeSlot;
        resit = cResit;
    }

    public Exam(int cWeighting, Person cSetBy, Person cMarkedBy, Person cReviewedBy, int cMarks, ExamEvent cTimeSlot)
    {
        super(cWeighting, cSetBy, cMarkedBy, cReviewedBy, cMarks);
        timeSlot = cTimeSlot;
        resit = null;
    }
}
