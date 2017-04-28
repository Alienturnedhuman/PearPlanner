package Model;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Building
{
    // private Data
    private String name;
    private String code;
    private double latitude;
    private double longitude;

    // getters
    public String getName()
    {
        // initial set up code below - check if this needs updating
        return name;
    }
    public String getCode()
    {
        // initial set up code below - check if this needs updating
        return code;
    }
    public double getLatitude()
    {
        // initial set up code below - check if this needs updating
        return latitude;
    }
    public double getLongitude()
    {
        // initial set up code below - check if this needs updating
        return longitude;
    }

    // setters
    public void setName(String newName)
    {
        // initial set up code below - check if this needs updating
        name = newName;
    }
    public void setCode(String newCode)
    {
        // initial set up code below - check if this needs updating
        code = newCode;
    }
    public void setLatitude(double newLatitude)
    {
        // initial set up code below - check if this needs updating
        latitude = newLatitude;
    }
    public void setLongitude(double newLongitude)
    {
        // initial set up code below - check if this needs updating
        longitude = newLongitude;
    }

    @Override
    public String toString()
    {
        return code+" "+name;
    }
}

