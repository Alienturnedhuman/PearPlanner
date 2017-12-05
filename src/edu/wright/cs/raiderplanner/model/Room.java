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
	// private data
	private Building building = null;
	private String roomNumber;

	@Override
	protected void replace(VersionControlEntity receivedVCE) {
		if (receivedVCE instanceof Room) {
			Room castedVCE = (Room) receivedVCE;
			if (castedVCE.getBuilding() != null) {
				this.building = castedVCE.getBuilding();
			}
			this.roomNumber = castedVCE.getRoomNumber();
		}
		super.replace(receivedVCE);
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
}
