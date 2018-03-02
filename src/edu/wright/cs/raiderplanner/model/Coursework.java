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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17.
 */
public class Coursework extends Assignment {
	private Event startDate;
	private Deadline deadline;
	private ArrayList<Extension> extensions;

	// private methods
	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Coursework) {
			Coursework castedVce = (Coursework) receivedVce;
			if (castedVce.getStartDate() != null) {
				this.startDate = castedVce.getStartDate();
			}
			if (castedVce.getDeadline() != null) {
				this.deadline = castedVce.getDeadline();
			}
			if (castedVce.getExtensions() != null) {
				this.extensions = castedVce.getExtensions();
			}
		}

		super.replace(receivedVce);
	}
	// public methods

	// getters
	public Event getStartDate() {
		return startDate;
	}

	public Deadline getDeadline() {
		return deadline;
	}

	public ArrayList<Extension> getExtensions() {
		return extensions;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	/**
	 * Returns a String representing the deadline.
	 * Used in JavaFX.
	 *
	 * @return String
	 */
	public String getDeadlineString() {
		return new SimpleDateFormat("dd/MM/yyyy HH:MM").format(this.deadline.getDate());
	}

	// setters
	public void addNote(Note newNote) {
		if (!notes.contains(newNote)) {
			notes.add(newNote);
		}
	}

	public void removeNote(Note oldNote) {
		if (notes.contains(oldNote)) {
			notes.remove(oldNote);
		}
	}

	// Constructors
	public Coursework(int cweighting, Person csetBy, Person cmarkedBy, Person creviewedBy,
			int cmarks, Event cstartDate, Deadline cdeadline,
			ArrayList<Extension> cextensions) {
		super(cweighting, csetBy, cmarkedBy, creviewedBy, cmarks);
		startDate = cstartDate;
		deadline = cdeadline;
		extensions = new ArrayList<Extension>(cextensions);
	}

}
