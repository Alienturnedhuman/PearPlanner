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
import edu.wright.cs.raiderplanner.controller.MenuController.Window;

import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class QuantityType extends ModelEntity {
	private static ArrayList<QuantityType> quantityDatabase = new ArrayList<>();

	/**
	 * gets a list of names inside the database.
	 * @return array of names
	 */
	public static String[] listOfNames() {
		String[] rr = new String[quantityDatabase.size()];
		int ii = quantityDatabase.size();
		int jj = -1;
		while (++jj < ii) {
			rr[jj] = quantityDatabase.get(jj).getName();
		}
		return rr;
	}

	/**
	 * gets an array of quantityTypes.
	 * @return array of quantityTypes
	 */
	public static QuantityType[] listOfQuantityTypes() {
		QuantityType[] rr = new QuantityType[quantityDatabase.size()];
		int jj = -1;
		int ii = quantityDatabase.size();
		while (++jj < ii) {
			rr[jj] = quantityDatabase.get(jj);
		}
		return rr;
	}

	/**
	 * gets the quantityType.
	 * @param tt string of the quantity
	 * @return the quantity type
	 */
	public static QuantityType get(String tt) {
		int jj = -1;
		int ii = quantityDatabase.size();
		while (++jj < ii) {
			if (quantityDatabase.get(jj).equals(tt)) {
				return quantityDatabase.get(jj);
			}
		}
		return DEFAULT;
	}

	/**
	 * checks if the quantityType exists.
	 * @param qt quantityType
	 * @return true or false if it exists
	 */
	public static boolean exists(QuantityType qt) {
		int jj = -1;
		int ii = quantityDatabase.size();
		while (++jj < ii) {
			if (quantityDatabase.get(jj).equals(qt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks to see if the string exists in the database.
	 * @param tt name of the quantity
	 * @return  true or false depending on if it is in database
	 */
	public static boolean exists(String tt) {
		int jj = -1;
		int ii = quantityDatabase.size();
		while (++jj < ii) {
			if (quantityDatabase.get(jj).equals(tt)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create a new QuantityType.
	 *
	 * @param name	Name of the quantity.
	 * @param details Details of the quantity.
	 * @return quantityType
	 */
	public static QuantityType create(String name, String details) {
		QuantityType type = new QuantityType(name, details);
		if (MainController.getSpc() != null) {
			MainController.getSpc().addQuantityType(type);
		}
		return type;
	}

	/**
	 * Create a new QuantityType.
	 *
	 * @param name Name of the quantity.
	 * @return quantityType
	 */
	public static QuantityType create(String name) {
		QuantityType type = new QuantityType(name);
		if (MainController.getSpc() != null) {
			MainController.getSpc().addQuantityType(type);
		}
		return type;
	}

	/**
	 * Create a new QuantityType from an existing one.
	 *
	 * @param type QuantityType object
	 */
	public static void create(QuantityType type) {
		if (!QuantityType.quantityDatabase.contains(type)) {
			QuantityType.quantityDatabase.add(type);
			if (MainController.getSpc() != null) {
				MainController.getSpc().addQuantityType(type);
			}
		}
	}

	/**
	 * A toString method used in TableView.
	 * @return name
	 */
	public String toString() {
		return this.name;
	}

	// a temporary way to populate the array until we later replace from reading a set up file
	static {
		/**
		 * Class to populate the quantityType until it is better implemented.
		 * @author Eric Sweet
		 *
		 */
		class Pair {
			public String one;
			public String two;

			/**
			 * Constructor of the pair.
			 * @param name name of the type
			 * @param details detail of the type
			 */
			Pair(String name, String details) {
				one = name;
				two = details;
			}
		}

		Pair[] staticTypes = {
				new Pair("Other", "Other"),
				new Pair("Hours", "Work in hours"),
				new Pair("Books read", "Read this number of books"),
				new Pair("Videos watched", "Watched this number of videos"),
				new Pair("thousand words written", "Number of thousand words written"),
				new Pair("questions answered", "Number of questions answered")
		};
		int jj = -1;
		int ii = staticTypes.length;
		while (++jj < ii) {
			QuantityType type = new QuantityType(staticTypes[jj].one, staticTypes[jj].two);
		}
	}

	/**
	 * adds the quantity to the database if it does not exist.
	 * @param name name of the quantity
	 */
	private QuantityType(String name) {
		super(name);
		if (!exists(this)) {
			quantityDatabase.add(this);
		}
	}

	/**
	 * adds the quantity to database if it does not exist.
	 * @param name name of quantity
	 * @param details details of quantity
	 */
	private QuantityType(String name, String details) {
		super(name, details);
		if (!exists(this)) {
			quantityDatabase.add(this);
		}
	}

	/**
	 * Test to see if the object name is equal to type.
	 * @return if they are equal
	 */
	@Override
	public boolean equals(Object obj) {
		QuantityType that = (QuantityType) obj;
		try {
			return that.getName().equals(getName());
		} catch (NullPointerException e) {
			System.out.println("QuantityType name was null");
			return false;
		}
	}

	/**
	 * Sees if the name is equal to string c.
	 * @param name name to check against
	 * @return returns if they are equal
	 */
	public boolean equals(String name) {
		return getName().equals(name);
	}

	/**
	 * Only used to Override has code.
	 */
	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	public static QuantityType DEFAULT = quantityDatabase.get(0);

	/* (non-Javadoc)
	 * @see edu.wright.cs.raiderplanner.model.ModelEntity#open
	 * (edu.wright.cs.raiderplanner.controller.MenuController.Window)
	 */
	@Override
	public void open(Window current) {
		// TODO Auto-generated method stub
	}
}
