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

package edu.wright.cs.raiderplanner.controller;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wright.cs.raiderplanner.model.MultilineString;

import static edu.wright.cs.raiderplanner.controller.MainController.isNumeric;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by bendickson on 5/6/17.
 */
public class XMLcontroller {
	public enum ImportAs {
		BOOLEAN, STRING, INTEGER, DOUBLE, MULTILINESTRING, NODELIST
	}

	public class NodeReturn {
		private ImportAs importedAs;
		private String stringValue;
		private int integerValue;
		private double doubleValue;
		private MultilineString multilineStringValue;
		private NodeList nodeList;
		private boolean booleanValue = false;

		public boolean getBoolean() {
			if (importedAs == ImportAs.BOOLEAN) {
				return booleanValue;
			} else {
				return false;
			}
		}

		public String getString() {
			if (importedAs == ImportAs.STRING) {
				return stringValue;
			} else {
				return null;
			}
		}

		public MultilineString getMultilineString() {
			if (importedAs == ImportAs.MULTILINESTRING) {
				return multilineStringValue;
			} else {
				return null;
			}
		}

		public int getInt() {
			if (importedAs == ImportAs.INTEGER) {
				return integerValue;
			} else {
				return 0;
			}
		}

		public double getDouble() {
			if (importedAs == ImportAs.DOUBLE) {
				return doubleValue;
			} else {
				return 0;
			}
		}

		public NodeList getNodeList() {
			if (importedAs == ImportAs.NODELIST) {
				return nodeList;
			} else {
				return null;
			}
		}

		NodeReturn(boolean nv) {
			importedAs = ImportAs.BOOLEAN;
			booleanValue = nv;
		}

		NodeReturn(int nv) {
			importedAs = ImportAs.INTEGER;
			integerValue = nv;
		}

		NodeReturn(double nv) {
			importedAs = ImportAs.DOUBLE;
			doubleValue = nv;
		}

		NodeReturn(String nv) {
			importedAs = ImportAs.STRING;
			stringValue = nv;
		}

		NodeReturn(MultilineString nv) {
			importedAs = ImportAs.MULTILINESTRING;
			multilineStringValue = nv;
		}

		NodeReturn(NodeList nv) {
			importedAs = ImportAs.NODELIST;
			nodeList = nv;
		}
	}

	public HashMap<String, NodeReturn> getSchemaValues(NodeList nodes, 
			HashMap<String, ImportAs> schema) {
		HashMap<String, NodeReturn> r = new HashMap<>();
		int i = -1;
		int ii = nodes.getLength();
		String nodeName;
		String temp;
		while (++i < ii) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				nodeName = nodes.item(i).getNodeName();
				if (schema.containsKey(nodeName) && !r.containsKey(nodeName)) {
					switch (schema.get(nodeName)) {
					case BOOLEAN:
						r.put(nodeName, new NodeReturn(nodes.item(i)
								.getTextContent().equals("true")));
						break;
					case STRING:
						r.put(nodeName, new NodeReturn(nodes.item(i).getTextContent()));
						break;
					case MULTILINESTRING:
						r.put(nodeName, new NodeReturn(new MultilineString(nodes.item(i)
								.getTextContent())));
						break;
					case INTEGER:
						temp = nodes.item(i).getTextContent();
						if (isNumeric(temp)) {
							r.put(nodeName, new NodeReturn(Integer.parseInt(temp)));
						}
						break;
					case DOUBLE:
						temp = nodes.item(i).getTextContent();
						if (isNumeric(temp)) {
							r.put(nodeName, new NodeReturn(Double.parseDouble(temp)));
						}
						break;
					case NODELIST:
						if (nodes.item(i).hasChildNodes()) {
							r.put(nodeName, new NodeReturn(nodes.item(i).getChildNodes()));
						}
					}
				}
			}
		}
		return r;
	}

	static public NodeList getNodes(Node parentNode) {
		NodeList tList = parentNode.getChildNodes();
		int i = tList.getLength();
		while (0 < i--) {
			if (tList.item(i).getNodeType() != Node.ELEMENT_NODE) {
				parentNode.removeChild(tList.item(i));
			}
		}
		return parentNode.getChildNodes();
	}

	@Deprecated
	static public boolean validNodeList(NodeList nodes, String[] nodeNames) {
		int i = -1;
		int ii = nodeNames.length;
		if (nodes.getLength() != ii) {
			return false;
		}
		while (++i < ii) {
			if (!nodes.item(i).getNodeName().equals(nodeNames[i])) {
				return false;
			}
		}
		return true;
	}

	static public boolean matchesSchema(NodeList nodes, HashMap<String, XMLcontroller.ImportAs> schema) {
		HashSet<String> match = new HashSet<>();
		int i = -1;
		int ii = nodes.getLength();
		while (++i < ii) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE
					&& schema.containsKey(nodes.item(i).getNodeName())) {
				match.add(nodes.item(i).getNodeName());
			}
		}
		return match.size() == schema.size();
	}
}
