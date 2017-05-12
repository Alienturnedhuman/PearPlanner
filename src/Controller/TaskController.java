package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 12/05/2017.
 */
public class TaskController implements Initializable
{
    // TODO add requirements and dependencies

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
    @FXML private Button addReq;
    @FXML private Button addDep;
    @FXML private Button removeReq;
    @FXML private Button removeDep;

    // Panes:
    @FXML private GridPane pane;

    // Text:
    @FXML private TextArea details;
    @FXML private ComboBox<String> taskType;
    @FXML private DatePicker deadline;
    @FXML private TextField name;
    @FXML private TextField weighting;

    // Labels:
    @FXML private Label title;

    // Lists:
    @FXML private ListView<Requirement> requirements;
    @FXML private ListView<Task> dependencies;

    /**
     * Handle changes to the text fields
     */
    public void handleChange()
    {
        if (!this.name.getText().trim().isEmpty() &&
                !this.weighting.getText().trim().isEmpty() && MainController.isNumeric(this.weighting.getText()) &&
                !this.deadline.getEditor().getText().trim().isEmpty() &&
                this.taskType.getSelectionModel().getSelectedIndex() != -1 /*&&
                this.requirements.getItems().size() > 0*/)

            this.submit.setDisable(false);
    }

    /**
     * Validate data in the Weighting field
     */
    public void validateWeighting()
    {
        if (!MainController.isNumeric(this.weighting.getText()) || Integer.parseInt(this.weighting.getText()) > 100)
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
        if (this.task == null)
        {
            // Create a new Task:
            this.task = new Task(this.name.getText(), this.details.getText(), this.deadline.getValue(),
                    Integer.parseInt(this.weighting.getText()), this.taskType.getValue());

            for (Requirement req : this.requirements.getItems())
                this.task.addRequirement(req);

            for (Task t : this.dependencies.getItems())
                this.task.addDependency(t);
            // =================
        } else
        {
            // Update the current task:
            this.task.setName(this.name.getText());
            this.task.setDetails(this.details.getText());
            this.task.setDeadline(this.deadline.getValue());
            this.task.setWeighting(Integer.parseInt(this.weighting.getText()));
            this.task.setType(this.taskType.getValue());

            this.task.replaceDependencies(this.dependencies.getItems());
            this.task.replaceRequirements(this.requirements.getItems());
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
     * Constructor for the TaskController
     *
     * @param assignment to which the Task relates
     */
    public TaskController(Assignment assignment)
    {
        this.assignment = assignment;
    }

    /**
     * Constructor for a TaskController with an existing Task
     *
     * @param task
     */
    public TaskController(Task task)
    {
        this.task = task;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.taskType.getItems().addAll(TaskType.listOfNames());

        if (this.task != null)
        {
            // Disable/modify elements:
            this.title.setText("Task");
            // =================

            // Fill in data:
            this.name.setText(this.task.getName());
            this.details.setText(this.task.getDetails().getAsString());
            this.weighting.setText(Integer.toString(this.task.getWeighting()));
            this.taskType.getSelectionModel().select(this.task.getType().getName());
            this.deadline.setValue(this.task.getDeadlineDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.dependencies.getItems().addAll(this.task.getDependencies());
            this.requirements.getItems().addAll(this.task.getRequirements());
            // =================
        }

        Platform.runLater(() -> this.pane.requestFocus());
    }
}

