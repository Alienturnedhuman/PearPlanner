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

<<<<<<< HEAD
	private static final long serialVersionUID = 1L;
||||||| merged common ancestors
	private static Pattern dateRegex = Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");
=======
	protected GregorianCalendar date = null;
	private static final int DEFAULT_DURATION = 0;
	private static Pattern dateRegex =
			Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3

	private static final int DEFAULT_DURATION = 0;
	private static Pattern dateRegex =
			Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");

	protected GregorianCalendar date = null;
	protected int duration = DEFAULT_DURATION;

	/**
<<<<<<< HEAD
	 * Create an Event with the given date.
||||||| merged common ancestors
	 * Validates the given String
=======
	 * Validates the given String.
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	 *
<<<<<<< HEAD
	 * @param date date of the event
||||||| merged common ancestors
	 * @param dateString a String containing a Date
	 * @return whether the given String is a valide date
=======
	 * @param dateString a String containing a Date
	 * @return whether the given String is a valid date
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
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
<<<<<<< HEAD
	 * @return this Event's date.
||||||| merged common ancestors
	 * Returns a Date object containing this Date
	 *
	 * @return Date object
=======
	 * Returns a Date object containing this Date.
	 *
	 * @return Date object
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	 */
	public Date getDate() {
		return this.date.getTime();
	}

<<<<<<< HEAD
	/**
	 * @return the Event's duration.
	 */
	public int getDuration() {
		return duration;
||||||| merged common ancestors
	public GregorianCalendar getCalendar() {
		return date;
=======
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
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	}

<<<<<<< HEAD
	/**
	 * @return this Event's calendar object.
	 */
	public GregorianCalendar getCalendar() {
		return date;
||||||| merged common ancestors
	public String toString() {
		return this.date.getTime().toString();
=======
	@Override
	public String toString() {
		return this.date.getTime().toString();
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	}

<<<<<<< HEAD
	/**
	 * Sets the date using the date regex.
	 *
	 * @param dateString input of the date
	 */
||||||| merged common ancestors
	// setters:
=======
	// setters:
	/**
	 * sets the date using the date regex.
	 * @param dateString input of the date
	 */
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
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

<<<<<<< HEAD
	/**
	 * Validates the given String.
	 *
	 * @param dateString a String containing a Date
	 * @return whether the given String is a valid date
	 */
	public static boolean validDateString(String dateString) {
		return dateRegex.matcher(dateString).matches();
||||||| merged common ancestors
	// Constructors:
	public Event(String cDate) {
		setDate(cDate);
=======
	// Constructors:
	/**
	 * Constructor of an event with a date.
	 * @param date date of the event
	 */
	public Event(String date) {
		setDate(date);
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	}

<<<<<<< HEAD
	@Override
	public String toString() {
		return this.date.getTime().toString();
||||||| merged common ancestors
	public Event() {
=======
	/**
	 * Constructor of a blank event.
	 */
	public Event() {
>>>>>>> 76e78f5a99e05488b4c2632fa584a968d0066ed3
	}

}
