package Controller;

import Model.Account;
import Model.Notification;
import Model.StudyPlanner;
import View.UIManager;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.GregorianCalendar;

/**
 * Created by bendickson on 5/4/17.
 */
public class MainController
{
    // Public:
    public static UIManager ui = new UIManager();

    // Private:
    private static StudyPlannerController SPC;
    private static SecretKey key64 = new SecretKeySpec(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07}, "Blowfish");
    private static String fileName = "StudyPlanner.dat";

    public static StudyPlannerController getSPC()
    {
        return SPC;
    }

    public static void initialise()
    {
        File plannerFile = new File("StudyPlanner.dat");
        try
        {
            if (plannerFile.exists())
            {
                Cipher cipher = Cipher.getInstance("Blowfish");
                cipher.init(Cipher.DECRYPT_MODE, key64);
                CipherInputStream cipherInputStream = new CipherInputStream(new BufferedInputStream(new FileInputStream(fileName)), cipher);
                ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
                SealedObject sealedObject = (SealedObject) inputStream.readObject();
                SPC = new StudyPlannerController((StudyPlanner) sealedObject.getObject(cipher));
            } else
            {
                Account newAccount = ui.createAccount();
                SPC = new StudyPlannerController(newAccount);
                // Welcome notification:
                Notification not = new Notification("Welcome!", new GregorianCalendar(), "Thank you for using PearPlanner!");
                SPC.getPlanner().addNotification(not);
            }
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

    /**
     * Apparent (according to Stackoverflow) the Java Standard library doesn't have a
     * standard check for testing if a string value is a number or not?!)
     * <p>
     * Therefore, we are using this proposed isNumeric method from:
     * <p>
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
        } catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    /**
     * Save the current state of the program to file
     * @return
     */
    public static boolean save()
    {
        try
        {
            SPC.save(MainController.key64, MainController.fileName);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
