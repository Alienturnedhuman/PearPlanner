package sample;

import java.sql.Time;

/**
 * Created by bendickson on 4/27/17.
 */
public class TimetableEvent extends Event
{
    private Time time;
    private Room room;
    private Person lecturer;
    private TimeTableEventType timeTableEventType;
    private int duration;

}
