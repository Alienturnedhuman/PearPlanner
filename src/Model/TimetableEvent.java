/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar
 *
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package Model;


/**
 * PearPlanner/RaiderPlanner
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
            if (castedVCE.getLecturer() != null)
            {
                this.lecturer = castedVCE.getLecturer();
            }
            if (castedVCE.getRoom() != null)
            {
                this.room = castedVCE.getRoom();
            }
            if (castedVCE.getTimeTableEventType() != null)
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
        return room;
    }

    public Person getLecturer()
    {
        return lecturer;
    }

    public TimeTableEventType getTimeTableEventType()
    {
        return timeTableEventType;
    }

    public int getDuration()
    {
        return duration;
    }

    // setters
    public void setRoom(Room newRoom)
    {
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
