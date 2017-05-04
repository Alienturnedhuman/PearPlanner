package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class StudyPlanner {
    // private data
    private Account account;
    private ArrayList<QuantityType> quantityTypes;
    private ArrayList<TaskType> taskTypes;
    private ArrayList<StudyProfile> studyProfiles;
    private ArrayList<ActivityEvent> activityList;
    private ArrayList<TimeTableEventType> timeTableEventTypes;
    private ArrayList<Event> calendar;


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
