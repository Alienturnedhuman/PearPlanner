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
import java.util.Arrays;

/**
 * Created by bendickson on 4/27/17.
 */
public class MultilineString implements Serializable, Cloneable {
	// private Data;
	private ArrayList<String> lines;

	// public methods

	/**
	 * gets the multi-string clone from the array.
	 */
	public MultilineString clone() {
		return new MultilineString(this.getAsArray());
	}

	/**
	 * Returns the number of lines in this MultilineString.
	 *
	 * @return number of lines
	 */
	public int getLines() {
		return lines.size();
	}

	/**
	 * Gets the arrayList.
	 * @return arrayList of type string
	 */
	public ArrayList<String> getAsArrayList() {
		return lines;
	}

	/**
	 * get the lines as an array.
	 * @return an array of lines
	 */
	public String[] getAsArray() {
		String[] line = new String [(lines.size())];
		line = lines.toArray(line);
		return line;
	}

	/**
	 * get the arrayList as a string.
	 * @return the string joiner
	 */
	public String getAsString() {
		return String.join("\n", getAsArray());
	}

	//Constructors

	/**
	 * Constructor with no arguments.
	 */
	public MultilineString() {
		lines = new ArrayList<>();
	}

	/**
	 * Constructor with a String.
	 * @param string string to construct the multilineString
	 */
	public MultilineString(String string) {
		lines = new ArrayList<>(Arrays.asList(string.split("\n")));
	}

	/**
	 * Constructor with an Array of type String.
	 * @param string array of strings to construct the multilineString
	 */
	public MultilineString(String[] string) {
		lines = new ArrayList<>(Arrays.asList(string));
	}
}
