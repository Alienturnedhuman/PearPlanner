package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bijan on 04/05/2017.
 */
public class AccountTest {

    Person person;
    Account account;

    @Before
    public void setUp() throws Exception {
        person = new Person("Mr","Andrew Odintsov", true);
        account = new Account(person, "10012721-UG");
    }

    @Test
    public void getStudentDetails() throws Exception
    {
        assertEquals(person, account.getStudentDetails());
    }

    @Test
    public void getStudentNumber() throws Exception
    {
        assertEquals("10012721-UG", account.getStudentNumber());
    }

    @Test
    public void setStudentDetails() throws Exception
    {
        Person person2 = new Person("Dr","Zilvinas Ceikauskas", true, "zil.Cei@gmail.com");
        account.setStudentDetails(person2);
        assertEquals(person2, account.getStudentDetails());
    }

    @Test
    public void setStudentNumber() throws Exception
    {
        account.setStudentNumber("99222213-UG");
        assertEquals("99222213-UG", account.getStudentNumber());
    }

}