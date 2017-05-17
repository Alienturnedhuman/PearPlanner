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
