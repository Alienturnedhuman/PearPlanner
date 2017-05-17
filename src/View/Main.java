package View;

import Controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Initialize the StudyPlanner:
        MainController.initialise();
        // If initialization passed, call the Main menu:
        MainController.main();
        //UIManager.areYouFeelingLucky();
    }

    public static void main(String[] args)
    {
        launch(args);
        // Upon exit, save the StudyPlanner:
        MainController.save();
    }
}