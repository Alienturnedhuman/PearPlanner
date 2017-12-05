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

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Note implements Serializable
{
    // private data
    private String title;
    private GregorianCalendar timeStamp;
    private MultilineString text;

    // public methods

    // getters
    public String getTitle()
    {
        // initial set up code below - check if this needs updating
        return title;
    }

    public GregorianCalendar getTimeStamp()
    {
        // initial set up code below - check if this needs updating
        return timeStamp;
    }

    public MultilineString getText()
    {
        return text;
    }

    // setters
    public void setTitle(String newTitle)
    {
        // initial set up code below - check if this needs updating
        title = newTitle;
    }

    public void setTimeStamp(GregorianCalendar newTimeStamp)
    {
        // initial set up code below - check if this needs updating
        timeStamp = newTimeStamp;
    }

    public void setTimeStamp(int Y, int M, int D, int h, int m, int s)
    {
        // initial set up code below - check if this needs updating
        timeStamp = new GregorianCalendar(Y, M, D, h, m, s);
    }

    public void setText(MultilineString newText)
    {
        // initial set up code below - check if this needs updating
        text = newText;
    }

    public Note(String title, GregorianCalendar timeStamp, MultilineString text)
    {
        this.title = title;
        this.timeStamp = timeStamp;
        this.text = text;
    }
}
