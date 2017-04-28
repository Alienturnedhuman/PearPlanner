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
    void replace(Exam receivedVCE)
    {
        this.resit = receivedVCE.getResit();
        this.timeSlot = receivedVCE.getTimeSlot();

        // super.replace(receivedVCE);
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
