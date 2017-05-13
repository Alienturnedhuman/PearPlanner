package Controller;

import Model.Activity;
import Model.QuantityType;
import Model.Requirement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 13/05/2017.
 */
public class RequirementController implements Initializable
{
    private Requirement requirement;
    private boolean success = false;

    public Requirement getRequirement()
    {
        return requirement;
    }

    public boolean isSuccess()
    {
        return success;
    }

    // Panes:
    @FXML private GridPane pane;
    @FXML private TableView<Activity> activities;
    @FXML private TableColumn<Activity, String> nameColumn;
    @FXML private TableColumn<Activity, Integer> quantityColumn;
    @FXML private TableColumn<Activity, String> dateColumn;

    // Buttons:
    @FXML private Button submit;

    // Text:
    @FXML private TextArea details;
    @FXML private ComboBox<String> quantityType;
    @FXML private TextField name;
    @FXML private TextField quantity;
    @FXML private TextField time;

    // Labels:
    @FXML private Label title;
    @FXML private Label completed;

    /**
     * Handle changes to the input fields
     */
    public void handleChange()
    {
        // Check the input fields:
        if (!this.name.getText().trim().isEmpty() &&
                !this.details.getText().trim().isEmpty() &&
                !this.quantity.getText().trim().isEmpty() &&
                !this.time.getText().trim().isEmpty() &&
                this.quantityType.getSelectionModel().getSelectedIndex() != -1)

            this.submit.setDisable(false);
        // =================
    }

    /**
     * Validate data in the Time field
     */
    public void validateTime()
    {
        if (!MainController.isNumeric(this.time.getText()) || Double.parseDouble(this.time.getText()) < 0)
        {
            this.time.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        } else
        {
            this.time.setStyle("");
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
        if (this.requirement != null)
            this.completed.setVisible(false);
    }

    /**
     * Submit the form and create a new Task
     */
    public void handleSubmit()
    {
        if (this.requirement == null)
        {
            // Create a new Requirement:
            this.requirement = new Requirement(this.name.getText(), this.details.getText(),
                    Double.parseDouble(this.time.getText()), Integer.parseInt(this.quantity.getText()),
                    this.quantityType.getValue());
            // =================
        } else
        {
            // Update the current requirement:
            this.requirement.setName(this.name.getText());
            this.requirement.setDetails(this.details.getText());
            this.requirement.setEstimatedTimeInHours(Double.parseDouble(this.time.getText()));
            this.requirement.setInitialQuantity(Integer.parseInt(this.quantity.getText()));
            this.requirement.setQuantityType(this.quantityType.getValue());
            // =================

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
     * Constructor for the RequirementController
     */
    public RequirementController()
    {
    }

    /**
     * Constructor for a RequirementController with an existing Requirement
     *
     * @param requirement
     */
    public RequirementController(Requirement requirement)
    {
        this.requirement = requirement;
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.quantityType.getItems().addAll(QuantityType.listOfNames());

        // Hide the Activities table:
        if (this.requirement == null)
        {
            this.pane.getChildren().remove(this.activities);
            this.pane.getRowConstraints().remove(3);

            Node bottomNode = this.pane.getChildren().get(0);
            this.pane.getChildren().remove(bottomNode);
            this.pane.getRowConstraints().remove(3);

            GridPane.setColumnSpan(bottomNode, 2);
            this.pane.addRow(3, bottomNode);
        }
        // =================
        else
        {
            // Disable/modify elements:
            this.title.setText("Requirement");

            if (this.requirement.isComplete())
                this.completed.setVisible(true);
            // =================

            // Fill in data:
            this.name.setText(this.requirement.getName());
            this.details.setText(this.requirement.getDetails().getAsString());
            this.time.setText(Double.toString(this.requirement.getEstimatedTimeInHours()));
            this.quantity.setText(Integer.toString(this.requirement.getRemainingQuantity()));
            this.quantityType.getSelectionModel().select(this.requirement.getQuantityType().getName());
            this.activities.getItems().addAll(this.requirement.getActivityLog());
            // =================
        }
        // =================

        Platform.runLater(() -> this.pane.requestFocus());
    }
}
