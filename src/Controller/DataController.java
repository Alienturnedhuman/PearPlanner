package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
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
            if(!nodes.item(i).equals(nodeNames[i]))
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

        NodeList assetNodes = nList.item(1).getChildNodes();
        String[] assetSchema = {"persons","rooms","buildings","timetableEventTypes"};

        NodeList studyProfileNodes = nList.item(2).getChildNodes();
        String[] studyProfileSchema = {"year","semester","modules"};

        if(validNodeList(assetNodes,assetSchema) && validNodeList(studyProfileNodes,studyProfileSchema))
        {
            ArrayList<VersionControlEntity> newAssets = new ArrayList<>();
            ArrayList<Module> newModules = new ArrayList<>();
            // replace these with reading the values
            int year = 2017;
            int semester = 1;

            // loop through assets adding to new Assets

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
                    NodeList nList = rootElement.getChildNodes();
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
