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

import edu.wright.cs.raiderplanner.controller.AccountController;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 08/05/2017
 */
public abstract class FxBase extends ApplicationTest {

	@Override
	public void start(Stage stage) throws Exception {
		AccountController accountControl = new AccountController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
		loader.setController(accountControl);
		Parent root = loader.load();

		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});

		Scene scene = new Scene(root, 550, 232);

		stage.setScene(scene);

		stage.show();
	}

	/**
	 * This test should handle the cleanup of the CreateAccountUi dialog after each run.
	 * @throws TimeoutException if the operation takes too long or hangs.
	 */
	@AfterEach
	public void afterEachTest() throws TimeoutException {
		FxToolkit.hideStage();
		release(new KeyCode[]{});
		release(new MouseButton[]{});
	}

	/**
	 * This method attempts to retrieve UI Node objects from the CreateAccountUi dialog.
	 * @param query the Node we want to retrieve.
	 * @return the requested Node.
	 */
	// Suppressing because converting to type T should work with all objects of Node.
	@SuppressWarnings("unchecked")
	public <T extends Node> T find(final String query) {
		return (T) lookup(query).queryAll().iterator().next();
	}

}
