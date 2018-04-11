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

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class ExtensionApplication implements Serializable {
	// private data
	private Extension extension;
	private String moduleCode;
	private String assignmentUId;
	private Account account;

	// public methods

	/**
	 * Getter for the Extension.
	 * @return
	 */
	public Extension getExtension() {
		// initial set up code below - check if this needs updating
		return extension;
	}

	/**
	 * Getter for the Module Code.
	 * @return
	 */
	public String getModuleCode() {
		// initial set up code below - check if this needs updating
		return moduleCode;
	}

	/**
	 * Getter for the AssignmentUIds.
	 * @return
	 */
	public String getAssignmentUIds() {
		// initial set up code below - check if this needs updating
		return assignmentUId;
	}
}
