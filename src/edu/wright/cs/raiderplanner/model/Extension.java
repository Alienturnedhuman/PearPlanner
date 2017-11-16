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
public class Extension extends VersionControlEntity {
	// private data
	private Deadline newDeadline;
	private MultilineString circumstances;
	private ApprovalStatus approvalStatus;

	@Override
	protected void replace(VersionControlEntity receivedVCE) {
		if (receivedVCE instanceof Extension) {
			Extension castedVCE = (Extension) receivedVCE;
			this.newDeadline = castedVCE.getNewDeadline();
			this.circumstances = castedVCE.getCircumstances();
			this.approvalStatus = castedVCE.getApprovalStatus();
		}

		super.replace(receivedVCE);
	}

	public enum ApprovalStatus {
		PENDING, APPROVED, DECLINED
	}

	// public methods

	// getters
	public Deadline getNewDeadline() {
		// initial set up code below - check if this needs updating
		return newDeadline;
	}

	public MultilineString getCircumstances() {
		// initial set up code below - check if this needs updating
		return circumstances;
	}

	public ApprovalStatus getApprovalStatus() {
		// initial set up code below - check if this needs updating
		return approvalStatus;
	}

	// setters
	public void setCircumstances(MultilineString newCircumstances) {
		// initial set up code below - check if this needs updating
		circumstances = newCircumstances;
	}

	public void setNewDeadline(Deadline newNewDeadline) {
		// initial set up code below - check if this needs updating
		newDeadline = newNewDeadline;
	}

	public void setApprovalStatus(ApprovalStatus newApprovalStatus) {
		// initial set up code below - check if this needs updating
		approvalStatus = newApprovalStatus;
	}

	// constructor

}
