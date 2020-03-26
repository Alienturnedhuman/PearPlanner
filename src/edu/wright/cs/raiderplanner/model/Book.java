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

import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17.
 */
public class Book /*extends Requirement*/ {
	// private data
	private ArrayList<String> chapters;

	// public methods

	/**
	 * gets Chapters.
	 * @return chapters
	 */
	public ArrayList<String> getChapters() {
		// initial set up code below - check if this needs updating
		return chapters;
	}

	/**
	 * sets Chapters.
	 * @param newChapters the new chapters
	 */
	public void setChapters(ArrayList<String> newChapters) {
		// initial set up code below - check if this needs updating
		chapters = newChapters;
	}

	/**
	 * constructor.
	 * @param chapters the chapters
	 */
	public Book(ArrayList<String> chapters) {
		this.chapters = chapters;
	}
}
