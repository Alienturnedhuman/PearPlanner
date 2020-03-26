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
public class Notification implements Serializable {
	// private data
	private String title;
	private GregorianCalendar dateTime;
	private MultilineString details;
	private boolean read;
	private ModelEntity link;

	// public methods

	/**
	 * Getter for the title.
	 * @return String for variable title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for the date.
	 * @return GregorianCalendar for variable dateTime
	 */
	public GregorianCalendar getDateTime() {
		return dateTime;
	}

	/**
	 * Getter for the details.
	 * @return MultilineString for variable details
	 */
	public MultilineString getDetails() {
		return details;
	}

	/**
	 * Getter for the details as a String.
	 * @return String for variable details
	 */
	public String getDetailsAsString() {
		return this.details.getAsString();
	}

	/**
	 * Getter for value of read.
	 * @return Boolean for variable read
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * Getter for link.
	 * @return ModelEntity for variable link
	 */
	public ModelEntity getLink() {
		return link;
	}

	/**
	 * toString() method.
	 * @return Formatted String of title and details
	 */
	public String toString() {
		return this.title + ": " + this.getDetailsAsString();
	}

	// setters

	/**
	 * Setter for variable read. Sets read to true.
	 */
	public void read() {
		this.read = true;
	}

	/**
	 * Setter for variable read. Sets read to false.
	 */
	public void unread() {
		this.read = false;
	}

	/**
	 * Setter for variable read. Will toggle the value or read.
	 */
	public void toggle() {
		this.read = !read;
	}

	// constructors

	/**
	 * Constructor when all parameters are passed.
	 * @param title String
	 * @param dateTime GregorianCalendar
	 * @param details String
	 * @param link ModelEntity
	 */
	public Notification(String title, GregorianCalendar dateTime, String details,
			ModelEntity link) {
		this.title = title;
		this.dateTime = dateTime;
		this.details = new MultilineString(details);
		this.read = false;
		this.link = link;
	}

	/**
	 * Constructor when the title, dateTime, and details parameters are passed.
	 * @param title String
	 * @param dateTime GregorianCalendar
	 * @param details String
	 */
	public Notification(String title, GregorianCalendar dateTime, String details) {
		this.title = title;
		this.dateTime = dateTime;
		this.details = new MultilineString(details);
		this.read = false;
	}

	/**
	 * Constructor when the title and dateTime parameters are passed.
	 * @param title String
	 * @param dateTime GregorianCalendar
	 */
	public Notification(String title, GregorianCalendar dateTime) {
		this.title = title;
		this.dateTime = dateTime;
		this.read = false;
	}
}