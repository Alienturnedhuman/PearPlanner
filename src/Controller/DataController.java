/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar
 *
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package Controller;

import Model.*;
import View.ConsoleIO;
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
public class DataController
{


    /**
     * checks if there is a settings file and it is valid
     * returns true if this is the case
     * returns false if it isn't
     */
    static public boolean existingSettingsFile()
    {
        return false;
        // not implmented yet
    }

    static private void processVCEupdate(VersionControlEntity vce)
    {
        if (vce.addToLibrary() || VersionControlEntity.get(vce.getUID()).update(vce))
        {
            ConsoleIO.setConsoleMessage(vce + " added", true);
        } else
        {
            ConsoleIO.setConsoleMessage(vce + " not added", true);
        }
    }

    static private HubFile processUpdateHubFile(NodeList nList) throws Exception
    {
        HubFile r = null;
        XMLcontroller xmlTools = new XMLcontroller();

        HashMap<String, XMLcontroller.NodeReturn> fValues = xmlTools.getSchemaValues(nList,
                HubFile.SCHEMA_UPDATE_FILE);

        int rootVersion = fValues.get("version").getInt();
        NodeList updates = fValues.get("updates").getNodeList();

        int i = -1;
        int ii = updates.getLength();
        while (++i < ii)
        {
            Node n = updates.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && HubFile.updateableNode(n.getNodeName()) &&
                    XMLcontroller.matchesSchema(n.getChildNodes(), HubFile.SCHEMA_VCE))
            {
                String nodeName = n.getNodeName();
                HashMap<String, XMLcontroller.NodeReturn> nodeValues =
                        xmlTools.getSchemaValues(n.getChildNodes(), HubFile.schemaList.get(nodeName));

                int version = nodeValues.get("version").getInt();
                String uid = nodeValues.get("uid").getString();
                if (VersionControlEntity.inLibrary(uid))
                {
                    if (VersionControlEntity.get(uid).getVersion() < version)
                    {
                        NodeList nc = XMLcontroller.getNodes(n);
                        switch (nodeName)
                        {
                            case "person":
                                processVCEupdate(HubFile.createPerson(nc));
                                break;
                            case "building":
                                processVCEupdate(HubFile.createBuilding(nc));

                                break;
                            case "room":
                                processVCEupdate(HubFile.createRoom(nc, VersionControlEntity.getLibrary()));
                                break;
                            case "module":
                                // not yet added
                                break;
                            case "exam":
                                processVCEupdate(HubFile.createExam(nc, VersionControlEntity.getLibrary()));
                                break;
                            case "coursework":
                                processVCEupdate(HubFile.createCoursework(nc, VersionControlEntity.getLibrary()));
                                break;
                            case "timetableEventType":
                                processVCEupdate(HubFile.createTimetableEventType(nc));
                                break;
                            case "timetableEvent":
                                processVCEupdate(HubFile.createTimetableEvent(nc, VersionControlEntity.getLibrary()));
                                break;
                            case "event":
                                // not yet added
                                break;
                            case "deadline":
                                // not yet added
                                break;
                            case "examEvent":
                                // not yet added
                                break;
                        }
                    }
                }
            }
        }


