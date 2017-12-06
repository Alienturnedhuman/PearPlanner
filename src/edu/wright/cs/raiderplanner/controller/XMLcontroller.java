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
					default:
						break;
					}
				}
			}
		}
		return hash;
	}

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

	@Deprecated
	public static boolean validNodeList(NodeList nodes, String[] nodeNames) {
		int num = -1;
		int ii = nodeNames.length;
		if (nodes.getLength() != ii) {
			return false;
		}
		while (++num < ii) {
			if (!nodes.item(num).getNodeName().equals(nodeNames[num])) {
				return false;
			}
		}
		return true;
	}

	public static boolean matchesSchema(NodeList nodes, HashMap<String, XMLcontroller.ImportAs> schema) {
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
