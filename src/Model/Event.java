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

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * ${FILENAME}
 * Created by Andrew Odintsov on 4/27/17.
 */
public class Event extends VersionControlEntity
{
    protected GregorianCalendar date = null;

    private static Pattern dateRegex = Pattern.compile("(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");

    // public methods

    /**
     * Validates the given String
     *
     * @param dateString a String containing a Date
     * @return whether the given String is a valide date
     */
    public static boolean validDateString(String dateString)
    {
        return dateRegex.matcher(dateString).matches();
    }

    // getters

    /**
     * Returns a Date object containing this Date
     *
     * @return Date object
     */
    public Date getDate()
    {
        return this.date.getTime();
    }

    public GregorianCalendar getCalendar()
    {
        return date;
    }

    public String toString()
    {
        return this.date.getTime().toString();
    }

    // setters:
    public void setDate(String dateString)
    {
        // 09/04/2017T15:00:00Z
        if (validDateString(dateString))
        {
            String sDay = dateString.substring(0, 2);
            String sMonth = dateString.substring(3, 5);
            String sYear = dateString.substring(6, 10);
            String sHour = dateString.substring(11, 13);
            String sMinute = dateString.substring(14, 16);
            String sSecond = dateString.substring(17, 19);
            if (MainController.isNumeric(sDay) && MainController.isNumeric(sMonth) && MainController.isNumeric(sYear) &&
                    MainController.isNumeric(sHour) && MainController.isNumeric(sMinute) &&
                    MainController.isNumeric(sSecond))
            {
                date = new GregorianCalendar(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay)
                        , Integer.parseInt(sHour), Integer.parseInt(sMinute), Integer.parseInt(sSecond));
            }
        }
    }

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof Event)
        {
            Event castedVCE = (Event) receivedVCE;
            if (castedVCE.getCalendar() != null)
            {
                this.date = castedVCE.getCalendar();
            }
        }
        super.replace(receivedVCE);
    }

    // Constructors:
    public Event(String cDate)
    {
        setDate(cDate);
    }

    public Event()
    {
    }
}
