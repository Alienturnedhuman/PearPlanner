package Model;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class ExamEvent extends Event
{
    // private GregorianCalendar time; // no longer needed due to using Gregorian time
    private Room room;
    private int duration;

    public ExamEvent(String cDate, Room cRoom , int cDuration)
    {
        super(cDate);
        room = cRoom;
        duration = cDuration;
    }

    public int getDuration() {
        return duration;
    }
}
