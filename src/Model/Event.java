package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * ${FILENAME}
 * Created by Andrew Odintsov on 4/27/17.
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
