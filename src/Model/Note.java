package Model;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Note implements Serializable
{
    // private data
    private String title;
    private GregorianCalendar timeStamp;
    private MultilineString text;

    // public methods

    // getters
    public String getTitle()
    {
        // initial set up code below - check if this needs updating
        return title;
    }

    public GregorianCalendar getTimeStamp()
    {
        // initial set up code below - check if this needs updating
        return timeStamp;
    }

    public MultilineString getText()
    {
        return text;
    }

    // setters
    public void setTitle(String newTitle)
    {
        // initial set up code below - check if this needs updating
        title = newTitle;
    }

    public void setTimeStamp(GregorianCalendar newTimeStamp)
    {
        // initial set up code below - check if this needs updating
        timeStamp = newTimeStamp;
    }

    public void setTimeStamp(int Y, int M, int D, int h, int m, int s)
    {
        // initial set up code below - check if this needs updating
        timeStamp = new GregorianCalendar(Y, M, D, h, m, s);
    }

    public void setText(MultilineString newText)
    {
        // initial set up code below - check if this needs updating
        text = newText;
    }

    public Note(String title, GregorianCalendar timeStamp, MultilineString text)
    {
        this.title = title;
        this.timeStamp = timeStamp;
        this.text = text;
    }
}
