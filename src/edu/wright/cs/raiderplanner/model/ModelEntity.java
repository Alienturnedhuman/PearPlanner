/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar
 * Copyright (C) 2018 - Roberto C. Sánchez
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

import edu.wright.cs.raiderplanner.controller.MenuController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public abstract class ModelEntity implements Serializable {
	protected String name = "";
	protected MultilineString details = null;
	protected ArrayList<Note> notes;

	/**
	 * Default constructor.
	 */
	public ModelEntity() {
		this("");
	}

	/**
	 * Construct a ModelEntity with the given name and no details or notes.
	 *
	 * @param cname The name of the entity
	 */
	public ModelEntity(String cname) {
		this(cname, "");
	}

	/**
	 * Construct a ModelEntity with the given name and white space-separated
	 * list of details and no notes.
	 *
	 * @param cname The name of the entity
	 * @param cdetails The entity details (white space-separated list)
	 */
	public ModelEntity(String cname, String cdetails) {
		this(cname, cdetails.split("\n"));
	}

	/**
	 * Construct a ModelEntity with the given name and array of details and no
	 * notes.
	 *
	 * @param cname The name of the entity
	 * @param cdetails The entity details (array)
	 */
	public ModelEntity(String cname, String[] cdetails) {
		setName(cname);
		setDetails(cdetails);
		notes = new ArrayList<>();
	}

	/**
	 * Construct a ModelEntity with the given name and white space-separated
	 * list of details and list of notes.
	 *
	 * @param cname The name of the entity
	 * @param cdetails The entity details (array)
	 * @param cnotes The entity notes
	 */
	public ModelEntity(String cname, String[] cdetails, List<Note> cnotes) {
		this(cname, cdetails);
		notes = new ArrayList<>(cnotes);
	}

	/**
	 * Returns the name of the entity.
	 *
	 * @return The name of the entity
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the details of the entity.
	 *
	 * @return The details of the entity
	 */
	public MultilineString getDetails() {
		return details;
	}

	/**
	 * Set a new name for the entity.
	 *
	 * @param newName The new name for the entity
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Set new details for the entity.
	 *
	 * @param newDetails The new details for the entity
	 */
	public void setDetails(String newDetails) {
		details = new MultilineString(newDetails);
	}

	/**
	 * Set new details for the entity.
	 *
	 * @param newDetails The new details for the entity
	 */
	public void setDetails(String[] newDetails) {
		details = new MultilineString(newDetails);
	}

	/**
	 * Set new details for the entity.
	 *
	 * @param newDetails The new details for the entity
	 */
	public void setDetails(List<String> newDetails) {
		details = new MultilineString(newDetails.toArray(new String[newDetails.size()]));
	}

	/**
	 * Set new details for the entity.
	 *
	 * @param newDetails The new details for the entity
	 */
	public void setDetails(MultilineString newDetails) {
		details = newDetails;
	}

	/**
	 * Set new name and details for the entity.
	 *
	 * @param aname The new entity name
	 * @param adetails The new details for the entity
	 */
	public void addProperties(String aname, MultilineString adetails) {
		setName(aname);
		setDetails(adetails.clone());
	}

	/**
	 * Set new name and details for the entity.
	 *
	 * @param aname The new entity name
	 * @param adetails The new details for the entity
	 */
	public void addProperties(String aname, String adetails) {
		setName(aname);
		setDetails(adetails);
	}

	/**
	 * Open the appropriate UI window for this class
	 * To be overridden by children.
	 * @return
	 */
	public abstract void open(MenuController.Window current);

}
