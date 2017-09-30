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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bendickson on 4/27/17.
 */
public class MultilineString implements Serializable
{
    // private Data;
    private ArrayList<String> lines;

    // public methods
    public MultilineString clone()
    {
        return new MultilineString(this.getAsArray());
    }

    // getters

    /**
     * Returns the number of lines in this MultilineString
     *
     * @return number of lines
     */
    public int getLines()
    {
        return lines.size();
    }

    public ArrayList<String> getAsArrayList()
    {
        return lines;
    }

    public String[] getAsArray()
    {
        String r[] = new String[lines.size()];
        r = lines.toArray(r);
        return r;
    }

    public String getAsString()
    {
        return String.join("\n", getAsArray());
    }

    public MultilineString()
    {
        lines = new ArrayList<>();
    }

    public MultilineString(String mString)
    {
        lines = new ArrayList<>(Arrays.asList(mString.split("\n")));
    }

    public MultilineString(String[] mString)
    {
        lines = new ArrayList<>(Arrays.asList(mString));
    }
}
