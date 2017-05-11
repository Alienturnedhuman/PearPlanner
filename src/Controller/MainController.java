package Controller;

import Model.Account;
import Model.HubFile;
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

    /**
     * Initializes the Study Planner by either registering a new account or
     * importing an existing Study Planner file.
     */
    public static void initialise()
    {
        File plannerFile = new File("StudyPlanner.dat");
        try
        {
            // If a file is present:
            if (plannerFile.exists())
            {
                Cipher cipher = Cipher.getInstance("Blowfish");
                cipher.init(Cipher.DECRYPT_MODE, key64);
                CipherInputStream cipherInputStream = new CipherInputStream(new BufferedInputStream(new FileInputStream(fileName)), cipher);
                ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
                SealedObject sealedObject = (SealedObject) inputStream.readObject();
                SPC = new StudyPlannerController((StudyPlanner) sealedObject.getObject(cipher));
            } else
            // If not, prompt to create a new account:
            {
                Account newAccount = ui.createAccount();
                SPC = new StudyPlannerController(newAccount);
                // Welcome notification:
                Notification not = new Notification("Welcome!", new GregorianCalendar(), "Thank you for using PearPlanner!");
                SPC.getPlanner().addNotification(not);
            }
        } catch (Exception e)
        {
            UIManager.reportError(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Display the main menu:
     */
    public static void main()
    {
        try
        {
            ui.mainMenu();
        } catch (Exception e)
        {
            UIManager.reportError(e.getMessage());
        }
    }

    /**
     * Handle importing a new file:
     */
    public static boolean importFile()
    {
        // Call a dialog:
        File tempFile = ui.fileDialog();
        if (tempFile != null)
        {
            // If a file was selected, process the file:
            HubFile fileData = DataController.loadHubFile(tempFile);
            if (fileData != null)
            {
                if (fileData.isUpdate() && !MainController.SPC.updateStudyProfile(fileData))
                    UIManager.reportError("Cannot update a Study Profile!");
                else if (!MainController.SPC.createStudyProfile(fileData))
                    UIManager.reportError("This Study Profile is already created!");
                else
                    return true;
            }
        }
        return false;
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
            UIManager.reportError("FAILED TO SAVE YOUR DATA!");
            return false;
        }
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
        } catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
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
