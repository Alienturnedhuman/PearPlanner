/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Ian Smith
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
	static final long serialVersionUID = 1L;
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
	/**
	 * Default method to return the building this room resides in.
	 * @return this room's building.
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Default method to return the room number.
	 * @return this room's number.
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Method to output the name and room number of a person. If the room does
	 * not have a building number, the output message displays &quot;name
	 * &minus; Unknown Building&quot;. If the building is not null, the output
	 * message displays &quot;name ( room ) located in building&quot;, where
	 * {@code room} represents the room number and {@code building} represents
	 * the building object.
	 *
	 * @return the location of this room.
	 */
	public String getLocation() {
		if (building == null) {
			return name + " - Unknown Building";
		} else {
			return name + " ( " + roomNumber + " ) located in " + building.toString();
		}
	}

	/**
	 * Delegates to {@code getLocation()}.
	 */
	@Override
	public String toString() {
		return this.getLocation();
	}

	// setters
	/**
	 * Sets a value for a building.
	 *
	 * @param newBuilding set this room's building to the specified value
	 */
	public void setBuilding(Building newBuilding) {
		building = newBuilding;
	}

	/**
	 * Sets the value for a roomNumber.
	 *
	 * @param newRoomNumber set this room's number to the specified value
	 */
	public void setRoomNumber(String newRoomNumber) {
		roomNumber = newRoomNumber;
	}

	// Constructors:
	/**
	 * Constructor to create a room with a building and room number.
	 *
	 * @param constructRoomNumber value of the number of the room
	 * @param constructBuilding building in which the room is located
	 */
	public Room(String constructRoomNumber, Building constructBuilding) {
		setRoomNumber(constructRoomNumber);
		setBuilding(constructBuilding);
	}

	/**
	 * Constructor that creates a room with a room number, but without a
	 * building. This allows the building to be added at a later time. Could be
	 * useful if a structure is being built, but has not been named, or is being
	 * renamed.
	 *
	 * @param constructRoomNumber value of the number of the room
	 */
	public Room(String constructRoomNumber) {
		setRoomNumber(constructRoomNumber);
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
