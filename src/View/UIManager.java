package View;

import Controller.*;
import Model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by Zilvinas on 04/05/2017.
 */
public class UIManager
{
    private static Stage mainStage = new Stage();
    private static MenuController mc = new MenuController();

    /**
     * Displays a 'Create Account' window and handles the creation of
     * a new Account object
     *
     * @return newly created Account
     * @throws Exception
     */
    public Account createAccount() throws Exception
    {
        AccountController accountControl = new AccountController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateAccount.fxml"));
        loader.setController(accountControl);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 550, 232));
        stage.setTitle("Create Account");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Handle creation of the Account object:
        if (accountControl.isSuccess())
        {
            Account newAccount = accountControl.getAccount();
            return newAccount;
        } else
            throw new Exception("User quit.");

    }

    /**
     * Displays the main menu
     *
     * @throws Exception
     */
    public void mainMenu() throws Exception
    {
        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenu.fxml"));
        loader.setController(UIManager.mc);
        Parent root = loader.load();

        // Set the scene:
        mainStage.setScene(new Scene(root, 1000, 750));
        mainStage.setTitle("PearPlanner");
        mainStage.getIcons().add(new Image("file:icon.png"));
        mainStage.showAndWait();
    }

    /**
     * Display the 'Add Activity' window
     */
    public Activity addActivity() throws Exception
    {
        ActivityController ac = new ActivityController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Activity.fxml"));
        loader.setController(ac);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 358));
        stage.setTitle("New Activity");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Add the activity to the StudyPlanner
        if (ac.isSuccess())
            return ac.getActivity();
        return null;
    }

    /**
     * Displays the Activity details page
     */
    public void activityDetails(Activity activity) throws IOException
    {
        ActivityController ac = new ActivityController(activity);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Activity.fxml"));
        loader.setController(ac);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 358));
        stage.setTitle("Activity");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Displays the StudyProfile details page
     */
    public void studyProfileDetails(StudyProfile profile) throws IOException
    {
        StudyProfileController spc = new StudyProfileController(profile);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/StudyProfile.fxml"));
        loader.setController(spc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 232));
        stage.setTitle(profile.getName());
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Displays the Module details page
     */
    public void moduleDetails(Module module, MenuController.Window current) throws IOException
    {
        UIManager.mc.loadModule(module, current, null);
    }

    public void moduleDetails(Module module, ModelEntity current) throws IOException
    {
        UIManager.mc.loadModule(module, MenuController.Window.Empty, current);
    }

    /**
     * Displays the Assignment details page
     */
    public void assignmentDetails(Assignment assignment, MenuController.Window current) throws IOException
    {
        UIManager.mc.loadAssignment(assignment, current, null);
    }

    public void assignmentDetails(Assignment assignment, ModelEntity current) throws IOException
    {
        UIManager.mc.loadAssignment(assignment, MenuController.Window.Empty, current);
    }

    /**
     * Creates a window for adding a new Task
     */
    public Task addTask() throws Exception
    {
        TaskController tc = new TaskController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Task.fxml"));
        loader.setController(tc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 558));
        stage.setTitle("New Task");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Handle creation of the Account object:
        if (tc.isSuccess())
            return tc.getTask();
        return null;
    }

    /**
     * Displays the Task details page
     */
    public void taskDetails(Task task) throws IOException
    {
        TaskController tc = new TaskController(task);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Task.fxml"));
        loader.setController(tc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 558));
        stage.setTitle("Task");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Creates a window for adding a new Requirement
     */
    public Requirement addRequirement() throws Exception
    {
        RequirementController rc = new RequirementController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Requirement.fxml"));
        loader.setController(rc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 260));
        stage.setTitle("New Requirement");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

        // Handle creation of the Account object:
        if (rc.isSuccess())
            return rc.getRequirement();
        return null;
    }

    /**
     * Displays the Task details page
     */
    public void requirementDetails(Requirement requirement) throws IOException
    {
        RequirementController rc = new RequirementController(requirement);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Requirement.fxml"));
        loader.setController(rc);
        Parent root = loader.load();

        // Set the scene:
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 558));
        stage.setTitle("Requirement");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();
    }

    /**
     * Displays a file dialog for importing .xml files
     *
     * @return a File object
     */
    public File fileDialog()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a HUB file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file", "*.xml"));
        File file = fileChooser.showOpenDialog(mainStage);
        return file;
    }

    /**
     * Displays an error message
     *
     * @param message to be displayed
     */
    public static void reportError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    /**
     * Reports that an action was successful and displays a message
     *
     * @param message to be displayed
     */
    public static void reportSuccess(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    /**
     * Reports that an action was successful
     */
    public static void reportSuccess()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success!");
        alert.showAndWait();
    }

    /**
     * Confirm box with 'Yes' or 'No' as available options
     *
     * @param message message to be displayed
     * @return true for yes, false for no.
     */
    public static boolean confirm(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult().equals(ButtonType.YES);
    }

    /**
     * Please don't use
     */
    public static void areYouFeelingLucky()
    {
        while (UIManager.confirm("Are you feeling lucky?") == (Math.random() < 0.5))
        {
        }
    }

}