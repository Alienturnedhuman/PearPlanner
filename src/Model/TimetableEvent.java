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
<<<<<<< HEAD
 * PearPlanner/RaiderPlanner.
 *
 * @author Andrew Odintsov
||||||| merged common ancestors
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
=======
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
 */
public class TimetableEvent extends Event {

	private static final long serialVersionUID = 1L;

	private Room room;
	private Person lecturer;
	private TimeTableEventType timeTableEventType;

	/**
	 * Create a new Timetable event from the given parameters.
	 *
	 * @param date date of the event
	 * @param room room the event will be in
	 * @param lecturer teacher of the event
	 * @param timeTableEventType creating timetable event type
	 * @param duration how long the event will be
	 */
	public TimetableEvent(String date, Room room, Person lecturer,
			TimeTableEventType timeTableEventType, int duration) {

		super(date);
		setRoom(room);
		setLecturer(lecturer);
		setTimeTableEventType(timeTableEventType);
		setDuration(duration);

	}

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof TimetableEvent) {
			TimetableEvent castedVce = (TimetableEvent) receivedVce;
			this.duration = castedVce.getDuration();
			if (castedVce.getLecturer() != null) {
				this.lecturer = castedVce.getLecturer();
			}
			if (castedVce.getRoom() != null) {
				this.room = castedVce.getRoom();
			}
			if (castedVce.getTimeTableEventType() != null) {
				this.timeTableEventType = castedVce.getTimeTableEventType();
			}
		}
		super.replace(receivedVce);
	}

<<<<<<< HEAD
	/**
	 * @return the details of the room the event is in.
	 */
||||||| merged common ancestors
	// getters
=======
	// getters
	/**
	 * Gets the details of the room the event is in.
	 * returns room
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	public Room getRoom() {
		return room;
	}

<<<<<<< HEAD
	/**
	 * @return the details of the lecturer holding the event.
	 */
||||||| merged common ancestors
=======
	/**
	 * Gets the details of the lecturer holding the event.
	 * returns lecturer
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	public Person getLecturer() {
		return lecturer;
	}

<<<<<<< HEAD
	/**
	 * @return the eventType of the timetable event.
	 */
||||||| merged common ancestors
=======
	/**
	 * Gets the eventType of the timetable event.
	 * returns timeTableEventType
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	public TimeTableEventType getTimeTableEventType() {
		return timeTableEventType;
	}

<<<<<<< HEAD
	/**
	 * Sets the room the event will be in.
	 *
	 * @param newRoom room event will be in.
	 */
||||||| merged common ancestors
	public int getDuration() {
		return duration;
	}

	// setters
=======
	/**
	 * Overrides the default getDuration behavior from Event.
	 * returns duration
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	// setters
	/**
	 * Sets the room the event will be in.
	 * @param newRoom room event will be in.
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	public void setRoom(Room newRoom) {
		room = newRoom;
	}

<<<<<<< HEAD
	/**
	 * Sets the lecturer of the event.
	 *
	 * @param newLecturer person holding the event.
	 */
||||||| merged common ancestors
=======
	/**
	 * Sets the lecturer of the event.
	 * @param newLecturer person holding the event.
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	public void setLecturer(Person newLecturer) {
		lecturer = newLecturer;
	}

<<<<<<< HEAD
	/**
	 * Creates the event type of the timeTableEvent.
	 *
	 * @param newTimeTableEventType type of event
	 */
||||||| merged common ancestors
=======
	/**
	 * Creats the event type of the timeTableEvent.
	 * @param newTimeTableEventType type of event
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	public void setTimeTableEventType(TimeTableEventType newTimeTableEventType) {
		timeTableEventType = newTimeTableEventType;
	}

<<<<<<< HEAD
	/**
	 * Sets the duration of the event.
	 *
	 * @param newDuration duration of the event.
	 */
||||||| merged common ancestors
=======
	/**
	 * Sets the duration of the event.
	 * @param newDuration duration of the event.
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	public void setDuration(int newDuration) {
		duration = newDuration;
	}

	@Override
	public String toString() {
		return name + " in " + room.toString() + " at " + date.getTime();
	}

<<<<<<< HEAD
||||||| merged common ancestors
	// constructor
	public TimetableEvent(String cDate, Room cRoom, Person cLecturer, TimeTableEventType cTimeTableEventType,
						int cDuration) {
		super(cDate);
		setRoom(cRoom);
		setLecturer(cLecturer);
		setTimeTableEventType(cTimeTableEventType);
		setDuration(cDuration);

	}
=======
	// constructor
	/**
	 * Constructor creating the new Timetable event.
	 * @param date date of the event
	 * @param room room the event will be in
	 * @param lecturer teacher of the event
	 * @param timeTableEventType creating timetable event type
	 * @param duration how long the event will be
	 */
	public TimetableEvent(String date, Room room, Person lecturer,
			TimeTableEventType timeTableEventType, int duration) {
		super(date);
		setRoom(room);
		setLecturer(lecturer);
		setTimeTableEventType(timeTableEventType);
		setDuration(duration);

	}
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
}
