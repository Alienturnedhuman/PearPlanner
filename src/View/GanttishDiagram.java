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

package View;

import Model.Assignment;
import Model.StudyPlanner;
import Model.Task;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by bendickson on 5/15/17.
 */
public class GanttishDiagram
{

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
    static Paint GANTT_ENTRY_DEFAULT_COLOR = badgeColors.GREY.getPaint();
    static HashMap<String, Paint> GANTT_ENTRY_COLOR = new HashMap<>();

    static
    {
        GANTT_ENTRY_COLOR.put("Reading", badgeColors.PINK.getPaint());
        GANTT_ENTRY_COLOR.put("Exercises", badgeColors.ORANGE.getPaint());
        GANTT_ENTRY_COLOR.put("Revision", badgeColors.YELLOW.getPaint());
        GANTT_ENTRY_COLOR.put("Listening", badgeColors.GREEN.getPaint());
        GANTT_ENTRY_COLOR.put("Coursework", badgeColors.BLUE.getPaint());
        GANTT_ENTRY_COLOR.put("Meeting", badgeColors.PURPLE.getPaint());
    }

    static Paint GANTT_ENTRY_DEFAULT_FCOLOR = badgeColors.T_GREY.getPaint();
    static HashMap<String, Paint> GANTT_ENTRY_FCOLOR = new HashMap<>();

    static
    {
        GANTT_ENTRY_FCOLOR.put("Reading", badgeColors.T_PINK.getPaint());
        GANTT_ENTRY_FCOLOR.put("Exercises", badgeColors.T_ORANGE.getPaint());
        GANTT_ENTRY_FCOLOR.put("Revision", badgeColors.T_YELLOW.getPaint());
        GANTT_ENTRY_FCOLOR.put("Listening", badgeColors.T_GREEN.getPaint());
        GANTT_ENTRY_FCOLOR.put("Coursework", badgeColors.T_BLUE.getPaint());
        GANTT_ENTRY_FCOLOR.put("Meeting", badgeColors.T_PURPLE.getPaint());
    }


    enum stroke
    {
        DASHED(true, 1),
        NONE(false, 0),
        SOLID(false, 1);

        double widthMult = 1.0;
        boolean dashed = false;

        BasicStroke getStroke(int thickness)
        {
            if (widthMult == 0)
            {
                return new BasicStroke(0);
            } else if (dashed)
            {
                float[] pat = {10.0f};
                return new BasicStroke((float) widthMult * 1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, pat, 0.0f);
            } else
            {
                return new BasicStroke((float) widthMult * (float) thickness);
            }
        }

        stroke(boolean cDashed, double cWidthMult)
        {
            dashed = cDashed;
            widthMult = cWidthMult;
        }
    }

    enum badgeColors
    {
        FINISHED(0, 128, 255),
        STARTED(64, 255, 0),
        CANSTART(255, 128, 0),
        CANNOTSTART(64, 64, 64),

        FINISHED_FILL(255, 255, 255),
        STARTED_FILL(255, 255, 255),
        CANSTART_FILL(192, 192, 192),
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

        private int r;
        private int g;
        private int b;
        private int a = 255;

        private int[] getColor()
        {
            int[] cols = new int[4];
            cols[0] = r;
            cols[1] = g;
            cols[2] = b;
            cols[3] = a;
            return cols;
        }

        private Paint getPaint()
        {
            return new Color(r, g, b, a);
        }

        badgeColors(int cr, int cg, int cb)
        {
            r = cr > 255 ? 255 : (cr < 0 ? 0 : cr);
            g = cg > 255 ? 255 : (cg < 0 ? 0 : cg);
            b = cb > 255 ? 255 : (cb < 0 ? 0 : cb);
        }

        badgeColors(int cr, int cg, int cb, int ca)
        {
            this(cr, cg, cb);
            a = ca > 255 ? 255 : (ca < 0 ? 0 : ca);
        }
    }

    /**
     * Creates a GanttishDiagram from a given Assignment.
     *
     * @param fromStudyProfile StudyProfile
     * @param fromAssignment   Assignment for which to generate the GanttishDiagram.
     * @return Generated diagram
     */
    public static BufferedImage createGanttishDiagram(StudyPlanner fromStudyProfile, Assignment fromAssignment)
    {
        return createGanttishDiagram(fromStudyProfile, fromAssignment, "");
    }

    /**
     * Creates a GanttishDiagram from a given Assignment and saves it to a file.
     *
     * @param fromStudyProfile StudyProfile
     * @param fromAssignment   Assignment for which to generate the GanttishDiagram.
     * @param filePath         file path
     * @return Generated diagram
     */
    public static BufferedImage createGanttishDiagram(StudyPlanner fromStudyProfile, Assignment fromAssignment, String filePath)
    {
        return createGanttishDiagram(fromStudyProfile, fromAssignment, filePath, false);
    }