        return r;
    }

    public static <T extends VersionControlEntity> T inList(HashMap<String, VersionControlEntity> list, String uid) throws Exception
    {
        VersionControlEntity vce = null;
        if (list.containsKey(uid))
        {
            vce = list.get(uid);
        } else if (VersionControlEntity.inLibrary(uid))
        {
            vce = VersionControlEntity.get(uid);
        }
        // can't do instanceof T annoyingly, so hopefully casting to T and failing will work instead
        if (vce != null)
        {
            try
            {
                return (T) vce;
            } catch (Exception e)
            {
                throw new Exception("Incorrect type referenced for '" + uid + "'");
            }
        }
        throw new Exception("UID referenced is not in database for '" + uid + "'");
    }

    static public void addVCEproperties(VersionControlEntity vce, HashMap<String, XMLcontroller.NodeReturn> values)
    {
        vce.addProperties(values.get("name").getString(),
                values.get("details").getMultilineString());
        String UID = values.get("uid").getString();
        vce.makeImporter();
        vce.setUID(UID, values.get("version").getInt());

    }

    static private HubFile processNewHubFile(NodeList nList) throws Exception
    {
        int beginLog = ConsoleIO.getLogSize();
        ConsoleIO.setConsoleMessage("Importing New Hub File", true);
        HubFile r = null;
        XMLcontroller xmlTools = new XMLcontroller();

        HashMap<String, XMLcontroller.NodeReturn> fValues = xmlTools.getSchemaValues(nList,
                HubFile.SCHEMA_NEW_STUDYPROFILE);

        int version = fValues.get("version").getInt();
        NodeList assetNodes = fValues.get("assets").getNodeList();
        NodeList studyProfileNodes = fValues.get("studyProfile").getNodeList();

        if (XMLcontroller.matchesSchema(assetNodes, HubFile.SCHEMA_ASSETS) &&
                XMLcontroller.matchesSchema(studyProfileNodes, HubFile.SCHEMA_STUDYPROFILE))
        {
            ConsoleIO.setConsoleMessage("Schema Validates: assets", true);
            ConsoleIO.setConsoleMessage("Schema Validates: studyProfile", true);

            HashMap<String, XMLcontroller.NodeReturn> studyProfileValues =
                    xmlTools.getSchemaValues(studyProfileNodes, HubFile.SCHEMA_STUDYPROFILE);

            int year = studyProfileValues.get("year").getInt();
            int semester = studyProfileValues.get("semester").getInt();

            if (MainController.getSPC().containsStudyProfile(year, semester))
            {
                throw new Exception("Study profile for " + year + " semester " + semester + " already imported");
            }

            HashMap<String, VersionControlEntity> assetList = new HashMap<>();
            HashMap<String, XMLcontroller.NodeReturn> assetValues = xmlTools.getSchemaValues(assetNodes,
                    HubFile.SCHEMA_ASSETS);

            ArrayList<VersionControlEntity> newAssets = new ArrayList<>();
            ArrayList<Module> newModules = new ArrayList<>();

            Node n;
            NodeList nc;
            int i, ii;
            String UID;

            // Add all the Person classes
            if (assetValues.containsKey("persons"))
            {

                NodeList personList = assetValues.get("persons").getNodeList();
                Person tp;
                i = -1;
                ii = personList.getLength();
                ConsoleIO.setConsoleMessage("Reading persons tag, " + Integer.toString(ii) + " nodes:", true);
                while (++i < ii)
                {
                    n = personList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("person") && XMLcontroller.matchesSchema(nc,
                                HubFile.SCHEMA_PERSON))
                        {

                            ConsoleIO.setConsoleMessage("Valid Node found:", true);

                            tp = HubFile.createPerson(nc);

                            assetList.put(tp.getUID(), tp);

                            ConsoleIO.setConsoleMessage("Adding person: " + tp.toString(), true);
                        }
                    }
                }
            }
            // add all the Buildings

            if (assetValues.containsKey("buildings"))
            {
                NodeList buildingList = assetValues.get("buildings").getNodeList();
                Building tb;
                i = -1;
                ii = buildingList.getLength();
                ConsoleIO.setConsoleMessage("Reading buildings tag, " + Integer.toString(ii) + " nodes:", true);
                while (++i < ii)
                {
                    n = buildingList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("building") && XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_BUILDING))
                        {
                            ConsoleIO.setConsoleMessage("Valid Node found:", true);


                            tb = HubFile.createBuilding(nc);

                            assetList.put(tb.getUID(), tb);
                            ConsoleIO.setConsoleMessage("Adding buiding: " + tb.toString(), true);
                        }
                    }
                }
            }

            // add all the Rooms
            if (assetValues.containsKey("rooms"))
            {
                NodeList roomList = assetValues.get("rooms").getNodeList();
                Room tr;
                i = -1;
                ii = roomList.getLength();
                ConsoleIO.setConsoleMessage("Reading rooms tag, " + Integer.toString(ii) + " nodes:", true);
                while (++i < ii)
                {
                    n = roomList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("room") && XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_ROOM))
                        {
                            ConsoleIO.setConsoleMessage("Valid Node found:", true);

                            tr = HubFile.createRoom(nc, assetList);
                            assetList.put(tr.getUID(), tr);
                            ConsoleIO.setConsoleMessage("Adding room: " + tr.toString(), true);
                        }
                    }
                }
            }
            // add all the TimeTableTypes
            if (assetValues.containsKey("timetableEventTypes"))
            {
                NodeList ttetList = assetValues.get("timetableEventTypes").getNodeList();
                i = -1;
                ii = ttetList.getLength();
                ConsoleIO.setConsoleMessage("Reading timetableEventTypes tag, " + Integer.toString(ii) + " nodes:", true);
                while (++i < ii)
                {
                    n = ttetList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE)
                    {
                        nc = XMLcontroller.getNodes(n);
                        if (n.getNodeName().equals("timetableEventType") &&
                                XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_TIMETABLE_EVENT_TYPE))
                        {
                            ConsoleIO.setConsoleMessage("Valid Node found:", true);
                            TimeTableEventType ttet = HubFile.createTimetableEventType(nc);


                            assetList.put(ttet.getUID(), ttet);
                            ConsoleIO.setConsoleMessage("Adding timetable event: " + ttet.toString(), true);
                        }
                    }

                }
            }


            NodeList modules = studyProfileValues.get("modules").getNodeList();
            i = -1;
            ii = modules.getLength();
            while (++i < ii)
            {
                if (modules.item(i).getNodeType() == Node.ELEMENT_NODE && modules.item(i).getNodeName().equals("module")
                        && modules.item(i).hasChildNodes() && XMLcontroller.matchesSchema(modules.item(i).getChildNodes(), HubFile.SCHEMA_MODULE))
                {
                    HashMap<String, XMLcontroller.NodeReturn> moduleValues =
                            xmlTools.getSchemaValues(modules.item(i).getChildNodes(), HubFile.SCHEMA_MODULE);

                    String linkedPerson = moduleValues.get("organiser").getString();

                    Person organiser = inList(assetList, linkedPerson);
                    String moduleCode = moduleValues.get("moduleCode").getString();
                    Module thisModule = new Module(organiser, moduleCode);


                    addVCEproperties(thisModule, moduleValues);

                    assetList.put(moduleValues.get("uid").getString(), thisModule);

                    NodeList assignments = moduleValues.get("assignments").getNodeList();
                    int j = -1;
                    int jj = assignments.getLength();
                    ConsoleIO.setConsoleMessage("Reading assignments tag, " + Integer.toString(ii) + " nodes:", true);
                    while (++j < jj)
                    {
                        if (assignments.item(j).getNodeType() == Node.ELEMENT_NODE)
                        {
                            nc = assignments.item(j).getChildNodes();
                            switch (assignments.item(j).getNodeName())
                            {
                                case "coursework":
                                    if (XMLcontroller.matchesSchema(assignments.item(j).getChildNodes(),
                                            HubFile.SCHEMA_COURSEWORK))
                                    {
                                        ConsoleIO.setConsoleMessage("Valid Node found:", true);
                                        Coursework newCoursework = HubFile.createCoursework(nc, assetList);
                                        assetList.put(newCoursework.getUID(), newCoursework);
                                        thisModule.addAssignment(newCoursework);
                                        ConsoleIO.setConsoleMessage("Adding coursework: " + newCoursework.toString(), true);
                                    }
                                    break;

                                case "exam":
                                    if (XMLcontroller.matchesSchema(assignments.item(j).getChildNodes(),
                                            HubFile.SCHEMA_EXAM))
                                    {
                                        ConsoleIO.setConsoleMessage("Valid Node found:", true);


                                        Exam newExam = HubFile.createExam(nc, assetList);
                                        assetList.put(newExam.getUID(), newExam);
                                        thisModule.addAssignment(newExam);
                                        ConsoleIO.setConsoleMessage("Adding exam: " + newExam.toString(), true);
                                    }

                                    break;
                            }

                        }
                    }
                    NodeList timetable = moduleValues.get("timetable").getNodeList();
                    j = -1;
                    jj = timetable.getLength();
                    ConsoleIO.setConsoleMessage("Reading timetable tag, " + Integer.toString(ii) + " nodes:", true);
                    while (++j < jj)
                    {
                        if (timetable.item(j).getNodeType() == Node.ELEMENT_NODE &&
                                timetable.item(j).getNodeName().equals("timetableEvent") &&
                                XMLcontroller.matchesSchema(timetable.item(j).getChildNodes(),
                                        HubFile.SCHEMA_TIMETABLE_EVENT))

                        {
                            ConsoleIO.setConsoleMessage("Valid Node found:", true);
                            nc = timetable.item(j).getChildNodes();

                            TimetableEvent newTTE = HubFile.createTimetableEvent(nc, assetList);
                            assetList.put(newTTE.getUID(), newTTE);
                            thisModule.addTimetableEvent(newTTE);

                            ConsoleIO.setConsoleMessage("Adding TimetableEvent: " + newTTE.toString(), true);
                        }
                    }
                    newModules.add(thisModule);
                }
            }


            String name = studyProfileValues.get("name").getString();
            MultilineString details = studyProfileValues.get("details").getMultilineString();
            UID = studyProfileValues.get("uid").getString();


            ConsoleIO.setConsoleMessage("Attempting to import " + Integer.toString(assetList.size())
                    + " items to VersionControl...", true);
            ConsoleIO.setConsoleMessage("Starting with " + VersionControlEntity.libraryReport() + " entries");

            ArrayList<Event> calendarItems = new ArrayList<>();

            for (String key : assetList.keySet())
            {
                ConsoleIO.setConsoleMessage("Adding Asset: " + key, true);
                if (assetList.get(key).addToLibrary())
                {
                    ConsoleIO.setConsoleMessage("New Asset, adding... ", true);
                    if (assetList.get(key) instanceof Event)
                    {

                        ConsoleIO.setConsoleMessage("Event - adding to Calendar: ", true);
                        calendarItems.add((Event) assetList.get(key));
                    }
                } else if (assetList.get(key).isImporter())
                {
                    ConsoleIO.setConsoleMessage("In library, attempting update: ", true);
                    ConsoleIO.setConsoleMessage(assetList.get(key).toString() +
                            (VersionControlEntity.get(key).update(assetList.get(key)) ? " updated" : " not updated"), true);
                } else
                {
                    ConsoleIO.setConsoleMessage(assetList.get(key).toString() + " not imported", true);
                }
            }
            ConsoleIO.setConsoleMessage("Ending with " + VersionControlEntity.libraryReport() + " entries");
            ConsoleIO.saveLog("import_report.txt", beginLog, ConsoleIO.getLogSize());


            r = new HubFile(version, year, semester, newModules, newAssets, calendarItems, name, details, UID);
        }
        return r;
    }

    static public HubFile loadHubFile(File tempFile)
    {
        HubFile r = null;
        if (tempFile.exists())
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
                if (rootElementTag.equals("hubfile") && rootElement.hasChildNodes())
                {
                    NodeList nList = XMLcontroller.getNodes(rootElement);
                    if (XMLcontroller.matchesSchema(nList, HubFile.SCHEMA_NEW_STUDYPROFILE))
                    {
                        r = processNewHubFile(nList);
                    } else if (XMLcontroller.matchesSchema(nList, HubFile.SCHEMA_UPDATE_FILE))
                    {
                        r = processUpdateHubFile(nList);
                    } else
                    {
                        UIManager.reportError("Invalid Parent Nodes");
                    }
                } else
                {
                    UIManager.reportError("Invalid XML file");
                }

            } catch (Exception e)
            {
                UIManager.reportError("Invalid File: \n" + e.getMessage());
            }
        }
        return r;
    }
}
