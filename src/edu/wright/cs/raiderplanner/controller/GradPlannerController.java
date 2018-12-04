/*
 * Copyright (C) 2018 - Mark Riedel
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
package edu.wright.cs.raiderplanner.controller;
import edu.wright.cs.raiderplanner.model.Account;
import edu.wright.cs.raiderplanner.model.Notification;
import edu.wright.cs.raiderplanner.model.Requirement;
import edu.wright.cs.raiderplanner.view.UiManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * Controller for the first window that is presented when the application
 * launches (i.e., New/Open/Exit buttons).
 *
 * @author Mark Riedel on 10/9/2018.
*/
public class GradPlannerController {
	
	ObservableList<String> semesterList = FXCollections
			.observableArrayList("Semster 1, Semester2,...",
			"Fall 2018,Spring 2018,...","Year 1 Fall,Year 1 Spring",
			"Custom");
	
	@FXML
	private TextField startYearFx;
	@FXML
	private TextField semesterOne;
	@FXML
	private TextField semesterTwo;
	@FXML
	private TextField semesterThree;
	@FXML
	private TextField semesterFour;
	@FXML
	private TextField semesterFive;
	@FXML
	private TextField semesterSix;
	@FXML
	private TextField semesterSeven;
	@FXML
	private TextField semesterEight;
	@FXML
	private ComboBox chooseSemester;
	
	String startYear = startYearFx.getText();
	String year1 = startYear.toString();
	
	String secondYear = startYearFx.getText() + 1;
	String year2 = secondYear.toString();
	
	String thirdYear = startYearFx.getText() + 2;
	String year3 = thirdYear.toString();
	
	String fourthYear = startYearFx.getText();
	String year4 = fourthYear.toString();
	
	/**
	 * Adds SemesterList to the Combo Box
	 */
	@FXML
	private void initialize() {
		chooseSemester.setValue("semester");
		chooseSemester.setItems(semesterList);
	}
	/**
	 * Allows for the TextFields Standard Configurations. 
	 */
	public void semesterNumber() {
		semesterOne.setText("Semester 1");
		semesterOne.setEditable(false);
		semesterTwo.setText("Semester 2");
		semesterTwo.setEditable(false);
		semesterThree.setText("Semester 3");
		semesterThree.setEditable(false);
		semesterFour.setText("Semester 4");
		semesterFour.setEditable(false);
		semesterFive.setText("Semester 5");
		semesterFive.setEditable(false);
		semesterSix.setText("Semester 6");
		semesterSix.setEditable(false);
		semesterSeven.setText("Semester 7");
		semesterSeven.setEditable(false);
		semesterEight.setText("Semester 8");
		semesterEight.setEditable(false);
		
	}
	
	public void nameYear() {
		semesterOne.setText("Fall " + year1);
		semesterOne.setEditable(false);
		semesterTwo.setText("Spring" + year1);
		semesterTwo.setEditable(false);
		semesterThree.setText("Fall" + year2);
		semesterThree.setEditable(false);
		semesterFour.setText("Spring" + year2);
		semesterFour.setEditable(false);
		semesterFive.setText("Fall" + year3);
		semesterFive.setEditable(false);
		semesterSix.setText("Spring" + year3);
		semesterSix.setEditable(false);
		semesterSeven.setText("Fall" + year4);
		semesterSeven.setEditable(false);
		semesterEight.setText("Spring" + year4);
		semesterEight.setEditable(false);
		
	}
	/*
	 * Allows for custom naming semesters
	 */
	public void customName() {
		semesterOne.setEditable(true);
		semesterTwo.setEditable(true);
		semesterThree.setEditable(true);
		semesterFour.setEditable(true);
		semesterFive.setEditable(true);
		semesterSix.setEditable(true);
		semesterSeven.setEditable(true);
		semesterEight.setEditable(true);
	}
	
	
	
}
