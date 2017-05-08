package Model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * ${FILENAME}
 * Created by Andrew Odintsov on 4/27/17.
 */
public class Event
{
    protected GregorianCalendar date;
    protected String name;
    protected MultilineString details;
    protected ArrayList<Note> notes;

    // public methods

    // getters
    public String getName()
    {
        return name;
    }

    // setters
    public void setName(String newName)
    {
        name = newName;
    }
}
