package Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bijan on 04/05/2017.
 */
public class PersonTest {

    ArrayList<String> personName = new ArrayList<>();
    Person person;

    //TODO Write test for replace
    @Test
    public void replace() throws Exception
    {
    }

    @Test
    public void getFullName() throws Exception
    {
        personName.add("James");
        person = new Person("Mr", personName, "Smith", true);
        String expectedFullName = "Mr James Smith";
        assertEquals(expectedFullName, person.getFullName());

        person = new Person("Mr", personName, "Smith", false);
        expectedFullName = "Mr Smith James";
        assertEquals(expectedFullName, person.getFullName());
    }

    @Test
    public void getFamilyName() throws Exception
    {
        personName.add("James");
        person = new Person("Mr", personName, "Smith", true);
        String expectedFamilyName = "Smith";
        assertEquals(expectedFamilyName, person.getFamilyName());
    }

    //TODO Fix constructors
    @Test
    public void getEmail() throws Exception
    {
//        personName.add("James");
//        person = new Person("Mr", personName, "Smith", true);
//        String expectedemail = "something@gmail.com";
//        assertEquals(expectedemail, person.getEmail());
    }

    @Test
    public void getGivenNames() throws Exception
    {
        // Testing with one name
        personName.add("James");
        person = new Person("Mr", personName, "Smith", true);
        assertEquals(personName, person.getGivenNames());

        // Testing with multiple names
        personName.add("Jake");
        personName.add("Tom");
        person = new Person("Mr", personName, "Smith", true);
        assertEquals(personName, person.getGivenNames());
    }

    @Test
    public void getFamilyNameLast() throws Exception
    {
        // Testing with TRUE value for familyNameLast
        personName.add("James");
        person = new Person("Mr", personName, "Smith", true);
        assertEquals(true, person.getFamilyNameLast());

        // Testing with FALSE value for familyNameLast
        personName.add("James");
        person = new Person("Mr", personName, "Smith", false);
        assertEquals(false, person.getFamilyNameLast());
    }

    @Test
    public void getSalutation() throws Exception
    {
        personName.add("James");
        person = new Person("Mr", personName, "Smith", true);
        String expectedSalutation = "Mr";
        assertEquals(expectedSalutation, person.getSalutation());
    }

    @Test
    public void hasSalutation() throws Exception
    {
        // Testing with salutation
        personName.add("James");
        person = new Person("Mr", personName, "Smith", true);
        assertEquals(true, person.hasSalutation());

        // Testing without salutation
        personName.add("James");
        person = new Person("", personName, "Smith", true);
        assertEquals(false, person.hasSalutation());
    }

    @Test
    public void getPreferredName() throws Exception
    {
        personName.add("James");
        person = new Person("Mr", personName, "Smith", true);
        assertEquals(personName.get(0), person.getPreferredName());
    }

    @Test
    public void setFamilyName() throws Exception
    {
    }

    @Test
    public void setGivenNames() throws Exception
    {
    }

    @Test
    public void setName() throws Exception
    {
    }

    @Test
    public void setSalutation() throws Exception
    {
    }

}