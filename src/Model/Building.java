package Model;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Building extends VersionControlEntity
{
    // private Data
    private String code = null;
    private double latitude;
    private double longitude;

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof Building)
        {
            Building castedVCE = (Building) receivedVCE;
            if (castedVCE.getCode() != null)
            {
                this.code = castedVCE.getCode();
            }
            this.latitude = castedVCE.getLatitude();
            this.longitude = castedVCE.getLongitude();
        }

        super.replace(receivedVCE);
    }

    // getters
    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    // setters
    public void setName(String newName)
    {
        name = newName;
    }

    public void setCode(String newCode)
    {
        code = newCode;
    }

    public void setLatitude(double newLatitude)
    {
        latitude = newLatitude;
    }

    public void setLongitude(double newLongitude)
    {
        longitude = newLongitude;
    }

    // constructor
    public Building(String cCode, double cLatitude, double cLongitude)
    {
        code = cCode;
        latitude = cLatitude;
        longitude = cLongitude;
    }

    @Override
    public String toString()
    {
        return code + " " + name + " ( " + Double.toString(latitude) +
                " , " + Double.toString(longitude) + " )";
    }
}

