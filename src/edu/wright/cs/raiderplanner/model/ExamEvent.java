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

import java.text.SimpleDateFormat;

/**
 * An exam event with a location/room and a duration.
 *
 * @author Andrew Odintsov
 */
public class ExamEvent extends Event {

	private static final long serialVersionUID = 1127426583437715164L;

	private Room room;

	/**
	 * Create a new exam event from the given parameters.
	 *
	 * @param date date of the exam
	 * @param room room exam is in
	 * @param duration duration of the exam
	 */
	public ExamEvent(String date, Room room, int duration) {
		super(date);
		this.room = room;
		this.duration = duration;
	}

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof ExamEvent) {
			ExamEvent castedVce = (ExamEvent) receivedVce;
			if (castedVce.getRoom() != null) {
				this.room = castedVce.getRoom();
			}
			this.duration = castedVce.getDuration();
		}
		super.replace(receivedVce);
	}

	/**
	 * Returns a String representing the event.
	 * Used in JavaFX.
	 *
	 * @return a string representation of this exam event's date
	 */
	public String getDateString() {
		// TODO: we should not create a new SimpleDateFormat every time
		return new SimpleDateFormat("dd/MM/yyyy HH:MM").format(this.date.getTime());

	}

	/**
	 * @return the room associated with this exam event.
	 */
	public Room getRoom() {
		return room;
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
