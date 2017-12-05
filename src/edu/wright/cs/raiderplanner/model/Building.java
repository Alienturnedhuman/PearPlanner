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
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Building extends VersionControlEntity {
	// private Data
	private String code = null;
	private double latitude;
	private double longitude;

	@Override
	protected void replace(VersionControlEntity receivedVCE) {
		if (receivedVCE instanceof Building) {
			Building castedVCE = (Building) receivedVCE;
			if (castedVCE.getCode() != null) {
				this.code = castedVCE.getCode();
			}
			this.latitude = castedVCE.getLatitude();
			this.longitude = castedVCE.getLongitude();
		}

		super.replace(receivedVCE);
	}

	// getters
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	// setters
	public void setName(String newName) {
		name = newName;
	}

	public void setCode(String newCode) {
		code = newCode;
	}

	public void setLatitude(double newLatitude) {
		latitude = newLatitude;
	}

	public void setLongitude(double newLongitude) {
		longitude = newLongitude;
	}

	// constructor
	public Building(String cCode, double cLatitude, double cLongitude) {
		code = cCode;
		latitude = cLatitude;
		longitude = cLongitude;
	}

	@Override
	public String toString() {
		return code + " " + name + " ( " + Double.toString(latitude)
				+ " , " + Double.toString(longitude) + " )";
	}
}

