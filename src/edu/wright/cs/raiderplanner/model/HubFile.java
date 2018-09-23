/*
 * Copyright (C) 2017 - Benjamin Dickson, Andrew Odintsov, Zilvinas Ceikauskas,
 * Bijan Ghasemi Afshar
 * Copyright (C) 2018 - Roberto C. Sánchez
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

package edu.wright.cs.raiderplanner.model;

import edu.wright.cs.raiderplanner.controller.DataController;
import edu.wright.cs.raiderplanner.controller.XmlController;

import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PearPlanner/RaiderPlanner.
 * Created by Team BRONZE on 4/27/17
 */
public class HubFile implements Serializable {

	private ArrayList<VersionControlEntity> assets = new ArrayList<>();
	private ArrayList<Module> modules = new ArrayList<>();
	private ArrayList<ExtensionApplication> extensions = new ArrayList<>();
	private ArrayList<VersionControlEntity> updates = new ArrayList<>();
	private ArrayList<Event> calendarList = new ArrayList<>();
	private int version;
	private int semester;
	private int year;
	private boolean updateFile;
	private String semesterName;
	private String semesterUId;
	private MultilineString semesterDetails;

	// schemas
	// note, for the time being these are hard coded into the code
	// long term, these would be imported from a settings file

	// special SCHEMA for update file
	public static HashMap<String, XmlController.ImportAs> SCHEMA_VCE;

