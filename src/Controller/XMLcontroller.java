package Controller;

import Model.MultilineString;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

import static Controller.MainController.isNumeric;

/**
 * Created by bendickson on 5/6/17.
 */
public class XMLcontroller
{
    public enum ImportAs
    {
        BOOLEAN, STRING, INTEGER, DOUBLE, MULTILINESTRING, NODELIST;
    }

    public class NodeReturn
    {
        private ImportAs importedAs;
        private String stringValue;
        private int integerValue;
        private double doubleValue;
        private MultilineString multilineStringValue;
        private NodeList nodeList;
        private boolean booleanValue = false;

        public boolean getBoolean()
        {
            if (importedAs == ImportAs.BOOLEAN)
            {
                return booleanValue;
            } else
            {
                return false;
            }
        }

        public String getString()
        {
            if (importedAs == ImportAs.STRING)
            {
                return stringValue;
            } else
            {
                return null;
            }
        }

        public MultilineString getMultilineString()
        {
            if (importedAs == ImportAs.MULTILINESTRING)
            {
                return multilineStringValue;
            } else
            {
                return null;
            }
        }

        public int getInt()
        {
            if (importedAs == ImportAs.INTEGER)
            {
                return integerValue;
            } else
            {
                return 0;
            }
        }

        public double getDouble()
        {
            if (importedAs == ImportAs.DOUBLE)
            {
                return doubleValue;
            } else
            {
                return 0;
            }
        }

        public NodeList getNodeList()
        {
            if (importedAs == ImportAs.NODELIST)
            {
                return nodeList;
            } else
            {
                return null;
            }
        }

        NodeReturn(boolean nv)
        {
            importedAs = ImportAs.BOOLEAN;
            booleanValue = nv;
        }

        NodeReturn(int nv)
        {
            importedAs = ImportAs.INTEGER;
            integerValue = nv;
        }

        NodeReturn(double nv)
        {
            importedAs = ImportAs.DOUBLE;
            doubleValue = nv;
        }

        NodeReturn(String nv)
        {
            importedAs = ImportAs.STRING;
            stringValue = nv;
        }

        NodeReturn(MultilineString nv)
        {
            importedAs = ImportAs.MULTILINESTRING;
            multilineStringValue = nv;
        }

        NodeReturn(NodeList nv)
        {
            importedAs = ImportAs.NODELIST;
            nodeList = nv;
        }
    }

    public HashMap<String, NodeReturn> getSchemaValues(NodeList nodes, HashMap<String, ImportAs> schema)
    {
        HashMap<String, NodeReturn> r = new HashMap<>();
        int i = -1;
        int ii = nodes.getLength();
        String nodeName;
        String temp;
        while (++i < ii)
        {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
                nodeName = nodes.item(i).getNodeName();
                if (schema.containsKey(nodeName) && !r.containsKey(nodeName))
                {
                    switch (schema.get(nodeName))
                    {
                        case BOOLEAN:
                            r.put(nodeName, new NodeReturn(nodes.item(i).getTextContent().equals("true")));
                            break;
                        case STRING:
                            r.put(nodeName, new NodeReturn(nodes.item(i).getTextContent()));
                            break;
                        case MULTILINESTRING:
                            r.put(nodeName, new NodeReturn(new MultilineString(nodes.item(i).getTextContent())));
                            break;
                        case INTEGER:
                            temp = nodes.item(i).getTextContent();
                            if (isNumeric(temp))
                            {
                                r.put(nodeName, new NodeReturn(Integer.parseInt(temp)));
                            }
                            break;
                        case DOUBLE:
                            temp = nodes.item(i).getTextContent();
                            if (isNumeric(temp))
                            {
                                r.put(nodeName, new NodeReturn(Double.parseDouble(temp)));
                            }
                            break;
                        case NODELIST:
                            if (nodes.item(i).hasChildNodes())
                            {
                                r.put(nodeName, new NodeReturn(nodes.item(i).getChildNodes()));
                            }
                    }
                }
            }
        }
        return r;
    }

    static public NodeList getNodes(Node parentNode)
    {
        NodeList tList = parentNode.getChildNodes();
        int i = tList.getLength();
        while (0 < i--)
        {
            if (tList.item(i).getNodeType() != Node.ELEMENT_NODE)
            {
                parentNode.removeChild(tList.item(i));
            }
        }
        return parentNode.getChildNodes();
    }

    static public boolean validNodeList(NodeList nodes, String[] nodeNames)
    {
        int i = -1;
        int ii = nodeNames.length;
        if (nodes.getLength() != ii)
        {
            return false;
        }
        while (++i < ii)
        {
            if (!nodes.item(i).getNodeName().equals(nodeNames[i]))
            {
                return false;
            }
        }
        return true;
    }

    static public boolean matchesSchema(NodeList nodes, HashMap<String, XMLcontroller.ImportAs> schema)
    {
        HashMap<String, String> match = new HashMap<>();
        int i = -1;
        int ii = nodes.getLength();
        while (++i < ii)
        {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE && schema.containsKey(nodes.item(i).getNodeName()))
            {
                match.put(nodes.item(i).getNodeName(), "found");
            }
        }
        return match.size() == schema.size();
    }
}
