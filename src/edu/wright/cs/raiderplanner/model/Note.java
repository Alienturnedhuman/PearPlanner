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

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class Note implements Serializable {
	// private data
	private String title;
	private GregorianCalendar timeStamp;
	private MultilineString text;

	// public methods

	/**
	 * This getter gets the title of the note.
	 * @return the title
	 */
	public String getTitle() {
		// initial set up code below - check if this needs updating
		return title;
	}

	/**
	 * This getter gets the time stamp of the note.
	 * @return the time stamp
	 */
	public GregorianCalendar getTimeStamp() {
		// initial set up code below - check if this needs updating
		return timeStamp;
	}

	/**
	 * This getter gets the text of the note.
	 * @return the text
	 */
	public MultilineString getText() {
		return text;
	}

	/**
	 * This setter sets the title of the note.
	 * @param newTitle this is the new title to be set
	 */
	public void setTitle(String newTitle) {
		// initial set up code below - check if this needs updating
		title = newTitle;
	}

	/**
	 * This setter sets the time stamp of the note.
	 * @param newTimeStamp This is the new time stamp to be set
	 */
	public void setTimeStamp(GregorianCalendar newTimeStamp) {
		// initial set up code below - check if this needs updating
		timeStamp = newTimeStamp;
	}

	/**
	 * This setter sets the time stamp of the note.
	 * @param yr for year
	 * @param month for month
	 * @param day for day
	 * @param hr for hour
	 * @param min for minute
	 * @param sec for second
	 */
	public void setTimeStamp(int yr, int month, int day, int hr, int min, int sec) {
		// initial set up code below - check if this needs updating
		timeStamp = new GregorianCalendar(yr, month, day, hr, min, sec);
	}

	/**
	 * This setter sets the text of the note.
	 * @param newText This is the new text to be set
	 */
	public void setText(MultilineString newText) {
		// initial set up code below - check if this needs updating
		text = newText;
	}

	/**
	 * This is the constructor for the class.
	 * @param title This is the title
	 * @param timeStamp This is the time stamp
	 * @param text This is the text
	 */
	public Note(String title, GregorianCalendar timeStamp, MultilineString text) {
		this.title = title;
		this.timeStamp = timeStamp;
		this.text = text;
	}
}
