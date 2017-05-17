package Model;


/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Room extends VersionControlEntity
{
    // private data
    private Building building = null;
    private String roomNumber;

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if (receivedVCE instanceof Room)
        {
            Room castedVCE = (Room) receivedVCE;
            if (castedVCE.getBuilding() != null)
            {
                this.building = castedVCE.getBuilding();
            }
            this.roomNumber = castedVCE.getRoomNumber();
        }
        super.replace(receivedVCE);
    }

    // public methods

    // getters
    public Building getBuilding()
    {
        return building;
    }

    public String getRoomNumber()
    {
        return roomNumber;
    }

    public String getLocation()
    {
        return name + "( " + roomNumber + " )";
    }

    @Override
    public String toString()
    {
        if (building == null)
        {
            return name + "( " + roomNumber + " )";
        } else
        {
            return name + "( " + roomNumber + " ) located in " + building.toString();
        }
    }

    // setters
    public void setBuilding(Building newBuilding)
    {
        building = newBuilding;
    }

    public void setRoomNumber(String newRoomNumber)
    {
        roomNumber = newRoomNumber;
    }

    // Constructors:
    public Room(String cRoomNumber, Building cBuilding)
    {
        setRoomNumber(cRoomNumber);
        setBuilding(cBuilding);
    }

    public Room(String cRoomNumber)
    {
        setRoomNumber(cRoomNumber);
    }
}
