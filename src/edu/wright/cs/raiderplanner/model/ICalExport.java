/*
 * Copyright (C) 2017 - Amila Dias
 *
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

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Summary;
import biweekly.util.Duration;

import edu.wright.cs.raiderplanner.view.UiManager;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**Class used to create ICS files for export.
 * @author Amila Dias
 * 				CEG 3120 - RaiderPlanner
 */

public class ICalExport {
	private Date eventStart;
	private String title;
	private MultilineString description;
	private int hours;
	private int minutes;
	private ICalendar ical = new ICalendar();

	/**
	 * Take a user-created Event and add it to the ICalender for later export.
	 * to an ICS file.
	 *
	 * @param event User created event
	 */
	public void createExportEvent(Event event) {
		setEventStart(event.getDate());
		setTitle(event.getName());
		setDescription(event.getDetails());

		VEvent calEvent = new VEvent();
		Summary summary = calEvent.setSummary(title);
		summary.setLanguage(getLang());

		//Default hard coded duration
		hours = 1;
		minutes = 0;

		int intDuration = event.getDuration();
		hours = intDuration / 60;
		minutes = intDuration % 60;

		//Set duration within event
		Duration duration = new Duration.Builder().hours(hours).minutes(minutes).build();
		calEvent.setDuration(duration);
		//Set start date
		calEvent.setDateStart(eventStart);
		//Add new event to ICS File
		ical.addEvent(calEvent);
	}

	/**
	 * Merge all existing iCal events and export the prepared ical events to an ICS file.
	 */
	public void exportToFile(File file) {
		createIcs(ical, file);
	}

	/**
	 * Generate the ICS file for export.
	 *
	 * @param ical ICalender that contains items for new ICS file
	 * @param file File object to be created by ICalendar information
	 */
	private void createIcs(ICalendar ical, File file) {
		try {
			Biweekly.write(ical).go(file);
		} catch (IOException e) {
			UiManager.reportError("File does not exist: " + e.getMessage());
		}
	}

	/**
	 * Gets the current language of user based on the environment and locale.
	 *
	 * @return String formatted to provide user location and language
	 */
	private String getLang() {
		Locale currentLocale = Locale.getDefault();
		String langCode = currentLocale.getDisplayLanguage()
				+ "-" + currentLocale.getDisplayCountry();
		return langCode;
	}

	/**
	 * Sets the event start date.
	 *
	 * @param eventStart the event start date to set
	 */
	public void setEventStart(Date eventStart) {
		this.eventStart = eventStart;
	}

	/**
	 * Sets the title of the event.
	 *
	 * @param title the event title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the description of the event.
	 *
	 * @param description the event description to set
	 */
	public void setDescription(MultilineString description) {
		this.description = description;
	}

}