    /**
     * Creates a GanttishDiagram from a given Assignment with the option to set the background transparent.
     *
     * @param fromStudyProfile      StudyProfile
     * @param fromAssignment        Assignment for which to generate the GanttishDiagram.
     * @param filePath              file path
     * @param transparentBackground whether the background should be white or transparent
     * @return Generated diagram
     */
    public static BufferedImage createGanttishDiagram(StudyPlanner fromStudyProfile, Assignment fromAssignment,
                                                      String filePath, boolean transparentBackground)
    {

        HashMap<String, ArrayList<Task>> catTasks = new HashMap<>();

        String COMPLETED = "Completed tasks";
        String STARTED = "Tasks started";
        String POSSIBLE = "Possible to start";
        String IMPOSSIBLE = "Not possible to start";

        String[] categoryList = {COMPLETED, STARTED, POSSIBLE, IMPOSSIBLE};
        badgeColors[][] badges = {{badgeColors.FINISHED, badgeColors.FINISHED_FILL},
                {badgeColors.STARTED, badgeColors.STARTED_FILL},
                {badgeColors.CANSTART, badgeColors.CANSTART_FILL},
                {badgeColors.CANNOTSTART, badgeColors.CANNOTSTART_FILL}};

        catTasks.put(COMPLETED, new ArrayList<>());
        catTasks.put(STARTED, new ArrayList<>());
        catTasks.put(POSSIBLE, new ArrayList<>());
        catTasks.put(IMPOSSIBLE, new ArrayList<>());

        ArrayList<Task> assignmentTasks = fromAssignment.getTasks();

        Iterator<Task> i = assignmentTasks.iterator();
        int longest = 0;
        while (i.hasNext())
        {
            Task t = i.next();
            String cat;
            if (t.isCheckedComplete())
            {
                cat = COMPLETED;
            } else if (t.requirementsComplete() > 0)
            {
                cat = STARTED;
            } else if (t.dependenciesComplete())
            {
                cat = POSSIBLE;
            } else
            {
                cat = IMPOSSIBLE;
            }
            catTasks.get(cat).add(t);
            if (catTasks.get(cat).size() > longest)
            {
                longest = catTasks.get(cat).size();
            }
        }

        int j = -1;
        int jj = categoryList.length;

        int cWidth = GANTT_COLUMN_PADDING + jj * (GANTT_COLUMN_PADDING + GANTT_COLUMN_WIDTH);
        int cHeight = 2 * GANTT_COLUMN_PADDING + GANTT_TITLE_SPACE + (1 + longest) * (GANTT_COLUMN_SPACING + GANTT_ENTRY_HEIGHT);

        BufferedImage r = new BufferedImage(cWidth, cHeight, imageType);
        Graphics2D g2d = r.createGraphics();

        if (!transparentBackground)
        {

            g2d.setPaint(Color.white);
            g2d.fillRect(0, 0, cWidth, cHeight);
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
        drawMessage(g2d, GANTT_TITLE_FONT_SIZE, fromAssignment.getName(), GANTT_COLUMN_PADDING, GANTT_COLUMN_PADDING,
                cWidth - (2 * GANTT_COLUMN_PADDING), GANTT_TITLE_SPACE);
        while (++j < jj)
        {
            int ox = GANTT_COLUMN_PADDING + j * (GANTT_COLUMN_PADDING + GANTT_COLUMN_WIDTH);
            int oy = GANTT_COLUMN_PADDING + GANTT_TITLE_SPACE;

            String name = categoryList[j];
            int k = 0;
            int kk = catTasks.get(name).size() + 1;


            g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
            drawMessage(g2d, GANTT_ENTRY_FONT_SIZE, name, ox, oy, GANTT_COLUMN_WIDTH, GANTT_ENTRY_HEIGHT);

            while (++k < kk)
            {
                Task dt = catTasks.get(name).get(k - 1);
                String dtt = dt.getType().getName();
                Paint col, fcol;
                if (GANTT_ENTRY_COLOR.containsKey(dtt))
                {
                    fcol = (GANTT_ENTRY_FCOLOR.get(dtt));
                    col = (GANTT_ENTRY_COLOR.get(dtt));
                } else
                {
                    fcol = (GANTT_ENTRY_DEFAULT_FCOLOR);
                    col = (GANTT_ENTRY_DEFAULT_COLOR);
                }

                int oyk = oy + k * (GANTT_COLUMN_SPACING + GANTT_ENTRY_HEIGHT);

                g2d.setPaint(fcol);
                g2d.fillRoundRect(ox, oyk, GANTT_COLUMN_WIDTH, GANTT_ENTRY_HEIGHT, 15, 15);

                g2d.setPaint(col);
                g2d.drawRoundRect(ox, oyk, GANTT_COLUMN_WIDTH, GANTT_ENTRY_HEIGHT, 15, 15);

                g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
                drawMessage(g2d, GANTT_ENTRY_FONT_SIZE, dt.getName(), ox, oyk, GANTT_COLUMN_WIDTH -
                        GANTT_ENTRY_HEIGHT, GANTT_ENTRY_HEIGHT);
                int percentage;
                if (dt.requirementCount() <= 0)
                {
                    percentage = dt.isCheckedComplete() ? 100 : 0;
                } else
                {
                    percentage = (100 * dt.requirementsComplete()) / dt.requirementCount();
                }
                BufferedImage badge = getBadge(percentage, dt.dependenciesComplete(), 0.5);

                g2d.drawImage(badge, ox + GANTT_COLUMN_WIDTH -
                        GANTT_ENTRY_HEIGHT, oyk, null);
            }
        }


        if (!filePath.equals(""))
        {
            try
            {
                File outputfile = new File(filePath);
                ImageIO.write(r, "png", outputfile);
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return r;
    }

    /**
     * Generates a Badge with the given progress.
     *
     * @param progress integer representation of the progress.
     * @param canStart true - default, false - greyed out.
     * @return generated Badge
     */
    public static BufferedImage getBadge(int progress, boolean canStart)
    {
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
    public static BufferedImage getBadge(int progress, boolean canStart, double multiplier)
    {
        int canvasSize = (int) ((double) badgeSize * multiplier + .5);
        int ringSize = (int) ((double) badgeRingSize * multiplier + .5);
        int badgeRingThickness = (int) ((double) getBadgeRingThickness * multiplier + .5);
        int badgeFontSize = (int) ((double) fontSize * multiplier + .5);

        int ovalOffset = (canvasSize - ringSize) / 2;


        BufferedImage r = new BufferedImage(canvasSize, canvasSize, imageType);
        Graphics2D g2d = r.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint ringColor;
        Paint fillColor;
        int arc = 100;

        if (!canStart)
        {
            g2d.setStroke(stroke.DASHED.getStroke(badgeRingThickness));
            ringColor = badgeColors.CANNOTSTART.getPaint();
            fillColor = badgeColors.CANNOTSTART_FILL.getPaint();
        } else if (progress <= 0)
        {
            g2d.setStroke(stroke.DASHED.getStroke(badgeRingThickness));
            ringColor = badgeColors.CANSTART.getPaint();
            fillColor = badgeColors.CANSTART_FILL.getPaint();
        } else if (progress < 100)
        {
            g2d.setStroke(stroke.SOLID.getStroke(badgeRingThickness));
            ringColor = badgeColors.STARTED.getPaint();
            fillColor = badgeColors.STARTED_FILL.getPaint();
            arc = progress;
        } else
        {
            g2d.setStroke(stroke.SOLID.getStroke(badgeRingThickness));
            ringColor = badgeColors.FINISHED.getPaint();
            fillColor = badgeColors.FINISHED_FILL.getPaint();
        }

        g2d.setPaint(fillColor);
        g2d.fillOval(ovalOffset, ovalOffset, ringSize, ringSize);

        Font font = new Font("Helvetica", Font.PLAIN, badgeFontSize);
        FontMetrics metrics = g2d.getFontMetrics(font);

        String msg = progress + "%";

        int msgX = ovalOffset + (ringSize - metrics.stringWidth(msg)) / 2;
        int msgY = ovalOffset + (ringSize - metrics.getHeight()) / 2 + metrics.getAscent();


        if (arc == 100)
        {
            g2d.setPaint(ringColor);
            g2d.drawOval(ovalOffset, ovalOffset, ringSize, ringSize);
        } else
        {
            g2d.setPaint(ringColor);
            int startAngle = 90;
            int endAngle = -((int) (((float) arc / 100f) * 360));
            g2d.drawArc(ovalOffset, ovalOffset, ringSize, ringSize, startAngle, endAngle);
        }


        g2d.setFont(font);
        g2d.drawString(msg, msgX, msgY);


/*        try {
            File outputfile = new File("badge_"+progress+".png");
            ImageIO.write(r, "png", outputfile);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }*/

        return r;
    }

    /**
     * Generates a rectangle with the given message in the middle.
     *
     * @param g2d
     * @param fontSize
     * @param msg
     * @param c_x
     * @param c_y
     * @param c_width
     * @param c_height
     */
    static void drawMessage(Graphics2D g2d, int fontSize, String msg, int c_x, int c_y, int c_width, int c_height)
    {

        Font font = new Font("Helvetica", Font.PLAIN, fontSize);
        FontMetrics metrics = g2d.getFontMetrics(font);


        int msgX = c_x + (c_width - metrics.stringWidth(msg)) / 2;
        int msgY = c_y + (c_height - metrics.getHeight()) / 2 + metrics.getAscent();


        g2d.setFont(font);
        g2d.drawString(msg, msgX, msgY);
    }
}
