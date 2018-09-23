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

package edu.wright.cs.raiderplanner.view;

import edu.wright.cs.raiderplanner.model.Assignment;
import edu.wright.cs.raiderplanner.model.StudyPlanner;
import edu.wright.cs.raiderplanner.model.Task;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

/**
 * Created by bendickson on 5/15/17.
 */
public class GanttishDiagram {

	static int width = 1920;
	static int height = 1280;

	static int imageType = BufferedImage.TYPE_INT_ARGB;

	static int badgeSize = 128;
	static int badgeRingSize = 112;
	static int getBadgeRingThickness = 8;
	static int fontSize = 24;

	static int GANTT_TITLE_SPACE = 80;
	static int GANTT_TITLE_FONT_SIZE = 64;
	static int GANTT_COLUMN_WIDTH = 400;
	static int GANTT_COLUMN_PADDING = 80;
	static int GANTT_COLUMN_SPACING = 16;
	static int GANTT_ENTRY_FONT_SIZE = 18;
	static Paint GANTT_ENTRY_FONT_COLOR = Color.black;
	static int GANTT_ENTRY_HEIGHT = 64;
	static Paint GANTT_ENTRY_DEFAULT_COLOR = BadgeColors.GREY.getPaint();
	static HashMap<String, Paint> GANTT_ENTRY_COLOR = new HashMap<>();

	static {
		GANTT_ENTRY_COLOR.put("Reading", BadgeColors.PINK.getPaint());
		GANTT_ENTRY_COLOR.put("Exercises", BadgeColors.ORANGE.getPaint());
		GANTT_ENTRY_COLOR.put("Revision", BadgeColors.YELLOW.getPaint());
		GANTT_ENTRY_COLOR.put("Listening", BadgeColors.GREEN.getPaint());
		GANTT_ENTRY_COLOR.put("Coursework", BadgeColors.BLUE.getPaint());
		GANTT_ENTRY_COLOR.put("Meeting", BadgeColors.PURPLE.getPaint());
	}

	static Paint GANTT_ENTRY_DEFAULT_FCOLOR = BadgeColors.T_GREY.getPaint();
	static HashMap<String, Paint> GANTT_ENTRY_FCOLOR = new HashMap<>();

	static {
		GANTT_ENTRY_FCOLOR.put("Reading", BadgeColors.T_PINK.getPaint());
		GANTT_ENTRY_FCOLOR.put("Exercises", BadgeColors.T_ORANGE.getPaint());
		GANTT_ENTRY_FCOLOR.put("Revision", BadgeColors.T_YELLOW.getPaint());
		GANTT_ENTRY_FCOLOR.put("Listening", BadgeColors.T_GREEN.getPaint());
		GANTT_ENTRY_FCOLOR.put("Coursework", BadgeColors.T_BLUE.getPaint());
		GANTT_ENTRY_FCOLOR.put("Meeting", BadgeColors.T_PURPLE.getPaint());
	}


	/**
	 * creates the strokes and sizes.
	 * @author Eric Sweet
	 *
	 */
	enum Stroke {
		DASHED(true, 1),
		NONE(false, 0),
		SOLID(false, 1);

		double widthMult = 1.0;
		boolean dashed = false;

		/**
		 * gets the stroke and thickness.
		 * @param thickness thickness of the stroke
		 * @return the thickness times width
		 */
		BasicStroke getStroke(int thickness) {
			if (widthMult == 0) {
				return new BasicStroke(0);
			} else if (dashed) {
				float[] pat = {10.0f};
				return new BasicStroke((float) widthMult * 1.0f,
						BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER,
						10.0f, pat, 0.0f);
			} else {
				return new BasicStroke((float) widthMult * thickness);
			}
		}

		/**
		 * modifies the stroke and width.
		 * @param dashed boolean if dashed
		 * @param widthMult width size
		 */
		Stroke(boolean dashed, double widthMult) {
			this.dashed = dashed;
			this.widthMult = widthMult;
		}
	}

	/**
	 * Creates the colors and fill diagram colors.
	 * @author Eric Sweet
	 *
	 */
	enum BadgeColors {
		FINISHED(0, 255, 0),
		started(255, 255, 0),
		CANSTART(140, 26, 26),
		CANNOTSTART(64, 64, 64),

