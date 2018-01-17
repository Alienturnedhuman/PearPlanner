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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wright.cs.raiderplanner.model.MultilineString;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Created by bijan on 06/05/2017.
 */
public class MultilineStringTest {

	private MultilineString multilineStringEmpty;
	private MultilineString multilineStringNormal;
	private MultilineString multilineStringArray;

	/**
	 * This test case creates the various MultilineString objects that are necessary for
	 * 			the other tests in this suite.
	 * @throws Exception if any of the MultilineString objects cannot be created.
	 */
	@BeforeEach
	public void setUp() throws Exception {
		multilineStringEmpty = new MultilineString();

		String text = "Much did had call new drew that kept.\n"
				+ "Limits expect wonder law she. Now has you views woman noisy match money rooms.\n"
				+ "To up remark it eldest length oh passed. Off because yet mistake "
				+ "feeling has men.";
		multilineStringNormal = new MultilineString(text);

		String[] textArray = {"Much did had call new drew that kept.",
				"Limits expect wonder law she. Now has you views woman noisy match money rooms.",
				"To up remark it eldest length oh passed. Off because yet mistake "
				+ "feeling has men."};
		multilineStringArray = new MultilineString(textArray);
	}

	@Test
	public void getLines() throws Exception {
		// Checking an empty MultilineString
		assertEquals(0, multilineStringEmpty.getLines());

		// Checking a MultilineString with 3 lines
		assertEquals(3, multilineStringNormal.getLines());

		// Checking a MultilineString with 3 lines (Created by an array of Strings)
		assertEquals(3, multilineStringArray.getLines());
	}

	@Test
	public void getAsArrayList() throws Exception {
		// Checking an empty MultilineString
		ArrayList<String> empty = new ArrayList<>();
		assertEquals(empty, multilineStringEmpty.getAsArrayList());

		// Checking a MultilineString with 3 lines
		ArrayList<String> text = new ArrayList<>();
		text.add("Much did had call new drew that kept.");
		text.add("Limits expect wonder law she. Now has you views woman noisy match money rooms.");
		text.add("To up remark it eldest length oh passed. "
				+ "Off because yet mistake feeling has men.");
		for (int i = 0; i < multilineStringNormal.getLines(); i++) {
			assertEquals(text.get(i), multilineStringNormal.getAsArrayList().get(i));
		}

		// Checking a MultilineString with 3 lines (Created by an array of Strings)
		for (int i = 0; i < multilineStringArray.getLines(); i++) {
			assertEquals(text.get(i), multilineStringArray.getAsArrayList().get(i));
		}
	}

	@Test
	public void getAsArray() throws Exception {
		// Checking an empty MultilineString
		String[] emptyText = {};
		assertArrayEquals(emptyText, multilineStringEmpty.getAsArray());

		// Checking a MultilineString with 3 lines
		String[] textWithThreeLines = {"Much did had call new drew that kept.",
				"Limits expect wonder law she. Now has you views woman noisy match money rooms.",
				"To up remark it eldest length oh passed. Off because yet mistake "
				+ "feeling has men."};
		assertArrayEquals(textWithThreeLines, multilineStringNormal.getAsArray());


		// Checking a MultilineString with 3 lines (Created by an array of Strings)
		String[] textWithThreeLinesArray = {"Much did had call new drew that kept.",
				"Limits expect wonder law she. Now has you views woman noisy match money rooms.",
				"To up remark it eldest length oh passed. Off because yet mistake "
				+ "feeling has men."};
		assertArrayEquals(textWithThreeLinesArray, multilineStringArray.getAsArray());

	}

	@Test
	public void getAsString() throws Exception {
		// Checking an empty MultilineString
		String empty = "";
		assertEquals(empty, multilineStringEmpty.getAsString());

		// Checking a MultilineString with 3 lines
		String text = "Much did had call new drew that kept.\n"
				+ "Limits expect wonder law she. Now has you views "
				+ "woman noisy match money rooms.\n"
				+ "To up remark it eldest length oh passed. Off because yet mistake "
				+ "feeling has men.";
		assertEquals(text, multilineStringNormal.getAsString());

		// Checking a MultilineString with 3 lines (Created by an array of Strings)
		assertEquals(text, multilineStringArray.getAsString());
	}

	/**
	 * This test case should set all MultilineString objects to null after each run so
	 * 			as to avoid interference with future test runs.
	 * @throws Exception if any of the MultilineString objects cannot be accessed.
	 */
	@AfterEach
	public void tearDown() throws Exception {
		multilineStringEmpty = null;
		multilineStringNormal =  null;
		multilineStringArray = null;
	}

}
