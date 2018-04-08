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
	 * Enumerated values for approval statuses.
	 * @author Hayden
	 *
	 */
	public enum ApprovalStatus {
		PENDING, APPROVED, DECLINED
	}

	// public methods

	// getters
	/**
	 * Returns the new deadline for the extension.
	 * @return the new deadline for the extension.
	 */
	public Deadline getNewDeadline() {
		// initial set up code below - check if this needs updating
		return newDeadline;
	}

	/**
	 * Returns the current extension circumstances as a MultilineString.
	 * @return current circumstances of the extension.
	 */
	public MultilineString getCircumstances() {
		// initial set up code below - check if this needs updating
		return circumstances;
	}

	/**
	 * Returns the current status of approval for the extension.
	 * @return the current status of approval for the extension.
	 */
	public ApprovalStatus getApprovalStatus() {
		// initial set up code below - check if this needs updating
		return approvalStatus;
	}

	// setters
	/**
	 * Sets the current circumstances of the extension.
	 * @param newCircumstances
	 * 				The new circumstances to set.
	 */
	public void setCircumstances(MultilineString newCircumstances) {
		// initial set up code below - check if this needs updating
		circumstances = newCircumstances;
	}

	/**
	 * Sets the new deadline for the extension.
	 * @param newNewDeadline
	 * 				The new deadline for the extension.
	 */
	public void setNewDeadline(Deadline newNewDeadline) {
		// initial set up code below - check if this needs updating
		newDeadline = newNewDeadline;
	}

	/**
	 * Sets the current extension approval status.
	 * @param newApprovalStatus
	 * 				The new extension approval status
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
