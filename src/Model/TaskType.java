/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar
 *
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package Model;

import Controller.MainController;

import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class TaskType extends ModelEntity
{
    private static ArrayList<TaskType> taskDatabase = new ArrayList<>();

    public static String[] listOfNames()
    {
        String[] r = new String[taskDatabase.size()];
        int i = -1;
        int ii = taskDatabase.size();
        while (++i < ii)
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
        while (++i < ii)
        {
            r[i] = taskDatabase.get(i);
        }
        return r;
    }

    public static TaskType get(String tt)
    {
        int i = -1;
        int ii = taskDatabase.size();
        while (++i < ii)
        {
            if (taskDatabase.get(i).equals(tt))
            {
                return taskDatabase.get(i);
            }
        }
        return DEFAULT;
    }

    public static boolean exists(TaskType tt)
    {
        int i = -1;
        int ii = taskDatabase.size();
        while (++i < ii)
        {
            if (taskDatabase.get(i).equals(tt))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean exists(String tt)
    {
        int i = -1;
        int ii = taskDatabase.size();
        while (++i < ii)
        {
            if (taskDatabase.get(i).equals(tt))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a new TaskType.
     *
     * @param cName    Name of the TaskType.
     * @param cDetails Details of the TaskType.
     * @return
     */
    public static TaskType create(String cName, String cDetails)
    {
        TaskType t = new TaskType(cName, cDetails);
        if (MainController.getSPC() != null)
            MainController.getSPC().addTaskType(t);
        return t;
    }

    /**
     * Create a new TaskType.
     *
     * @param cName Name of the TaskType.
     * @return
     */
    public static TaskType create(String cName)
    {
        TaskType t = new TaskType(cName);
        if (MainController.getSPC() != null)
            MainController.getSPC().addTaskType(t);
        return t;
    }

    /**
     * Create a new TaskType from an existing one.
     *
     * @param type TaskType object
     */
    public static void create(TaskType type)
    {
        if (!TaskType.taskDatabase.contains(type))
        {
            TaskType.taskDatabase.add(type);
            if (MainController.getSPC() != null)
                MainController.getSPC().addTaskType(type);
        }
    }

    // this is a temporary way to populate the array until we later replace from reading a set up file
    static
    {
        class pair
        {
            public String a;
            public String b;

            pair(String name, String details)
            {
                a = name;
                b = details;
            }
        }
        pair[] staticTypes = {
                new pair("Other", "Other type of task")
                ,
                new pair("Reading", "Read some required text")
                ,
                new pair("Exercises", "Did some assigned exercises")
                ,
                new pair("Listening", "Listened to a podcast")
                ,
                new pair("Coursework", "Worked towards coursework")
                ,
                new pair("Revision", "Revised towards exam")
                ,
                new pair("Meeting", "Meet with other course members")

        };
        int i = -1;
        int ii = staticTypes.length;
        while (++i < ii)
        {
            TaskType t = new TaskType(staticTypes[i].a, staticTypes[i].b);
        }
    }

    private TaskType(String cName)
    {
        super(cName);
        if (!exists(this))
        {
            taskDatabase.add(this);
        }
    }

    private TaskType(String cName, String cDetails)
    {
        super(cName, cDetails);
        if (!exists(this))
        {
            taskDatabase.add(this);
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        TaskType that = (TaskType) obj;
        return getName().equals(that.getName());
    }

    public boolean equals(String c)
    {
        return getName().equals(c);
    }

    public static TaskType DEFAULT = taskDatabase.get(0);
}
