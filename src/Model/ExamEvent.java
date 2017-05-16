package Model;

import java.text.SimpleDateFormat;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class ExamEvent extends Event
{
    // private GregorianCalendar time; // no longer needed due to using Gregorian time
    private Room room;
    private int duration;

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof ExamEvent)
        {
            ExamEvent castedVCE = (ExamEvent) receivedVCE;
            if(castedVCE.getRoom()!=null)
            {
                this.room = castedVCE.getRoom();
            }
            this.duration = castedVCE.getDuration();
        }
        super.replace(receivedVCE);
    }

    /**
     * Returns a String representing the event.
     * Used in JavaFX.
     *
     * @return String
     */
    public String getDateString()
    {
        return new SimpleDateFormat("dd/MM/yyyy HH:MM").format(this.date.getTime());

    }

    public ExamEvent(String cDate, Room cRoom, int cDuration)
    {
        super(cDate);
        room = cRoom;
        duration = cDuration;
    }

    public int getDuration() {
        return duration;
    }

    public Room getRoom() {
        return room;
    }
}
