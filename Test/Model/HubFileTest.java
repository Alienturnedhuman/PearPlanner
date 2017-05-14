package Model;

import Controller.MainController;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bijan on 14/05/2017.
 */
public class HubFileTest extends ApplicationTest
{
    ArrayList<Module> modules;
    ArrayList<VersionControlEntity> assests;
    ArrayList<Event> events;
    MultilineString semesterDetails;
    Module module1, module2;
    Event event1, event2;
    HubFile hubFile;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        MainController.isNumeric("23");
    }


    @Before
    public void setUp() throws Exception
    {
        modules = new ArrayList<>();
        assests = new ArrayList<>();
        events = new ArrayList<>();
        semesterDetails = new MultilineString();

        module1 = new Module(new Person("Dr.", "Mark Fisher", true), "CMP-1550Y");
        module2 = new Module(new Person("Dr.", "Rudy Lapeer", true), "CMP-1670Z");
        modules.add(module1);
        modules.add(module2);

        event1 = new Event("09/04/2017T11:00:00Z");
        event2 = new Event("29/06/2017T13:00:00Z");
        events.add(event1);
        events.add(event2);

        semesterDetails = new MultilineString("This is some details about the semester." +
                "\nThis is the first semester.");

        hubFile = new HubFile(1, 2017, 1, modules, assests, events, "Spring", semesterDetails, "SPRING_1_2017");
    }

    @After
    public void tearDown() throws Exception
    {
        modules = null;
        assests = null;
        events = null;
        semesterDetails = null;
        module1 = null;
        module2 = null;
        event1 = null;
        event2 = null;
        semesterDetails = null;
        hubFile = null;
    }

    @Test
    public void getModules() throws Exception
    {
        assertEquals(modules, hubFile.getModules());
    }

    @Test
    public void getExtensions() throws Exception
    {
        ArrayList<VersionControlEntity> updates = new ArrayList<>();
        ArrayList<ExtensionApplication> extensionApplications = new ArrayList<>();
        ExtensionApplication extensionApplication1 =new ExtensionApplication();
        extensionApplications.add(extensionApplication1);
        HubFile hubFile2 = new HubFile(2, extensionApplications, updates);
        assertEquals(extensionApplications, hubFile2.getExtensions());
    }

    @Test
    public void getCalendarList() throws Exception
    {
        assertEquals(events, hubFile.getCalendarList());
    }

    @Test
    public void getUpdates() throws Exception
    {
        ArrayList<VersionControlEntity> updates = new ArrayList<>();
        VersionControlEntity update1 = new VersionControlEntity("v.1");
        VersionControlEntity update2 = new VersionControlEntity("v.2");
        ArrayList<ExtensionApplication> extensionApplications = new ArrayList<>();
        HubFile hubFile2 = new HubFile(2, extensionApplications, updates);
        assertEquals(updates, hubFile2.getUpdates());
    }

    @Test
    public void toStringTest1() throws Exception
    {
        String expected = "HubFile for " + 2017 + " semester: " + 1 + " | Module Count: " +
                Integer.toString(modules.size());
        assertEquals(expected, hubFile.toString());
    }

    @Test
    public void toStringTest2() throws Exception
    {
        StringBuilder expected = new StringBuilder();

        expected.append(hubFile.toString());
        int i = -1;
        int ii = modules.size();
        while (++i < ii)
        {
            expected.append(modules.get(i).toString(true));
        }

        assertEquals(expected.toString(), hubFile.toString(true));

        assertEquals(hubFile.toString(), hubFile.toString(false));
    }

}