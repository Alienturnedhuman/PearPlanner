package Model;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Notification implements Serializable
{
    // private data
    private String title;
    private GregorianCalendar dateTime;
    private MultilineString details;
    private boolean read;
    private ModelEntity link;

    // public methods

    // getters
    public String getTitle()
    {
        return title;
    }

    public GregorianCalendar getDateTime()
    {
        return dateTime;
    }

    public MultilineString getDetails()
    {
        return details;
    }

    public String getDetailsAsString()
    {
        return this.details.getAsString();
    }

    public boolean isRead()
    {
        return read;
    }

    public ModelEntity getLink()
    {
        return link;
    }

    public String toString()
    {
        return this.title + ": " + this.getDetailsAsString();
    }

    // setters
    public void read()
    {
        this.read = true;
    }

    public void unread()
    {
        this.read = false;
    }

    public void toggle()
    {
        this.read = !read;
    }

    // constructors
    public Notification(String title, GregorianCalendar dateTime, String details, ModelEntity link)
    {
        this.title = title;
        this.dateTime = dateTime;
        this.details = new MultilineString(details);
        this.read = false;
        this.link = link;
    }

    public Notification(String title, GregorianCalendar dateTime, String details)
    {
        this.title = title;
        this.dateTime = dateTime;
        this.details = new MultilineString(details);
        this.read = false;
    }

    public Notification(String title, GregorianCalendar dateTime)
    {
        this.title = title;
        this.dateTime = dateTime;
        this.read = false;
    }
}