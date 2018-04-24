/*
 * Copyright (C) 2018 - Michael Pantoja
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

import edu.wright.cs.raiderplanner.model.Deadline;
import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.ExamEvent;
import edu.wright.cs.raiderplanner.model.TimetableEvent;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;


/**
 * This is a class to handle the code for the calendar feature.
 * @author MichaelPantoja
 *
 */
public class CalendarController {
	private VBox layout = new VBox();
	private HBox nav = new HBox();
	private HBox xx = new HBox();
	private Button agendaFwd = new Button(">");
	private Button agendaBwd = new Button("<");
	private Agenda content = new Agenda();
	private Button printBtn = new Button();
	private PrinterJob job = PrinterJob.createPrinterJob();
	private ArrayList<Event> calendarEvents;
	private LocalDateTime stime;

	/**
	 * This is a class to handle the code for the chat feature.
	 * @author MichaelPantoja
	 */
	public CalendarController() {
		setupLayout();
		setupNav();
		setupContent();
		setupAgendaButtons();
		populateAgenda();
		setupPrintBtn();
		calendarEvents = MainController.getSpc().getPlanner()
				.getCurrentStudyProfile().getCalendar();
		Platform.runLater(() -> content
				.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().plusMinutes(1050)));
	}

	/**
	 * Temporary function for calendar.
	 * @return The current layout of the calendar.
	 *
	 * @author MichaelPantoja
	 */
	public VBox getLayout() {
		return layout;
	}

	/**
	 * This function will set up the layout for the calendar.
	 */
	private void setupLayout() {
		GridPane.setHgrow(layout, Priority.ALWAYS);
		GridPane.setColumnSpan(layout, GridPane.REMAINING);
		layout.setSpacing(10);
		layout.setPadding(new Insets(15));
		layout.getStylesheets().add("/edu/wright/cs/raiderplanner/content/stylesheet.css");
		layout.getChildren().addAll(nav, content);
	}

	/**
	 * This function will set up the nav bar for the calendar.
	 */
	private void setupNav() {
		nav.setSpacing(15.0);
		nav.getChildren().addAll(xx, agendaBwd, agendaFwd);
		HBox.setHgrow(xx, Priority.ALWAYS);
		nav.getChildren().add(1, printBtn);
	}

	/**
	 * This function will set up the agenda for the calendar.
	 */
	private void setupContent() {
		content.setAllowDragging(false);
		content.setAllowResize(false);
		content.autosize();
		content.setActionCallback(param -> null);
		content.setEditAppointmentCallback(param -> null);
	}

	/**
	 * This function will set up the agenda buttons for the calendar.
	 */
	private void setupAgendaButtons() {
		agendaBwd.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				content.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().minusDays(7));
			}
		});
		agendaFwd.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				content.setDisplayedLocalDateTime(content.getDisplayedLocalDateTime().plusDays(7));
			}
		});
		VBox.setVgrow(content, Priority.ALWAYS);
	}

	/**
	 * This function will populate the agenda for the calendar.
	 */
	private void populateAgenda() {
		for (Event e : calendarEvents) {
			// TODO - find a way to eliminate this if/else-if/instanceof anti-pattern
			if (e instanceof TimetableEvent) {
				stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime)
						.withEndLocalDateTime(stime.plusMinutes(e.getDuration()))

						.withSummary(e.getName() + "\n" + "@ "
								+ ((TimetableEvent) e).getRoom().getLocation())
						.withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group5")));
			} else if (e instanceof ExamEvent) {
				stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime)
						.withSummary(
								e.getName() + "\n" + "@ " + ((ExamEvent) e).getRoom().getLocation())
						.withEndLocalDateTime(stime.plusMinutes(e.getDuration()))
						.withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group20")));
			} else if (e instanceof Deadline) {
				stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime.minusMinutes(60)).withSummary(e.getName())
						.withEndLocalDateTime(stime).withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group1")));
			} else {
				stime = LocalDateTime.ofInstant(e.getDate().toInstant(),
						ZoneId.systemDefault());
				content.appointments().addAll(new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(stime).withSummary(e.getName())
						.withEndLocalDateTime(stime.plusMinutes(60)).withAppointmentGroup(
								new Agenda.AppointmentGroupImpl().withStyleClass("group3")));
			}
		}
	}

	/**
	 * This function will set up the print button for the calendar.
	 */
	private void setupPrintBtn() {
		printBtn.getStyleClass().addAll("button-image", "print-button");
		printBtn.setOnMouseClicked(event -> {
			// Prints the currently selected week
			if (job != null) {
				if (!job.showPrintDialog(null)) {
					// user cancelled
					job.cancelJob();
					return;
				}
				content.print(job);
				job.endJob();
			}
		});
	}

}
