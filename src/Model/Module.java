package Model;

import java.util.ArrayList;

/**
 * Created by bendickson on 4/27/17.
 */
public class Module extends VersionControlEntity
{
    private ArrayList<Assignment> assignments;
    private Person organiser;
    private String moduleCode;
    private ArrayList<TimetableEvent> timetable;

}
