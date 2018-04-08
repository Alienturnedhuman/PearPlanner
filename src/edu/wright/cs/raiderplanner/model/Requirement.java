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
import edu.wright.cs.raiderplanner.controller.MenuController;
import edu.wright.cs.raiderplanner.view.UiManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class Requirement extends ModelEntity {
	protected boolean checkedCompleted;
	protected double estimatedTimeInHours;
	protected ArrayList<Activity> activityLog = new ArrayList<>();
	protected int initialQuantity;
	protected int remainingQuantity;
	protected QuantityType quantityType;

	// public methods

	/**
	 * checks if it is complete.
	 * @return boolean true or false
	 */
	public boolean isComplete() {
		return this.checkedCompleted;
	}

	/**
	 * Returns the QuantityType of this Requirement.
	 *
	 * @return.
	 */
	public QuantityType getQuantityType() {
		return this.quantityType;
	}

	/**
	 * Returns the estimated time of this Requirement (in hours).
	 *
	 * @return.
	 */
	public double getEstimatedTimeInHours() {
		return estimatedTimeInHours;
	}

	/**
	 * Returns the initial quantity of this Requirement.
	 *
	 * @return.
	 */
	public int getInitialQuantity() {
		return initialQuantity;
	}

	/**
	 * Returns the remaining quantity of this Requirement.
	 */
	public int getRemainingQuantity() {
		return remainingQuantity;
	}

	/**
	 * Returns an array of ActivityEvents that are associated with this Requirement.
	 *
	 * @return.
	 */
	public Activity[] getActivityLog() {
		return this.activityLog.toArray(new Activity[this.activityLog.size()]);
	}

	/**
	 * Returns a double value representing the progress of this Requirement.
	 *
	 * @return value between 0.0 and 0.1
	 */
	public double requirementProgress() {
		return (double) (this.initialQuantity - this.remainingQuantity) / this.initialQuantity;
	}

	/**
	 * sets the estimated time in hours.
	 * @param estimatedTimeInHours estimated time
	 */
	public void setEstimatedTimeInHours(double estimatedTimeInHours) {
		this.estimatedTimeInHours = estimatedTimeInHours;
	}

	/**
	 * Change the initial quantity. This will update the progress of this
	 * Requirement to reflect the change.
	 *
	 * @param initialQuantity : given value is int type
	 */
	public void setInitialQuantity(int initialQuantity) {
		if (this.initialQuantity == this.remainingQuantity) {
			this.initialQuantity = this.remainingQuantity = initialQuantity;
		} else {
			this.initialQuantity = initialQuantity;
			this.update();
		}
	}

	/**
	 * This method set the quantity type.
	 * @param quantityType : given value is String type
	 */
	public void setQuantityType(String quantityType) {
		this.quantityType = QuantityType.get(quantityType);
	}

	/**
	 * Add an Activity to the current Requirement and update the progress
	 * of this Requirement accordingly.
	 *
	 * @param activity Activity to be added.
	 */
	public void addActivity(Activity activity) {
		this.activityLog.add(activity);
		this.remainingQuantity -= activity.getActivityQuantity();
		if (remainingQuantity <= 0) {
			this.remainingQuantity = 0;
			this.checkedCompleted = true;
		}
	}

	/**
	 * Update the current Requirement to reflect changes.
	 *
	 * @return whether any changes were made
	 */
	public boolean update() {


		this.remainingQuantity = this.initialQuantity;
		this.checkedCompleted = false;
		for (Activity activity : this.activityLog) {
			this.remainingQuantity -= activity.getActivityQuantity();
		}
		int tempQuantity = this.remainingQuantity;

		if (this.remainingQuantity <= 0) {
			this.remainingQuantity = 0;
			this.checkedCompleted = true;
		}

		return tempQuantity == this.remainingQuantity;
	}

	/**
	 * Returns the Name of the Requirement (used for JavaFX).
	 *
	 * @return Name of the task
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Only used to Override has code.
	 */
	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}

		Requirement that = (Requirement) object;

		if (checkedCompleted != that.checkedCompleted) {
			return false;
		}
		if (Double.compare(that.estimatedTimeInHours, estimatedTimeInHours) != 0) {
			return false;
		}
		if (initialQuantity != that.initialQuantity) {
			return false;
		}
		if (remainingQuantity != that.remainingQuantity) {
			return false;
		}
		if (!activityLog.equals(that.activityLog)) {
			return false;
		}
		return quantityType.equals(that.quantityType);
	}

	@Override
	public void open(MenuController.Window current) {
		try {
			MainController.ui.requirementDetails(this);
		} catch (IOException e) {
			UiManager.reportError("Unable to open view file");
		}
	}

	/**
	 * Class Constructors.
	 * @param name : given value type is of String
	 * @param details : given value type is of String
	 * @param time : given value type is of double
	 * @param quantity : given value type is of int
	 * @param type : given value type is of String
	 */
	public Requirement(String name, String details, double time, int quantity, String type) {
		super(name, details);
		this.estimatedTimeInHours = time;
		this.initialQuantity = this.remainingQuantity = quantity;
		this.quantityType = QuantityType.get(type);
	}
}
