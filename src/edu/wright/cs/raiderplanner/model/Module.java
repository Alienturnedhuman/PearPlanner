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

package edu.wright.cs.raiderplanner.model;

import edu.wright.cs.raiderplanner.controller.MainController;
import edu.wright.cs.raiderplanner.controller.MenuController;

import java.util.ArrayList;

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17 at 20:59
 */
public class Module extends VersionControlEntity
{
    // private data
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private Person organiser;
    private String moduleCode;
    private ArrayList<TimetableEvent> timetable = new ArrayList<>();

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof Module)
        {
            Module castedVCE = (Module) receivedVCE;
            if (castedVCE.getOrganiser() != null)
            {
                this.organiser = castedVCE.getOrganiser();
            }
            if (castedVCE.getModuleCode() != null)
            {
                this.moduleCode = castedVCE.getModuleCode();
            }
            if (castedVCE.getAssignments() != null)
            {
                this.assignments = castedVCE.getAssignments();
            }
            if (castedVCE.getAssignments() != null)
            {
                this.timetable = castedVCE.getTimetable();
            }
        }

        super.replace(receivedVCE);
    }


    // public methods
    public String toString(boolean verbose)
    {
        if (verbose)
        {
            StringBuilder r = new StringBuilder();
            r.append(toString());
            r.append("\n");
            r.append("Organiser: " + organiser.toString());
            r.append("\n");
            r.append("Total Assignments: " + Integer.toString(assignments.size()));
            r.append("\n");

            int i = -1;
            int ii = assignments.size();

            while (++i < ii)
            {
                r.append("\n");
                r.append(assignments.get(i).toString(true));
            }

            return r.toString();

        } else
        {
            return toString();
        }
    }

    @Override
    public String toString()
    {
        return "Module: " + this.name + " ( " + this.moduleCode + " )";
    }

    // getters
    public ArrayList<Assignment> getAssignments()
    {
        return assignments;
    }

    public Person getOrganiser()
    {
        return organiser;
    }

    public String getModuleCode()
    {
        return moduleCode;
    }

    public ArrayList<TimetableEvent> getTimetable()
    {
        return timetable;
    }

    public int getNoOfAssignments()
    {
        return this.assignments.size();
    }

    /**
     * Calculates how much of this Module has been completed in percentage.
     *
     * @return int (0-100)
     */
    public int calculateProgress()
    {
        if (this.assignments.size() == 0)
            return 0;

        int sum = 0;
        for (Assignment assignment : this.assignments)
            sum += assignment.calculateProgress();
        return sum / this.assignments.size();
    }

    // setters
    public void addAssignment(Assignment newAssignment)
    {
        // initial set up code below - check if this needs updating
        if (!assignments.contains(newAssignment))
        {
            assignments.add(newAssignment);
        }
    }

    public void removeAssignment(Assignment newAssignment)
    {
        // initial set up code below - check if this needs updating
        if (assignments.contains(newAssignment))
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
        if (!timetable.contains(newTimetableEvent))
        {
            timetable.add(newTimetableEvent);
        }
    }

    public void removeTimetableEvent(TimetableEvent newTimetableEvent)
    {
        if (timetable.contains(newTimetableEvent))
        {
            timetable.remove(newTimetableEvent);
        }
    }

    @Override
    public void open(MenuController.Window current)
    {
        MainController.ui.moduleDetails(this, current);
    }

    // constructors
    public Module(Person cOrganiser, String cModuleCode)
    {
        setOrganiser(cOrganiser);
        setModuleCode(cModuleCode);
    }
}
