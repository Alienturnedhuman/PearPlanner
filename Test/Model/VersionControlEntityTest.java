package Model;

import Controller.MainController;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

/**
 * Created by bijan on 06/05/2017.
 */
@Ignore
public class VersionControlEntityTest extends ApplicationTest
{

    VersionControlEntity versionControlEntity = new VersionControlEntity();

    @Before
    public void setUp() throws Exception
    {
        versionControlEntity = new VersionControlEntity();
    }


    @Override
    public void start(Stage stage) throws Exception
    {
        MainController.initialise();
        MainController.isNumeric("23");
    }

    @Test
    public void findAndUpdate() throws Exception
    {
        assertFalse(VersionControlEntity.findAndUpdate(versionControlEntity));

        versionControlEntity.setUID("99856-ID");
        assertTrue(VersionControlEntity.findAndUpdate(versionControlEntity));
    }

    @Test
    public void inLibrary() throws Exception
    {
        versionControlEntity.setUID("1234-ID");
        assertTrue(VersionControlEntity.inLibrary("1234-ID"));

        assertFalse(VersionControlEntity.inLibrary("10132-ID"));
        versionControlEntity.setUID("10132-ID");
        assertTrue(VersionControlEntity.inLibrary("10132-ID"));
    }

    @Test
    public void getVersion() throws Exception
    {
        assertEquals(0, versionControlEntity.getVersion());
    }

    @Test
    public void getUID() throws Exception
    {
        assertEquals(null, versionControlEntity.getUID());
    }

    @Test
    public void setUID() throws Exception
    {
        // Testing setUID with one argument
        versionControlEntity.setUID("1234-ID");
        assertEquals("1234-ID", versionControlEntity.getUID());

        // Testing the duplication
        versionControlEntity.setUID("1234-ID");

        // Testing setUID with two argument
        assertEquals(true, versionControlEntity.setUID("95657-ID",1));
        assertEquals("95657-ID", versionControlEntity.getUID());
        assertEquals(1, versionControlEntity.getVersion());

        // Testing the duplication
//        assertFalse(versionControlEntity.setUID("5678-ID", 2));
    }

    @After
    public void tearDown() throws Exception
    {
        versionControlEntity = null;
    }
}