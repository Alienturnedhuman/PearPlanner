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
public class Extension extends VersionControlEntity {
	// private data
	private Deadline newDeadline;
	private MultilineString circumstances;
	private ApprovalStatus approvalStatus;

	@Override
	protected void replace(VersionControlEntity receivedVce) {
		if (receivedVce instanceof Extension) {
			Extension castedVce = (Extension) receivedVce;
			this.newDeadline = castedVce.getNewDeadline();
			this.circumstances = castedVce.getCircumstances();
			this.approvalStatus = castedVce.getApprovalStatus();
		}

		super.replace(receivedVce);
	}

	/**
	 * Enum for Approval Status.
	 * @author N/A
	 *
	 */
	public enum ApprovalStatus {
		PENDING, APPROVED, DECLINED
	}

	// public methods

	/**
	 * Getter for Deadline.
	 * @return newDeadline value
	 */
	public Deadline getNewDeadline() {
		// initial set up code below - check if this needs updating
		return newDeadline;
	}

	/**
	 * Getter for Circumstances MultilineString.
	 * @return MultilineString value
	 */
	public MultilineString getCircumstances() {
		// initial set up code below - check if this needs updating
		return circumstances;
	}

	/**
	 * Getter for Approval Status.
	 * @return ApprovalStatus value
	 */
	public ApprovalStatus getApprovalStatus() {
		// initial set up code below - check if this needs updating
		return approvalStatus;
	}

	/**
	 * Setter for the Circumstances.
	 * @param newCircumstances : given values is of type MultilineString
	 */
	public void setCircumstances(MultilineString newCircumstances) {
		// initial set up code below - check if this needs updating
		circumstances = newCircumstances;
	}

	/**
	 * Setter for the new Deadline.
	 * @param newNewDeadline : given values is of type Deadline
	 */
	public void setNewDeadline(Deadline newNewDeadline) {
		// initial set up code below - check if this needs updating
		newDeadline = newNewDeadline;
	}

	/**
	 * Set's the Approval status.
	 * @param newApprovalStatus : given value is of type ApprovalStatus
	 */
	public void setApprovalStatus(ApprovalStatus newApprovalStatus) {
		// initial set up code below - check if this needs updating
		approvalStatus = newApprovalStatus;
	}

	/* (non-Javadoc)
	 * @see edu.wright.cs.raiderplanner.model.ModelEntity#open
	 * (edu.wright.cs.raiderplanner.controller.MenuController.Window)
	 */
	@Override
	public void open(Window current) {
		// TODO Auto-generated method stub
	}

	// constructor

}
