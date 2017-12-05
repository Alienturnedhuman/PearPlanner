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
import java.util.ArrayList;

import edu.wright.cs.raiderplanner.controller.MenuController;

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class ModelEntity implements Serializable
{
    protected String name = "";
    protected MultilineString details = null;
    protected ArrayList<Note> notes;


    // getters
    public String getName()
    {
        return name;
    }

    public MultilineString getDetails()
    {
        return details;
    }

    public void setName(String newName)
    {
        name = newName;
    }

    public void setDetails(String newDetails)
    {
        details = new MultilineString(newDetails);
    }

    public void setDetails(String[] newDetails)
    {
        details = new MultilineString(newDetails);
    }

    public void setDetails(ArrayList<String> newDetails)
    {
        details = new MultilineString(newDetails.toArray(new String[newDetails.size()]));
    }

    public void setDetails(MultilineString newDetails)
    {
        details = newDetails;
    }


    public void addProperties(String aName, MultilineString aDetails)
    {
        setName(aName);
        setDetails(aDetails.clone());
    }

    public void addProperties(String aName, String aDetails)
    {
        setName(aName);
        setDetails(aDetails);
    }

    /**
     * Open the appropriate UI window for this class
     * To be overridden by children.
     */
    public void open(MenuController.Window current)
    {
    }

    public ModelEntity()
    {
        this("");
    }

    public ModelEntity(String cName)
    {
        this(cName, "");
    }

    public ModelEntity(String cName, String cDetails)
    {
        this(cName, cDetails.split("\n"));
    }

    public ModelEntity(String cName, String[] cDetails)
    {
        setName(cName);
        setDetails(cDetails);
        notes = new ArrayList<>();
    }

	public ModelEntity(String cName, String[] cDetails, ArrayList<Note> cNotes){
	this(cName, cDetails);
		notes = new ArrayList<Note>(cNotes);
	}
}
