package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by bendickson on 4/27/17.
 */
public class Note
{
    // private data
    private String title;
    private Date timeStamp;
    private ArrayList<String> text;


    // public methods


    // getters
    public String getTitle()
    {
        // initial set up code below - check if this needs updating
        return title;
    }
    public Date getTimeStamp()
    {
        // initial set up code below - check if this needs updating
        return timeStamp;
    }

    // setters
    public void setTitle(String newTitle)
    {
        // initial set up code below - check if this needs updating
        title = newTitle;
    }
    private void setTimeStamp(Date newTimeStamp)
    {
        // initial set up code below - check if this needs updating
        timeStamp = newTimeStamp;
    }
    private void setTimeStamp(int Y,int M, int D, int h , int m , int s , int ms)
    {
        // initial set up code below - check if this needs updating
        timeStamp = new Date(h,m,s);
    }
}
