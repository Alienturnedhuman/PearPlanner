package Model;

/**
 * Created by bendickson on 4/27/17.
 */
public class Account
{
    // private data
    private Person studentDetails;
    private String studentNumber;




    // public methods


    // getters
    public Person getStudentDetails()
    {
        // initial set up code below - check if this needs updating
        return studentDetails;
    }
    public String getStudentNumber()
    {
        // initial set up code below - check if this needs updating
        return studentNumber;
    }

    // setters
    public void setStudentDetails(Person newStudentDetails)
    {
        // initial set up code below - check if this needs updating
        studentDetails = newStudentDetails;
    }
    public void setStudentNumber(String newStudentNumber)
    {
        // initial set up code below - check if this needs updating
        studentNumber = newStudentNumber;
    }

    // constructors
}
