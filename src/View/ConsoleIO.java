package View;

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
            System.out.println("Please select an option");
            option = -1;
            while(++option<menuOptions.length)
            {
                System.out.printf("%i - " + menuOptions[option],option);
                option = scan.nextInt();
            }
        }
        return option;
    }
}
