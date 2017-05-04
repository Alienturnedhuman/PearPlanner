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

    public static void main()
    {
        if(initialise())
        {
            // list of options
            String[] menuOptions = {"Create Study Profile","View Study Profile","View Notifications"};
            View.ConsoleIO.getMenuOption(menuOptions);
        }
        else
        {
            View.ConsoleIO.setConsoleMessage("INITIALISATION FAILED");
        }
        View.ConsoleIO.setConsoleMessage("Good bye!");
    }
}
