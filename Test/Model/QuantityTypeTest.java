package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Created by bijan on 14/05/2017.
 */
public class QuantityTypeTest
{

    @Test
    public void listOfNames() throws Exception
    {
        String[] expected = {"Other", "Hours", "Books read", "Videos watched", "thousand words written",
                "questions answered"};
        assertArrayEquals(expected, QuantityType.listOfNames());
    }

    @Test
    public void get() throws Exception
    {
        // Testing with an existing Quantity type
        assertEquals("Hours", QuantityType.get("Hours").getName());

        // Testing with a non-existing Quantity type
        assertEquals("Other", QuantityType.get("Hous").getName());
    }

    @Test
    public void listOfQuantityTypes() throws Exception
    {
        QuantityType[] quantityTypes = {QuantityType.get("Other"), QuantityType.get("Hours"),
                QuantityType.get("Books read"), QuantityType.get("Videos watched"),
                QuantityType.get("thousand words written"), QuantityType.get("questions answered")};
        assertArrayEquals(quantityTypes, QuantityType.listOfQuantityTypes());
    }

    @Test
    public void exists() throws Exception
    {
        // Testing with an existing Quantity type
        assertEquals(true, QuantityType.exists("Hours"));

        // Testing with a non-existing Quantity type
        assertEquals(false, QuantityType.exists("fjksj"));
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals("Books read", QuantityType.get("Books read").toString());
    }

    @Test
    public void equals() throws Exception
    {
        assertTrue(QuantityType.get("Other").equals("Other"));
        assertFalse(QuantityType.get("Other").equals("Hours"));
    }

    @Test
    public void equals1() throws Exception
    {
        assertTrue(QuantityType.get("Other").equals(QuantityType.get("Other")));
        assertFalse(QuantityType.get("Other").equals(QuantityType.get("Hours")));
    }

}