package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bijan on 08/05/2017.
 */
@Ignore
public abstract class ModelEntityTest {

    ModelEntity modelEntity;

    @Before
    public abstract void setUp() throws Exception;

    @After
    public void tearDown() throws Exception
    {
        modelEntity = null;
    }

    @Test
    public void getName() throws Exception
    {
        assertEquals("", modelEntity.getName());
    }

    @Test
    public void getDetails() throws Exception
    {
        assertEquals("", modelEntity.getName());
    }

    @Test
    public void setName1() throws Exception
    {
        modelEntity.setName("Andrew");
        assertEquals("Andrew", modelEntity.getName());
    }

    @Test
    public void setDetails() throws Exception
    {
        // Testing setDetails with Striing argument
        modelEntity.setDetails("Some details to be added");
        assertEquals("Some details to be added", modelEntity.getDetails().getAsString());
    }

    @Test
    public void setDetails1() throws Exception
    {
        // Testing setDetails with Striing array argument
        String[] detailArray = {"Some details to be added", "more details to be added"};
        modelEntity.setDetails(detailArray);
        assertArrayEquals(detailArray, modelEntity.getDetails().getAsArray());
    }

    @Test
    public void setDetails2() throws Exception
    {
        // Testing setDetails with Striing ArrayList argument
        ArrayList<String> detailArrrayList = new ArrayList<>();
        detailArrrayList.add("New details to be added ");
        detailArrrayList.add("And some more details to be added ");
        modelEntity.setDetails(detailArrrayList);
        assertArrayEquals(detailArrrayList.toArray(), modelEntity.getDetails().getAsArray());
    }

}