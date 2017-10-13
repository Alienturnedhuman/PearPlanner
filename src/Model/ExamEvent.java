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
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class ExamEvent extends Event {
	private Room room;
	private int duration;

	@Override
	protected void replace(VersionControlEntity receivedVCE) {
		if (receivedVCE instanceof ExamEvent) {
			ExamEvent castedVCE = (ExamEvent) receivedVCE;
			if (castedVCE.getRoom() != null) {
				this.room = castedVCE.getRoom();
			}
			this.duration = castedVCE.getDuration();
		}
		super.replace(receivedVCE);
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

	public int getDuration() {
		return duration;
	}

	public Room getRoom() {
		return room;
	}

	public ExamEvent(String cDate, Room cRoom, int cDuration) {
		super(cDate);
		room = cRoom;
		duration = cDuration;
	}
}
