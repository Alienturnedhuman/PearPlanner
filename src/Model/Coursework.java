package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Coursework extends Assignment
{
    private Event startDate;
    private Deadline deadline;
    private ArrayList<Note> notes;
    private ArrayList<Extension> extensions;

    // private methods
    void replace(Coursework receivedVCE)
    {
        this.startDate = receivedVCE.getStartDate();
        this.deadline = receivedVCE.getDeadline();
        // this.notes = receivedVCE.getResit(); // do not update as these are added by the user, not by the hub
        this.extensions = receivedVCE.getExtensions();

        super.replace(receivedVCE);
    }
    // public methods

    // getters
    public Event getStartDate()
    {
        return startDate;
    }
    public Deadline getDeadline()
    {
        return deadline;
    }
    public ArrayList<Extension> getExtensions()
    {
        return extensions;
    }

    public ArrayList<Note> getNotes()
    {
        return notes;
    }
    // setters
    public void addNote(Note newNote)
    {
        if(!notes.contains(newNote))
        {
            notes.add(newNote);
        }
    }
    public void removeNote(Note oldNote)
    {
        if(notes.contains(oldNote))
        {
            notes.remove(oldNote);
        }
    }

}
