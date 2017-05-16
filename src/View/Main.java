package View;

import Controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        MainController.initialise();
        MainController.main();
        //UIManager.areYouFeelingLucky();
    }

    public static void main(String[] args)
    {
        launch(args);
        MainController.save();
    }
}