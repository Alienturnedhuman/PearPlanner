package Model;

import Controller.MainController;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * ${FILENAME}
 * Created by Andrew Odintsov on 4/27/17.
 */
public class Event extends VersionControlEntity
{
    protected GregorianCalendar date = null;

    // public methods
    private static Pattern dateRegex = Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");

    public static boolean validDateString(String dateString)
    {
        return dateRegex.matcher(dateString).matches();
    }




    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof Event)
        {
            Event castedVCE = (Event) receivedVCE;
            if(castedVCE.getCalendar()!=null)
            {
                this.date = castedVCE.getCalendar();
            }
        }
        super.replace(receivedVCE);
    }

    // getters

    /**
     * Returns a Date object containing this Date
     *
     * @return
     */
    public Date getDate()
    {
        return this.date.getTime();
    }

    public GregorianCalendar getCalendar()
    {
        return date;
    }

    public String toString()
    {
        return this.date.getTime().toString();
    }

    // setters:
    public void setDate(String dateString)
    {
        // 09/04/2017T15:00:00Z
        // at the moment coded to bypass validator as it's not working correctly
        if (validDateString(dateString))
        {

            String sDay = dateString.substring(0, 2);
            String sMonth = dateString.substring(3, 5);
            String sYear = dateString.substring(6, 10);
            String sHour = dateString.substring(11, 13);
            String sMinute = dateString.substring(14, 16);
            String sSecond = dateString.substring(17, 19);
            if (MainController.isNumeric(sDay) && MainController.isNumeric(sMonth) && MainController.isNumeric(sYear) &&
                    MainController.isNumeric(sHour) && MainController.isNumeric(sMinute) &&
                    MainController.isNumeric(sSecond))
            {
                date = new GregorianCalendar(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay)
                        , Integer.parseInt(sHour), Integer.parseInt(sMinute), Integer.parseInt(sSecond));
            }
        }
    }


    public Event(String cDate)
    {
        setDate(cDate);
    }

    public Event()
    {
    }
}
