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

import edu.wright.cs.raiderplanner.model.Building;
import edu.wright.cs.raiderplanner.model.Coursework;
import edu.wright.cs.raiderplanner.model.Event;
import edu.wright.cs.raiderplanner.model.Exam;
import edu.wright.cs.raiderplanner.model.HubFile;
import edu.wright.cs.raiderplanner.model.Module;
import edu.wright.cs.raiderplanner.model.MultilineString;
import edu.wright.cs.raiderplanner.model.Person;
import edu.wright.cs.raiderplanner.model.Room;
import edu.wright.cs.raiderplanner.model.TimeTableEventType;
import edu.wright.cs.raiderplanner.model.TimetableEvent;
import edu.wright.cs.raiderplanner.model.VersionControlEntity;
import edu.wright.cs.raiderplanner.view.ConsoleIO;
import edu.wright.cs.raiderplanner.view.UIManager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Helper class that contains static methods for working with version control
 * entities.
 *
 * @author Ben Dickson
 */
public class DataController {

	/**
	 * Private constructor to prevent object instantiation.
	 */
	private DataController() {
	}

	/**
	 * Update the library of version control entities with the given VCE.
	 *
	 * @param vce the entity to update.
	 */
	private static void processVceUpdate(VersionControlEntity vce) {
		if (vce.addToLibrary() || VersionControlEntity.get(vce.getUid()).update(vce)) {
			ConsoleIO.setConsoleMessage(vce + " added", true);
		} else {
			ConsoleIO.setConsoleMessage(vce + " not added", true);
		}
	}

	/**
	 * Update the library of version control entities based on the given node
	 * list.
	 *
	 * @param nodes the list of nodes to process for the update.
	 *
	 * @return The updated HUB file.
	 * @throws IOException if there is a problem creating an entity from the
	 * 				serialized representation
	 */
	private static HubFile processUpdateHubFile(NodeList nodes) throws IOException {

		HubFile hub = null;
		XMLcontroller xmlTools = new XMLcontroller();

		HashMap<String, XMLcontroller.NodeReturn> fileValues =
				xmlTools.getSchemaValues(nodes, HubFile.SCHEMA_UPDATE_FILE);

		NodeList updates = fileValues.get("updates").getNodeList();

		for (int i = 0; i < updates.getLength(); i++) {
			Node node = updates.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE
					&& HubFile.updateableNode(node.getNodeName())
					&& XMLcontroller.matchesSchema(node.getChildNodes(), HubFile.SCHEMA_VCE)) {
				String nodeName = node.getNodeName();
				HashMap<String, XMLcontroller.NodeReturn> nodeValues =
						xmlTools.getSchemaValues(node.getChildNodes(),
								HubFile.schemaList.get(nodeName));

				int version = nodeValues.get("version").getInt();
				String uid = nodeValues.get("uid").getString();
				if (VersionControlEntity.inLibrary(uid)
						&& VersionControlEntity.get(uid).getVersion() < version) {

					NodeList nc = XMLcontroller.getNodes(node);
					switch (nodeName) {
					case "person":
						processVceUpdate(HubFile.createPerson(nc));
						break;
					case "building":
						processVceUpdate(HubFile.createBuilding(nc));
						break;
					case "room":
						processVceUpdate(HubFile.createRoom(nc,
								VersionControlEntity.getLibrary()));
						break;
					case "module":
						// TODO: not yet added
						break;
					case "exam":
						processVceUpdate(HubFile.createExam(nc,
								VersionControlEntity.getLibrary()));
						break;
					case "coursework":
						processVceUpdate(HubFile.createCoursework(nc,
								VersionControlEntity.getLibrary()));
						break;
					case "timetableEventType":
						processVceUpdate(HubFile.createTimetableEventType(nc));
						break;
					case "timetableEvent":
						processVceUpdate(HubFile.createTimetableEvent(nc,
								VersionControlEntity.getLibrary()));
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
					default:
						throw new RuntimeException("Unknown node: " + nodeName);
					}

				}
			}
		}

