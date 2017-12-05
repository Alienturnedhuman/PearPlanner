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

package edu.wright.cs.raiderplanner.model;

import edu.wright.cs.raiderplanner.controller.DataController;
import edu.wright.cs.raiderplanner.controller.XMLcontroller;

import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * PearPlanner/RaiderPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class HubFile implements Serializable {
	// private data
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
	private String semesterUID;
	private MultilineString semesterDetails;

	// public methods

	// getters
	public ArrayList<Module> getModules() {
		return modules;
	}

	public ArrayList<ExtensionApplication> getExtensions() {
		return extensions;
	}

	public ArrayList<Event> getCalendarList() {
		return calendarList;
	}

	public ArrayList<VersionControlEntity> getUpdates() {
		return updates;
	}

	public int getVersion() {
		return version;
	}

	public int getSemester() {
		return semester;
	}

	public int getYear() {
		return year;
	}

	public String getSemesterName() {
		return semesterName;
	}

	public String getSemesterUID() {
		return semesterUID;
	}

	public MultilineString getSemesterDetails() {
		return semesterDetails;
	}

	public boolean isUpdate() {
		return updateFile;
	}

	@Override
	public String toString() {
		return "HubFile for " + Integer.toString(year) + " semester: " + Integer.toString(semester) + " | Module Count: " +
				Integer.toString(modules.size());
	}

	public String toString(boolean verbose) {
		if (verbose) {
			StringBuilder r = new StringBuilder();

			r.append(toString());
			int i = -1;
			int ii = modules.size();
			while (++i < ii) {
				r.append(modules.get(i).toString(true));
			}

			return r.toString();
		} else {
			return toString();
		}
	}
	// constructors

	/**
	 * Constructor for new Study Profile
	 *
	 * @param v   version
	 * @param y   year
	 * @param s   semester
	 * @param m   Module list
	 * @param a   VersionControlEntity list
	 * @param cal CALENDAR events list
	 */
	public HubFile(int v, int y, int s, ArrayList<Module> m, ArrayList<VersionControlEntity> a, ArrayList<Event> cal) {
		version = v;
		year = y;
		semester = s;
		modules = new ArrayList<Module>(m);
		assets = new ArrayList<VersionControlEntity>(a);
		calendarList = new ArrayList<Event>(cal);
		updateFile = false;
	}

	public HubFile(int v, int y, int s, ArrayList<Module> m, ArrayList<VersionControlEntity> a, ArrayList<Event> cal,
				   String n, MultilineString d, String u) {
		this(v, y, s, m, a, cal);
		semesterName = n;
		semesterDetails = d;
		semesterUID = u;
	}

	/**
	 * Constructor for update
	 *
	 * @param v version
	 * @param e ExtensionApplication list
	 * @param u VersionControlEntity list
	 */
	public HubFile(int v, ArrayList<ExtensionApplication> e, ArrayList<VersionControlEntity> u) {
		version = v;
		extensions = new ArrayList<ExtensionApplication>(e);
		updates = new ArrayList<VersionControlEntity>(u);
		updateFile = true;
	}

	public static boolean updateableNode(String name) {
		return schemaList.containsKey(name);
	}

	// schemas
	// note, for the time being these are hard coded into the code
	// long term, these would be imported from a settings file

	// special SCHEMA for update file
	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_VCE;

	static {
		SCHEMA_VCE = new HashMap<>();
		SCHEMA_VCE.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_VCE.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_VCE.put("uid", XMLcontroller.ImportAs.STRING);
		SCHEMA_VCE.put("version", XMLcontroller.ImportAs.INTEGER);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_ROOT;

	static {
		SCHEMA_ROOT = new HashMap<>();
		SCHEMA_ROOT.put("hubfile", XMLcontroller.ImportAs.NODELIST);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_NEW_STUDYPROFILE;

	static {
		SCHEMA_NEW_STUDYPROFILE = new HashMap<>();
		SCHEMA_NEW_STUDYPROFILE.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_NEW_STUDYPROFILE.put("assets", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_NEW_STUDYPROFILE.put("studyProfile", XMLcontroller.ImportAs.NODELIST);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_UPDATE_FILE;

	static {
		SCHEMA_UPDATE_FILE = new HashMap<>();
		SCHEMA_UPDATE_FILE.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_UPDATE_FILE.put("extensions", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_UPDATE_FILE.put("updates", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_UPDATE_FILE.put("new", XMLcontroller.ImportAs.NODELIST);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_ASSETS;

	static {
		SCHEMA_ASSETS = new HashMap<>();
		SCHEMA_ASSETS.put("persons", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_ASSETS.put("buildings", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_ASSETS.put("rooms", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_ASSETS.put("timetableEventTypes", XMLcontroller.ImportAs.NODELIST);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_STUDYPROFILE;

	static {
		SCHEMA_STUDYPROFILE = new HashMap<>();
		SCHEMA_STUDYPROFILE.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_STUDYPROFILE.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_STUDYPROFILE.put("uid", XMLcontroller.ImportAs.STRING);
		SCHEMA_STUDYPROFILE.put("year", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_STUDYPROFILE.put("semester", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_STUDYPROFILE.put("modules", XMLcontroller.ImportAs.NODELIST);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_PERSON;

	static {
		SCHEMA_PERSON = new HashMap<>();
		SCHEMA_PERSON.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_PERSON.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_PERSON.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_PERSON.put("uid", XMLcontroller.ImportAs.STRING);
		SCHEMA_PERSON.put("givenNames", XMLcontroller.ImportAs.STRING);
		SCHEMA_PERSON.put("familyName", XMLcontroller.ImportAs.STRING);
		SCHEMA_PERSON.put("salutation", XMLcontroller.ImportAs.STRING);
		SCHEMA_PERSON.put("email", XMLcontroller.ImportAs.STRING);
		SCHEMA_PERSON.put("familyNameLast", XMLcontroller.ImportAs.BOOLEAN);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_BUILDING;

	static {
		SCHEMA_BUILDING = new HashMap<>();
		SCHEMA_BUILDING.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_BUILDING.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_BUILDING.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_BUILDING.put("uid", XMLcontroller.ImportAs.STRING);
		SCHEMA_BUILDING.put("code", XMLcontroller.ImportAs.STRING);
		SCHEMA_BUILDING.put("latitude", XMLcontroller.ImportAs.DOUBLE);
		SCHEMA_BUILDING.put("longitude", XMLcontroller.ImportAs.DOUBLE);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_ROOM;

	static {
		SCHEMA_ROOM = new HashMap<>();
		SCHEMA_ROOM.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_ROOM.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_ROOM.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_ROOM.put("uid", XMLcontroller.ImportAs.STRING);
		SCHEMA_ROOM.put("building", XMLcontroller.ImportAs.STRING);
		SCHEMA_ROOM.put("roomNumber", XMLcontroller.ImportAs.STRING);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_TIMETABLE_EVENT_TYPE;

	static {
		SCHEMA_TIMETABLE_EVENT_TYPE = new HashMap<>();
		SCHEMA_TIMETABLE_EVENT_TYPE.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT_TYPE.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_TIMETABLE_EVENT_TYPE.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_TIMETABLE_EVENT_TYPE.put("uid", XMLcontroller.ImportAs.STRING);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_MODULE;

	static {
		SCHEMA_MODULE = new HashMap<>();
		SCHEMA_MODULE.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_MODULE.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_MODULE.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_MODULE.put("uid", XMLcontroller.ImportAs.STRING);
		SCHEMA_MODULE.put("organiser", XMLcontroller.ImportAs.STRING);
		SCHEMA_MODULE.put("moduleCode", XMLcontroller.ImportAs.STRING);
		SCHEMA_MODULE.put("timetable", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_MODULE.put("assignments", XMLcontroller.ImportAs.NODELIST);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_COURSEWORK;

	static {
		SCHEMA_COURSEWORK = new HashMap<>();
		SCHEMA_COURSEWORK.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_COURSEWORK.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_COURSEWORK.put("uid", XMLcontroller.ImportAs.STRING);

		SCHEMA_COURSEWORK.put("weighting", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_COURSEWORK.put("setBy", XMLcontroller.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("markedBy", XMLcontroller.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("reviewedBy", XMLcontroller.ImportAs.STRING);
		SCHEMA_COURSEWORK.put("marks", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_COURSEWORK.put("startDate", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_COURSEWORK.put("deadline", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_COURSEWORK.put("extensions", XMLcontroller.ImportAs.NODELIST);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_EXAM;

	static {
		SCHEMA_EXAM = new HashMap<>();
		SCHEMA_EXAM.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAM.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_EXAM.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_EXAM.put("uid", XMLcontroller.ImportAs.STRING);

		SCHEMA_EXAM.put("resit", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAM.put("timeslot", XMLcontroller.ImportAs.NODELIST);
		SCHEMA_EXAM.put("weighting", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_EXAM.put("setBy", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAM.put("markedBy", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAM.put("reviewedBy", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAM.put("marks", XMLcontroller.ImportAs.INTEGER);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_TIMETABLE_EVENT;

	static {
		SCHEMA_TIMETABLE_EVENT = new HashMap<>();
		SCHEMA_TIMETABLE_EVENT.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_TIMETABLE_EVENT.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_TIMETABLE_EVENT.put("uid", XMLcontroller.ImportAs.STRING);

		SCHEMA_TIMETABLE_EVENT.put("date", XMLcontroller.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("room", XMLcontroller.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("lecturer", XMLcontroller.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("timetableEventType", XMLcontroller.ImportAs.STRING);
		SCHEMA_TIMETABLE_EVENT.put("duration", XMLcontroller.ImportAs.INTEGER);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_EVENT;

	static {
		SCHEMA_EVENT = new HashMap<>();
		SCHEMA_EVENT.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_EVENT.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_EVENT.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_EVENT.put("uid", XMLcontroller.ImportAs.STRING);

		SCHEMA_EVENT.put("date", XMLcontroller.ImportAs.STRING);
	}

	public static HashMap<String, XMLcontroller.ImportAs> SCHEMA_EXAMEVENT;

	static {
		SCHEMA_EXAMEVENT = new HashMap<>();
		SCHEMA_EXAMEVENT.put("name", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAMEVENT.put("details", XMLcontroller.ImportAs.MULTILINESTRING);
		SCHEMA_EXAMEVENT.put("version", XMLcontroller.ImportAs.INTEGER);
		SCHEMA_EXAMEVENT.put("uid", XMLcontroller.ImportAs.STRING);

		SCHEMA_EXAMEVENT.put("date", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAMEVENT.put("room", XMLcontroller.ImportAs.STRING);
		SCHEMA_EXAMEVENT.put("duration", XMLcontroller.ImportAs.INTEGER);
	}

	public static HashMap<String, HashMap<String, XMLcontroller.ImportAs>> schemaList = new HashMap<>();

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


	private static XMLcontroller xmlTools = new XMLcontroller();

	public static Person createPerson(NodeList nc) {
		HashMap<String, XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_PERSON);

		Person r = new Person(pValues.get("salutation").getString(), pValues.get("givenNames").getString(),
				pValues.get("familyName").getString(), pValues.get("familyNameLast").getBoolean(),
				pValues.get("email").getString());

		DataController.addVceProperties(r, pValues);
		return r;
	}

	public static Building createBuilding(NodeList nc) {
		HashMap<String, XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_BUILDING);

		Building r = new Building(pValues.get("code").getString(), pValues.get("latitude").getDouble(),
				pValues.get("longitude").getDouble());

		DataController.addVceProperties(r, pValues);
		return r;
	}

	public static Room createRoom(NodeList nc, HashMap<String, VersionControlEntity> assetList) {
		HashMap<String, XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_ROOM);

		Room r;
		String linkedBuilding = pValues.get("building").getString();
		if (assetList.containsKey(linkedBuilding) &&
				assetList.get(linkedBuilding) instanceof Building) {
			r = new Room(pValues.get("roomNumber").getString(),
					(Building) assetList.get(linkedBuilding));
		} else {
			r = new Room(pValues.get("roomNumber").getString());
		}
		DataController.addVceProperties(r, pValues);
		return r;
	}

	public static TimeTableEventType createTimetableEventType(NodeList nc) {
		HashMap<String, XMLcontroller.NodeReturn> pValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_TIMETABLE_EVENT_TYPE);

		TimeTableEventType r = new TimeTableEventType();

		DataController.addVceProperties(r, pValues);
		return r;
	}

	/**
	 * @throws IOException if requested item is not found in the list
	 */
	public static Coursework createCoursework(NodeList nc,
			HashMap<String, VersionControlEntity> assetList) throws IOException {
		Coursework r;
		HashMap<String, XMLcontroller.NodeReturn> courseworkValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_COURSEWORK);


		Person cwSetBy, cwMarkedBy, cwReviewedBy;
		Event cwStartDate;
		Deadline cwDeadline;

		// extensions to be added later
		ArrayList<Extension> cwExtensions = new ArrayList<>();

		String linkedSetBy = courseworkValues.get("setBy").getString();
		String linkedMarkedBy = courseworkValues.get("markedBy").getString();
		String linkedReviewedBy = courseworkValues.get("reviewedBy").getString();


		cwSetBy = DataController.inList(assetList, linkedSetBy);
		cwMarkedBy = DataController.inList(assetList, linkedMarkedBy);
		cwReviewedBy = DataController.inList(assetList, linkedReviewedBy);


		if (courseworkValues.containsKey("startDate")
				&& XMLcontroller.matchesSchema(courseworkValues.get("startDate").getNodeList(),
						HubFile.SCHEMA_EVENT)) {
			HashMap<String, XMLcontroller.NodeReturn> eventValues =
					xmlTools.getSchemaValues(courseworkValues.get("startDate").getNodeList(),
							HubFile.SCHEMA_EVENT);
			cwStartDate = new Event(eventValues.get("date").getString());


			DataController.addVceProperties(cwStartDate, eventValues);
			assetList.put(eventValues.get("uid").getString(), cwStartDate);

		} else {
			cwStartDate = null;
		}
		if (courseworkValues.containsKey("deadline")
				&& XMLcontroller.matchesSchema(courseworkValues.get("deadline").getNodeList(),
						HubFile.SCHEMA_EVENT)) {
			HashMap<String, XMLcontroller.NodeReturn> eventValues =
					xmlTools.getSchemaValues(courseworkValues.get("deadline").getNodeList(),
							HubFile.SCHEMA_EVENT);

			cwDeadline = new Deadline(eventValues.get("date").getString());


			DataController.addVceProperties(cwDeadline, eventValues);
			assetList.put(eventValues.get("uid").getString(), cwDeadline);

		} else {
			cwDeadline = null;
		}


		r = new Coursework(courseworkValues.get("weighting").getInt(),
				cwSetBy, cwMarkedBy, cwReviewedBy, courseworkValues.get("marks").getInt(),
				cwStartDate, cwDeadline, cwExtensions);

		DataController.addVceProperties(r, courseworkValues);
		return r;
	}

	/**
	 * @throws IOException if requested item is not found in the list
	 */
	public static Exam createExam(NodeList nc, HashMap<String, VersionControlEntity> assetList)
			throws IOException {

		HashMap<String, XMLcontroller.NodeReturn> examValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_EXAM);


		Person exSetBy, exMarkedBy, exReviewedBy;
		ExamEvent exTimeSlot;

		String linkedSetBy = examValues.get("setBy").getString();
		String linkedMarkedBy = examValues.get("markedBy").getString();
		String linkedReviewedBy = examValues.get("reviewedBy").getString();

		exSetBy = DataController.inList(assetList, linkedSetBy);
		exMarkedBy = DataController.inList(assetList, linkedMarkedBy);
		exReviewedBy = DataController.inList(assetList, linkedReviewedBy);

		if (examValues.containsKey("timeslot")
				&& XMLcontroller.matchesSchema(examValues.get("timeslot").getNodeList(),
				HubFile.SCHEMA_EXAMEVENT)) {
			HashMap<String, XMLcontroller.NodeReturn> eventValues =
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
	 * @throws IOException if requested item is not found in the list
	 */
	public static TimetableEvent createTimetableEvent(NodeList nc,
			HashMap<String, VersionControlEntity> assetList) throws IOException {

		TimetableEvent newTTE;


		HashMap<String, XMLcontroller.NodeReturn> tteValues = xmlTools.getSchemaValues(nc,
				HubFile.SCHEMA_TIMETABLE_EVENT);


		String linkedRoom = tteValues.get("room").getString();
		String linkedLecturer = tteValues.get("lecturer").getString();
		String linkedTTET = tteValues.get("timetableEventType").getString();

		Room tRoom = DataController.inList(assetList, linkedRoom);
		Person tLecturer = DataController.inList(assetList, linkedLecturer);
		TimeTableEventType tTTET = DataController.inList(assetList, linkedTTET);


		newTTE = new TimetableEvent(tteValues.get("date").getString(), tRoom, tLecturer
				, tTTET, tteValues.get("duration").getInt());
		DataController.addVceProperties(newTTE, tteValues);


		return newTTE;
	}


}
