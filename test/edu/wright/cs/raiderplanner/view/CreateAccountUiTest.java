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

package edu.wright.cs.raiderplanner.view;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;

/**
 * Unit test that exercises the GUI window for creating new accounts.
 *
 * @author Team BRONZE
 */
public class CreateAccountUiTest extends FxBase {
	/**
	 * WIP: This test case should enter sample data into the CreateAccountUi dialog and
	 * 		submit it. Currently, this test causes the build to crash, so it is
	 * 		disabled. Once resolved, change the tag from @Disabled to @Test.
	 */
	@Disabled
	public void testTextField() {
		final TextField t1 = find("#salutation");
		final TextField t2 = find("#fullName");
		final TextField t3 = find("#accountNo");
		final TextField t4 = find("#email");
		final Button b1 = find("#submit");

		clickOn(t1).write("Mr");
		clickOn(t2).write("Andrei Odintsov");
		clickOn(t3).write("100125464");
		clickOn(t4).write("a1covker@gmail.com");

		verifyThat(b1, isEnabled());

	}

	/**
	 * WIP: This test case should verify that the regular expressions for each of the input
	 * 		fields works correctly. If so, the form should not allow the user to submit. In
	 * 		other words, the submit button should be disabled. Currently, this test causes
	 * 		the build to crash. When resolved, replace the @Disabled tag with @Test.
	 */
	@Disabled
	public void testTextFieldRegex() {
		final TextField t1 = find("#salutation");
		final TextField t2 = find("#fullName");
		final TextField t3 = find("#accountNo");
		final TextField t4 = find("#email");
		final Button b1 = find("#submit");

		clickOn(t1).write("Mr");
		clickOn(t2).write("Andrei Odintsov");
		clickOn(t3).write("100125464");
		clickOn(t4).write("somethingnotemail");

		verifyThat(b1, isDisabled());

	}

}