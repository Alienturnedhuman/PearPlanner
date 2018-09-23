/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar, Roberto C. Sánchez
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

package edu.wright.cs.raiderplanner.controller;

import static edu.wright.cs.raiderplanner.controller.MainController.isNumeric;

import edu.wright.cs.raiderplanner.model.MultilineString;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by bendickson on 5/6/17.
 */
public class XmlController {

	/**
	 * Signals the type as which to interpret an XML node being processed.
	 */
	public static enum ImportAs {
		BOOLEAN, STRING, INTEGER, DOUBLE, MULTILINESTRING, NODELIST
	}

	/**
	 * The class represents a node which has been parsed from an XML file as a
	 * specific primitive type or Object type.
	 */
	public static class NodeReturn {
		// TODO: The premise of this class seems wrong; replace with something less fragile
		// In particular, the fact that attempting to retrieve a primitive type
		// when that is not the type as which the node was imported will return
		// a value which may be confused with a legitimate value makes the whole
		// thing rather fragile.
		private ImportAs importedAs;
		private String stringValue;
		private int integerValue;
		private double doubleValue;
		private MultilineString multilineStringValue;
		private NodeList nodeList;
		private boolean booleanValue = false;

		/**
		 * Returns the boolean value parsed from the XML.
		 * @return The boolean value that was parsed from the XML.
		 */
		public boolean getBoolean() {
			if (importedAs == ImportAs.BOOLEAN) {
				return booleanValue;
			} else {
				return false;
			}
		}

		/**
		 * Returns the string value that was parsed from the XML.
		 * @return The String value that was parsed from the XML.
		 */
		public String getString() {
			if (importedAs == ImportAs.STRING) {
				return stringValue;
			} else {
				return null;
			}
		}

		/**
		 * Returns the MultilineString value that was parsed from the XML.
		 * @return The MultilineString value that was parsed from the XML.
		 */
		public MultilineString getMultilineString() {
			if (importedAs == ImportAs.MULTILINESTRING) {
				return multilineStringValue;
			} else {
				return null;
			}
		}

		/**
		 * Returns the integer value that was parsed from the XML.
		 * @return The int value that was parsed from the XML.
		 */
		public int getInt() {
			if (importedAs == ImportAs.INTEGER) {
				return integerValue;
			} else {
				return 0;
			}
		}

		/**
		 * Returns the double value that was parsed from the XML.
		 * @return The double value that was parsed from the XML.
		 */
		public double getDouble() {
			if (importedAs == ImportAs.DOUBLE) {
				return doubleValue;
			} else {
				return 0;
			}
		}

		/**
		 * Returns the NodeList value that was parsed from the XML.
		 * @return The NodeList value that was parsed from the XML.
		 */
		public NodeList getNodeList() {
			if (importedAs == ImportAs.NODELIST) {
				return nodeList;
			} else {
				return null;
			}
		}

		/**
		 * Create a NodeReturn containing the given boolean value.
		 *
		 * @param nv The boolean value that was parsed from the XML.
		 */
		private NodeReturn(boolean nv) {
			importedAs = ImportAs.BOOLEAN;
			booleanValue = nv;
		}

		/**
		 * Create a NodeReturn containing the given int value.
		 *
		 * @param nv The int value that was parsed from the XML.
		 */
		private NodeReturn(int nv) {
			importedAs = ImportAs.INTEGER;
			integerValue = nv;
		}

		/**
		 * Create a NodeReturn containing the given double value.
		 *
		 * @param nv The double value that was parsed from the XML.
		 */
		private NodeReturn(double nv) {
			importedAs = ImportAs.DOUBLE;
			doubleValue = nv;
		}

		/**
		 * Create a NodeReturn containing the given String value.
		 *
		 * @param nv The String value that was parsed from the XML.
		 */
		private NodeReturn(String nv) {
			importedAs = ImportAs.STRING;
			stringValue = nv;
		}

