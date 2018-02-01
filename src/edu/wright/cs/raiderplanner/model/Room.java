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


/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class Room extends VersionControlEntity {
	//the value for serialVersionUID is from the value used in Activity.java
	static final long serialVersionUID = -486727251420646703L;
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
	 * @return building.
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Default method to return the room number.
	 * @return roomNumber.
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Method to output the name and room number of a person.
	 * If the room does not have a building number, the
	 * output message displays "name - Unknown Building".
	 * If the building is not null, the output message displays
	 * "name ( room ) located in building" where room represents
	 * the room number and building represents the building object.
	 * @return String.
	 */
	public String getLocation() {
		if (building == null) {
			return name + " - Unknown Building";
		} else {
			return name + "( " + roomNumber + " ) located in " + building.toString();
		}
	}

	/**
	 * Overrides the toString method for the Room object.
	 * If the building is null, the output is "name - Unknown Building",
	 * otherwise the output is formatted as "name ( room ) located in building"
	 * where room represents the room number and building is the name of the
	 * building object.
	 * @return String
	 */
	@Override
	public String toString() {
		if (building == null) {
			return name + " - Unknown Building";
		} else {
			return name + "( " + roomNumber + " ) located in " + building.toString();
		}
	}

	// setters
	/**
	 * Sets a value for a building.
	 * @param newBuilding the new building being assigned a value.
	 */
	public void setBuilding(Building newBuilding) {
		building = newBuilding;
	}

	/**
	 * Sets the value for a roomNumber.
	 * @param newRoomNumber a String value being assigned to the roomNumber.
	 */
	public void setRoomNumber(String newRoomNumber) {
		roomNumber = newRoomNumber;
	}

	// Constructors:
	/**
	 * Constructor to create a room with a building and room number.
	 * @param constructRoomNumber String value of the number of the room.
	 * @param constructBuilding building in which the room is located.
	 */
	public Room(String constructRoomNumber, Building constructBuilding) {
		setRoomNumber(constructRoomNumber);
		setBuilding(constructBuilding);
	}

	/**
	 * Constructor that creates a room with a room number, but without
	 * a building. This allows the building to be added at a later time.
	 * Could be useful if a structure is being built, but has not been
	 * named, or is being renamed.
	 * @param constructRoomNumber String value of the number of the room.
	 */
	public Room(String constructRoomNumber) {
		setRoomNumber(constructRoomNumber);
	}
}
