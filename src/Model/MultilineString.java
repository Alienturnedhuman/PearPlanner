package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bendickson on 4/27/17.
 */
public class MultilineString
{
    // private Data;
    private ArrayList<String> lines;


    // public methods

    // getters
    public int getLines()
    {
        // initial set up code below - check if this needs updating
        return lines.size();
    }
    public ArrayList<String> getAsArrayList()
    {
        // initial set up code below - check if this needs updating
        return lines;
    }

    public String[] getAsArray()
    {
        // initial set up code below - check if this needs updating
        String r[] = new String[lines.size()];
        r = lines.toArray(r);
        return r;
    }
    public String getAsString()
    {
        // initial set up code below - check if this needs updating
        return String.join("\n",getAsArray());
    }

    MultilineString()
    {
        lines = new ArrayList<>();
    }
    MultilineString(String mString)
    {
        lines = new ArrayList<>(Arrays.asList(mString.split("\n")));
    }
    MultilineString(String[] mString)
    {
        lines = new ArrayList<>(Arrays.asList(mString));
    }
}
