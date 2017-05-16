package Model;

import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class StudyPlanner implements Serializable
{
    // private data
    private static final long serialVersionUID = 101L;

    private int version = -1;
    private Account account;
    private ArrayList<QuantityType> quantityTypes = new ArrayList<>();
    private ArrayList<TaskType> taskTypes = new ArrayList<>();
    private ArrayList<StudyProfile> studyProfiles = new ArrayList<>();
    private ArrayList<Activity> activityList = new ArrayList<>();
    private ArrayList<TimeTableEventType> timeTableEventTypes = new ArrayList<>();
    private ArrayList<Event> calendar = new ArrayList<>();
    private ArrayList<Notification> notifications = new ArrayList<>();
    private HashMap<ModelEntity, boolean[]> deadlineNotifications = new HashMap<>();

    private StudyProfile currentStudyProfile;

    // public methods

    // getters

    public ArrayList<Event> getCalendar()
    {
        return calendar;
    }

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

    /**
     * Returns a HashMap that contains information about Deadline notifications.
     *
     * @return
     */
    public HashMap<ModelEntity, boolean[]> getDeadlineNotifications()
    {
        return deadlineNotifications;
    }

    /**
     * Returns an ArrayList of QuantityTypes.
     *
     * @return ArrayList<QuantityType>
     */
    public ArrayList<QuantityType> getQuantityTypes()
    {
        return this.quantityTypes;
    }

    /**
     * Returns an ArrayList of TaskTypes.
     *
     * @return ArrayList<QuantityType>
     */
    public ArrayList<TaskType> getTaskTypes()
    {
        return this.taskTypes;
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
        ArrayList<Assignment> assignments = new ArrayList<>();
        // Loop through all Tasks:
        for (Task t : activity.getTasks())
        {
            // Distribute Activity Quantity to available Requirements of a Task:
            int quantity = activity.getActivityQuantity();
            for (Requirement r : t.getRequirements())
            {
                if (r.getQuantityType().equals(activity.getType()) && !r.checkedCompleted)
                {
                    quantity -= r.getRemainingQuantity();
                    Activity extracted = new Activity(activity);

                    if (quantity > 0)
                    {
                        extracted.setActivityQuantity(r.getRemainingQuantity());
                        r.addActivity(extracted);
                    } else
                    {
                        extracted.setActivityQuantity(quantity + r.getRemainingQuantity());
                        r.addActivity(extracted);
                        break;
                    }
                }
            }
            // =================
            for (Assignment assignment : t.getAssignmentReferences())
            {
                if (!assignments.contains(assignment))
                    assignments.add(assignment);
            }
        }
        // =================

        // Distribute quantity to Assignment requirements:
        for (Assignment a : assignments)
        {
            int quantity = activity.getActivityQuantity();
            for (Requirement r : a.getRequirements())
            {
                if (r.getQuantityType().equals(activity.getType()) && !r.checkedCompleted)
                {
                    quantity -= r.getRemainingQuantity();
                    Activity extracted = new Activity(activity);

                    if (quantity > 0)
                    {
                        extracted.setActivityQuantity(r.getRemainingQuantity());
                        r.addActivity(extracted);
                    } else
                    {
                        extracted.setActivityQuantity(quantity + r.getRemainingQuantity());
                        r.addActivity(extracted);
                        break;
                    }
                }
            }
        }
        // =================
    }


    public boolean setVersion(int newVersion)
    {
        if (newVersion > version)
        {
            version = newVersion;
            return true;
        } else
        {
            return false;
        }
    }

    public int getVersion()
    {
        return version;
    }

    // constructors

    public StudyPlanner(Account newAccount) throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        this.account = newAccount;
        // Add Default Quantity types:
        Collections.addAll(this.quantityTypes, QuantityType.listOfQuantityTypes());
        // Add Default Task types:
        Collections.addAll(this.taskTypes, TaskType.listOfTaskTypes());
    }
}
