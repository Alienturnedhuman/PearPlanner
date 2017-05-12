package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class TaskType extends ModelEntity
{
    private static ArrayList<TaskType> taskDatabase = new ArrayList<>();
    // empty class until we add GUI

    public static String[] listOfNames()
    {
        String[] r = new String[taskDatabase.size()];
        int i = -1;
        int ii = taskDatabase.size();
        while(++i<ii)
        {
            r[i] = taskDatabase.get(i).getName();
        }
        return r;
    }
    public static TaskType[] listOfTaskTypes()
    {
        TaskType[] r = new TaskType[taskDatabase.size()];
        int i = -1;
        int ii = taskDatabase.size();
        while(++i<ii)
        {
            r[i] = taskDatabase.get(i);
        }
        return r;
    }

    public static boolean exists(TaskType tt)
    {
        int i=-1;
        int ii = taskDatabase.size();
        while(++i<ii)
        {
            if(taskDatabase.get(i).equals(tt))
            {
                return true;
            }
        }
        return false;
    }
    public static TaskType exists(String tt)
    {
        int i=-1;
        int ii = taskDatabase.size();
        while(++i<ii)
        {
            if(taskDatabase.get(i).equals(tt))
            {
                return taskDatabase.get(i);
            }
        }
        return null;
    }

    // this is a temporary way to populate the array until we later replace from reading a set up file
    static
    {
        class pair
        {
            public String a;
            public String b;
            pair(String name,String details)
            {
                a = name;
                b = details;
            }
        }
        pair[] staticTypes = {
                new pair("Reading","Read some required text")
                ,
                new pair("Exercises","Did some assigned exercises")
                ,
                new pair("Listening","Listened to a podcast")
                ,
                new pair("Coursework","Worked towards coursework")
                ,
                new pair("Revision","Revised towards exam")
                ,
                new pair("Meeting","Meet with other course members")
                ,
                new pair("Other","Other type of task")
        };
        int i = -1;
        int ii = staticTypes.length;
        while(++i<ii)
        {
            TaskType t = new TaskType(staticTypes[i].a,staticTypes[i].b);
        }
    }

    private TaskType(String cName, String cDetails)
    {
        this.name = cName;
        if(!exists(this))
        {
            taskDatabase.add(this);
        }
    }

    public boolean equals(TaskType c)
    {
        return getName().equals(c.getName());
    }
    public boolean equals(String c)
    {
        return getName().equals(c);
    }
}
