package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by bendickson on 5/4/17.
 */
public class DataController {


    /**
     * checks if there is a settings file and it is valid
     * returns true if this is the case
     * returns false if it isn't
     * @return
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

    static private HubFile processNewHubFile(NodeList nList)
    {
        HubFile r = null;
        XMLcontroller xmlTools = new XMLcontroller();

        HashMap<String,XMLcontroller.NodeReturn> fValues = xmlTools.getSchemaValues(nList, HubFile.SCHEMA_NEW_STUDYPROFILE);

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
            HashMap<String,XMLcontroller.NodeReturn> assetValues = xmlTools.getSchemaValues(assetNodes,HubFile.SCHEMA_ASSETS);

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
                        if (n.getNodeName().equals("person") && XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_PERSON))
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
                            System.out.println(tp.toString());
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
                            System.out.println(tb.toString());
                        }
                    }
                }
            }



            // add all the Rooms

            if(assetValues.containsKey("rooms"))
            {
                System.out.println("Has rooms");
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
                            // version control values

                            System.out.println("Has room!!!!");

                            HashMap<String,XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
                                    HubFile.SCHEMA_ROOM);
                            System.out.println("Room schema processed!!!");

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

                            System.out.println(tr.toString());
                        }
                    }
                }
            }



            // add all the TimeTableTypes

            int year = 2017;
            int semester = 1;


            System.out.println("done!");



            // loop through studyProfile adding new modules

            // if succcessful do this:
            // r = new HubFile(version,year,semester,newModules,newAssets);
        }


        return r;
    }

    static public HubFile loadHubFile(String path)
    {
        File tempFile = new File(path);
        HubFile r = null;
        if(tempFile.exists())
        {
            try
            {
                // learned from:
                // https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(path);
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
                        MainController.reportError("Invalid Parent Nodes");
                    }
                }
                else
                {
                    MainController.reportError("Invalid XML file");
                }

            }
            catch(Exception e)
            {
                MainController.reportError("Invalid File");
            }
        }

        return r;

    }
}
