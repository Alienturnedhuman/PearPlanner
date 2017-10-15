/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Eric Sweet, Roberto C. Sánchez
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
 * A basic event with a calendar date and duration.
 *
 * @author Andrew Odintsov
 */
public class Event extends VersionControlEntity {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_DURATION = 0;
	private static Pattern dateRegex =
			Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");

	protected GregorianCalendar date = null;
	protected int duration = DEFAULT_DURATION;

	/**
	 * Create an Event with the given date.
	 *
	 * @param date date of the event
	 */
	public Event(String date) {
		setDate(date);
	}

	/**
	 * Create a blank Event.
	 */
	public Event() {
	}

	/**
	 * @return this Event's date.
	 */
	public Date getDate() {
		return this.date.getTime();
	}

	/**
	 * @return the Event's duration.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @return this Event's calendar object.
	 */
	public GregorianCalendar getCalendar() {
		return date;
	}

	/**
	 * Sets the date using the date regex.
	 *
	 * @param dateString input of the date
	 */
	public void setDate(String dateString) {
		// TODO: Fix this to use SimpleDateFormat
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

	/**
	 * Validates the given String.
	 *
	 * @param dateString a String containing a Date
	 * @return whether the given String is a valid date
	 */
	public static boolean validDateString(String dateString) {
		return dateRegex.matcher(dateString).matches();
	}

	@Override
	public String toString() {
		return this.date.getTime().toString();
	}

}
