/*
 * Copyright (C) 2018
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * @author champ
 *
 */
public class Settings {
	private boolean isAccountStartup = false;
	private String accountFilePath = "";
	Properties prop = new Properties();

	public Settings() {
		//createConfig();
		loadSettings();
	}

	public void createConfig() {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("config.properties");
			// Save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void loadSettings() {
		InputStream input = null;
		try {

			input = new FileInputStream("config.properties"); // Looks in root directory

			// load a properties file
			this.prop.load(input);

			// get the property value and assign it to variable
			this.isAccountStartup = Boolean.parseBoolean(prop.getProperty("isAccountStartup"));
			this.accountFilePath = prop.getProperty("filePath");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void saveSettings() {
		OutputStream output = null;
		try {
			// Assign property files
			this.prop.setProperty("isAccountStartup", String.valueOf(this.isAccountStartup));
			this.prop.setProperty("filePath", this.accountFilePath);
			// Determine file to save properties as
			output = new FileOutputStream("config.properties");
			this.prop.store(output, null); //Stores in root directory
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setProfileStartup(boolean isAccountStartupTemp) {
		this.isAccountStartup = isAccountStartupTemp;
	}

	public void setDefaultFilePath(String accountFilePath) {
		this.accountFilePath = accountFilePath;
	}

	public boolean getProfileStartup() {
		return this.isAccountStartup;
	}

	public String getDefaultFilePath() {
		return this.accountFilePath;
	}
}
