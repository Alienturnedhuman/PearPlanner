package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bijan on 04/05/2017.
 */
public class PersonTest {

    ArrayList<String> personName = new ArrayList<>();
    Person person1, person2;

    @Before
    public void setUp() throws Exception {
        personName.add("Andrew");
        person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
        person2 = new Person("Mr","Zilvinas Ceikauskas", true, "Zill.cei@apple.com");
    }

    //TODO Write test for replace
    @Test
    public void replace() throws Exception
    {
//        assertTrue(false);
    }

    @Test
    public void getFullName() throws Exception
    {
        // Testing with seperate given names and family name passed to the constructor
        String expectedFullName = "Mr Andrew Odintsov";
        assertEquals(expectedFullName, person1.getFullName());

        person1 = new Person("Mr", personName, "Odintsov", false, "Andrew.odi@apple.com");
        expectedFullName = "Mr Odintsov Andrew";
        assertEquals(expectedFullName, person1.getFullName());

        personName.add("Ben");
        person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
        expectedFullName = "Mr Andrew Ben Odintsov";
        assertEquals(expectedFullName, person1.getFullName());

        person1 = new Person("Mr", personName, "Odintsov", false, "Andrew.odi@apple.com");
        expectedFullName = "Mr Odintsov Andrew Ben";
        assertEquals(expectedFullName, person1.getFullName());

        // Testing with full name passed to the constructor
        expectedFullName = "Mr Zilvinas Ceikauskas";
        assertEquals(expectedFullName, person2.getFullName());

        person2 = new Person("Mr","Ceikauskas Zilvinas", false, "Zill.cei@apple.com");
        expectedFullName = "Mr Ceikauskas Zilvinas";
        assertEquals(expectedFullName, person2.getFullName());
    }

    @Test
    public void getFamilyName() throws Exception
    {
        // Testing with seperate given names and family name passed to the constructor
        String expectedFamilyName = "Odintsov";
        assertEquals(expectedFamilyName, person1.getFamilyName());

        // Testing with full name passed to the constructor
        expectedFamilyName = "Ceikauskas";
        assertEquals(expectedFamilyName, person2.getFamilyName());

    }

    @Test
    public void getEmail() throws Exception
    {
        // Testing with seperate given names and family name passed to the constructor
        String expectedFullName = "Andrew.odi@apple.com";
        assertEquals(expectedFullName, person1.getEmail());

        // Testing with full name passed to the constructor
        expectedFullName = "Zill.cei@apple.com";
        assertEquals(expectedFullName, person2.getEmail());
    }

    @Test
    public void getGivenNames() throws Exception
    {
        // Testing with seperate given names and family name passed to the constructor
        assertEquals(personName, person1.getGivenNames());

        personName.add("Ben");
        person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
        assertEquals(personName, person1.getGivenNames());

        // Testing with full name passed to the constructor
        ArrayList<String> person2GivenNames = new ArrayList<>();
        person2GivenNames.add("Zilvinas");
        assertEquals(person2GivenNames, person2.getGivenNames());

        person2 = new Person("Mr","Zilvinas Ben Ceikauskas", true, "Zill.cei@apple.com");
        person2GivenNames.add("Ben");
        assertEquals(person2GivenNames, person2.getGivenNames());
    }

    @Test
    public void getFamilyNameLast() throws Exception
    {
        // Testing with TRUE value for familyNameLast
        assertTrue(person1.getFamilyNameLast());

        // Testing with FALSE value for familyNameLast
        person2 = new Person("Mr","Zilvinas Ceikauskas", false, "Zill.cei@apple.com");
        assertFalse(person2.getFamilyNameLast());
    }

    @Test
    public void getSalutation() throws Exception
    {
        String expectedSalutation = "Mr";
        assertEquals(expectedSalutation, person1.getSalutation());

        expectedSalutation = "Mr";
        assertEquals(expectedSalutation, person2.getSalutation());
    }

    @Test
    public void hasSalutation() throws Exception
    {
        // Testing with salutation
        assertEquals(true, person1.hasSalutation());

        // Testing without salutation
        person2 = new Person("","Zilvinas Ceikauskas", true, "Zill.cei@apple.com");
        assertEquals(false, person2.hasSalutation());
    }

    @Test
    public void getPreferredName() throws Exception
    {
        // Testing with one name
        assertEquals("Andrew", person1.getPreferredName());
        assertEquals("Zilvinas", person2.getPreferredName());

        // Testing with multiple names
        personName.add("Ben");
        person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
        assertEquals(personName.get(0), person1.getPreferredName());

        person2 = new Person("Mr","Zilvinas Ben Ceikauskas", true, "Zill.cei@apple.com");
        assertEquals("Zilvinas", person2.getPreferredName());

        // Testing with no names
        personName.clear();
        person1 = new Person("Mr", personName, "Odintsov", true, "Andrew.odi@apple.com");
        assertEquals("Odintsov", person1.getPreferredName());

        person2 = new Person("Mr","Ceikauskas", true, "Zill.cei@apple.com");
        assertEquals("Ceikauskas", person2.getPreferredName());

        // Testing with Preferred Name set
        person1.setPreferredName("Andy");
        assertEquals("Andy", person1.getPreferredName());

        person2.setPreferredName("Zil");
        assertEquals("Zil", person2.getPreferredName());
    }

    @Test
    public void setFamilyName() throws Exception
    {
        person1.setFamilyName("Williams");
        assertEquals("Williams", person1.getFamilyName());

        person2.setFamilyName("Dickson");
        assertEquals("Dickson", person2.getFamilyName());
    }

    @Test
    public void setGivenNames() throws Exception
    {
        person1.setGivenNames("Zil Ceikauskas");
        ArrayList<String> names = new ArrayList<>();
        names.add("Zil");
        names.add("Ceikauskas");
        for(int i=0; i<2; i++){
            assertEquals(names.get(i), person1.getGivenNames().get(i));
        }

        person2.setGivenNames("Andrew Odintsov");
        names.clear();
        names.add("Andrew");
        names.add("Odintsov");
        for(int i=0; i<2; i++){
            assertEquals(names.get(i), person2.getGivenNames().get(i));
        }
    }

    @Test
    public void setName() throws Exception
    {
        person1.setName("Zil Ceikauskas", true);
        assertEquals("Zil", person1.getPreferredName());
        assertEquals("Ceikauskas", person1.getFamilyName());

        person1.setName("Ceikauskas Zil", false);
        assertEquals("Zil", person1.getPreferredName());
        assertEquals("Ceikauskas", person1.getFamilyName());
    }

    @Test
    public void setSalutation() throws Exception
    {
        person1.setSalutation("Dr");
        assertEquals("Dr", person1.getSalutation());

        person2.setSalutation("Ms");
        assertEquals("Ms", person2.getSalutation());
    }

}