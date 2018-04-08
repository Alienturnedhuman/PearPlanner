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
public class Building extends VersionControlEntity {
	// private Data
	private String code = null;
	private double latitude;
	private double longitude;

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Building) {
			Building castedVce = (Building) receivedVce;
			if (castedVce.getCode() != null) {
				this.code = castedVce.getCode();
			}
			this.latitude = castedVce.getLatitude();
			this.longitude = castedVce.getLongitude();
		}

		super.replace(receivedVce);
	}

	/**
	 * Getter for Name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for Code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Getter for latitude.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Getter for longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Setter for Name.
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Setter for Code.
	 */
	public void setCode(String newCode) {
		code = newCode;
	}

	/**
	 * Setter for latitude.
	 */
	public void setLatitude(double newLatitude) {
		latitude = newLatitude;
	}

	/**
	 * Setter for longitude.
	 */
	public void setLongitude(double newLongitude) {
		longitude = newLongitude;
	}

	/**
	 * Class Constructor.
	 * @param ccode : given value is of type String
	 * @param clatitude  : given value is of type double
	 * @param clongitude  : given value is of type double
	 */
	public Building(String ccode, double clatitude, double clongitude) {
		code = ccode;
		latitude = clatitude;
		longitude = clongitude;
	}

	@Override
	public String toString() {
		return code + " " + name + " ( " + Double.toString(latitude)
				+ " , " + Double.toString(longitude) + " )";
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

