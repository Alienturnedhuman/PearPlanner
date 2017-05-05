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
    private static StudyPlannerController SPC;

    public static boolean initialise()
    {
        try
        {
            Account newAccount = ui.createAccount();
            SPC = new StudyPlannerController(newAccount);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    public static void reportError(String message)
    {
        System.out.println(message);
    }

    private static void consoleUI(String menu)
    {
        while(!menu.equals(""))
        {
            switch(menu)
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

    public static void main()
    {
        if(initialise())
        {
            consoleUI("Return to Main Menu");
        }
        else
        {
            View.ConsoleIO.setConsoleMessage("INITIALISATION FAILED");
        }
        View.ConsoleIO.setConsoleMessage("Good bye!");
    }
}
