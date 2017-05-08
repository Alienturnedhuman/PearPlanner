package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class StudyPlanner {
    // private data
    private Account account;
    private ArrayList<QuantityType> quantityTypes = new ArrayList<QuantityType>();
    private ArrayList<TaskType> taskTypes = new ArrayList<TaskType>();
    private ArrayList<StudyProfile> studyProfiles = new ArrayList<StudyProfile>();
    private ArrayList<ActivityEvent> activityList = new ArrayList<ActivityEvent>();
    private ArrayList<TimeTableEventType> timeTableEventTypes = new ArrayList<TimeTableEventType>();
    private ArrayList<Event> calendar = new ArrayList<Event>();
    private ArrayList<Notification> notifications = new ArrayList<Notification>();


    /**
     * returns a String array of studyProfile names
     * @return
     */
    public String[] getListOfStudyProfiles()
    {
        int i = -1;
        String[] r = new String[studyProfiles.size()];
        while(++i<studyProfiles.size())
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

    // public methods
    void loadFile (String filePath)
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    // getters
    void processHubFile(HubFile newHubFile)
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    // setters



    // constructor
    public StudyPlanner(Account newAccount)
    {
        // it may make sense to clone this to stop someone retaining access to the
        // object
        account = newAccount;
    }


}
