package View;

import Controller.AccountController;
import Controller.StudyProfileController;
import Model.Account;
import Model.StudyProfile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    /**
     * Displays a 'Create Account' window and handles the creation of
     * a new Account object
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
     * @throws Exception
     */
    public void mainMenu() throws Exception
    {
        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenu.fxml"));
        Parent root = loader.load();

        // Set the scene:
        mainStage.setScene(new Scene(root, 700, 750));
        mainStage.setTitle("PearPlanner");
        mainStage.getIcons().add(new Image("file:icon.png"));
        mainStage.showAndWait();
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
     * Displays a file dialog for importing .xml files
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
     * @param message to be displayed
     */
    public static void reportError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    /**
     * Reports that an action was successful and displays a message
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

}