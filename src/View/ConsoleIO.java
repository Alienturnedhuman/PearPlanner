package View;

import Controller.*;
import Model.*;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by bendickson on 5/4/17.
 */
public class ConsoleIO {
    static public String getDataString(String message)
    {
        Scanner scan = new Scanner(System.in);
        String r = "";
        while(r.equals("")) {
            System.out.println(message);
            r = scan.nextLine();
        }
        return r;
    }
    static public boolean getDataBool(String message)
    {
        Scanner scan = new Scanner(System.in);
        String r = "";
        while(!(r.equals("y")||r.equals("n"))) {
            System.out.println(message);
            r = scan.next();
        }
        return r.equals("y");
    }
    static public void setConsoleMessage(String message)
    {
        System.out.println(message);
    }
    static public int getMenuOption(String[] menuOptions)
    {
        Scanner scan = new Scanner(System.in);
        int option = -1;
        while(option<0||option>=menuOptions.length)
        {
            System.out.println("Please select an option\n");
            option = -1;
            while(++option<menuOptions.length)
            {
                System.out.printf("%d - " + menuOptions[option]+"\n",option);
            }
            option = scan.nextInt();
        }
        return option;
    }


    // Console view below
    static public String view_main()
    {
        View.ConsoleIO.setConsoleMessage("MAIN MENU");
        // list of options
        String[] menuOptions = {"Create Study Profile","View Study Profile","View Notifications","Quit Program"};
        int choice = View.ConsoleIO.getMenuOption(menuOptions);

        return menuOptions[choice];

    }
    static public String view_createSP()
    {
        View.ConsoleIO.setConsoleMessage("CREATE A STUDY PROFILE");
        // list of options
        String[] menuOptions = {"Load Study Profile File","Return to Main Menu"};
        int choice = View.ConsoleIO.getMenuOption(menuOptions);

        return menuOptions[choice];

    }
    static public String view_viewSP(StudyPlannerController SPC)
    {
        View.ConsoleIO.setConsoleMessage("VIEW A STUDY PROFILE");
        String[] studyProfiles = SPC.getStudyProfiles();
        int i =-1, ii = studyProfiles.length;

        if(ii<1)
        {
            View.ConsoleIO.setConsoleMessage("No existing study profiles");
        }

        String[] menuOptions = new String[ii+1];
        while(++i<ii)
        {
            menuOptions[i] = studyProfiles[i];
        }
        menuOptions[ii] = "Return to Main Menu";

        int m = -1;
        while(m<ii)
        {
            m = View.ConsoleIO.getMenuOption(menuOptions);
            if(m<ii)
            {

            }
        }

        return menuOptions[ii];
    }
    static public String view_loadSP(StudyPlannerController SPC)
    {
        View.ConsoleIO.setConsoleMessage("LOAD A STUDY PROFILE");

        String filename = getDataString("Enter filepath:");
        HubFile fileData = DataController.loadHubFile(filename);
        while(!filename.equals("") || fileData == null)
        {
            filename = getDataString("File not valid, enter a different filepath:");
            fileData = DataController.loadHubFile(filename);
        }

        System.out.println(fileData);

        return "Return to Main Menu";
    }
}
