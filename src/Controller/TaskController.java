package Controller;

import Model.Requirement;
import Model.Task;
import Model.TaskType;
import View.UIManager;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

/**
 * Created by Zilvinas on 12/05/2017.
 */
public class TaskController implements Initializable
{
    private Task task;
    private boolean success = false;

    public Task getTask()
    {
        return this.task;
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
    @FXML private ToggleButton markComplete;

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
    @FXML private Label completed;
    @FXML private Label canComplete;

    // Lists:
    @FXML private ListView<Requirement> requirements;
    @FXML private ListView<Task> dependencies;

    /**
     * Handle changes to the input fields
     */
    public void handleChange()
    {
        // Check the input fields:
        if (!this.name.getText().trim().isEmpty() &&
                !this.weighting.getText().trim().isEmpty() &&
                !this.deadline.getEditor().getText().trim().isEmpty() &&
                this.taskType.getSelectionModel().getSelectedIndex() != -1)

            this.submit.setDisable(false);
        // =================

        // Process requirements and dependencies:
        if (this.task != null)
        {
            this.task.replaceDependencies(this.dependencies.getItems());
            this.task.replaceRequirements(this.requirements.getItems());

            if (!this.task.isCheckedComplete() && this.task.canCheckComplete())
            {
                this.canComplete.setText("Can be completed.");
                this.canComplete.setTextFill(Paint.valueOf("green"));
                this.canComplete.setVisible(true);
                this.markComplete.setDisable(false);
            } else if (!this.task.canCheckComplete())
            {
                this.task.setComplete(false);
                this.canComplete.setText("Cannot be completed at this point.");
                this.canComplete.setTextFill(Paint.valueOf("red"));
                this.canComplete.setVisible(true);
                this.markComplete.setDisable(true);
            }
        }
        // =================
    }

    /**
     * Validate data in the Weighting field
     */
    public void validateWeighting()
    {
        if (!MainController.isNumeric(this.weighting.getText()) || Integer.parseInt(this.weighting.getText()) > 100
                || Integer.parseInt(this.weighting.getText()) < 0)
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
     * Handle the 'Add requirement' button action
     */
    public void addRequirement()
    {
        try
        {
            Requirement req = MainController.ui.addRequirement();
            if (req != null)
                this.requirements.getItems().add(req);
        } catch (IOException e1)
        {
            UIManager.reportError("Unable to open View file");
        } catch (Exception e1)
        {
        }
    }

    /**
     * Handle the 'Add dependency' button action
     */
    public void addDependency()
    {
        // Layout:
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setAlignment(Pos.BOTTOM_RIGHT);
        // =================

        // Tasks columns:
        TableColumn<Task, String> nameColumn = new TableColumn<>("Task");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, String> deadlineColumn = new TableColumn<>("Deadline");
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        deadlineColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        // =================

        // Table items:
        ObservableList<Task> list = FXCollections.observableArrayList(MainController.getSPC().getCurrentTasks());
        list.removeAll(this.dependencies.getItems());
        if (this.task != null)
        {
            list.remove(this.task);
            list.removeAll(this.task.getDependencies());
        }
        // =================

        // Create a table:
        TableView<Task> tasks = new TableView<>();
        tasks.setItems(list);
        tasks.getColumns().addAll(nameColumn, deadlineColumn);
        tasks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // =================

        // Table attributes:
        tasks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setHgrow(tasks, Priority.ALWAYS);
        GridPane.setVgrow(tasks, Priority.ALWAYS);
        // =================

        // Set click event:
        tasks.setRowFactory(TaskController::cellRowFormat);
        // =================

        // Button:
        Button OK = new Button("OK");
        OK.setOnAction(e -> {
            Stage current = (Stage) OK.getScene().getWindow();
            current.close();
        });
        VBox.setMargin(OK, new Insets(5));
        OK.setDefaultButton(true);
        // =================

        layout.getChildren().addAll(tasks, OK);

        // Set a new scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Select dependencies");
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
        // =================

        // Parse selected Tasks:
        this.dependencies.getItems().addAll(tasks.getSelectionModel().getSelectedItems());
        // =================
    }

    /**
     * Handles the 'Mark as complete' button action
     */
    public void toggleComplete()
    {
        if (this.task.isCheckedComplete())
        {
            this.task.toggleComplete();
            this.completed.setVisible(false);
            this.canComplete.setVisible(true);
        } else if (this.task.canCheckComplete())
        {
            this.task.toggleComplete();
            this.completed.setVisible(true);
            this.canComplete.setVisible(false);
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
     */
    public TaskController()
    {
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

        // ListChangeListener:
        this.dependencies.getItems().addListener((ListChangeListener<Task>) c -> handleChange());
        this.requirements.getItems().addListener((ListChangeListener<Requirement>) c -> handleChange());
        // =================

        // Bind properties on buttons:
        this.removeDep.disableProperty().bind(new BooleanBinding()
        {
            {
                bind(dependencies.getSelectionModel().getSelectedItems());
            }

            @Override
            protected boolean computeValue()
            {
                return !(dependencies.getItems().size() > 0 && dependencies.getSelectionModel().getSelectedItem() != null);
            }
        });

        this.removeReq.disableProperty().bind(new BooleanBinding()
        {
            {
                bind(requirements.getSelectionModel().getSelectedItems());
            }

            @Override
            protected boolean computeValue()
            {
                return !(requirements.getItems().size() > 0 && requirements.getSelectionModel().getSelectedItem() != null);
            }
        });
        // =================

        // Button actions:
        this.removeDep.setOnAction(e -> {
            if (UIManager.confirm("Are you sure you want to remove this dependency?"))
            {
                Task t = this.dependencies.getSelectionModel().getSelectedItem();
                this.dependencies.getItems().remove(t);
                this.task.removeDependency(t);
            }
        });

        this.removeReq.setOnAction(e -> {
            if (UIManager.confirm("Are you sure you want to remove this requirement?"))
            {
                Requirement r = this.requirements.getSelectionModel().getSelectedItem();
                this.requirements.getItems().remove(r);
                this.task.removeRequirement(r);
            }
        });

        this.requirements.setCellFactory(e -> {
            ListCell<Requirement> cell = new ListCell<Requirement>()
            {
                @Override
                protected void updateItem(final Requirement item, final boolean empty)
                {
                    super.updateItem(item, empty);
                    // If completed, mark:
                    if (!empty && item != null)
                    {
                        setText(item.toString());
                        if (item.isComplete())
                            this.getStyleClass().add("current-item");
                    } else setText(null);
                }
            };
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    try
                    {
                        MainController.ui.requirementDetails(cell.getItem());
                        this.requirements.refresh();
                    } catch (IOException e1)
                    {
                        UIManager.reportError("Unable to open View file");
                    }
                }
            });
            return cell;
        });
        // =================

        // Handle Task details:
        if (this.task != null)
        {
            // Disable/modify elements:
            this.title.setText("Task");
            this.markComplete.setVisible(true);
            this.canComplete.setTextFill(Paint.valueOf("green"));

            if (this.task.isCheckedComplete())
            {
                this.completed.setVisible(true);
                this.markComplete.setSelected(true);
                this.markComplete.setDisable(false);
            } else if (this.task.canCheckComplete())
            {
                this.markComplete.setDisable(false);
                this.canComplete.setVisible(true);
            } else
            {
                this.canComplete.setText("Cannot be completed at this point.");
                this.canComplete.setTextFill(Paint.valueOf("red"));
                this.canComplete.setVisible(true);
            }
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
        // =================

        Platform.runLater(() -> this.pane.requestFocus());
    }

    /**
     * Formats the cell for displaying Tasks.
     * @param e TableView to be formatted.
     * @return Formatted TableRow
     */
    protected static TableRow<Task> cellRowFormat(TableView<Task> e)
    {
        TableRow<Task> row = new TableRow<Task>()
        {
            @Override
            protected void updateItem(final Task item, final boolean empty)
            {
                super.updateItem(item, empty);
                // If Task is completed, mark:
                if (!empty && item != null && item.isCheckedComplete())
                    this.getStyleClass().add("current-item");
            }
        };
        row.setOnMouseClicked(event -> {
            if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
            {
                Stage current = (Stage) row.getScene().getWindow();
                current.close();
            }
        });
        return row;
    }
}