		FINISHED_FILL(255, 255, 255),
		started_FILL(255, 255, 255),
		CANSTART_FILL(255, 255, 255),
		CANNOTSTART_FILL(128, 128, 128),
		GREY(192, 192, 192),
		PINK(255, 192, 192),
		ORANGE(255, 128, 0),
		BLUE(0, 128, 255),
		GREEN(0, 255, 64),
		PURPLE(64, 0, 255),
		YELLOW(255, 64, 0),
		T_GREY(192, 192, 192, 128),
		T_PINK(255, 192, 192, 128),
		T_ORANGE(255, 128, 0, 128),
		T_BLUE(0, 128, 255, 128),
		T_GREEN(0, 255, 64, 128),
		T_PURPLE(64, 0, 255, 128),
		T_YELLOW(255, 64, 0, 128);

		private int red;
		private int green;
		private int blue;
		private int aa = 255;

		/**
		 * gets the color of the diagram.
		 * @return paint color
		 */
		private Paint getPaint() {
			return new Color(red, green, blue, aa);
		}

		/**
		 * Returns a color based on a progress out of 100.
		 *
		 * @param progress The progress of the module
		 *
		 * @return a color based on progress
		 */
		private Color getPaint(int progress) {
			if (progress > 100) {
				return new Color(0, 255, 0);
			}
			if (progress < 0) {
				return new Color(255, 0, 137);
			}

			red = 255 - (int)(2.5 * progress);
			green = (int)(Math.log(progress + 1) * 29);
			blue = 0;

			return new Color(red, green, blue, 255);
		}

		/**
		 * Creation of badge color.
		 * @param cr red spectrum
		 * @param cg green spectrum
		 * @param cb blue spectrum
		 */
		BadgeColors(int cr, int cg, int cb) {
			red = cr > 255 ? 255 : (cr < 0 ? 0 : cr);
			green = cg > 255 ? 255 : (cg < 0 ? 0 : cg);
			blue = cb > 255 ? 255 : (cb < 0 ? 0 : cb);
		}

		/**
		 * Creation of badge color with aa included.
		 * @param cr red color
		 * @param cg green color
		 * @param cb blue color
		 * @param ca aa color
		 */
		BadgeColors(int cr, int cg, int cb, int ca) {
			this(cr, cg, cb);
			aa = ca > 255 ? 255 : (ca < 0 ? 0 : ca);
		}
	}

	/**
	 * Creates a GanttishDiagram from a given Assignment.
	 *
	 * @param fromStudyProfile StudyProfile
	 * @param fromAssignment   Assignment for which to generate the GanttishDiagram.
	 * @return Generated diagram
	 */
	public static BufferedImage createGanttishDiagram(StudyPlanner fromStudyProfile,
			Assignment fromAssignment) {
		return createGanttishDiagram(fromStudyProfile, fromAssignment, "");
	}

	/**
	 * Creates a GanttishDiagram from a given Assignment and saves it to a file.
	 *
	 * @param fromStudyProfile StudyProfile
	 * @param fromAssignment   Assignment for which to generate the GanttishDiagram.
	 * @param filePath		 file path
	 * @return Generated diagram
	 */
	public static BufferedImage createGanttishDiagram(StudyPlanner fromStudyProfile,
			Assignment fromAssignment, String filePath) {
		return createGanttishDiagram(fromStudyProfile, fromAssignment, filePath, false);
	}

