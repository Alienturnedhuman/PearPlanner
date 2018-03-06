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

import edu.wright.cs.raiderplanner.controller.MainController;

import java.util.HashMap;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public abstract class VersionControlEntity extends ModelEntity {
	protected int version;
	protected String uid;
	protected boolean sealed;
	private static HashMap<String, VersionControlEntity> library = new HashMap<>();
	protected boolean importer = false; // used for VCEs created during XML import
	// private methods

	/**
	 * This method overwrites the data in the received object with that received.
	 * This method will need to overrode in every class that extends it
	 *
	 * @param receivedVce VersionControlEntity to override
	 */
	protected void replace(VersionControlEntity receivedVce) {
		name = receivedVce.getName();
		details = receivedVce.getDetails();
		version = receivedVce.getVersion();
		// super.replace(receivedVce);
	}

	// public methods

	/**
	 * Update this VCE with a given one.
	 *
	 * @param receivedVce received VCE for updating the current one.
	 * @return whether updated successfully.
	 */
	public boolean update(VersionControlEntity receivedVce) {
		if (uid.equals(receivedVce.getUid()) && version < receivedVce.getVersion()) {
			replace(receivedVce);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Find the given VCE in the library and then update it.
	 *
	 * @param receivedVce a VCE to be looked for and updated.
	 * @return whether found and updated successfully.
	 */
	public static boolean findAndUpdate(VersionControlEntity receivedVce) {
		String uid = receivedVce.getUid();
		if (inLibrary(uid)) {
			library.get(uid).update(receivedVce);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Creates a importer if not sealed.
	 * @return boolean
	 */
	public boolean makeImporter() {
		if (!sealed) {
			importer = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is an importer.
	 * @return boolean
	 */
	public boolean isImporter() {
		return importer;
	}

	/**
	 * Add this VCE to the library.
	 *
	 * @return whether added successfully.
	 */
	public boolean addToLibrary() {
		if (importer) {
			if (inLibrary(uid)) {
				return false;
			} else {
				importer = false;
				sealed = true;
				library.put(uid, this);
				MainController.getSpc().getPlanner().addToVersionControlLibrary(this);
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Get a VCE from the library by it's UID.
	 *
	 * @param uid UID to be looked for.
	 * @return a valid VCE if found, null otherwise.
	 */
	public static VersionControlEntity get(String uid) {
		if (inLibrary(uid)) {
			return library.get(uid);
		} else {
			return null;
		}
	}

	/**
	 * Check whether a VCE with the given UID exists in the library.
	 *
	 * @param uid UID to be checked for.
	 * @return true if found, false otherwise.
	 */
	public static boolean inLibrary(String uid) {
		return library.containsKey(uid);
	}

	// getters

	/**
	 * gets the version of the entity.
	 * @return version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * gets the UID of user.
	 * @return boolean
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Returns the VCE library.
	 *
	 * @return HashMap containing all VCEs.
	 */
	public static HashMap<String, VersionControlEntity> getLibrary() {
		return library;
	}

	/**
	 * Returns a summary of the VCEs in the library.
	 *
	 * @return String
	 */
	public static String libraryReport() {
		return "Total Entries: " + library.size();
	}

	/**
	 * Set a new UID and version for this VCE.
	 *
	 * @param newUid	 new UID
	 * @param newVersion new version
	 * @return whether changed successfully.
	 */
	public boolean setUid(String newUid, int newVersion) {
//		setUid(newUID);
		if (importer) {
			setUid(newUid);
			version = newVersion;
			return true;
		} else if (sealed || library.containsKey(newUid)) {
			return false;
		} else {
			setUid(newUid);
			version = newVersion;
			return true;
		}
	}

	/**
	 * Set a new UID for this VCE.
	 *
	 * @param newUid new UID
	 * @return whether changed successfully.
	 */
	public boolean setUid(String newUid) {
		if (importer) {
			uid = newUid;
			return true;
		} else if (sealed || library.containsKey(newUid)) {
			return false;
		} else {
			uid = newUid;
			library.put(newUid, this);
			MainController.getSpc().getPlanner().addToVersionControlLibrary(this);
			return true;
		}
	}

	/**
	 * Called once the program is loaded, adds this VCE to the library if possible.
	 */
	public void reload() {
		if (!inLibrary(this.uid) && !importer && sealed) {
			library.put(this.uid, this);
		}
	}

	// Constructors

	/**
	 * Constructor with a boolean if it should stay sealed.
	 * @param leaveUnsealed if it should be sealed
	 */
	public VersionControlEntity(boolean leaveUnsealed) {
		super();
		sealed = !leaveUnsealed;
	}

	/**
	 * Constructor with no String UID.
	 */
	public VersionControlEntity() {
		super();
		sealed = false;
	}

	/**
	 * Constructor with a String UID.
	 * @param uid UID of user
	 */
	public VersionControlEntity(String uid) {
		super();
		sealed = setUid(uid);
	}

}
