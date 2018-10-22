/*
 * Copyright (C) 2018 - Logan Krause, Corbin McGuire, Daniel Howard
 *
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

package edu.wright.cs.raiderplanner.util;

/**
 * Custom exception class that displays more useful error messages to users and programmers.
 * @author LoganKrause
 * @author CorbinMcGuire
 * @author DanielHoward
 */
@SuppressWarnings("serial")
public class RaiderException extends Exception {
	/**
	 * Parameterless constructor.
	 */
	public RaiderException() {
		getMessage();
	}

	/**
	 * Message that displays the custom message.
	 * @param message Helping message
	 */
	public RaiderException(String message) {
		super(message);
		message = "Error, Please try again or seek help.";
	}

	/**
	 * Returns the message to the user.
	 * @see java.lang.Throwable#getMessage()
	 * Able to assign specific message
	 */
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	/**
	 * Displays Error message and exception.
	 * @param message Receives the message passed by the thrown exception.
	 * @param throwable Thrown exception.
	 */
	public RaiderException(String message, Throwable throwable) {
		super(message, throwable);
	}
}