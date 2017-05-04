package Model;


import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by bijan on 04/05/2017.
 */
public class AccountTest {
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }

    @Test
    public void getStudentDetails() throws Exception {
        ArrayList<String> personName = new ArrayList<>();
        personName.add("John");

        Person person = new Person("Mr.",personName, "Wick", true);

        Account account = new Account(person, "10012721-UG");


        //assertEquals(person, account.getStudentDetails());
    }

    @Test
    public void getStudentNumber() throws Exception {
    }

    @Test
    public void setStudentDetails() throws Exception {
    }

    @Test
    public void setStudentNumber() throws Exception {
    }

}