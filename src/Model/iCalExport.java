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

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Summary;
import biweekly.util.Duration;

/**
 * @author Amila Dias
 * CEG 3120 - RaiderPlanner
 *
 */
public class iCalExport {
	
	private Date eStart;
	private String title;
	private MultilineString description;
	private int hours;
	private int minutes;
	private int counter;
	
	public void createExportEvent(Event event, int counter) {
		seteStart(event.getDate());
		setTitle(event.getName());
		setDescription(event.getDetails());
		this.counter = counter;

		File file = new File("calendarExport/" + createFileName(counter));

		ICalendar ical = new ICalendar();
		VEvent calEvent = new VEvent();

		Summary summary = calEvent.setSummary(title);
		summary.setLanguage();

		//Default hard coded duration
		hours = 1;
		minutes = 0;

		//If duration exists within event, pull duration
		//TODO: Resolution of issue #88 in RaiderPlanner git should allow for single call and removal of if statement		
		if (event instanceof TimetableEvent) {
			int intDuration = ((TimetableEvent) event).getDuration();
			hours = intDuration/60;
			minutes = intDuration%60;
		} else if (event instanceof ExamEvent) {
			int intDuration = ((ExamEvent) event).getDuration();
			hours = intDuration/60;
			minutes = intDuration%60;
		} 

		//Set duration within event
		Duration duration = new Duration.Builder().hours(hours).minutes(minutes).build();
		calEvent.setDuration(duration);
		//Set start date
		calEvent.setDateStart(eStart);
		//Add new event to ICS File
		ical.addEvent(calEvent);
		//Create ICS File
		createIcs(ical, file);
	}

	public void iCalSetup() {
		File newDir = new File("calendarExport");
		if(newDir.exists()){
			System.out.println("Calendar Export Directory already exists, all files were overwritten");
		} else {
			newDir.mkdirs();
		}
	}

	private void createIcs(ICalendar iCal, File file) {
		try{
			Biweekly.write(iCal).go(file);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	private String createFileName(int counter) {
		String str = "calendarExport" + counter + ".ics";
		return str;
	}

	/**
	 * @param eStart the eStart to set
	 */
	public void seteStart(Date eStart) {
		this.eStart = eStart;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(MultilineString description) {
		this.description = description;
	}
}
