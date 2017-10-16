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

package Model;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Summary;
import biweekly.util.Duration;

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
	private int counter;

	/**Method creates an event to be exported to an ICS file.
	 * @param event - User created event
	 * @param counter - Counter used to ensure
	 * 				unique file names, counts how many files have been created
	 */
	public void createExportEvent(Event event, int counter) {
		seteStart(event.getDate());
		setTitle(event.getName());
		setDescription(event.getDetails());
		this.counter = counter;


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
		ICalendar ical = new ICalendar();
		ical.addEvent(calEvent);
		//Create ICS File
		File file = new File("calendarExport/" + createFileName(counter));
		createIcs(ical, file);
	}


	/**Method creates directory required to export ICS files.
	 */
	public void icalSetup() {
		File newDir = new File("calendarExport");
		if (newDir.exists()) {
			System.out.println("CALENDAR Export Directory"
					+ " already exists, all files were overwritten");
		} else {
			newDir.mkdirs();
		}
	}

	/**createIcs generates the ICS file for export.
	 * @param ical - ICS object that contains items for new ICS file
	 * @param file - File object to be created by ICalendar information
	 */
	private void createIcs(ICalendar ical, File file) {
		try {
			Biweekly.write(ical).go(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**Method creates unique file names.
	 * @param counter - imported to count number of files created, ensures unique file name
	 * @return - Returns string file name
	 */
	private String createFileName(int counter) {
		String str = "calendarExport" + counter + ".ics";
		return str;
	}

	/**Method gets current language of user.
	 * @return - Returns string formatted to provide user location and language
	 */
	private String getLang() {
		Locale currentLocale = Locale.getDefault();
		String langCode = currentLocale.getDisplayLanguage()
				+ "-" + currentLocale.getDisplayCountry();
		return langCode;
	}

	/**Method sets the event start date.
	 * @param eventStart the eStart to set
	 */
	public void seteStart(Date eventStart) {
		this.eventStart = eventStart;
	}

	/**Method sets title of the event.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**Method sets description of event.
	 * @param description the description to set
	 */
	public void setDescription(MultilineString description) {
		this.description = description;
	}
}
