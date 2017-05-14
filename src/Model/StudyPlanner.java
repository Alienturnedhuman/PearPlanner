package Model;

import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
    private ArrayList<Activity> activityList = new ArrayList<Activity>();
    private ArrayList<TimeTableEventType> timeTableEventTypes = new ArrayList<TimeTableEventType>();
    private ArrayList<Event> calendar = new ArrayList<Event>();
    private ArrayList<Notification> notifications = new ArrayList<Notification>();

    private StudyProfile currentStudyProfile;

    // public methods

    // getters

    /**
     * returns a String array of studyProfile names
     *
     * @return
     */
    public String[] getListOfStudyProfileNames()
    {
        int i = -1;
        String[] r = new String[studyProfiles.size()];
        while (++i < studyProfiles.size())
        {
            r[i] = studyProfiles.get(i).getName();
        }
        return r;
    }

    /**
     * Returns an array of study profiles
     *
     * @return
     */
    public StudyProfile[] getStudyProfiles()
    {
        StudyProfile[] sp = new StudyProfile[this.studyProfiles.size()];
        sp = this.studyProfiles.toArray(sp);
        return sp;
    }

    public boolean containsStudyProfile(int sYear, int sSem)
    {
        int i = -1;
        int ii = studyProfiles.size();
        while (++i < ii)
        {
            if (studyProfiles.get(i).matches(sYear, sSem))
            {
                return true;
            }
        }
        return false;
    }

    public void addEventToCalendar(Event event)
    {
        if (!calendar.contains(event))
        {
            calendar.add(event);
        }
    }

    /**
     * Returns the current StudyProfile
     *
     * @return current StudyProfile
     */
    public StudyProfile getCurrentStudyProfile()
    {
        return this.currentStudyProfile;
    }

    public String getUserName()
    {
        return this.account.getStudentDetails().getPreferredName();
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
    public boolean setCurrentStudyProfile(StudyProfile profile)
    {
        if (this.studyProfiles.contains(profile))
        {
            if (this.currentStudyProfile != null)
                this.currentStudyProfile.setCurrent(false);
            this.currentStudyProfile = profile;
            profile.setCurrent(true);
            return true;
        }
        return false;
    }

    public boolean setCurrentStudyProfile(String profileID)
    {
        this.studyProfiles.forEach(e -> {
            if (e.getUID().equals(profileID))
                this.setCurrentStudyProfile(e);
        });

        return this.currentStudyProfile.getUID().equals(profileID);
    }

    /**
     * Adds a new StudyProfile to the StudyPlanner
     *
     * @param profile
     */
    public void addStudyProfile(StudyProfile profile)
    {
        this.studyProfiles.add(profile);
    }

    public void addNotification(Notification notification)
    {
        this.notifications.add(notification);
    }

    /**
     * Add an Activity to this Study Planner and update appropriate fields.
     *
     * @param activity Activity to be added.
     */
    public void addActivity(Activity activity)
    {
        this.activityList.add(activity);
        for (Task t : activity.getTasks())
        {
            for (Requirement r : t.getRequirements())
            {
                if (r.getQuantityType().equals(activity.getType()) && !r.checkedCompleted)
                {
                    // TODO carry over requirements
                    r.addActivity(activity);
                    break;
                }
            }
            // TODO carry over requirements
            for (Assignment a : t.getAssignmentReferences())
                a.getRequirements().stream()
                        .filter(e -> e.getQuantityType().equals(activity.getType()) && !e.checkedCompleted)
                        .findFirst().ifPresent(e -> e.addActivity(activity));
        }
    }

    // constructors

    public StudyPlanner(Account newAccount) throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        // it may make sense to clone this to stop someone retaining access to the
        // object
        this.account = newAccount;
    }
}
