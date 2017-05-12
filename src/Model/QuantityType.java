package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class QuantityType extends ModelEntity
{
    private static ArrayList<QuantityType> quantityDatabase = new ArrayList<>();
    // empty class until we add GUI

    public static String[] listOfNames()
    {
        String[] rr = new String[quantityDatabase.size()];
        int ii = quantityDatabase.size();
        int i = -1;
        while(++i<ii)
        {
            rr[i] = quantityDatabase.get(i).getName();
        }
        return rr;
    }
    public static QuantityType[] listOfQuantityTypes()
    {
        QuantityType[] r = new QuantityType[quantityDatabase.size()];
        int i = -1;
        int ii = quantityDatabase.size();
        while(++i<ii)
        {
            r[i] = quantityDatabase.get(i);
        }
        return r;
    }

    public static boolean exists(QuantityType qt)
    {
        int i=-1;
        int ii = quantityDatabase.size();
        while(++i<ii)
        {
            if(quantityDatabase.get(i).equals(qt))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean exists(String tt)
    {
        int i=-1;
        int ii = quantityDatabase.size();
        while(++i<ii)
        {
            if(quantityDatabase.get(i).equals(tt))
            {
                return true;
            }
        }
        return false;
    }

    // this is a temporary way to populate the array until we later replace from reading a set up file
    static
    {
        class pair
        {
            public String a;
            public String b;
            pair(String name,String details)
            {
                a = name;
                b = details;
            }
        }
        pair[] staticTypes = {
                new pair("Hours","Work in hours")
                ,
                new pair("Booked read","Read this number of books")
                ,
                new pair("Videos watched","Watched this number of videos")
                ,
                new pair("thousand words written","Number of thousand words written")
                ,
                new pair("questions answers","Number of questions answered")
        };
        int i = -1;
        int ii = staticTypes.length;
        while(++i<ii)
        {
            QuantityType t = new QuantityType(staticTypes[i].a,staticTypes[i].b);
        }
    }

    private QuantityType(String cName, String cDetails)
    {
        super(cName,cDetails);
        if(!exists(this))
        {
            quantityDatabase.add(this);
        }
    }

    public boolean equals(QuantityType c)
    {
        return getName().equals(c.getName());
    }
    public boolean equals(String c)
    {
        return getName().equals(c);
    }
}
