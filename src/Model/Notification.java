package Model;

import java.util.GregorianCalendar;

/**
 * Created by bendickson on 4/27/17.
 */
public class Notification
{
    // private data
    private String title;
    private GregorianCalendar dateTime;
    private MultilineString details;
    private boolean read;
    private Object link;    // possibly to be replaced by "Linkable" or "Viewable" super class that everything sits over

    // public methods


    // getters
    public String getTitle()
    {
        // initial set up code below - check if this needs updating
        return title;
    }
    public GregorianCalendar getDateTime()
    {
        // initial set up code below - check if this needs updating
        return dateTime;
    }
    public MultilineString getDetails()
    {
        // initial set up code below - check if this needs updating
        return details;
    }
    public boolean getRead()
    {
        // initial set up code below - check if this needs updating
        return read;
    }
    public Object getLink()
    {
        // initial set up code below - check if this needs updating
        return link;
    }


    // setters
    public void setRead(boolean newRead)
    {
        // initial set up code below - check if this needs updating
        read = newRead;
    }


    // constructors
}