	static {
		SCHEMA_VCE = new HashMap<>();
		SCHEMA_VCE.put("name", XmlController.ImportAs.STRING);
		SCHEMA_VCE.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_VCE.put("uid", XmlController.ImportAs.STRING);
		SCHEMA_VCE.put("version", XmlController.ImportAs.INTEGER);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_ROOT;

	static {
		SCHEMA_ROOT = new HashMap<>();
		SCHEMA_ROOT.put("hubfile", XmlController.ImportAs.NODELIST);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_NEW_STUDYPROFILE;

	static {
		SCHEMA_NEW_STUDYPROFILE = new HashMap<>();
		SCHEMA_NEW_STUDYPROFILE.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_NEW_STUDYPROFILE.put("assets", XmlController.ImportAs.NODELIST);
		SCHEMA_NEW_STUDYPROFILE.put("studyProfile", XmlController.ImportAs.NODELIST);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_UPDATE_FILE;

	static {
		SCHEMA_UPDATE_FILE = new HashMap<>();
		SCHEMA_UPDATE_FILE.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_UPDATE_FILE.put("extensions", XmlController.ImportAs.NODELIST);
		SCHEMA_UPDATE_FILE.put("updates", XmlController.ImportAs.NODELIST);
		SCHEMA_UPDATE_FILE.put("new", XmlController.ImportAs.NODELIST);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_ASSETS;

	static {
		SCHEMA_ASSETS = new HashMap<>();
		SCHEMA_ASSETS.put("persons", XmlController.ImportAs.NODELIST);
		SCHEMA_ASSETS.put("buildings", XmlController.ImportAs.NODELIST);
		SCHEMA_ASSETS.put("rooms", XmlController.ImportAs.NODELIST);
		SCHEMA_ASSETS.put("timetableEventTypes", XmlController.ImportAs.NODELIST);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_STUDYPROFILE;

	static {
		SCHEMA_STUDYPROFILE = new HashMap<>();
		SCHEMA_STUDYPROFILE.put("name", XmlController.ImportAs.STRING);
		SCHEMA_STUDYPROFILE.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_STUDYPROFILE.put("uid", XmlController.ImportAs.STRING);
		SCHEMA_STUDYPROFILE.put("year", XmlController.ImportAs.INTEGER);
		SCHEMA_STUDYPROFILE.put("semester", XmlController.ImportAs.INTEGER);
		SCHEMA_STUDYPROFILE.put("modules", XmlController.ImportAs.NODELIST);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_PERSON;

	static {
		SCHEMA_PERSON = new HashMap<>();
		SCHEMA_PERSON.put("name", XmlController.ImportAs.STRING);
		SCHEMA_PERSON.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_PERSON.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_PERSON.put("uid", XmlController.ImportAs.STRING);
		SCHEMA_PERSON.put("givenNames", XmlController.ImportAs.STRING);
		SCHEMA_PERSON.put("familyName", XmlController.ImportAs.STRING);
		SCHEMA_PERSON.put("salutation", XmlController.ImportAs.STRING);
		SCHEMA_PERSON.put("email", XmlController.ImportAs.STRING);
		SCHEMA_PERSON.put("familyNameLast", XmlController.ImportAs.BOOLEAN);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_BUILDING;

	static {
		SCHEMA_BUILDING = new HashMap<>();
		SCHEMA_BUILDING.put("name", XmlController.ImportAs.STRING);
		SCHEMA_BUILDING.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_BUILDING.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_BUILDING.put("uid", XmlController.ImportAs.STRING);
		SCHEMA_BUILDING.put("code", XmlController.ImportAs.STRING);
		SCHEMA_BUILDING.put("latitude", XmlController.ImportAs.DOUBLE);
		SCHEMA_BUILDING.put("longitude", XmlController.ImportAs.DOUBLE);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_ROOM;

	static {
		SCHEMA_ROOM = new HashMap<>();
		SCHEMA_ROOM.put("name", XmlController.ImportAs.STRING);
		SCHEMA_ROOM.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_ROOM.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_ROOM.put("uid", XmlController.ImportAs.STRING);
		SCHEMA_ROOM.put("building", XmlController.ImportAs.STRING);
		SCHEMA_ROOM.put("roomNumber", XmlController.ImportAs.STRING);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_TIMETABLE_EVENT_TYPE;

	static {
		SCHEMA_TIMETABLE_EVENT_TYPE = new HashMap<>();
		SCHEMA_TIMETABLE_EVENT_TYPE.put("name", XmlController.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT_TYPE.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_TIMETABLE_EVENT_TYPE.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_TIMETABLE_EVENT_TYPE.put("uid", XmlController.ImportAs.STRING);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_MODULE;

	static {
		SCHEMA_MODULE = new HashMap<>();
		SCHEMA_MODULE.put("name", XmlController.ImportAs.STRING);
		SCHEMA_MODULE.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_MODULE.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_MODULE.put("uid", XmlController.ImportAs.STRING);
		SCHEMA_MODULE.put("organiser", XmlController.ImportAs.STRING);
		SCHEMA_MODULE.put("moduleCode", XmlController.ImportAs.STRING);
		SCHEMA_MODULE.put("timetable", XmlController.ImportAs.NODELIST);
		SCHEMA_MODULE.put("assignments", XmlController.ImportAs.NODELIST);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_COURSEWORK;

	static {
		SCHEMA_COURSEWORK = new HashMap<>();
		SCHEMA_COURSEWORK.put("name", XmlController.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_COURSEWORK.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_COURSEWORK.put("uid", XmlController.ImportAs.STRING);

		SCHEMA_COURSEWORK.put("weighting", XmlController.ImportAs.INTEGER);
		SCHEMA_COURSEWORK.put("setBy", XmlController.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("markedBy", XmlController.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("reviewedBy", XmlController.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("marks", XmlController.ImportAs.INTEGER);
		SCHEMA_COURSEWORK.put("startDate", XmlController.ImportAs.NODELIST);
		SCHEMA_COURSEWORK.put("deadline", XmlController.ImportAs.NODELIST);
		SCHEMA_COURSEWORK.put("extensions", XmlController.ImportAs.NODELIST);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_EXAM;

	static {
		SCHEMA_EXAM = new HashMap<>();
		SCHEMA_EXAM.put("name", XmlController.ImportAs.STRING);
		SCHEMA_EXAM.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_EXAM.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_EXAM.put("uid", XmlController.ImportAs.STRING);

		SCHEMA_EXAM.put("resit", XmlController.ImportAs.STRING);
		SCHEMA_EXAM.put("timeslot", XmlController.ImportAs.NODELIST);
		SCHEMA_EXAM.put("weighting", XmlController.ImportAs.INTEGER);
		SCHEMA_EXAM.put("setBy", XmlController.ImportAs.STRING);
		SCHEMA_EXAM.put("markedBy", XmlController.ImportAs.STRING);
		SCHEMA_EXAM.put("reviewedBy", XmlController.ImportAs.STRING);
		SCHEMA_EXAM.put("marks", XmlController.ImportAs.INTEGER);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_TIMETABLE_EVENT;

	static {
		SCHEMA_TIMETABLE_EVENT = new HashMap<>();
		SCHEMA_TIMETABLE_EVENT.put("name", XmlController.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_TIMETABLE_EVENT.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_TIMETABLE_EVENT.put("uid", XmlController.ImportAs.STRING);

		SCHEMA_TIMETABLE_EVENT.put("date", XmlController.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("room", XmlController.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("lecturer", XmlController.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("timetableEventType", XmlController.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("duration", XmlController.ImportAs.INTEGER);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_EVENT;

	static {
		SCHEMA_EVENT = new HashMap<>();
		SCHEMA_EVENT.put("name", XmlController.ImportAs.STRING);
		SCHEMA_EVENT.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_EVENT.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_EVENT.put("uid", XmlController.ImportAs.STRING);

		SCHEMA_EVENT.put("date", XmlController.ImportAs.STRING);
	}

	public static HashMap<String, XmlController.ImportAs> SCHEMA_EXAMEVENT;

	static {
		SCHEMA_EXAMEVENT = new HashMap<>();
		SCHEMA_EXAMEVENT.put("name", XmlController.ImportAs.STRING);
		SCHEMA_EXAMEVENT.put("details", XmlController.ImportAs.MULTILINESTRING);
		SCHEMA_EXAMEVENT.put("version", XmlController.ImportAs.INTEGER);
		SCHEMA_EXAMEVENT.put("uid", XmlController.ImportAs.STRING);

		SCHEMA_EXAMEVENT.put("date", XmlController.ImportAs.STRING);
		SCHEMA_EXAMEVENT.put("room", XmlController.ImportAs.STRING);
		SCHEMA_EXAMEVENT.put("duration", XmlController.ImportAs.INTEGER);
	}

	public static HashMap<String, HashMap<String, XmlController.ImportAs>> schemaList =
			new HashMap<>();

	static {
		schemaList.put("person",SCHEMA_PERSON);
		schemaList.put("examEvent",SCHEMA_EXAMEVENT);
		schemaList.put("event",SCHEMA_EVENT);
		schemaList.put("deadline",SCHEMA_EVENT);
		schemaList.put("timetableEvent",SCHEMA_TIMETABLE_EVENT);
		schemaList.put("exam",SCHEMA_EXAM);
		schemaList.put("coursework",SCHEMA_COURSEWORK);
		schemaList.put("module",SCHEMA_MODULE);
		schemaList.put("timetableEventType",SCHEMA_TIMETABLE_EVENT_TYPE);
		schemaList.put("building",SCHEMA_BUILDING);
		schemaList.put("room",SCHEMA_ROOM);
	}

	private static XmlController xmlTools = new XmlController();

	/**
	 * Constructor for new Study Profile.
	 *
	 * @param v1 version
	 * @param y1 year
	 * @param s1 semester
	 * @param m1 Module list
	 * @param a1 VersionControlEntity list
	 * @param cal1 CALENDAR events list
	 */
	public HubFile(int v1, int y1, int s1, List<Module> m1,
			List<VersionControlEntity> a1, List<Event> cal1) {
		version = v1;
		year = y1;
		semester = s1;
		modules = new ArrayList<>(m1);
		assets = new ArrayList<>(a1);
		calendarList = new ArrayList<>(cal1);
		updateFile = false;
	}

	/**
	 * Constructor for new Study Profile.
	 *
	 * @param v1 version
	 * @param y1 year
	 * @param s1 semester
	 * @param m1 Module list
	 */
	public HubFile(int v1, int y1, int s1, List<Module> m1,
			List<VersionControlEntity> a1, List<Event> cal1, String n1,
			MultilineString d1, String u1) {
		this(v1, y1, s1, m1, a1, cal1);
		semesterName = n1;
		semesterDetails = d1;
		semesterUId = u1;
	}

	/**
	 * Constructor for update.
	 *
	 * @param v2 version
	 * @param e2 ExtensionApplication list
	 * @param u2 VersionControlEntity list
	 */
	public HubFile(int v2, List<ExtensionApplication> e2,
			List<VersionControlEntity> u2) {
		version = v2;
		extensions = new ArrayList<>(e2);
		updates = new ArrayList<>(u2);
		updateFile = true;
	}

	/**
	 * Returns the modules of the file.
	 *
	 * @return The modules of the file
	 */
	public ArrayList<Module> getModules() {
		return modules;
	}

	/**
	 * Returns the extensions of the file.
	 *
	 * @return The extensions of the file
	 */
	public ArrayList<ExtensionApplication> getExtensions() {
		return extensions;
	}

	/**
	 * Returns the calendar list of the file.
	 *
	 * @return The calendar list of the file
	 */
	public ArrayList<Event> getCalendarList() {
		return calendarList;
	}

	/**
	 * Returns the updates of the file.
	 *
	 * @return The updates of the file
	 */
	public ArrayList<VersionControlEntity> getUpdates() {
		return updates;
	}

	/**
	 * Returns the version of the file.
	 *
	 * @return the version of the file
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Returns the semester of the file.
	 *
	 * @return The semester of the file
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * Returns the year of the file.
	 *
	 * @return The year of the file
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Returns the semester name of the file.
	 *
	 * @return The semester name of the file
	 */
	public String getSemesterName() {
		return semesterName;
	}

	/**
	 * Returns the semester UID of the file.
	 *
	 * @return The semester UID of the file
	 */
	public String getSemesterUId() {
		return semesterUId;
	}

	/**
	 * Returns the semester details of the file.
	 *
	 * @return The semester details of the file
	 */
	public MultilineString getSemesterDetails() {
		return semesterDetails;
	}

	/**
	 * Returns whether the file is an update file.
	 *
	 * @return True for an update file, false for any other file
	 */
	public boolean isUpdate() {
		return updateFile;
	}

	@Override
	public String toString() {
		return "HubFile for " + Integer.toString(year) + " semester: "
				+ Integer.toString(semester) + " | Module Count: "
				+ Integer.toString(modules.size());
	}

	/**
	 * An additional toString() method that allows for more verbose output.
	 *
	 * @param verbose True for verbose output, false for standard output
	 * @return A string representation of the object with the requested
	 * 				verbosity level
	 */
	public String toString(boolean verbose) {
		if (verbose) {
			StringBuilder verboseString = new StringBuilder();

			verboseString.append(toString());
			int num = -1;
			int ii = modules.size();
			while (++num < ii) {
				verboseString.append(modules.get(num).toString(true));
			}

			return verboseString.toString();
		} else {
			return toString();
		}
	}

	/**
	 * Returns true if the given node name is recognized and can therefore be
	 * updated. Returns false otherwise.
	 *
	 * @param name The node name to check
	 * @return true if the given node name is recognized and can therefore be
	 * 				updated, false otherwise
	 */
	public static boolean updateableNode(String name) {
		return schemaList.containsKey(name);
	}

	/**
	 * Create a Person object from the specified node list.
	 *
	 * @param nc The node list containing the Person attributes
	 * @return a Person object
	 */
	public static Person createPerson(NodeList nc) {
		HashMap<String, XmlController.NodeReturn> personValues =
				xmlTools.getSchemaValues(nc, HubFile.SCHEMA_PERSON);

		Person person = new Person(personValues.get("salutation").getString(),
				personValues.get("givenNames").getString(),
				personValues.get("familyName").getString(),
				personValues.get("familyNameLast").getBoolean(),
				personValues.get("email").getString());

		DataController.addVceProperties(person, personValues);
		return person;
	}

	/**
	 * Create a Building object from the specified node list.
	 *
	 * @param nc The node list containing the Building attributes
	 * @return a Building object
	 */
	public static Building createBuilding(NodeList nc) {
		HashMap<String, XmlController.NodeReturn> pvalues =
				xmlTools.getSchemaValues(nc, HubFile.SCHEMA_BUILDING);

		Building build = new Building(pvalues.get("code").getString(),
				pvalues.get("latitude").getDouble(),
				pvalues.get("longitude").getDouble());

		DataController.addVceProperties(build, pvalues);
		return build;
	}

	/**
	 * Create a Room object from the specified node list.
	 *
	 * @param nc The node list containing the Room attributes
	 * @return a Room object
	 */
	public static Room createRoom(NodeList nc, HashMap<String, VersionControlEntity> assetList) {
		HashMap<String, XmlController.NodeReturn> pvalues =
				xmlTools.getSchemaValues(nc, HubFile.SCHEMA_ROOM);

		Room room;
		String linkedBuilding = pvalues.get("building").getString();
		if (assetList.containsKey(linkedBuilding)
				&& assetList.get(linkedBuilding) instanceof Building) {
			room = new Room(pvalues.get("roomNumber").getString(),
					(Building) assetList.get(linkedBuilding));
		} else {
			room = new Room(pvalues.get("roomNumber").getString());
		}
		DataController.addVceProperties(room, pvalues);
		return room;
	}

	/**
	 * Create a TimeTableEventType object from the specified node list.
	 *
	 * @param nc The node list containing the TimeTableEventType attributes
	 * @return a TimeTableEventType object
	 */
	public static TimeTableEventType createTimetableEventType(NodeList nc) {
		HashMap<String, XmlController.NodeReturn> pvalues =
				xmlTools.getSchemaValues(nc, HubFile.SCHEMA_TIMETABLE_EVENT_TYPE);

		TimeTableEventType table = new TimeTableEventType();

		DataController.addVceProperties(table, pvalues);
		return table;
	}

	/**
	 * Create a Coursework object from the specified node list and asset list.
	 *
	 * @param nc The node list containing the Coursework attributes
	 * @param assetList The assets belonging to the Coursework
	 * @return a Coursework object
	 * @throws IOException if requested item is not found in the list
	 */
	public static Coursework createCoursework(NodeList nc,
			Map<String, VersionControlEntity> assetList) throws IOException {

		HashMap<String, XmlController.NodeReturn> courseworkValues =
				xmlTools.getSchemaValues(nc, HubFile.SCHEMA_COURSEWORK);

		Person cwSetBy;
		Person cwMarkedBy;
		Person cwReviewedBy;
		Event cwStartDate;
		Deadline cwDeadline;

		// extensions to be added later

		String linkedSetBy = courseworkValues.get("setBy").getString();
		String linkedMarkedBy = courseworkValues.get("markedBy").getString();
		String linkedReviewedBy = courseworkValues.get("reviewedBy").getString();

		cwSetBy = DataController.inList(assetList, linkedSetBy);
		cwMarkedBy = DataController.inList(assetList, linkedMarkedBy);
		cwReviewedBy = DataController.inList(assetList, linkedReviewedBy);

		ArrayList<Extension> cwExtensions = new ArrayList<>();

		if (courseworkValues.containsKey("startDate")
				&& XmlController.matchesSchema(courseworkValues.get("startDate").getNodeList(),
						HubFile.SCHEMA_EVENT)) {
			HashMap<String, XmlController.NodeReturn> eventValues =
					xmlTools.getSchemaValues(courseworkValues.get("startDate").getNodeList(),
							HubFile.SCHEMA_EVENT);
			cwStartDate = new Event(eventValues.get("date").getString());


			DataController.addVceProperties(cwStartDate, eventValues);
			assetList.put(eventValues.get("uid").getString(), cwStartDate);

		} else {
			cwStartDate = null;
		}
		if (courseworkValues.containsKey("deadline")
				&& XmlController.matchesSchema(courseworkValues.get("deadline").getNodeList(),
						HubFile.SCHEMA_EVENT)) {
			HashMap<String, XmlController.NodeReturn> eventValues =
					xmlTools.getSchemaValues(courseworkValues.get("deadline").getNodeList(),
							HubFile.SCHEMA_EVENT);

			cwDeadline = new Deadline(eventValues.get("date").getString());

			DataController.addVceProperties(cwDeadline, eventValues);
			assetList.put(eventValues.get("uid").getString(), cwDeadline);

		} else {
			cwDeadline = null;
		}

		Coursework coursework = new Coursework(courseworkValues.get("weighting").getInt(),
				cwSetBy, cwMarkedBy, cwReviewedBy, courseworkValues.get("marks").getInt(),
				cwStartDate, cwDeadline, cwExtensions);

		DataController.addVceProperties(coursework, courseworkValues);
		return coursework;

	}

	/**
	 * Create an Exam object from the specified node list and asset list.
	 *
	 * @param nc The node list containing the Exam attributes
	 * @param assetList The assets belonging to the Exam
	 * @return an Exam object
	 * @throws IOException if requested item is not found in the list
	 */
	public static Exam createExam(NodeList nc,
			Map<String, VersionControlEntity> assetList) throws IOException {

		HashMap<String, XmlController.NodeReturn> examValues =
				xmlTools.getSchemaValues(nc, HubFile.SCHEMA_EXAM);

		Person exSetBy;
		Person exMarkedBy;
		Person exReviewedBy;
		ExamEvent exTimeSlot;

		String linkedSetBy = examValues.get("setBy").getString();
		String linkedMarkedBy = examValues.get("markedBy").getString();
		String linkedReviewedBy = examValues.get("reviewedBy").getString();

		exSetBy = DataController.inList(assetList, linkedSetBy);
		exMarkedBy = DataController.inList(assetList, linkedMarkedBy);
		exReviewedBy = DataController.inList(assetList, linkedReviewedBy);

		if (examValues.containsKey("timeslot")
				&& XmlController.matchesSchema(examValues.get("timeslot").getNodeList(),
				HubFile.SCHEMA_EXAMEVENT)) {
			HashMap<String, XmlController.NodeReturn> eventValues =
					xmlTools.getSchemaValues(examValues.get("timeslot").getNodeList(),
							HubFile.SCHEMA_EXAMEVENT);
			//Room exRoom;
			String linkedRoom = eventValues.get("room").getString();
			Room exRoom = DataController.inList(assetList, linkedRoom);


			exTimeSlot = new ExamEvent(eventValues.get("date").getString(), exRoom,
					eventValues.get("duration").getInt());


			DataController.addVceProperties(exTimeSlot, eventValues);
			assetList.put(eventValues.get("uid").getString(), exTimeSlot);

		} else {
			exTimeSlot = null;
		}

		Exam exExamResit = null;
		if (examValues.containsKey("resit")) {
			String linkedResit = examValues.get("resit").getString();
			try {
				exExamResit = DataController.inList(assetList, linkedResit);
			} catch (Exception e) { // TODO maybe delete
				// do nothing!
				//exExamResit = null;
			}
		}

		Exam newExam;
		if (exExamResit == null) {
			newExam = new Exam(examValues.get("weighting").getInt(),
					exSetBy, exMarkedBy, exReviewedBy, examValues.get("marks").getInt(),
					exTimeSlot);
		} else {
			newExam = new Exam(examValues.get("weighting").getInt(),
					exSetBy, exMarkedBy, exReviewedBy, examValues.get("marks").getInt(),
					exTimeSlot, exExamResit);
		}

		DataController.addVceProperties(newExam, examValues);

		return newExam;
	}

	/**
	 * Create a TimetableEvent object from the specified node list and asset list.
	 *
	 * @param nc The node list containing the TimetableEvent attributes
	 * @param assetList The assets belonging to the TimetableEvent
	 * @return a TimetableEvent object
	 * @throws IOException if requested item is not found in the list
	 */
	public static TimetableEvent createTimetableEvent(NodeList nc,
			Map<String, VersionControlEntity> assetList) throws IOException {

		TimetableEvent newTte;

		HashMap<String, XmlController.NodeReturn> tteValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_TIMETABLE_EVENT);

		String linkedRoom = tteValues.get("room").getString();
		String linkedLecturer = tteValues.get("lecturer").getString();
		String linkedTtet = tteValues.get("timetableEventType").getString();

		Room troom = DataController.inList(assetList, linkedRoom);
		Person tlecturer = DataController.inList(assetList, linkedLecturer);
		TimeTableEventType tttet = DataController.inList(assetList, linkedTtet);

		newTte = new TimetableEvent(tteValues.get("date").getString(), troom, tlecturer,
				tttet, tteValues.get("duration").getInt());
		DataController.addVceProperties(newTte, tteValues);

		return newTte;
	}

}
