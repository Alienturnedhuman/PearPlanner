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

import java.text.SimpleDateFormat;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class ExamEvent extends Event {
	private Room room;
	private int duration;

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

	// Getters:

	/**
	 * Returns a String representing the event.
	 * Used in JavaFX.
	 *
	 * @return String
	 */
	public String getDateString() {
		return new SimpleDateFormat("dd/MM/yyyy HH:MM").format(this.date.getTime());

	}

	/**
	 * Overrides the default getDuration behavior from Event.
	 * returns duration
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	/**
	 * gets the room the exam is in.
	 * @return room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Constructor for new exam event.
	 * @param date date of the exam
	 * @param room room exam is in
	 * @param duration duration of the exam
	 */
	public ExamEvent(String date, Room room, int duration) {
		super(date);
		this.room = room;
		this.duration = duration;
	}
}
