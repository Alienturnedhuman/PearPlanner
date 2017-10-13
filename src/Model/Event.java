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

import Controller.MainController;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * ${FILENAME}
 * Created by Andrew Odintsov on 4/27/17.
 */
public class Event extends VersionControlEntity {

	protected GregorianCalendar date = null;
	private static final int DEFAULT_DURATION = 0;
	private static Pattern dateRegex =
			Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");

	// public methods

	/**
	 * Validates the given String.
	 *
	 * @param dateString a String containing a Date
	 * @return whether the given String is a valid date
	 */
	public static boolean validDateString(String dateString) {
		return dateRegex.matcher(dateString).matches();
	}

	// getters

	/**
	 * Returns a Date object containing this Date.
	 *
	 * @return Date object
	 */
	public Date getDate() {
		return this.date.getTime();
	}

	/**
	 * Returns a default duration of zero.
	 * @return the constant default duration
	 */
	public int getDuration() {
		return DEFAULT_DURATION;
	}

	/**
	 * Gets the GregorianCalendar date.
	 * @return date
	 */
	public GregorianCalendar getCalendar() {
		return date;
	}

	@Override
	public String toString() {
		return this.date.getTime().toString();
	}

	// setters:
	/**
	 * sets the date using the date regex.
	 * @param dateString input of the date
	 */
	public void setDate(String dateString) {
		// 09/04/2017T15:00:00Z
		if (validDateString(dateString)) {
			String day = dateString.substring(0, 2);
			String month = dateString.substring(3, 5);
			String year = dateString.substring(6, 10);
			String hour = dateString.substring(11, 13);
			String minute = dateString.substring(14, 16);
			String second = dateString.substring(17, 19);
			if (MainController.isNumeric(day) && MainController.isNumeric(month)
					&& MainController.isNumeric(year) && MainController.isNumeric(hour)
					&& MainController.isNumeric(minute) && MainController.isNumeric(second)) {
				date = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month) - 1,
						Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute),
						Integer.parseInt(second));
			}
		}
	}

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Event) {
			Event castedVce = (Event) receivedVce;
			if (castedVce.getCalendar() != null) {
				this.date = castedVce.getCalendar();
			}
		}
		super.replace(receivedVce);
	}

	// Constructors:
	/**
	 * Constructor of an event with a date.
	 * @param date date of the event
	 */
	public Event(String date) {
		setDate(date);
	}

	/**
	 * Constructor of a blank event.
	 */
	public Event() {
	}
}
