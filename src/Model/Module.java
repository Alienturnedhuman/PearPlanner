package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17 at 20:59
 */
public class Module extends VersionControlEntity
{
    private ArrayList<Assignment> assignments;
    private Person organiser;
    private String moduleCode;
    private ArrayList<TimetableEvent> timetable;

}
