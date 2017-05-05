package View;

import Controller.AccountController;
import Model.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

/**
 * Created by Zilvinas on 04/05/2017.
 */
public class UIManager
{
    public Account createAccount() throws Exception
    {
        AccountController accountControl = new AccountController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
        loader.setController(accountControl);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 550, 232));
        stage.setTitle("Create Account");
        stage.resizableProperty().setValue(false);
        stage.getIcons().add(new Image("file:icon.png"));
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, e -> {
            accountControl.handleCreate();
        });
        stage.showAndWait();

        if (accountControl.isSuccess())
        {
            Account newAccount = accountControl.getAccount();
            return newAccount;
        } else
            throw new Exception("User quit.");

    }

    public void mainMenu() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 640));
        stage.setTitle("Main");
        stage.getIcons().add(new Image("file:icon.png"));
        stage.showAndWait();

    }
}