	/**
	 * Creates a GanttishDiagram from a given Assignment,
	 *  with the option to set the background transparent.
	 *
	 * @param fromStudyProfile	  StudyProfile
	 * @param fromAssignment		Assignment for which to generate the GanttishDiagram.
	 * @param filePath			  file path
	 * @param transparentBackground whether the background should be white or transparent
	 * @return Generated diagram
	 */
	public static BufferedImage createGanttishDiagram(StudyPlanner fromStudyProfile,
			Assignment fromAssignment, String filePath, boolean transparentBackground) {

		HashMap<String, ArrayList<Task>> catTasks = new HashMap<>();

		String completed = "completed tasks";
		String started = "Tasks started";
		String possible = "possible to start";
		String impossible = "Not possible to start";

		/**
		BadgeColors[][] badges = {{BadgeColors.FINISHED, BadgeColors.FINISHED_FILL},
				{BadgeColors.started, BadgeColors.started_FILL},
				{BadgeColors.CANSTART, BadgeColors.CANSTART_FILL},
				{BadgeColors.CANNOTSTART, BadgeColors.CANNOTSTART_FILL}};
		 */

		catTasks.put(completed, new ArrayList<>());
		catTasks.put(started, new ArrayList<>());
		catTasks.put(possible, new ArrayList<>());
		catTasks.put(impossible, new ArrayList<>());

		ArrayList<Task> assignmentTasks = fromAssignment.getTasks();

		Iterator<Task> iterator = assignmentTasks.iterator();
		int longest = 0;
		while (iterator.hasNext()) {
			Task taskT = iterator.next();
			String cat;
			if (taskT.isCheckedComplete()) {
				cat = completed;
			} else if (taskT.requirementsComplete() > 0) {
				cat = started;
			} else if (taskT.dependenciesComplete()) {
				cat = possible;
			} else {
				cat = impossible;
			}
			catTasks.get(cat).add(taskT);
			if (catTasks.get(cat).size() > longest) {
				longest = catTasks.get(cat).size();
			}
		}

		String[] categoryList = {completed, started, possible, impossible};
		int jj = categoryList.length;

		int width = GANTT_COLUMN_PADDING + jj * (GANTT_COLUMN_PADDING + GANTT_COLUMN_WIDTH);
		int height = 2 * GANTT_COLUMN_PADDING + GANTT_TITLE_SPACE + (1 + longest)
				* (GANTT_COLUMN_SPACING + GANTT_ENTRY_HEIGHT);

		BufferedImage bufferedImageR = new BufferedImage(width, height, imageType);
		Graphics2D g2d = bufferedImageR.createGraphics();

		if (!transparentBackground) {

			g2d.setPaint(Color.white);
			g2d.fillRect(0, 0, width, height);
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
		drawMessage(g2d, GANTT_TITLE_FONT_SIZE, fromAssignment.getName(), GANTT_COLUMN_PADDING,
				GANTT_COLUMN_PADDING, width - (2 * GANTT_COLUMN_PADDING), GANTT_TITLE_SPACE);

		int ii = -1;
		while (++ii < jj) {
			int ox = GANTT_COLUMN_PADDING + ii * (GANTT_COLUMN_PADDING + GANTT_COLUMN_WIDTH);
			int oy = GANTT_COLUMN_PADDING + GANTT_TITLE_SPACE;

			String name = categoryList[ii];
			int inc = 0;
			int kk = catTasks.get(name).size() + 1;


			g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
			drawMessage(g2d, GANTT_ENTRY_FONT_SIZE, name, ox, oy, GANTT_COLUMN_WIDTH,
					GANTT_ENTRY_HEIGHT);

			while (++inc < kk) {
				Task dt = catTasks.get(name).get(inc - 1);
				String dtt = dt.getType().getName();
				Paint col;
				Paint fcol;
				if (GANTT_ENTRY_COLOR.containsKey(dtt)) {
					fcol = (GANTT_ENTRY_FCOLOR.get(dtt));
					col = (GANTT_ENTRY_COLOR.get(dtt));
				} else {
					fcol = (GANTT_ENTRY_DEFAULT_FCOLOR);
					col = (GANTT_ENTRY_DEFAULT_COLOR);
				}

				int oyk = oy + inc * (GANTT_COLUMN_SPACING + GANTT_ENTRY_HEIGHT);

				g2d.setPaint(fcol);
				g2d.fillRoundRect(ox, oyk, GANTT_COLUMN_WIDTH, GANTT_ENTRY_HEIGHT, 15, 15);

				g2d.setPaint(col);
				g2d.drawRoundRect(ox, oyk, GANTT_COLUMN_WIDTH, GANTT_ENTRY_HEIGHT, 15, 15);

				g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
				drawMessage(g2d, GANTT_ENTRY_FONT_SIZE, dt.getName(), ox, oyk, GANTT_COLUMN_WIDTH
						- GANTT_ENTRY_HEIGHT, GANTT_ENTRY_HEIGHT);
				int percentage;
				if (dt.requirementCount() <= 0) {
					percentage = dt.isCheckedComplete() ? 100 : 0;
				} else {
					percentage = (100 * dt.requirementsComplete()) / dt.requirementCount();
				}
				BufferedImage badge = getBadge(percentage, dt.dependenciesComplete(), 0.5);

				g2d.drawImage(badge, ox + GANTT_COLUMN_WIDTH
						- GANTT_ENTRY_HEIGHT, oyk, null);
			}
		}


		if (!filePath.equals("")) {
			try {
				File outputfile = new File(filePath);
				ImageIO.write(bufferedImageR, "png", outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return bufferedImageR;
	}

	/**
	 * Generates a Badge with the given progress.
	 *
	 * @param progress integer representation of the progress.
	 * @param canStart true - default, false - grayed out.
	 * @return generated Badge
	 */
	public static BufferedImage getBadge(int progress, boolean canStart) {
		return getBadge(progress, canStart, 1.0f);
	}

	/**
	 * Generates a Badge with the given progress.
	 *
	 * @param progress   integer representation of the progress.
	 * @param canStart   true - default, false - greyed out.
	 * @param multiplier size multiplier.
	 * @return generated Badge
	 */
	public static BufferedImage getBadge(int progress, boolean canStart, double multiplier) {
		int canvasSize = (int) (badgeSize * multiplier + .5);
		int ringSize = (int) (badgeRingSize * multiplier + .5);
		int badgeRingThickness = (int) (getBadgeRingThickness * multiplier + .5);

		BufferedImage imageBufferR = new BufferedImage(canvasSize, canvasSize, imageType);
		Graphics2D g2d = imageBufferR.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Paint ringColor;
		Paint fillColor;

		if (!canStart) {
			g2d.setStroke(Stroke.DASHED.getStroke(badgeRingThickness));
			ringColor = new Color(140 ,26, 26);
			fillColor = Color.white;
		} else {
			g2d.setStroke(Stroke.SOLID.getStroke(badgeRingThickness));
			ringColor = BadgeColors.FINISHED.getPaint(progress);
			fillColor = BadgeColors.FINISHED_FILL.getPaint();
		}

		int ovalOffset = (canvasSize - ringSize) / 2;
		g2d.setPaint(fillColor);
		g2d.fillOval(ovalOffset, ovalOffset, ringSize, ringSize);

		int badgeFontSize = (int) (fontSize * multiplier + .5);
		Font font = new Font("Helvetica", Font.PLAIN, badgeFontSize);
		FontMetrics metrics = g2d.getFontMetrics(font);

		String msg = progress + "%";

		int msgX = ovalOffset + (ringSize - metrics.stringWidth(msg)) / 2;
		int msgY = ovalOffset + (ringSize - metrics.getHeight()) / 2 + metrics.getAscent();

		int arc = 100;
		if (arc == 100) {
			g2d.setPaint(ringColor);
			g2d.drawOval(ovalOffset, ovalOffset, ringSize, ringSize);
		} else {
			g2d.setPaint(ringColor);
			int startAngle = 90;
			int endAngle = -((int) ((arc / 100f) * 360));
			g2d.drawArc(ovalOffset, ovalOffset, ringSize, ringSize, startAngle, endAngle);
		}


		g2d.setFont(font);
		g2d.drawString(msg, msgX, msgY);


/*		try {
			File outputfile = new File("badge_"+progress+".png");
			ImageIO.write(r, "png", outputfile);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}*/

		return imageBufferR;
	}

	/**
	 * Generates a rectangle with the given message in the middle.
	 *
	 * @param g2d 2d graphics
	 * @param fontSize font size
	 * @param msg message
	 * @param cx x-axis
	 * @param cy y-axis
	 * @param width width
	 * @param height height
	 */
	private static void drawMessage(Graphics2D g2d, int fontSize, String msg,
			int cx, int cy, int width, int height) {

		Font font = new Font("Helvetica", Font.PLAIN, fontSize);
		FontMetrics metrics = g2d.getFontMetrics(font);

		int msgX = cx + (width - metrics.stringWidth(msg)) / 2;
		int msgY = cy + (height - metrics.getHeight()) / 2 + metrics.getAscent();

		g2d.setFont(font);
		g2d.drawString(msg, msgX, msgY);
	}
}
