package Controller;

/**
 * Created by bendickson on 5/4/17.
 */
public class MainController
{
    private static StudyPlannerController SPC;
    public static boolean initialise()
    {
        SPC = new StudyPlannerController();
        return true;
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

    /**
     * Apparent (according to Stackoverflow) the Java Standard library doesn't have a
     * standard check for testing if a string value is a number or not?!)
     *
     * Therefore, we are using this proposed isNumeric method from:
     *
     * http://stackoverflow.com/a/1102916
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
