package Model;

import java.io.Serializable;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Account implements Serializable
{
    // private data
    private Person studentDetails;
    private String studentNumber;

    // public methods

    // getters
    public Person getStudentDetails()
    {
        return studentDetails;
    }

    public String getStudentNumber()
    {
        return studentNumber;
    }

    // setters
    public void setStudentDetails(Person newStudentDetails)
    {
        studentDetails = newStudentDetails;
    }

    public void setStudentNumber(String newStudentNumber)
    {
        studentNumber = newStudentNumber;
    }

    // constructors
    public Account(Person studentDetails, String studentNumber)
    {
        this.studentDetails = studentDetails;
        this.studentNumber = studentNumber;
    }
}
