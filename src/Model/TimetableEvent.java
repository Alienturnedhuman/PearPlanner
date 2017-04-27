package Model;

import java.sql.Time;

/**
 * Created by bendickson on 4/27/17.
 */
public class TimetableEvent extends Event
{
    // private data
    private Time time;
    private Room room;
    private Person lecturer;
    private TimeTableEventType timeTableEventType;
    private int duration;

    // public methods

    // getters
    public Time getTime()
    {
        // initial set up code below - check if this needs updating
        return time;
    }
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
    public void setTime(Time newTime)
    {
        // initial set up code below - check if this needs updating
        time = newTime;
    }
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
