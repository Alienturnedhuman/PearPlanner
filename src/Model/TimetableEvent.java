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



    // constructor
}
