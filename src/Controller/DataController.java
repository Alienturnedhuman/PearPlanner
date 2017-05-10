package Controller;

import Model.*;
import View.UIManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


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



        return r;
    }

    static VersionControlEntity inList( HashMap<String,VersionControlEntity> list,String uid)
    {
        if(list.containsKey(uid))
        {
            return list.get(uid);
        }
        return null;
    }

    static private HubFile processNewHubFile(NodeList nList)
    {
        HubFile r = null;
        XMLcontroller xmlTools = new XMLcontroller();

        HashMap<String,XMLcontroller.NodeReturn> fValues = xmlTools.getSchemaValues(nList,
                HubFile.SCHEMA_NEW_STUDYPROFILE);

        int version = fValues.get("version").getInt();
        NodeList assetNodes = fValues.get("assets").getNodeList();
        NodeList studyProfileNodes = fValues.get("studyProfile").getNodeList();

        /*
        NodeList assetNodes = XMLcontroller.getNodes(nList.item(1));
        String[] assetSchema = {"persons","buildings","rooms","timetableEventTypes"};

        NodeList studyProfileNodes = XMLcontroller.getNodes(nList.item(2));
        String[] studyProfileSchema = {"year","semester","modules"};
        */
        if(XMLcontroller.matchesSchema(assetNodes,HubFile.SCHEMA_ASSETS) &&
                XMLcontroller.matchesSchema(studyProfileNodes,HubFile.SCHEMA_STUDYPROFILE))
        {


            HashMap<String,VersionControlEntity> assetList = new HashMap<>();
            HashMap<String,XMLcontroller.NodeReturn> assetValues = xmlTools.getSchemaValues(assetNodes,
                    HubFile.SCHEMA_ASSETS);

            ArrayList<VersionControlEntity> newAssets = new ArrayList<>();
            ArrayList<Module> newModules = new ArrayList<>();
            // replace these with reading the values



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
                while(++i<ii)
                {
                    n = personList.item(i);
                    if(n.getNodeType()==Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("person") && XMLcontroller.matchesSchema(nc,
                                HubFile.SCHEMA_PERSON))
                        {
                            HashMap<String,XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
                                    HubFile.SCHEMA_PERSON);

                            tp = new Person(pValues.get("salutation").getString(), pValues.get("givenNames").getString(),
                                    pValues.get("familyName").getString(), pValues.get("familyNameLast").getBoolean(),
                                    pValues.get("email").getString());
                            UID = pValues.get("uid").getString();
                            tp.setUID(UID,pValues.get("version").getInt());
                            tp.setName(pValues.get("name").getString());
                            tp.setDetails(pValues.get("details").getMultilineString().getAsArray());
                            assetList.put(UID,tp);
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
                while(++i<ii)
                {
                    n = buildingList.item(i);
                    if(n.getNodeType()==Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("building") && XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_BUILDING))
                        {

                            HashMap<String,XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
                                    HubFile.SCHEMA_BUILDING);

                            tb = new Building(pValues.get("code").getString(), pValues.get("latitude").getDouble(),
                                    pValues.get("longitude").getDouble());

                            UID = pValues.get("uid").getString();
                            tb.setUID(UID,pValues.get("version").getInt());
                            tb.setName(pValues.get("name").getString());
                            tb.setDetails(pValues.get("details").getMultilineString().getAsArray());
                            assetList.put(UID,tb);
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

                            UID = pValues.get("uid").getString();
                            tr.setUID(UID,pValues.get("version").getInt());
                            tr.setName(pValues.get("name").getString());
                            tr.setDetails(pValues.get("details").getMultilineString().getAsArray());
                            assetList.put(UID,tr);
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
                            ttet.addProperties(ttetValues.get("name").getString(),
                                    ttetValues.get("details").getMultilineString());
                            UID = ttetValues.get("uid").getString();
                            ttet.setUID(UID,ttetValues.get("version").getInt());
                            assetList.put(UID,ttet);
                        }
                    }

                }
            }



            HashMap<String,XMLcontroller.NodeReturn> studyProfileValues =
                    xmlTools.getSchemaValues(studyProfileNodes,HubFile.SCHEMA_STUDYPROFILE);

            int year = studyProfileValues.get("year").getInt();
            int semester = studyProfileValues.get("semester").getInt();

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
                    Person organiser;
                    if(assetList.containsKey(linkedPerson) &&
                            assetList.get(linkedPerson) instanceof Person)
                    {
                        organiser = (Person)assetList.get(linkedPerson);
                    }
                    else
                    {
                        organiser = null;
                    }
                    String moduleCode = moduleValues.get("moduleCode").getString();
                    Module thisModule = new Module(organiser,moduleCode);
                    thisModule.addProperties(moduleValues.get("name").getString(),
                            moduleValues.get("details").getMultilineString());
                    UID = moduleValues.get("uid").getString();
                    thisModule.setUID(UID,moduleValues.get("version").getInt());
                    assetList.put(UID,thisModule);

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
                                        if(assetList.containsKey(linkedSetBy) &&
                                                assetList.get(linkedSetBy) instanceof Person)
                                        {
                                            cwSetBy = (Person)assetList.get(linkedSetBy);
                                        }
                                        else
                                        {
                                            cwSetBy = null;
                                        }
                                        if(assetList.containsKey(linkedMarkedBy) &&
                                                assetList.get(linkedMarkedBy) instanceof Person)
                                        {
                                            cwMarkedBy = (Person)assetList.get(linkedMarkedBy);
                                        }
                                        else
                                        {
                                            cwMarkedBy = null;
                                        }
                                        if(assetList.containsKey(linkedReviewedBy) &&
                                                assetList.get(linkedReviewedBy) instanceof Person)
                                        {
                                            cwReviewedBy = (Person)assetList.get(linkedReviewedBy);
                                        }
                                        else
                                        {
                                            cwReviewedBy = null;
                                        }
                                        if(XMLcontroller.matchesSchema(courseworkValues.get("startDate").getNodeList(),
                                                HubFile.SCHEMA_EVENT))
                                        {
                                            HashMap<String,XMLcontroller.NodeReturn> eventValues =
                                                    xmlTools.getSchemaValues(courseworkValues.get("startDate").getNodeList(),
                                                            HubFile.SCHEMA_EVENT);
                                            cwStartDate = new Event(eventValues.get("date").getString());
                                            cwStartDate.addProperties(eventValues.get("name").getString(),
                                                    eventValues.get("details").getMultilineString());
                                            cwStartDate.setUID(eventValues.get("name").getString());

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
                                            cwDeadline.addProperties(eventValues.get("name").getString(),
                                                    eventValues.get("details").getMultilineString());
                                            cwDeadline.setUID(eventValues.get("name").getString());

                                        }
                                        else
                                        {
                                            cwDeadline = null;
                                        }
                                        Coursework newCoursework = new Coursework(courseworkValues.get("weighting").getInt(),
                                                cwSetBy,cwMarkedBy,cwReviewedBy,courseworkValues.get("marks").getInt(),
                                                cwStartDate,cwDeadline,cwExtensions);
                                        newCoursework.addProperties(courseworkValues.get("name").getString(),
                                                courseworkValues.get("details").getMultilineString());
                                        UID = courseworkValues.get("uid").getString();
                                        newCoursework.setUID(UID,courseworkValues.get("version").getInt());
                                        assetList.put(UID,newCoursework);
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
                                        if(assetList.containsKey(linkedSetBy) &&
                                                assetList.get(linkedSetBy) instanceof Person)
                                        {
                                            exSetBy = (Person)assetList.get(linkedSetBy);
                                        }
                                        else
                                        {
                                            exSetBy = null;
                                        }
                                        if(assetList.containsKey(linkedMarkedBy) &&
                                                assetList.get(linkedMarkedBy) instanceof Person)
                                        {
                                            exMarkedBy = (Person)assetList.get(linkedMarkedBy);
                                        }
                                        else
                                        {
                                            exMarkedBy = null;
                                        }
                                        if(assetList.containsKey(linkedReviewedBy) &&
                                                assetList.get(linkedReviewedBy) instanceof Person)
                                        {
                                            exReviewedBy = (Person)assetList.get(linkedReviewedBy);
                                        }
                                        else
                                        {
                                            exReviewedBy = null;
                                        }

                                        if(XMLcontroller.matchesSchema(examValues.get("timeslot").getNodeList(),
                                                HubFile.SCHEMA_EXAMEVENT))
                                        {
                                            HashMap<String,XMLcontroller.NodeReturn> eventValues =
                                                    xmlTools.getSchemaValues(examValues.get("timeslot").getNodeList(),
                                                            HubFile.SCHEMA_EXAMEVENT);
                                            Room exRoom;
                                            String linkedRoom = eventValues.get("room").getString();
                                            if(assetList.containsKey(linkedRoom) &&
                                                    assetList.get(linkedRoom) instanceof Room)
                                            {
                                                exRoom = (Room)assetList.get(linkedRoom);
                                            }
                                            else
                                            {
                                                exRoom = null;
                                            }
                                            exTimeSlot = new ExamEvent(eventValues.get("date").getString(),exRoom,
                                                    eventValues.get("duration").getInt());
                                            exTimeSlot.addProperties(eventValues.get("name").getString(),
                                                    eventValues.get("details").getMultilineString());

                                            UID = eventValues.get("uid").getString();
                                            exTimeSlot.setUID(UID,eventValues.get("version").getInt());

                                        }
                                        else
                                        {
                                            exTimeSlot = null;
                                        }
                                        Exam exExamResit;
                                        if(assetList.containsKey(linkedResit) &&
                                                assetList.get(linkedResit) instanceof Exam)
                                        {
                                            exExamResit = (Exam)assetList.get(linkedResit);
                                        }
                                        else
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
                                        newExam.addProperties(examValues.get("name").getString(),
                                                examValues.get("details").getMultilineString());
                                        UID = examValues.get("uid").getString();
                                        newExam.setUID(UID,examValues.get("version").getInt());
                                        assetList.put(UID,newExam);
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
                                timetable.item(j).getNodeName()=="timetableEvent" &&
                                XMLcontroller.matchesSchema(assignments.item(j).getChildNodes(),
                                HubFile.SCHEMA_TIMETABLE_EVENT))

                        {
                            HashMap<String,XMLcontroller.NodeReturn> tteValues =
                                    xmlTools.getSchemaValues(assignments.item(j).getChildNodes(),
                                            HubFile.SCHEMA_TIMETABLE_EVENT);

                            Room tRoom;
                            Person tLecturer;
                            TimeTableEventType tTTET;

                            String linkedRoom = tteValues.get("room").getString();
                            String linkedLecturer = tteValues.get("lecturer").getString();
                            String linkedTTET = tteValues.get("timetableEventType").getString();

                            tRoom = (Room)inList(assetList,linkedRoom);
                            tLecturer = (Person)inList(assetList,linkedLecturer);
                            tTTET = (TimeTableEventType)inList(assetList,linkedTTET);

                            TimetableEvent newTTE = new TimetableEvent(tteValues.get("date").getString(),tRoom,tLecturer
                                    ,tTTET,tteValues.get("duration").getInt());

                            newTTE.addProperties(tteValues.get("name").getString(),
                                    tteValues.get("details").getMultilineString());
                            UID = tteValues.get("uid").getString();
                            newTTE.setUID(UID,tteValues.get("version").getInt());
                            assetList.put(UID,newTTE);
                            thisModule.addTimetableEvent(newTTE);
                        }
                    }
                    newModules.add(thisModule);
                }
            }


            String name = studyProfileValues.get("name").getString();
            MultilineString details = studyProfileValues.get("details").getMultilineString();
            UID = studyProfileValues.get("uid").getString();

            r = new HubFile(version,year,semester,newModules,newAssets,name,details,UID);
        }
        return r;
    }

    static public HubFile loadHubFile(File tempFile)
    {
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
                UIManager.reportError("Invalid File");
            }
        }

        return r;

    }
}
