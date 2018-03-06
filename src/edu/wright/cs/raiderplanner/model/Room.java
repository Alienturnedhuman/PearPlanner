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

import edu.wright.cs.raiderplanner.controller.MenuController.Window;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class Room extends VersionControlEntity {
	// private data
	private Building building = null;
	private String roomNumber;

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Room) {
			Room castedVce = (Room) receivedVce;
			if (castedVce.getBuilding() != null) {
				this.building = castedVce.getBuilding();
			}
			this.roomNumber = castedVce.getRoomNumber();
		}
		super.replace(receivedVce);
	}

	// public methods

	// getters
	public Building getBuilding() {
		return building;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public String getLocation() {
		return name + "( " + roomNumber + " )";
	}

	@Override
	public String toString() {
		// TODO: Figure out if there is a better approach here, perhaps including
		// "unknown" or similar if build is null; also make output consistent with
		// other methods, like getLocation() and getRoomNumber()
		if (building == null) {
			return name + "( " + roomNumber + " )";
		} else {
			return name + "( " + roomNumber + " ) located in " + building.toString();
		}
	}

	// setters
	public void setBuilding(Building newBuilding) {
		building = newBuilding;
	}

	public void setRoomNumber(String newRoomNumber) {
		roomNumber = newRoomNumber;
	}

	// Constructors:
	public Room(String cRoomNumber, Building cBuilding) {
		setRoomNumber(cRoomNumber);
		setBuilding(cBuilding);
	}

	public Room(String cRoomNumber) {
		setRoomNumber(cRoomNumber);
	}

	/* (non-Javadoc)
	 * @see edu.wright.cs.raiderplanner.model.ModelEntity#open
	 * (edu.wright.cs.raiderplanner.controller.MenuController.Window)
	 */
	@Override
	public void open(Window current) {
		// TODO Auto-generated method stub
	}
}
