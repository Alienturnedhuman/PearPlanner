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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

        // Process Task list:
        if (this.activity != null)
            this.activity.replaceTasks(this.tasks.getItems());
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
        list.removeAll(this.tasks.getItems());
        if (this.activity != null)
            list.remove(this.activity.getTasks());
        // =================

        // Create a table:
        TableView<Task> taskTable = new TableView<>();
        taskTable.setItems(list);
        taskTable.getColumns().addAll(nameColumn, deadlineColumn);
        taskTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // =================

        // Table attributes:
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setHgrow(taskTable, Priority.ALWAYS);
        GridPane.setVgrow(taskTable, Priority.ALWAYS);
        // =================

        // Set click event:
        taskTable.setRowFactory(TaskController::cellRowFormat);
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

        layout.getChildren().addAll(taskTable, OK);

        // Set a new scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Select dependencies");
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
        // =================

        // Parse selected Tasks:
        this.tasks.getItems().addAll(taskTable.getSelectionModel().getSelectedItems());
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
        } else
        {
            // Update the current activity:
            this.activity.setName(this.name.getText());
            this.activity.setDetails(this.details.getText());
            this.activity.setDate(this.date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "T00:00:01Z");
            this.activity.setDuration(Integer.parseInt(this.duration.getText()));
            this.activity.setActivityQuantity(Integer.parseInt(this.quantity.getText()));
            this.activity.setType(this.quantityType.getValue());
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
