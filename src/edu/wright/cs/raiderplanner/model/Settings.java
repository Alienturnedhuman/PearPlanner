/*
 * Copyright (C) 2018 - Clayton D. Terrill
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

package edu.wright.cs.raiderplanner.model;

import edu.wright.cs.raiderplanner.view.UiManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Class to load and save settings to a
 * config.properties file.
 * @author Clayton D. Terrill
 *
 */
public class Settings {

	// Object Classes
	Properties prop = new Properties();

	// Global Parameters with default values
	private boolean isAccountStartup = false;
	private String accountFilePath = "";

	/**
	 * Constructor loads the settings from
	 * the properties file.
	 */
	public Settings() {
		//createConfig();
		loadSettings();
	}

	/**
	 * Creates the config.properties file to
	 * store the setting's properties.
	 */
	public void createConfig() {
		OutputStream output = null;
		try {
			// Set output stream to config.properties
			output = new FileOutputStream("config.properties");
			// Insert default values into prop
			this.prop.setProperty("isAccountStartup", String.valueOf(this.isAccountStartup));
			this.prop.setProperty("filePath", this.accountFilePath);
			// Save config.properties to project root folder
			this.prop.store(output, null);
		} catch (IOException io) {
			UiManager.reportError("Error, unable to create config.properties.");
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					UiManager.reportError("Error, unable to close config.properties.");
				}
			}
		}
	}

	/**
	 * Loads the settings from the config.properties file
	 * located within the root folder. The class variables
	 * are assigned these values.
	 */
	public void loadSettings() {
		InputStream input = null;
		try {
			File file = new File("config.properties");
			if (!file.exists()) {
				createConfig();
			}
			// Set input stream to config.properties
			input = new FileInputStream("config.properties"); // Looks in root directory
			// Load the config.properties file
			this.prop.load(input);
			// Get the property value and assign it to variable
			this.isAccountStartup = Boolean.parseBoolean(prop.getProperty("isAccountStartup"));
			this.accountFilePath = prop.getProperty("filePath");
		} catch (IOException ex) {
			UiManager.reportError("Error, unable to load config.properties.");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					UiManager.reportError("Error, unable to close config.properties.");
				}
			}
		}
	}

	/**
	 * Saves the settings to the config.properties file
	 * located within the root folder. Values being saved
	 * are from the class variables.
	 */
	public void saveSettings() {
		OutputStream output = null;
		try {
			File file = new File(this.accountFilePath);
			if (file.exists()) {
				// Assign property files
				this.prop.setProperty("isAccountStartup", String.valueOf(this.isAccountStartup));
				this.prop.setProperty("filePath", this.accountFilePath);
			} else {
				// File did not exists so do not use for startup.
				this.prop.setProperty("isAccountStartup", "False");
				this.prop.setProperty("filePath", "");
			}
			// Set output stream to config.properties
			output = new FileOutputStream("config.properties");
			this.prop.store(output, null); //Stores in root directory
		} catch (FileNotFoundException e) {
			UiManager.reportError("Error, config.properties does not exist.");
		} catch (IOException e) {
			UiManager.reportError("Error, unable to save to config.properties.");
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					UiManager.reportError("Error, unable to close config.properties.");
				}
			}
		}
	}

	/**
	 * Sets the isAccountStartup variable.
	 * @param isAccountStartupTemp - Value for isAccountStartup.
	 */
	public void setAccountStartup(boolean isAccountStartupTemp) {
		this.isAccountStartup = isAccountStartupTemp;
	}

	/**
	 * Sets the accountFilePath variable.
	 * @param accountFilePathTemp - Value for accountFilePath.
	 */
	public void setAccountFilePath(String accountFilePathTemp) {
		this.accountFilePath = accountFilePathTemp;
	}

	/**
	 * Returns whether the account startup is used or not.
	 * @return boolean isAccountStartup
	 */
	public boolean getAccountStartup() {
		return this.isAccountStartup;
	}

	/**
	 * Returns the account file path.
	 * @return String accountFilePath
	 */
	public String getDefaultFilePath() {
		return this.accountFilePath;
	}
}
