package Controller;

import java.io.File;
import java.util.ArrayList;
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

    static public NodeList getNodes(Node parentNode)
    {
        NodeList tList = parentNode.getChildNodes();
        int i = tList.getLength();
        while(0<i--)
        {
            if(tList.item(i).getNodeType()!=Node.ELEMENT_NODE)
            {
                parentNode.removeChild(tList.item(i));
            }
        }
        return parentNode.getChildNodes();
    }

    static public boolean validNodeList(NodeList nodes , String[] nodeNames)
    {
        int i = -1;
        int ii = nodeNames.length;
        if(nodes.getLength() != ii)
        {
            return false;
        }
        while(++i<ii)
        {
            if(!nodes.item(i).getNodeName().equals(nodeNames[i]))
            {
                return false;
            }
        }
        return true;
    }

    static private HubFile processUpdateHubFile(NodeList nList, int version)
    {
        HubFile r = null;



        return r;
    }

    static private HubFile processNewHubFile(NodeList nList, int version)
    {
        HubFile r = null;

        NodeList assetNodes = getNodes(nList.item(1));
        String[] assetSchema = {"persons","buildings","rooms","timetableEventTypes"};

        NodeList studyProfileNodes = getNodes(nList.item(2));
        String[] studyProfileSchema = {"year","semester","modules"};

        if(validNodeList(assetNodes,assetSchema) && validNodeList(studyProfileNodes,studyProfileSchema))
        {
            ArrayList<VersionControlEntity> newAssets = new ArrayList<>();
            ArrayList<Module> newModules = new ArrayList<>();
            // replace these with reading the values
            int year = 2017;
            int semester = 1;

            // loop through assets adding to new Assets
            NodeList personList = getNodes(assetNodes.item(0));
            String[] personSchema = {"name","details","version","uid","givenNames","familyName","salutation","email"
                    ,"familyNameLast"};

            NodeList buildingList = getNodes(assetNodes.item(1));
            String[] buildingSchema = {"name","details","version","uid","code","latitude","longitude"};

            NodeList roomList = getNodes(assetNodes.item(2));
            String[] roomSchema = {"name","details","version","uid","building","roomNumber"};

            NodeList timeTableTypeList = getNodes(assetNodes.item(3));
            String[] timeTableTypeSchema = {"name","details","version","uid"};


            ArrayList<VersionControlEntity> assetList = new ArrayList<>();
            int vcVersion;
            String vcName,vcDetails,vcUID;

            int i = -1;
            int ii = personList.getLength();
            Node n;
            NodeList nc;
            Person tp;
            String pGivenNames,pFamilyName,pSalutation,pEmail;
            boolean pFamilyNameLast;
            while(++i<ii)
            {
                n = personList.item(i);
                if(n.getNodeType()==Node.ELEMENT_NODE) {
                    nc = getNodes(n);
                    ;
                    if (n.getNodeName().equals("person") && validNodeList(nc, personSchema)) {
                        // version control values
                        vcName = nc.item(0).getTextContent();
                        vcDetails = nc.item(1).getTextContent();
                        vcVersion = Integer.parseInt(nc.item(2).getTextContent());
                        vcUID = nc.item(3).getTextContent();

                        pGivenNames = nc.item(4).getTextContent();
                        pFamilyName = nc.item(5).getTextContent();
                        pSalutation = nc.item(6).getTextContent();
                        pEmail = nc.item(7).getTextContent();
                        pFamilyNameLast = nc.item(8).getTextContent().equals("true");


                        tp = new Person(pSalutation, pGivenNames, pFamilyName, pFamilyNameLast, pEmail);
                        System.out.println(tp.setUID(vcUID)?"VC updated":"VC not updated");
                        tp.setName(vcName);
                        tp.setDetails(vcDetails);

                        assetList.add(tp);

                        System.out.println(tp.toString());
                    }
                }
            }



            // loop through studyProfile adding new modules

            // if succcessful do this:
            r = new HubFile(version,year,semester,newModules,newAssets);
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
                    NodeList nList = getNodes(rootElement);
                    String[] schemaNew = {"version","assets","studyProfile"};
                    String[] schemaUpdate = {"version","extensions","updates"};
                    boolean newProfile = validNodeList(nList,schemaNew);
                    boolean updateFile = validNodeList(nList,schemaUpdate);
                    if(newProfile||updateFile)
                    {
                        String versionString = nList.item(0).getTextContent();
                        try{
                            int version = Integer.parseInt(versionString);
                            if(version>=0)
                            {
                                r = newProfile?processNewHubFile(nList,version):processUpdateHubFile(nList,version);
                            }
                            else
                            {
                                MainController.reportError("Invalid Version Number");
                            }
                        }
                        catch(Exception e)
                        {
                            MainController.reportError("Invalid Version Data");
                        }
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
