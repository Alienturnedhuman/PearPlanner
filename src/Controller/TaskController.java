package Controller;

import Model.Account;
import Model.Assignment;
import Model.Person;
import Model.Task;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 12/05/2017.
 */
public class TaskController implements Initializable
{
    private Assignment assignment;

    private Task task;
    private boolean success = false;

    public Task getTask()
    {
        return task;
    }

    public boolean isSuccess()
    {
        return success;
    }

    // Buttons:
    @FXML private Button submit;
    @FXML private Button removeReq;
    @FXML private Button removeDep;

    // Panes:
    @FXML private GridPane pane;

    // Text:
    @FXML private TextArea details;
    @FXML private ChoiceBox<String> taskType;
    @FXML private DatePicker deadline;
    @FXML private TextField name;
    @FXML private TextField weighting;

    // Lists:
    @FXML private ListView<String> requirements;
    @FXML private ListView<String> dependencies;

    /**
     * Handle changes to the text fields
     */
    public void handleChange()
    {
        if (!this.name.getText().trim().isEmpty() &&
            !this.weighting.getText().trim().isEmpty() && MainController.isNumeric(this.weighting.getText()) &&
            !this.deadline.getEditor().getText().trim().isEmpty() &&
            this.taskType.getSelectionModel().getSelectedIndex() != -1 &&
            this.requirements.getItems().size() > 0)

            this.submit.setDisable(false);
    }

    /**
     * Validate data in the Weighting field
     */
    public void validateWeighting()
    {
        if (!MainController.isNumeric(this.weighting.getText()))
        {
            this.weighting.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        } else
        {
            this.weighting.setStyle("");
            this.handleChange();
        }
    }

    /**
     * Validate data in the Deadline field
     */
    public void validateDeadline()
    {
        if (this.deadline.getValue().isBefore(LocalDate.now()))
        {
            this.deadline.setStyle("-fx-border-color:red;");
            this.submit.setDisable(true);
        } else
        {
            this.deadline.setStyle("");
            this.handleChange();
        }
    }

    /**
     * Submit the form and create a new Task
     */
    public void handleSubmit()
    {
        Task p = new Task(); // Create here

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
     * Constructor for the TaskController
     *
     * @param assignment to which the Task relates
     */
    public TaskController(Assignment assignment)
    {
        this.assignment = assignment;
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(() -> this.pane.requestFocus());
    }
}

