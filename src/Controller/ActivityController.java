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

package Controller;

import Model.Activity;
import Model.QuantityType;
import Model.Task;
import View.UIManager;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 13/05/2017.
 */
public class ActivityController implements Initializable
{
    private Activity activity;
    private boolean success = false;

    public Activity getActivity()
    {
        return this.activity;
    }

    public boolean isSuccess()
    {
        return success;
    }

    // Panes:
    @FXML private GridPane pane;

    // Buttons:
    @FXML private Button submit;
    @FXML private Button addTask;
    @FXML private Button removeTask;

    // Text:
    @FXML private TextField name;
    @FXML private TextArea details;
    @FXML private ComboBox<String> quantityType;
    @FXML private TextField quantity;
    @FXML private TextField duration;
    @FXML private DatePicker date;

    // Labels:
    @FXML private Label title;

    // Lists:
    @FXML private ListView<Task> tasks;

    /**
     * Handle changes to the input fields
     */
    public void handleChange()
    {
        // Check the input fields:
        if (!this.name.getText().trim().isEmpty() &&
                !this.quantity.getText().trim().isEmpty() &&
                !this.date.getValue().isBefore(LocalDate.now()) &&
                !this.duration.getText().trim().isEmpty() &&
                this.quantityType.getSelectionModel().getSelectedIndex() != -1 &&
                this.tasks.getItems().size() > 0)

            this.submit.setDisable(false);
        // =================
    }

    /**
     * Validate data in the Duration field
     */
    public void validateDuration()
    {
        if (!MainController.isNumeric(this.duration.getText()) || Integer.parseInt(this.duration.getText()) < 0)
        {
            this.duration.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        } else
        {
            this.duration.setStyle("");
            this.handleChange();
        }
    }

    /**
     * Validate data in the Quantity field
     */
    public void validateQuantity()
    {
        if (!MainController.isNumeric(this.quantity.getText()) || Integer.parseInt(this.quantity.getText()) < 0)
        {
            this.quantity.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        } else
        {
            this.quantity.setStyle("");
            this.handleChange();
        }
    }

    /**
     * Validate data in the Date field
     */
    public void validateDate()
    {
        if (this.date.getValue().isBefore(LocalDate.now()))
        {
            this.date.setStyle("-fx-border-color:red;");
            this.submit.setDisable(true);
        } else
        {
            this.date.setStyle("");
            this.handleChange();
        }
    }

    /**
     * Handle the 'Add Task' button action
     */
    public void addTask()
    {
        // Table items:
        ObservableList<Task> list = FXCollections.observableArrayList(MainController.getSPC().getCurrentTasks());
        list.removeAll(this.tasks.getItems());
        if (this.activity != null)
            list.remove(this.activity.getTasks());
        list.removeIf(e -> !e.dependenciesComplete());
        // =================

        // Parse selected Tasks:
        this.tasks.getItems().addAll(TaskController.taskSelectionWindow(list));
        // =================
    }

    /**
     * Submit the form and create a new Activity
     */
    public void handleSubmit()
    {
        if (this.activity == null)
        {
            // Create a new Activity:
            this.activity = new Activity(this.name.getText(), this.details.getText(), this.date.getValue(),
                    Integer.parseInt(this.duration.getText()), Integer.parseInt(this.quantity.getText()),
                    this.quantityType.getValue());
            // =================
            this.activity.addTasks(this.tasks.getItems());
        }

        this.success = true;
        Stage stage = (Stage) this.submit.getScene().getWindow();
        stage.close();
    }

    /**
     * Handle Quit button
     */
    public void handleQuit()
    {
        Stage stage = (Stage) this.submit.getScene().getWindow();
        stage.close();
    }

    /**
     * Constructor for the ActivityController
     */
    public ActivityController()
    {
    }

    /**
     * Constructor for an ActivityController with an existing Activity
     *
     * @param activity
     */
    public ActivityController(Activity activity)
    {
        this.activity = activity;
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.quantityType.getItems().addAll(QuantityType.listOfNames());
        this.date.setValue(LocalDate.now());

        // TODO draggable tasks (same as requirement)
        // ListChangeListener:
        this.tasks.getItems().addListener((ListChangeListener<Task>) c -> handleChange());
        // =================

        // Bind properties on buttons:
        this.removeTask.disableProperty().bind(new BooleanBinding()
        {
            {
                bind(tasks.getSelectionModel().getSelectedItems());
            }

            @Override
            protected boolean computeValue()
            {
                return !(tasks.getItems().size() > 0 && tasks.getSelectionModel().getSelectedItem() != null);
            }
        });
        // =================

        // Button actions:
        this.removeTask.setOnAction(e -> {
            if (UIManager.confirm("Are you sure you want to remove this Task from the list?"))
            {
                Task t = this.tasks.getSelectionModel().getSelectedItem();
                this.tasks.getItems().remove(t);
                if (this.activity != null)
                    this.activity.removeTask(t);
            }
        });

        this.tasks.setCellFactory(e -> {
            ListCell<Task> cell = new ListCell<Task>()
            {
                @Override
                protected void updateItem(final Task item, final boolean empty)
                {
                    super.updateItem(item, empty);
                    // If completed, mark:
                    if (!empty && item != null)
                    {
                        setText(item.toString());
                        if (item.isCheckedComplete())
                            this.getStyleClass().add("current-item");
                    } else
                    {
                        setText(null);
                        this.getStyleClass().remove("current-item");
                    }
                }
            };
            return cell;
        });
        // =================

        // Handle Activity details:
        if (this.activity != null)
        {
            // Disable/modify elements:
            this.title.setText("Activity");
            this.addTask.setVisible(false);
            this.removeTask.setVisible(false);
            this.name.setEditable(false);
            this.details.setEditable(false);
            this.duration.setEditable(false);
            this.quantity.setEditable(false);
            this.date.setDisable(true);
            this.quantityType.setDisable(true);
            // =================

            // Fill in data:
            this.name.setText(this.activity.getName());
            this.details.setText(this.activity.getDetails().getAsString());
            this.duration.setText(Integer.toString(this.activity.getDuration()));
            this.quantity.setText(Integer.toString(this.activity.getActivityQuantity()));
            this.date.setValue(this.activity.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.quantityType.getSelectionModel().select(this.activity.getType().getName());
            this.tasks.getItems().addAll(this.activity.getTasks());
            // =================
        } else this.handleChange();
        // =================

        Platform.runLater(() -> this.pane.requestFocus());
    }
}
