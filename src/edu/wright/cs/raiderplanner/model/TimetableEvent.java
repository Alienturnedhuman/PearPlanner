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

package edu.wright.cs.raiderplanner.model;

import edu.wright.cs.raiderplanner.controller.MenuController.Window;

/**
 * PearPlanner/RaiderPlanner.
 *
 * @author Andrew Odintsov
 */
public class TimetableEvent extends Event {

	private static final long serialVersionUID = 939793921119553866L;

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

	/**
	 * @return the details of the room the event is in.
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @return the details of the lecturer holding the event.
	 */
	public Person getLecturer() {
		return lecturer;
	}

	/**
	 * @return the eventType of the timetable event.
	 */
	public TimeTableEventType getTimeTableEventType() {
		return timeTableEventType;
	}

	/**
	 * Sets the room the event will be in.
	 *
	 * @param newRoom room event will be in.
	 */
	public void setRoom(Room newRoom) {
		room = newRoom;
	}

	/**
	 * Sets the lecturer of the event.
	 *
	 * @param newLecturer person holding the event.
	 */
	public void setLecturer(Person newLecturer) {
		lecturer = newLecturer;
	}

	/**
	 * Creates the event type of the timeTableEvent.
	 *
	 * @param newTimeTableEventType type of event
	 */
	public void setTimeTableEventType(TimeTableEventType newTimeTableEventType) {
		timeTableEventType = newTimeTableEventType;
	}

	/**
	 * Sets the duration of the event.
	 *
	 * @param newDuration duration of the event.
	 */
	public void setDuration(int newDuration) {
		duration = newDuration;
	}

	@Override
	public String toString() {
		return name + " in " + room.toString() + " at " + date.getTime();
	}

	/* (non-Javadoc)
	 * @see edu.wright.cs.raiderplanner.model.ModelEntity#open
	 * (edu.wright.cs.raiderplanner.controller.MenuController.Window)
	 */
	@Override
	public void open(Window current) {
		// TODO Auto-generated method stub
	}

}
