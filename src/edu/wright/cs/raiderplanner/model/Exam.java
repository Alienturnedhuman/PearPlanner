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

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Exam extends Assignment
{
    // private data
    private Exam resit = null;
    private ExamEvent timeSlot = null;

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof Exam)
        {
            Exam castedVCE = (Exam) receivedVCE;
            if (castedVCE.getResit() != null)
            {
                this.resit = castedVCE.getResit();
            }
            if (castedVCE.getTimeSlot() != null)
            {
                this.timeSlot = castedVCE.getTimeSlot();
            }
        }

        super.replace(receivedVCE);
    }

    // public methods

    // getters
    public Exam getResit()
    {
        return resit;
    }

    public ExamEvent getTimeSlot()
    {
        return timeSlot;
    }


    // constructors
    public Exam(int cWeighting, Person cSetBy, Person cMarkedBy, Person cReviewedBy, int cMarks, ExamEvent cTimeSlot, Exam cResit)
    {
        super(cWeighting, cSetBy, cMarkedBy, cReviewedBy, cMarks);
        timeSlot = cTimeSlot;
        resit = cResit;
    }

    public Exam(int cWeighting, Person cSetBy, Person cMarkedBy, Person cReviewedBy, int cMarks, ExamEvent cTimeSlot)
    {
        super(cWeighting, cSetBy, cMarkedBy, cReviewedBy, cMarks);
        timeSlot = cTimeSlot;
        resit = null;
    }
}