		return hub;
	}

	/**
	 * Returns the version control entity for the given UID.
	 *
	 * @param list a list from which the version control entity may be taken.
	 * @param uid the identifier to look up.
	 *
	 * @return the entity from the list or the library.
	 * @throws IOException if the requested entity is not in the list or cannot
	 * 				be cast to the specified type
	 */
	public static <T extends VersionControlEntity> T inList(
			Map<String, VersionControlEntity> list, String uid) throws IOException {

		VersionControlEntity vce = null;
		if (list.containsKey(uid)) {
			vce = list.get(uid);
		} else if (VersionControlEntity.inLibrary(uid)) {
			vce = VersionControlEntity.get(uid);
		}
		// can't do instanceof T annoyingly, so hopefully casting to T and failing will work instead
		// TODO find a better approach for this
		if (vce != null) {
			try {
				return (T) vce;
			} catch (Exception e) {
				// TODO this should be IllegalStateException, RuntimeException, or other
				throw new IOException("Incorrect type referenced for '" + uid + "'");
			}
		}
		// TODO this should not be here, rather callers should receive the null return
		throw new IOException("UID referenced is not in database for '" + uid + "'");

	}

	/**
	 * Add the given values to the specified version control entity.
	 *
	 * @param vce the entity to update.
	 * @param values the values to use for the update.
	 */
	public static void addVceProperties(VersionControlEntity vce,
			Map<String, XMLcontroller.NodeReturn> values) {

		vce.addProperties(values.get("name").getString(),
				values.get("details").getMultilineString());
		String uid = values.get("uid").getString();
		vce.makeImporter();
		vce.setUid(uid, values.get("version").getInt());

	}

	/**
	 * Create a new HUB file from the given list of nodes.
	 *
	 * @param nodes the list of nodes to use for constituting the HUB file.
	 *
	 * @return the HUB file.
	 * @throws IOException when attempting to import an already existing study
	 * 				profile, or for errors connected with creating objects from
	 * 				their serialized representations
	 */
	private static HubFile processNewHubFile(NodeList nodes) throws IOException {

		int beginLog = ConsoleIO.getLogSize();
		ConsoleIO.setConsoleMessage("Importing New Hub File", true);
		HubFile hub = null;
		XMLcontroller xmlTools = new XMLcontroller();

		HashMap<String, XMLcontroller.NodeReturn> fileValues =
				xmlTools.getSchemaValues(nodes, HubFile.SCHEMA_NEW_STUDYPROFILE);

		int version = fileValues.get("version").getInt();
		NodeList assetNodes = fileValues.get("assets").getNodeList();
		NodeList studyProfileNodes = fileValues.get("studyProfile").getNodeList();

		if (XMLcontroller.matchesSchema(assetNodes, HubFile.SCHEMA_ASSETS)
				&& XMLcontroller.matchesSchema(studyProfileNodes, HubFile.SCHEMA_STUDYPROFILE)) {
			ConsoleIO.setConsoleMessage("Schema Validates: assets", true);
			ConsoleIO.setConsoleMessage("Schema Validates: studyProfile", true);

			HashMap<String, XMLcontroller.NodeReturn> studyProfileValues =
					xmlTools.getSchemaValues(studyProfileNodes, HubFile.SCHEMA_STUDYPROFILE);

			int year = studyProfileValues.get("year").getInt();
			int semester = studyProfileValues.get("semester").getInt();

			if (MainController.getSpc().containsStudyProfile(year, semester)) {
				// TODO this should be IllegalStateException, RuntimeException, or other
				throw new IOException("Study profile for " + year + " semester "
						+ semester + " already imported");
			}

			HashMap<String, VersionControlEntity> assetList = new HashMap<>();
			HashMap<String, XMLcontroller.NodeReturn> assetValues =
					xmlTools.getSchemaValues(assetNodes, HubFile.SCHEMA_ASSETS);

			ArrayList<Module> newModules = new ArrayList<>();

			Node node;
			NodeList nc;

			// Add all the Person classes
			if (assetValues.containsKey("persons")) {

				NodeList personList = assetValues.get("persons").getNodeList();
				Person tp;
				int pll = personList.getLength();
				ConsoleIO.setConsoleMessage("Reading persons tag, "
						+ Integer.toString(pll) + " nodes:", true);
				for (int i = 0; i < pll; i++) {
					node = personList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						nc = XMLcontroller.getNodes(node);
						if (node.getNodeName().equals("person")
								&& XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_PERSON)) {

							ConsoleIO.setConsoleMessage("Valid Node found:", true);

							tp = HubFile.createPerson(nc);

							assetList.put(tp.getUid(), tp);

							ConsoleIO.setConsoleMessage("Adding person: " + tp.toString(), true);
						}
					}
				}
			}

			// add all the Buildings
			if (assetValues.containsKey("buildings")) {
				NodeList buildingList = assetValues.get("buildings").getNodeList();
				Building tb;
				int bll = buildingList.getLength();
				ConsoleIO.setConsoleMessage("Reading buildings tag, "
						+ Integer.toString(bll) + " nodes:", true);
				for (int i = 0; i < bll; i++) {
					node = buildingList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						nc = XMLcontroller.getNodes(node);
						if (node.getNodeName().equals("building")
								&& XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_BUILDING)) {
							ConsoleIO.setConsoleMessage("Valid Node found:", true);


							tb = HubFile.createBuilding(nc);

							assetList.put(tb.getUid(), tb);
							ConsoleIO.setConsoleMessage("Adding buiding: " + tb.toString(), true);
						}
					}
				}
			}

			// add all the Rooms
			if (assetValues.containsKey("rooms")) {
				NodeList roomList = assetValues.get("rooms").getNodeList();
				Room tr;
				int rll = roomList.getLength();
				ConsoleIO.setConsoleMessage("Reading rooms tag, "
						+ Integer.toString(rll) + " nodes:", true);
				for (int i = 0; i < rll; i++) {
					node = roomList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						nc = XMLcontroller.getNodes(node);
						if (node.getNodeName().equals("room")
								&& XMLcontroller.matchesSchema(nc, HubFile.SCHEMA_ROOM)) {
							ConsoleIO.setConsoleMessage("Valid Node found:", true);

							tr = HubFile.createRoom(nc, assetList);
							assetList.put(tr.getUid(), tr);
							ConsoleIO.setConsoleMessage("Adding room: " + tr.toString(), true);
						}
					}
				}
			}

			// add all the TimeTableTypes
			if (assetValues.containsKey("timetableEventTypes")) {
				NodeList ttetList = assetValues.get("timetableEventTypes").getNodeList();
				int tll = ttetList.getLength();
				ConsoleIO.setConsoleMessage("Reading timetableEventTypes tag, "
						+ Integer.toString(tll) + " nodes:", true);
				for (int i = 0; i < tll; i++) {
					node = ttetList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						nc = XMLcontroller.getNodes(node);
						if (node.getNodeName().equals("timetableEventType")
								&& XMLcontroller.matchesSchema(nc,
										HubFile.SCHEMA_TIMETABLE_EVENT_TYPE)) {
							ConsoleIO.setConsoleMessage("Valid Node found:", true);
							TimeTableEventType ttet = HubFile.createTimetableEventType(nc);


							assetList.put(ttet.getUid(), ttet);
							ConsoleIO.setConsoleMessage("Adding timetable event: "
									+ ttet.toString(), true);
						}
					}

				}
			}


			NodeList modules = studyProfileValues.get("modules").getNodeList();
			int mll = modules.getLength();
			for (int i = 0; i < mll; i++) {
				if (modules.item(i).getNodeType() == Node.ELEMENT_NODE
						&& modules.item(i).getNodeName().equals("module")
						&& modules.item(i).hasChildNodes()
						&& XMLcontroller.matchesSchema(modules.item(i).getChildNodes(),
								HubFile.SCHEMA_MODULE)) {

					HashMap<String, XMLcontroller.NodeReturn> moduleValues =
							xmlTools.getSchemaValues(modules.item(i).getChildNodes(),
									HubFile.SCHEMA_MODULE);

					String linkedPerson = moduleValues.get("organiser").getString();

					Person organiser = inList(assetList, linkedPerson);
					String moduleCode = moduleValues.get("moduleCode").getString();
					Module thisModule = new Module(organiser, moduleCode);


					addVceProperties(thisModule, moduleValues);

					assetList.put(moduleValues.get("uid").getString(), thisModule);

					NodeList assignments = moduleValues.get("assignments").getNodeList();
					int all = assignments.getLength();
					ConsoleIO.setConsoleMessage("Reading assignments tag, "
							+ Integer.toString(all) + " nodes:", true);
					for (int j = 0; j < all; j++) {
						if (assignments.item(j).getNodeType() == Node.ELEMENT_NODE) {
							nc = assignments.item(j).getChildNodes();
							String nodeName = assignments.item(j).getNodeName();
							switch (nodeName) {
							case "coursework":
								if (XMLcontroller.matchesSchema(
										assignments.item(j).getChildNodes(),
										HubFile.SCHEMA_COURSEWORK)) {
									ConsoleIO.setConsoleMessage("Valid Node found:", true);
									Coursework newCoursework =
											HubFile.createCoursework(nc, assetList);
									assetList.put(newCoursework.getUid(), newCoursework);
									thisModule.addAssignment(newCoursework);
									ConsoleIO.setConsoleMessage("Adding coursework: "
											+ newCoursework.toString(), true);
								}
								break;

							case "exam":
								if (XMLcontroller.matchesSchema(
										assignments.item(j).getChildNodes(),
										HubFile.SCHEMA_EXAM)) {
									ConsoleIO.setConsoleMessage("Valid Node found:", true);


									Exam newExam = HubFile.createExam(nc, assetList);
									assetList.put(newExam.getUid(), newExam);
									thisModule.addAssignment(newExam);
									ConsoleIO.setConsoleMessage("Adding exam: "
											+ newExam.toString(), true);
								}

								break;
							default:
								throw new RuntimeException("Unknown assignment tag: " + nodeName);
							}

						}
					}
					NodeList timetable = moduleValues.get("timetable").getNodeList();
					int ttll = timetable.getLength();
					ConsoleIO.setConsoleMessage("Reading timetable tag, "
							+ Integer.toString(ttll) + " nodes:", true);
					for (int j = 0; j < ttll; j++) {
						if (timetable.item(j).getNodeType() == Node.ELEMENT_NODE
								&& timetable.item(j).getNodeName().equals("timetableEvent")
								&& XMLcontroller.matchesSchema(timetable.item(j).getChildNodes(),
										HubFile.SCHEMA_TIMETABLE_EVENT)) {
							ConsoleIO.setConsoleMessage("Valid Node found:", true);
							nc = timetable.item(j).getChildNodes();

							TimetableEvent newTte =
									HubFile.createTimetableEvent(nc, assetList);
							assetList.put(newTte.getUid(), newTte);
							thisModule.addTimetableEvent(newTte);

							ConsoleIO.setConsoleMessage("Adding TimetableEvent: "
									+ newTte.toString(), true);
						}
					}
					newModules.add(thisModule);
				}
			}

			ConsoleIO.setConsoleMessage("Attempting to import "
					+ Integer.toString(assetList.size())
					+ " items to VersionControl...", true);
			ConsoleIO.setConsoleMessage("Starting with "
					+ VersionControlEntity.libraryReport() + " entries");

			ArrayList<Event> calendarItems = new ArrayList<>();

			for (String key : assetList.keySet()) {
				ConsoleIO.setConsoleMessage("Adding Asset: " + key, true);
				if (assetList.get(key).addToLibrary()) {
					ConsoleIO.setConsoleMessage("New Asset, adding... ", true);
					if (assetList.get(key) instanceof Event) {

						ConsoleIO.setConsoleMessage("Event - adding to Calendar: ", true);
						calendarItems.add((Event) assetList.get(key));
					}
				} else if (assetList.get(key).isImporter()) {
					ConsoleIO.setConsoleMessage("In library, attempting update: ", true);
					ConsoleIO.setConsoleMessage(assetList.get(key).toString()
							+ (VersionControlEntity.get(key).update(assetList.get(key))
									? " updated" : " not updated"), true);
				} else {
					ConsoleIO.setConsoleMessage(assetList.get(key).toString()
							+ " not imported", true);
				}
			}
			ConsoleIO.setConsoleMessage("Ending with "
					+ VersionControlEntity.libraryReport() + " entries");
			ConsoleIO.saveLog("import_report.txt", beginLog, ConsoleIO.getLogSize());

			String name = studyProfileValues.get("name").getString();
			MultilineString details = studyProfileValues.get("details").getMultilineString();
			String uid = studyProfileValues.get("uid").getString();

			hub = new HubFile(version, year, semester, newModules,
					new ArrayList<VersionControlEntity>(), calendarItems, name,
					details, uid);
		}
		return hub;
	}

	/**
	 * Create a HUB file from the given file.
	 *
	 * @param tempFile the file from which to create the HUB file.
	 *
	 * @return the HUB file.
	 */
	public static HubFile loadHubFile(File tempFile) {
		HubFile hub = null;
		if (tempFile.exists()) {
			try {
				// learned from:
				// https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(tempFile);
				doc.getDocumentElement().normalize();

				String rootElementTag = doc.getDocumentElement().getNodeName();
				Node rootElement = doc.getDocumentElement();

				// check it is a hubfile
				if (rootElementTag.equals("hubfile")
						&& rootElement.hasChildNodes()) {
					NodeList nodes = XMLcontroller.getNodes(rootElement);
					if (XMLcontroller.matchesSchema(nodes, HubFile.SCHEMA_NEW_STUDYPROFILE)) {
						hub = processNewHubFile(nodes);
					} else if (XMLcontroller.matchesSchema(nodes, HubFile.SCHEMA_UPDATE_FILE)) {
						hub = processUpdateHubFile(nodes);
					} else {
						UIManager.reportError("Invalid Parent Nodes");
					}
				} else {
					UIManager.reportError("Invalid XML file");
				}

			} catch (IOException e) {
				UIManager.reportError("Invalid File: \n" + e.getMessage());
			} catch (SAXException e) {
				UIManager.reportError("SAX Exception");
			} catch (Exception e) {
				UIManager.reportError(e.getMessage());
			}
		}
		return hub;
	}
}