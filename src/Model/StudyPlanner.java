package Model;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class StudyPlanner implements Serializable
{
    // private data
    private static final long serialVersionUID = 101L; //probably needs to be linked to the version control or such

    private Account account;
    private ArrayList<QuantityType> quantityTypes = new ArrayList<QuantityType>();
    private ArrayList<TaskType> taskTypes = new ArrayList<TaskType>();
    private ArrayList<StudyProfile> studyProfiles = new ArrayList<StudyProfile>();
    private ArrayList<ActivityEvent> activityList = new ArrayList<ActivityEvent>();
    private ArrayList<TimeTableEventType> timeTableEventTypes = new ArrayList<TimeTableEventType>();
    private ArrayList<Event> calendar = new ArrayList<Event>();
    private ArrayList<Notification> notifications = new ArrayList<Notification>();

    // public methods

    // getters
    public String getUserName()
    {
        return this.account.getStudentDetails().getFullName();
    }

    /**
     * returns a String array of studyProfile names
     *
     * @return
     */
    public String[] getListOfStudyProfiles()
    {
        int i = -1;
        String[] r = new String[studyProfiles.size()];
        while (++i < studyProfiles.size())
        {
            r[i] = studyProfiles.get(i).getName();
        }
        return r;
    }

    public Notification[] getNotifications()
    {
        Notification[] r = new Notification[this.notifications.size()];
        r = this.notifications.toArray(r);
        return r;
    }

    public Notification[] getUnreadNotifications()
    {
        Notification[] r = this.notifications.stream().filter(e -> !e.isRead()).toArray(Notification[]::new);
        return r;
    }

    // setters

    public void loadFile(String filePath)
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    public void processHubFile(HubFile newHubFile)
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    public void addNotification(Notification notification)
    {
        this.notifications.add(notification);
    }

    // constructors

    public StudyPlanner(Account newAccount) throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        // it may make sense to clone this to stop someone retaining access to the
        // object
        account = newAccount;
    }
}
