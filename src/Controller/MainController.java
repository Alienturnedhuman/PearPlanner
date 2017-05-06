package Controller;

import Model.Account;
import Model.Person;
import View.UIManager;

/**
 * Created by bendickson on 5/4/17.
 */
public class MainController
{
    public static UIManager ui = new UIManager();
    public static StudyPlannerController SPC;

    public static void initialise()
    {
        try
        {
            Account newAccount = ui.createAccount();
            SPC = new StudyPlannerController(newAccount);
        } catch (Exception e)
        {
            View.ConsoleIO.setConsoleMessage("INITIALISATION FAILED");
            View.ConsoleIO.setConsoleMessage("Good bye!");
            System.exit(1);
        }

        //consoleUI("Return to Main Menu");
    }

    public static void main()
    {
        try
        {
            ui.mainMenu();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void reportError(String message)
    {
        System.out.println(message);
    }

    private static void consoleUI(String menu)
    {
        while (!menu.equals(""))
        {
            switch (menu)
            {
                case "Quit Program":
                    menu = "";
                    break;
                case "Main Menu":
                case "Return to Main Menu":
                    menu = View.ConsoleIO.view_main();
                    break;
                case "Create Study Profile":
                    menu = View.ConsoleIO.view_createSP();
                    break;
                case "View Study Profile":
                    menu = View.ConsoleIO.view_viewSP(SPC);
                    break;
                case "Load Study Profile File":
                    menu = View.ConsoleIO.view_loadSP(SPC);
                default:
                    menu = "";
            }
        }
    }
}
