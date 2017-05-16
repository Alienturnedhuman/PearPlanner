package Model;


/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class TimetableEvent extends Event
{
    // private data
    private Room room;
    private Person lecturer;
    private TimeTableEventType timeTableEventType;
    private int duration;



    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof TimetableEvent)
        {
            TimetableEvent castedVCE = (TimetableEvent) receivedVCE;
            this.duration = castedVCE.getDuration();
            if(castedVCE.getLecturer()!=null)
            {
                this.lecturer = castedVCE.getLecturer();
            }
            if(castedVCE.getRoom()!=null)
            {
                this.room = castedVCE.getRoom();
            }
            if(castedVCE.getTimeTableEventType()!=null)
            {
                this.timeTableEventType = castedVCE.getTimeTableEventType();
            }
        }
        super.replace(receivedVCE);
    }
    // public methods

    // getters
    public Room getRoom()
    {
        // initial set up code below - check if this needs updating
        return room;
    }

    public Person getLecturer()
    {
        // initial set up code below - check if this needs updating
        return lecturer;
    }

    public TimeTableEventType getTimeTableEventType()
    {
        // initial set up code below - check if this needs updating
        return timeTableEventType;
    }

    public int getDuration()
    {
        // initial set up code below - check if this needs updating
        return duration;
    }

    // setters
    public void setRoom(Room newRoom)
    {
        // initial set up code below - check if this needs updating
        room = newRoom;
    }

    public void setLecturer(Person newLecturer)
    {
        lecturer = newLecturer;
    }

    public void setTimeTableEventType(TimeTableEventType newTimeTableEventType)
    {
        timeTableEventType = newTimeTableEventType;
    }

    public void setDuration(int newDuration)
    {
        duration = newDuration;
    }


    @Override
    public String toString()
    {
        return name + " in " + room.toString() + " at " + date.getTime();
    }


    // constructor
    public TimetableEvent(String cDate, Room cRoom, Person cLecturer, TimeTableEventType cTimeTableEventType,
                          int cDuration)
    {
        super(cDate);
        setRoom(cRoom);
        setLecturer(cLecturer);
        setTimeTableEventType(cTimeTableEventType);
        setDuration(cDuration);

    }
}
