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

	// getters
	public String getTitle() {
		return title;
	}

	public GregorianCalendar getDateTime() {
		return dateTime;
	}

	public MultilineString getDetails() {
		return details;
	}

	public String getDetailsAsString() {
		return this.details.getAsString();
	}

	public boolean isRead() {
		return read;
	}

	public ModelEntity getLink() {
		return link;
	}

	public String toString() {
		return this.title + ": " + this.getDetailsAsString();
	}

	// setters
	public void read() {
		this.read = true;
	}

	public void unread() {
		this.read = false;
	}

	public void toggle() {
		this.read = !read;
	}

	// constructors
	public Notification(String title, GregorianCalendar dateTime, String details,
			ModelEntity link) {
		this.title = title;
		this.dateTime = dateTime;
		this.details = new MultilineString(details);
		this.read = false;
		this.link = link;
	}

	public Notification(String title, GregorianCalendar dateTime, String details) {
		this.title = title;
		this.dateTime = dateTime;
		this.details = new MultilineString(details);
		this.read = false;
	}

	public Notification(String title, GregorianCalendar dateTime) {
		this.title = title;
		this.dateTime = dateTime;
		this.read = false;
	}
}