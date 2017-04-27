package Model;

import java.util.ArrayList;

/**
 * Created by bendickson on 4/27/17.
 */
public class Room
{
    // private data
    private String name;
    private Building building;
    private String roomNumber;
    private ArrayList<String> details;



    // public methods


    // getters
    public String getName()
    {
        // initial set up code below - check if this needs updating
        return name;
    }
    public Building getBuilding()
    {
        // initial set up code below - check if this needs updating
        return building;
    }

    public String getRoomNumber()
    {
        // initial set up code below - check if this needs updating
        return roomNumber;
    }
    public ArrayList<String> getDetails()
    {
        // initial set up code below - check if this needs updating
        return details;
    }

    // setters
    public void setName(String newName)
    {
        // initial set up code below - check if this needs updating
        name = newName;
    }
    public void setBuilding(Building newBuilding)
    {
        // initial set up code below - check if this needs updating
        building = newBuilding;
    }

    public void setRoomNumber(String newRoomNumber)
    {
        // initial set up code below - check if this needs updating
        roomNumber = newRoomNumber;
    }
    public void setDetails(ArrayList<String> newDetails)
    {
        // initial set up code below - check if this needs updating
        details = newDetails;
    }
}