		/**
		 * Create a NodeReturn containing the given MultilineString value.
		 *
		 * @param nv The MultilineString value that was parsed from the XML.
		 */
		private NodeReturn(MultilineString nv) {
			importedAs = ImportAs.MULTILINESTRING;
			multilineStringValue = nv;
		}

		/**
		 * Create a NodeReturn containing the given NodeList value.
		 *
		 * @param nv The NodeList value that was parsed from the XML.
		 */
		private NodeReturn(NodeList nv) {
			importedAs = ImportAs.NODELIST;
			nodeList = nv;
		}
	}

	/**
	 * Process the given NodeList using the given schema and return a map of
	 * node names and their corresponding values.
	 *
	 * @param nodes The NodeList to process
	 * @param schema The schema of types for each node name
	 *
	 * @return A map of node names and their corresponding values
	 */
	public HashMap<String, NodeReturn> getSchemaValues(NodeList nodes,
			HashMap<String, ImportAs> schema) {
		HashMap<String, NodeReturn> hash = new HashMap<>();
		int num = -1;
		int ii = nodes.getLength();
		String nodeName;
		String temp;
		while (++num < ii) {
			if (nodes.item(num).getNodeType() == Node.ELEMENT_NODE) {
				nodeName = nodes.item(num).getNodeName();
				if (schema.containsKey(nodeName) && !hash.containsKey(nodeName)) {
					switch (schema.get(nodeName)) {
					case BOOLEAN:
						hash.put(nodeName, new NodeReturn(nodes.item(num)
								.getTextContent().equals("true")));
						break;
					case STRING:
						hash.put(nodeName, new NodeReturn(nodes.item(num).getTextContent()));
						break;
					case MULTILINESTRING:
						hash.put(nodeName, new NodeReturn(new MultilineString(nodes.item(num)
								.getTextContent())));
						break;
					case INTEGER:
						temp = nodes.item(num).getTextContent();
						if (isNumeric(temp)) {
							hash.put(nodeName, new NodeReturn(Integer.parseInt(temp)));
						}
						break;
					case DOUBLE:
						temp = nodes.item(num).getTextContent();
						if (isNumeric(temp)) {
							hash.put(nodeName, new NodeReturn(Double.parseDouble(temp)));
						}
						break;
					case NODELIST:
						if (nodes.item(num).hasChildNodes()) {
							hash.put(nodeName, new NodeReturn(nodes.item(num).getChildNodes()));
						}
						break;
					default:
						// Do nothing
					}
				}
			}
		}
		return hash;
	}

	/**
	 * Returns the NodeList of children under a given parent node.
	 *
	 * @param parentNode The parent node to process
	 *
	 * @return The NodeList of child nodes
	 */
	public static NodeList getNodes(Node parentNode) {
		NodeList tlist = parentNode.getChildNodes();
		int count = tlist.getLength();
		while (0 < count--) {
			if (tlist.item(count).getNodeType() != Node.ELEMENT_NODE) {
				parentNode.removeChild(tlist.item(count));
			}
		}
		return parentNode.getChildNodes();
	}

	/**
	 * Determines if the given list of nodes and associated schema contain
	 * matching sets of node names.
	 *
	 * @param nodes The list of nodes to match against the schema
	 * @param schema The schema to match against the list of nodes
	 *
	 * @return True if the node list and schema match, false otherwise
	 */
	public static boolean matchesSchema(NodeList nodes,
			HashMap<String, XmlController.ImportAs> schema) {
		HashSet<String> match = new HashSet<>();
		int num = -1;
		int ii = nodes.getLength();
		while (++num < ii) {
			if (nodes.item(num).getNodeType() == Node.ELEMENT_NODE
					&& schema.containsKey(nodes.item(num).getNodeName())) {
				match.add(nodes.item(num).getNodeName());
			}
		}
		return match.size() == schema.size();
	}
}
