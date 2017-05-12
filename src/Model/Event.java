package Model;

import Controller.MainController;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * ${FILENAME}
 * Created by Andrew Odintsov on 4/27/17.
 */
public class Event extends VersionControlEntity
{
    protected GregorianCalendar date;

    // public methods
    private static Pattern dateRegex = Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)");

    public static boolean validDateString(String dateString)
    {
        return dateRegex.matcher(dateString).matches();
    }


    // getters
    public String toString()
    {
        String[] numbers = {String.format("%02d", date.get(Calendar.DAY_OF_MONTH)),
                String.format("%02d", date.get(Calendar.MONTH)), String.format("%02d", date.get(Calendar.HOUR_OF_DAY)),
                String.format("%02d", date.get(Calendar.MINUTE)), String.format("%02d", date.get(Calendar.SECOND))};

        StringBuilder dateString = new StringBuilder();
        dateString.append(numbers[0]);
        dateString.append("/");
        dateString.append(numbers[1]);
        dateString.append("/");
        dateString.append(date.get(Calendar.YEAR));
        dateString.append("T");
        dateString.append(numbers[2]);
        dateString.append(":");
        dateString.append(numbers[3]);
        dateString.append(":");
        dateString.append(numbers[4]);

        return dateString.toString();
    }

    // setters:
    public void setDate(String dateString)
    {
        if(true||validDateString(dateString))
        {

            String sDay = dateString.substring(0,2);
            String sMonth = dateString.substring(3,5);
            String sYear = dateString.substring(6,10);
            String sHour = dateString.substring(11,13);
            String sMinute = dateString.substring(14,16);
            String sSecond = dateString.substring(17,19);
//            if(MainController.isNumeric(sDay) && MainController.isNumeric(sMonth) && MainController.isNumeric(sYear) &&
//                    MainController.isNumeric(sHour) && MainController.isNumeric(sMinute) &&
//                    MainController.isNumeric(sSecond))
//            {
                date = new GregorianCalendar(Integer.parseInt(sYear), Integer.parseInt(sMonth), Integer.parseInt(sDay),
                        Integer.parseInt(sHour), Integer.parseInt(sMinute), Integer.parseInt(sSecond));
//            }
        }
    }


    public Event(String cDate)
    {
        setDate(cDate);
    }
}
