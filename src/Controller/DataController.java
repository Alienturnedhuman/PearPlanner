package Controller;

import Model.*;
import View.UIManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.HashMap;

import View.*;

/**
 * Created by bendickson on 5/4/17.
 */
public class DataController {


    /**
     * checks if there is a settings file and it is valid
     * returns true if this is the case
     * returns false if it isn't
     *
     */
    static public boolean existingSettingsFile()
    {
        return false;
        // not implmented yet
    }

    static private HubFile processUpdateHubFile(NodeList nList)
    {
        HubFile r = null;
        XMLcontroller xmlTools = new XMLcontroller();

        HashMap<String,XMLcontroller.NodeReturn> fValues = xmlTools.getSchemaValues(nList,
                HubFile.SCHEMA_UPDATE_FILE);

        int rootVersion = fValues.get("version").getInt();
        NodeList updates = fValues.get("updates").getNodeList();

        int i = -1;
        int ii = updates.getLength();
        while(++i<ii)
        {
            Node n = updates.item(i);
            if(n.getNodeType() == Node.ELEMENT_NODE && HubFile.updateableNode(n.getNodeName()) &&
                    XMLcontroller.matchesSchema(n.getChildNodes(),HubFile.SCHEMA_VCE))
            {
                String nodeName = n.getNodeName();
                HashMap<String,XMLcontroller.NodeReturn> nodeValues =
                        xmlTools.getSchemaValues(n.getChildNodes(),HubFile.schemaList.get(nodeName));

                int version = nodeValues.get("version").getInt();
                String uid = nodeValues.get("uid").getString();
                if(VersionControlEntity.inLibrary(uid))
                {
                    if(VersionControlEntity.get(uid).getVersion()<version)
                    {
                        switch(nodeName)
                        {
                            case "person":

                                break;
                            case "building":

                                break;
                            case "room":

                                break;
                            case "module":

                                break;
                            case "exam":

                                break;
                            case "coursework":

                                break;
                            case "timetableEventType":

                                break;
                            case "timetableEvent":

                                break;
                            case "event":

                                break;
                            case "deadline":

                                break;
                            case "examEvent":

                                break;
                        }
                    }
                }
            }
        }


        return r;
    }

    private static <T extends VersionControlEntity> T inList( HashMap<String,VersionControlEntity> list,String uid) throws Exception
    {
        VersionControlEntity vce = null;
        if(list.containsKey(uid)) {
            vce = list.get(uid);
        }
        else if(VersionControlEntity.inLibrary(uid))
        {
            vce = VersionControlEntity.get(uid);
        }
        // can't do instanceof T annoyingly, so hopefully casting to T and failing will work instead
        if(vce!=null)
        {
            try {
                return (T) vce;
            } catch (Exception e) {
                throw new Exception("Incorrect type referenced for '"+uid+"'");
            }
        }
        throw new Exception("UID referenced is not in database for '"+uid+"'");
    }

    static public void addVCEproperties(VersionControlEntity vce , HashMap<String,XMLcontroller.NodeReturn> values)
    {
        vce.addProperties(values.get("name").getString(),
                values.get("details").getMultilineString());
        String UID = values.get("uid").getString();
        vce.makeImporter();
        vce.setUID(UID,values.get("version").getInt());

    }

