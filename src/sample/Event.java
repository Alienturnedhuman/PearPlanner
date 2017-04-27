package sample;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bendickson on 4/27/17.
 */
public class Event
{
    protected Date date;
    protected String name;
    protected ArrayList<String> details;
    protected ArrayList<Note> notes;

    // public methods

    // getters
    public String getName()
    {
        // initial set up code below - check if this needs updating
        return name;
    }

    // setters
    public void setName(String newName)
    {
        // initial set up code below - check if this needs updating
        name = newName;
    }
}
