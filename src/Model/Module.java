package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17 at 20:59
 */
public class Module extends VersionControlEntity
{
    // private data
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private Person organiser;
    private String moduleCode;
    private ArrayList<TimetableEvent> timetable;


    // private methods
    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if(receivedVCE instanceof Module)
        {
            Module castedVCE = (Module)receivedVCE;
            this.organiser = castedVCE.getOrganiser();
            this.moduleCode = castedVCE.getModuleCode();
            this.assignments = castedVCE.getAssignments();
            this.timetable = castedVCE.getTimetable();
        }

        super.replace(receivedVCE);
    }


    // public methods

    // getters
    public ArrayList<Assignment> getAssignments()
    {
        // initial set up code below - check if this needs updating
        return assignments;
    }

    public Person getOrganiser()
    {
        // initial set up code below - check if this needs updating
        return organiser;
    }

    public String getModuleCode()
    {
        // initial set up code below - check if this needs updating
        return moduleCode;
    }

    public ArrayList<TimetableEvent> getTimetable()
    {
        // initial set up code below - check if this needs updating
        return timetable;
    }



    // setters
    public void addAssignment(Assignment newAssignment)
    {
        // initial set up code below - check if this needs updating
        if(!assignments.contains(newAssignment))
        {
            assignments.add(newAssignment);
        }
    }
    public void removeAssignment(Assignment newAssignment)
    {
        // initial set up code below - check if this needs updating
        if(assignments.contains(newAssignment))
        {
            assignments.remove(newAssignment);
        }
    }
    public void setOrganiser(Person newOrganiser)
    {
        organiser = newOrganiser;
    }
    public void setModuleCode(String newModuleCode)
    {
        moduleCode = newModuleCode;
    }
    public void addTimetableEvent(TimetableEvent newTimetableEvent)
    {
        if(!timetable.contains(newTimetableEvent))
        {
            timetable.add(newTimetableEvent);
        }
    }
    public void removeTimetableEvent(TimetableEvent newTimetableEvent)
    {
        if(timetable.contains(newTimetableEvent))
        {
            timetable.remove(newTimetableEvent);
        }
    }
    // constructors

    public Module(Person cOrganiser , String cModuleCode)
    {
        setOrganiser(cOrganiser);
        setModuleCode(cModuleCode);
    }


}