    static private HubFile processNewHubFile(NodeList nList) throws Exception
    {
        int beginLog = ConsoleIO.getLogSize();
        ConsoleIO.setConsoleMessage("Importing New Hub File" , true);
        HubFile r = null;
        XMLcontroller xmlTools = new XMLcontroller();

        HashMap<String,XMLcontroller.NodeReturn> fValues = xmlTools.getSchemaValues(nList,
                HubFile.SCHEMA_NEW_STUDYPROFILE);

        int version = fValues.get("version").getInt();
        NodeList assetNodes = fValues.get("assets").getNodeList();
        NodeList studyProfileNodes = fValues.get("studyProfile").getNodeList();

        if(XMLcontroller.matchesSchema(assetNodes,HubFile.SCHEMA_ASSETS) &&
                XMLcontroller.matchesSchema(studyProfileNodes,HubFile.SCHEMA_STUDYPROFILE))
        {
            ConsoleIO.setConsoleMessage("Schema Validates: assets" , true);
            ConsoleIO.setConsoleMessage("Schema Validates: studyProfile" , true);

            HashMap<String,XMLcontroller.NodeReturn> studyProfileValues =
                    xmlTools.getSchemaValues(studyProfileNodes,HubFile.SCHEMA_STUDYPROFILE);

            int year = studyProfileValues.get("year").getInt();
            int semester = studyProfileValues.get("semester").getInt();

            if(MainController.getSPC().containsStudyProfile(year,semester))
            {
                throw new Exception("Study profile for "+year+" semester "+semester+" already imported");
            }

            HashMap<String,VersionControlEntity> assetList = new HashMap<>();
            HashMap<String,XMLcontroller.NodeReturn> assetValues = xmlTools.getSchemaValues(assetNodes,
                    HubFile.SCHEMA_ASSETS);

            ArrayList<VersionControlEntity> newAssets = new ArrayList<>();
            ArrayList<Module> newModules = new ArrayList<>();

            Node n;
            NodeList nc;
            int i,ii;
            String UID;

            // Add all the Person classes
            if(assetValues.containsKey("persons"))
            {

                NodeList personList = assetValues.get("persons").getNodeList();
                Person tp;
                i = -1;
                ii = personList.getLength();
                ConsoleIO.setConsoleMessage("Reading persons tag, " + Integer.toString(ii) + " nodes:" , true);
                while(++i<ii)
                {
                    n = personList.item(i);
                    if(n.getNodeType()==Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("person") && XMLcontroller.matchesSchema(nc,
                                HubFile.SCHEMA_PERSON))
                        {

                            ConsoleIO.setConsoleMessage("Valid Node found:" , true);
                            /*
                            HashMap<String,XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
                                    HubFile.SCHEMA_PERSON);

                            tp = new Person(pValues.get("salutation").getString(), pValues.get("givenNames").getString(),
                                    pValues.get("familyName").getString(), pValues.get("familyNameLast").getBoolean(),
                                    pValues.get("email").getString());

                            addVCEproperties(tp,pValues);
                            */

                            tp = HubFile.createPerson(nc);

                            assetList.put(tp.getUID(),tp);

                            ConsoleIO.setConsoleMessage("Adding person: " + tp.toString() , true);
                        }
                    }
                }
            }
            // add all the Buildings

            if(assetValues.containsKey("buildings"))
            {
                NodeList buildingList = assetValues.get("buildings").getNodeList();
                Building tb;
                i = -1;
                ii = buildingList.getLength();
                ConsoleIO.setConsoleMessage("Reading buildings tag, " + Integer.toString(ii) + " nodes:" , true);
                while(++i<ii)
                {
                    n = buildingList.item(i);
                    if(n.getNodeType()==Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("building") && XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_BUILDING))
                        {
/*
                            HashMap<String,XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
                                    HubFile.SCHEMA_BUILDING);

                            tb = new Building(pValues.get("code").getString(), pValues.get("latitude").getDouble(),
                                    pValues.get("longitude").getDouble());

                            addVCEproperties(tb,pValues);*/


                            tb = HubFile.createBuilding(nc);

                            assetList.put(tb.getUID(),tb);
                        }
                    }
                }
            }

            // add all the Rooms
            if(assetValues.containsKey("rooms"))
            {
                NodeList roomList = assetValues.get("rooms").getNodeList();
                Room tr;
                i = -1;
                ii = roomList.getLength();
                ConsoleIO.setConsoleMessage("Reading rooms tag, " + Integer.toString(ii) + " nodes:" , true);
                while(++i<ii)
                {
                    n = roomList.item(i);
                    if(n.getNodeType()==Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("room") && XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_ROOM))
                        {
                            HashMap<String,XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
                                    HubFile.SCHEMA_ROOM);

                            String linkedBuilding = pValues.get("building").getString();
                            if(assetList.containsKey(linkedBuilding) &&
                                    assetList.get(linkedBuilding) instanceof Building)
                            {
                                tr = new Room(pValues.get("roomNumber").getString() ,
                                        (Building)assetList.get(linkedBuilding));
                            }
                            else {
                                tr = new Room(pValues.get("roomNumber").getString());
                            }


                            addVCEproperties(tr,pValues);

                            assetList.put(pValues.get("uid").getString(),tr);
                        }
                    }
                }
            }
            // add all the TimeTableTypes
            if(assetValues.containsKey("timetableEventTypes"))
            {
                NodeList ttetList = assetValues.get("timetableEventTypes").getNodeList();
                i = -1;
                ii = ttetList.getLength();
                while(++i<ii)
                {
                    n = ttetList.item(i);
                    if(n.getNodeType()==Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("timetableEventType") &&
                                XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_TIMETABLE_EVENT_TYPE))
                        {
                            HashMap<String,XMLcontroller.NodeReturn> ttetValues =
                                    xmlTools.getSchemaValues(nc,HubFile.SCHEMA_TIMETABLE_EVENT_TYPE);


                            TimeTableEventType ttet = new TimeTableEventType();

                            addVCEproperties(ttet,ttetValues);

                            assetList.put(ttetValues.get("uid").getString(),ttet);
                        }
                    }

                }
            }




            NodeList modules = studyProfileValues.get("modules").getNodeList();
            i = -1;
            ii = modules.getLength();
            while(++i<ii)
            {
                if(modules.item(i).getNodeType()==Node.ELEMENT_NODE && modules.item(i).getNodeName().equals("module")
                        && modules.item(i).hasChildNodes() && XMLcontroller.matchesSchema(modules.item(i).getChildNodes(), HubFile.SCHEMA_MODULE))
                {
                    HashMap<String,XMLcontroller.NodeReturn> moduleValues =
                            xmlTools.getSchemaValues(modules.item(i).getChildNodes(),HubFile.SCHEMA_MODULE);

                    String linkedPerson = moduleValues.get("organiser").getString();

                    Person organiser = inList(assetList,linkedPerson);
                    String moduleCode = moduleValues.get("moduleCode").getString();
                    Module thisModule = new Module(organiser,moduleCode);


                    addVCEproperties(thisModule,moduleValues);

                    assetList.put(moduleValues.get("uid").getString(),thisModule);

                    NodeList assignments = moduleValues.get("assignments").getNodeList();
                    int j = -1;
                    int jj = assignments.getLength();
                    while(++j<jj)
                    {
                        if(assignments.item(j).getNodeType()==Node.ELEMENT_NODE)
                        {

                            switch (assignments.item(j).getNodeName())
                            {
                                case "coursework":
                                    if(XMLcontroller.matchesSchema(assignments.item(j).getChildNodes(),
                                            HubFile.SCHEMA_COURSEWORK))
                                    {
                                        HashMap<String,XMLcontroller.NodeReturn> courseworkValues =
                                                xmlTools.getSchemaValues(assignments.item(j).getChildNodes(),
                                                        HubFile.SCHEMA_COURSEWORK);

                                        Person cwSetBy,cwMarkedBy,cwReviewedBy;
                                        Event cwStartDate;
                                        Deadline cwDeadline;
                                        ArrayList<Extension> cwExtensions = new ArrayList<>();
                                        String linkedSetBy = courseworkValues.get("setBy").getString();
                                        String linkedMarkedBy = courseworkValues.get("markedBy").getString();
                                        String linkedReviewedBy = courseworkValues.get("reviewedBy").getString();


                                        cwSetBy = inList(assetList,linkedSetBy);
                                        cwMarkedBy = inList(assetList,linkedMarkedBy);
                                        cwReviewedBy = inList(assetList,linkedReviewedBy);


                                        if(XMLcontroller.matchesSchema(courseworkValues.get("startDate").getNodeList(),
                                                HubFile.SCHEMA_EVENT))
                                        {
                                            HashMap<String,XMLcontroller.NodeReturn> eventValues =
                                                    xmlTools.getSchemaValues(courseworkValues.get("startDate").getNodeList(),
                                                            HubFile.SCHEMA_EVENT);
                                            cwStartDate = new Event(eventValues.get("date").getString());


                                            addVCEproperties(cwStartDate,eventValues);
                                            assetList.put(eventValues.get("uid").getString(),cwStartDate);

                                        }
                                        else
                                        {
                                            cwStartDate = null;
                                        }
                                        if(XMLcontroller.matchesSchema(courseworkValues.get("deadline").getNodeList(),
                                                HubFile.SCHEMA_EVENT))
                                        {
                                            HashMap<String,XMLcontroller.NodeReturn> eventValues =
                                                    xmlTools.getSchemaValues(courseworkValues.get("deadline").getNodeList(),
                                                            HubFile.SCHEMA_EVENT);

                                            cwDeadline = new Deadline(eventValues.get("date").getString());


                                            addVCEproperties(cwDeadline,eventValues);
                                            assetList.put(eventValues.get("uid").getString(),cwDeadline);

                                        }
                                        else
                                        {
                                            cwDeadline = null;
                                        }
                                        Coursework newCoursework = new Coursework(courseworkValues.get("weighting").getInt(),
                                                cwSetBy,cwMarkedBy,cwReviewedBy,courseworkValues.get("marks").getInt(),
                                                cwStartDate,cwDeadline,cwExtensions);

                                        addVCEproperties(newCoursework,courseworkValues);

                                        assetList.put(courseworkValues.get("uid").getString(),newCoursework);
                                        thisModule.addAssignment(newCoursework);
                                    }
                                    break;

                                case "exam":
                                    if(XMLcontroller.matchesSchema(assignments.item(j).getChildNodes(),
                                            HubFile.SCHEMA_EXAM))
                                    {
                                        HashMap<String,XMLcontroller.NodeReturn> examValues =
                                                xmlTools.getSchemaValues(assignments.item(j).getChildNodes(),
                                                        HubFile.SCHEMA_EXAM);

                                        Person exSetBy,exMarkedBy,exReviewedBy;
                                        ExamEvent exTimeSlot;
                                        ArrayList<Extension> cwExtensions = new ArrayList<>();



                                        String linkedSetBy = examValues.get("setBy").getString();
                                        String linkedMarkedBy = examValues.get("markedBy").getString();
                                        String linkedReviewedBy = examValues.get("reviewedBy").getString();
                                        String linkedResit = examValues.get("resit").getString();

                                        exSetBy = inList(assetList,linkedSetBy);
                                        exMarkedBy = inList(assetList,linkedMarkedBy);
                                        exReviewedBy = inList(assetList,linkedReviewedBy);

                                        if(XMLcontroller.matchesSchema(examValues.get("timeslot").getNodeList(),
                                                HubFile.SCHEMA_EXAMEVENT))
                                        {
                                            HashMap<String,XMLcontroller.NodeReturn> eventValues =
                                                    xmlTools.getSchemaValues(examValues.get("timeslot").getNodeList(),
                                                            HubFile.SCHEMA_EXAMEVENT);
                                            //Room exRoom;
                                            String linkedRoom = eventValues.get("room").getString();
                                            Room exRoom = inList(assetList,linkedRoom);


                                            exTimeSlot = new ExamEvent(eventValues.get("date").getString(),exRoom,
                                                    eventValues.get("duration").getInt());



                                            addVCEproperties(exTimeSlot,eventValues);
                                            assetList.put(eventValues.get("uid").getString(),exTimeSlot);

                                        }
                                        else
                                        {
                                            exTimeSlot = null;
                                        }
                                        Exam exExamResit;
                                        try {
                                            exExamResit = inList(assetList, linkedResit);
                                        }
                                        catch(Exception e)
                                        {
                                            exExamResit = null;
                                        }


                                        Exam newExam;
                                        if(exExamResit==null) {
                                            newExam = new Exam(examValues.get("weighting").getInt(),
                                                    exSetBy, exMarkedBy, exReviewedBy, examValues.get("marks").getInt(),
                                                    exTimeSlot);
                                        }
                                        else
                                        {
                                            newExam = new Exam(examValues.get("weighting").getInt(),
                                                    exSetBy, exMarkedBy, exReviewedBy, examValues.get("marks").getInt(),
                                                    exTimeSlot,exExamResit);
                                        }



                                        addVCEproperties(newExam,examValues);

                                        assetList.put(examValues.get("uid").getString(),newExam);
                                        thisModule.addAssignment(newExam);
                                    }

                                    break;
                            }

                        }
                    }
                    NodeList timetable = moduleValues.get("timetable").getNodeList();
                    j=-1;
                    jj = timetable.getLength();
                    while(++j<jj)
                    {
                        if(timetable.item(j).getNodeType()==Node.ELEMENT_NODE &&
                                timetable.item(j).getNodeName().equals("timetableEvent") &&
                                XMLcontroller.matchesSchema(timetable.item(j).getChildNodes(),
                                HubFile.SCHEMA_TIMETABLE_EVENT))

                        {
                            HashMap<String,XMLcontroller.NodeReturn> tteValues =
                                    xmlTools.getSchemaValues(timetable.item(j).getChildNodes(),
                                            HubFile.SCHEMA_TIMETABLE_EVENT);



                            String linkedRoom = tteValues.get("room").getString();
                            String linkedLecturer = tteValues.get("lecturer").getString();
                            String linkedTTET = tteValues.get("timetableEventType").getString();

                            Room tRoom = inList(assetList,linkedRoom);
                            Person tLecturer = inList(assetList,linkedLecturer);
                            TimeTableEventType tTTET = inList(assetList,linkedTTET);


                            TimetableEvent newTTE = new TimetableEvent(tteValues.get("date").getString(),tRoom,tLecturer
                                    ,tTTET,tteValues.get("duration").getInt());
                            addVCEproperties(newTTE,tteValues);

                            assetList.put(tteValues.get("uid").getString(),newTTE);
                            thisModule.addTimetableEvent(newTTE);
                        }
                    }
                    newModules.add(thisModule);
                }
            }


            String name = studyProfileValues.get("name").getString();
            MultilineString details = studyProfileValues.get("details").getMultilineString();
            UID = studyProfileValues.get("uid").getString();



            ConsoleIO.setConsoleMessage("Attempting to import "+Integer.toString(assetList.size())
                    +" items to VersionControl..." , true);
            ConsoleIO.setConsoleMessage("Starting with "+VersionControlEntity.libraryReport()+" entries");

            ArrayList<Event> calendarItems = new ArrayList<>();

            for (String key : assetList.keySet()) {
                ConsoleIO.setConsoleMessage("Adding Asset: "+key , true);
                if(assetList.get(key).addToLibrary())
                {
                    if(assetList.get(key) instanceof Event)
                    {
                        calendarItems.add((Event)assetList.get(key));
                    }
                    System.out.println(assetList.get(key).toString() + " added");
                }
                else if(assetList.get(i).isImporter())
                {
                    VersionControlEntity.get(key).update(assetList.get(i));
                    System.out.println(assetList.get(key).toString() + " update attempted");
                }
                else
                {
                    System.out.println(assetList.get(key).toString() + " not imported");
                }
            }
            ConsoleIO.setConsoleMessage("Ending with "+VersionControlEntity.libraryReport()+" entries");
            ConsoleIO.saveLog("import_report.txt",beginLog,ConsoleIO.getLogSize());


            r = new HubFile(version,year,semester,newModules,newAssets,calendarItems,name,details,UID);
        }
        return r;
    }

    static public HubFile loadHubFile(File tempFile)
    {

        /// begin GANTT CODE TESTING - remove once properly implemented
        System.out.println("GANT");
        if(MainController.getSPC().getPlanner().containsStudyProfile(2017,1))
        {
            StudyPlanner sp = MainController.getSPC().getPlanner();
            Assignment a = (sp.getStudyProfiles()[0].getModules()[0].getAssignments()).get(1);
            GanttishDiagram.createGanttishDiagram(sp,a);
        }
        // END GANTT CODE TESTING

        System.out.println("GANT!!");
        HubFile r = null;
        if(tempFile.exists())
        {
            try
            {
                // learned from:
                // https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(tempFile);
                doc.getDocumentElement().normalize();

                String rootElementTag = doc.getDocumentElement().getNodeName();
                Node rootElement = doc.getDocumentElement();

                // check it is a hubfile
                if(rootElementTag.equals("hubfile") && rootElement.hasChildNodes())
                {
                    NodeList nList = XMLcontroller.getNodes(rootElement);
                    if (XMLcontroller.matchesSchema(nList, HubFile.SCHEMA_NEW_STUDYPROFILE))
                    {
                        r = processNewHubFile(nList);
                    }
                    else if (XMLcontroller.matchesSchema(nList, HubFile.SCHEMA_UPDATE_FILE))
                    {
                        r = processUpdateHubFile(nList);
                    }
                    else
                    {
                        UIManager.reportError("Invalid Parent Nodes");
                    }
                }
                else
                {
                    UIManager.reportError("Invalid XML file");
                }

            }
            catch(Exception e)
            {
                UIManager.reportError("Invalid File: \n"+e.getMessage());
            }
        }
        return r;
    }
}
