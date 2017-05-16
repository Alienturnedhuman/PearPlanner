package View;

import Model.Assignment;
import Model.StudyPlanner;
import Model.Task;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by bendickson on 5/15/17.
 */
public class GanttishDiagram {

    static int width = 1920;
    static int height = 1280;

    static int imageType = BufferedImage.TYPE_INT_ARGB;

    static int badgeSize = 128;
    static int badgeRingSize = 112;
    static int getBadgeRingThickness = 4;
    static int fontSize = 18;


    static int GANTT_COLUMN_WIDTH = 400;
    static int GANTT_COLUMN_PADDING = 80;
    static int GANTT_COLUMN_SPACING = 16;
    static int GANTT_ENTRY_FONT_SIZE = 24;
    static Paint GANTT_ENTRY_FONT_COLOR = Color.black;
    static int GANTT_ENTRY_HEIGHT = 36;
    static Paint GANTT_ENTRY_DEFAULT_COLOR = badgeColors.GREY.getPaint();
    static HashMap<String,Paint> GANTT_ENTRY_COLOR = new HashMap<>();
    static
    {
        GANTT_ENTRY_COLOR.put("Reading",badgeColors.PINK.getPaint());
        GANTT_ENTRY_COLOR.put("Exercises",badgeColors.ORANGE.getPaint());
        GANTT_ENTRY_COLOR.put("Revision",badgeColors.YELLOW.getPaint());
        GANTT_ENTRY_COLOR.put("Listening",badgeColors.GREEN.getPaint());
        GANTT_ENTRY_COLOR.put("Coursework",badgeColors.BLUE.getPaint());
        GANTT_ENTRY_COLOR.put("Meeting",badgeColors.PURPLE.getPaint());
    }
    static Paint GANTT_ENTRY_DEFAULT_FCOLOR = badgeColors.T_GREY.getPaint();
    static HashMap<String,Paint> GANTT_ENTRY_FCOLOR = new HashMap<>();
    static
    {
        GANTT_ENTRY_FCOLOR.put("Reading",badgeColors.T_PINK.getPaint());
        GANTT_ENTRY_FCOLOR.put("Exercises",badgeColors.T_ORANGE.getPaint());
        GANTT_ENTRY_FCOLOR.put("Revision",badgeColors.T_YELLOW.getPaint());
        GANTT_ENTRY_FCOLOR.put("Listening",badgeColors.T_GREEN.getPaint());
        GANTT_ENTRY_FCOLOR.put("Coursework",badgeColors.T_BLUE.getPaint());
        GANTT_ENTRY_FCOLOR.put("Meeting",badgeColors.T_PURPLE.getPaint());
    }


    enum stroke{
        DASHED(true,1)
        ,
        NONE(false,0)
        ,
        SOLID(false,1)
        ;

        double widthMult = 1.0;
        boolean dashed = false;

        BasicStroke getStroke(int thickness){
            if(widthMult==0)
            {
                return new BasicStroke(0);
            }
            else if(dashed)
            {
                float[] pat = {10.0f};
                return new BasicStroke((float)widthMult*1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, pat, 0.0f);
            }
            else
            {
                return new BasicStroke((float)widthMult*(float)thickness);
            }
        }

        stroke(boolean cDashed , double cWidthMult)
        {
            dashed = cDashed;
            widthMult = cWidthMult;
        }
    }

    enum badgeColors{
        FINISHED(0,128,255)
        ,
        STARTED(64,255,0)
        ,
        CANSTART(255,128,0,0)
        ,
        CANNOTSTART(64,64,64,0)
        ,

        FINISHED_FILL(255,255,255)
        ,
        STARTED_FILL(255,255,255)
        ,
        CANSTART_FILL(192,192,192)
        ,
        CANNOTSTART_FILL(128,128,128)

        ,
        GREY(192,192,192)
        ,
        PINK(255,192,192)
        ,
        ORANGE(255,128,0)
        ,
        BLUE(0,128,255)
        ,
        GREEN(0,255,64)
        ,
        PURPLE(64,0,255)
        ,
        YELLOW(255,64,0)
        ,
        T_GREY(192,192,192,128)
        ,
        T_PINK(255,192,192,128)
        ,
        T_ORANGE(255,128,0,128)
        ,
        T_BLUE(0,128,255,128)
        ,
        T_GREEN(0,255,64,128)
        ,
        T_PURPLE(64,0,255,128)
        ,
        T_YELLOW(255,64,0,128)

        ;

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
            return new Color(r,g,b,a);
        }

        badgeColors(int cr, int cg,int cb)
        {
            r = cr>255?255:(cr<0?0:cr);
            g = cg>255?255:(cg<0?0:cg);
            b = cb>255?255:(cb<0?0:cb);
        }
        badgeColors(int cr, int cg,int cb,int ca)
        {
            this(cr,cg,cb);
            a = ca>255?255:(ca<0?0:ca);
        }
    }

    public static BufferedImage createGanttishDiagram(StudyPlanner fromStudyProfile,Assignment fromAssignment)
    {

        HashMap<String,ArrayList<Task>> catTasks = new HashMap<>();

        String COMPLETED = "Completed tasks";
        String POSSIBLE = "Possible to start";
        String IMPOSSIBLE = "Not possible to start";

        String[] categoryList = {COMPLETED,POSSIBLE,IMPOSSIBLE};

        catTasks.put(COMPLETED,new ArrayList<>());
        catTasks.put(POSSIBLE,new ArrayList<>());
        catTasks.put(IMPOSSIBLE,new ArrayList<>());

        ArrayList<Task> assignmentTasks = fromAssignment.getTasks();

        Iterator<Task> i = assignmentTasks.iterator();
        int longest = 0;
        while(i.hasNext())
        {
            Task t = i.next();
            String cat;
            if(t.isCheckedComplete())
            {
                cat = COMPLETED;
            }
            else if(t.isPossibleToComplete())
            {
                cat = POSSIBLE;
            }
            else
            {
                cat = IMPOSSIBLE;
            }
            catTasks.get(cat).add(t);
            if(catTasks.get(cat).size()>longest)
            {
                longest = catTasks.get(cat).size();
            }
        }

        int j = -1; int jj = categoryList.length;

        int cWidth = GANTT_COLUMN_PADDING + jj*(GANTT_COLUMN_PADDING+GANTT_COLUMN_WIDTH);
        int cHeight = 2*GANTT_COLUMN_PADDING + (1+longest)*(GANTT_COLUMN_SPACING+GANTT_ENTRY_HEIGHT);

        BufferedImage r = new BufferedImage(cWidth,cHeight,imageType);
        Graphics2D g2d = r.createGraphics();
        g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        while(++j<jj)
        {
            int ox = GANTT_COLUMN_PADDING + j*(GANTT_COLUMN_PADDING+GANTT_COLUMN_WIDTH);
            int oy = GANTT_COLUMN_PADDING;

            String name = categoryList[j];
            int k  =0;
            int kk = catTasks.get(name).size()+1;


            g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
            drawMessage(g2d,GANTT_ENTRY_FONT_SIZE,name,ox,oy,GANTT_COLUMN_WIDTH,GANTT_ENTRY_HEIGHT);

            while(++k<kk)
            {
                Task dt = catTasks.get(name).get(k-1);
                String dtt = dt.getType().getName();
                Paint col,fcol;
                if(GANTT_ENTRY_COLOR.containsKey(dtt))
                {
                    fcol = (GANTT_ENTRY_FCOLOR.get(dtt));
                    col = (GANTT_ENTRY_COLOR.get(dtt));
                }
                else
                {
                    fcol = (GANTT_ENTRY_DEFAULT_FCOLOR);
                    col = (GANTT_ENTRY_DEFAULT_COLOR);
                }

                int oyk = oy+k*(GANTT_COLUMN_SPACING+GANTT_ENTRY_HEIGHT);

                g2d.setPaint(fcol);
                g2d.fillRoundRect(ox, oyk, GANTT_COLUMN_WIDTH, GANTT_ENTRY_HEIGHT,15,15);

                g2d.setPaint(col);
                g2d.drawRoundRect(ox, oyk, GANTT_COLUMN_WIDTH, GANTT_ENTRY_HEIGHT,15,15);

                g2d.setPaint(GANTT_ENTRY_FONT_COLOR);
                drawMessage(g2d,GANTT_ENTRY_FONT_SIZE,dt.getName(),ox,oyk,GANTT_COLUMN_WIDTH,GANTT_ENTRY_HEIGHT);
            }
        }


        try {
            File outputfile = new File("gantt.png");
            ImageIO.write(r, "png", outputfile);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        /*

        HashMap<Task,ArrayList<Task>> familyTree = new HashMap<>();
        HashMap<Task,Boolean> noParents = new HashMap<>();
        HashMap<Task,Boolean> noInternalParents = new HashMap<>();

        Iterator<Task> i = assignmentTasks.iterator();
        while(i.hasNext())
        {
            Task t = i.next();
            familyTree.put(t,new ArrayList<Task>());
            noInternalParents.put(t,true);
        }
        i = assignmentTasks.iterator();
        while(i.hasNext())
        {
            Task t = i.next();
            if(t.hasDependencies())
            {
                noParents.put(t,false);
                ArrayList<Task> taskDependencies = new ArrayList<Task>(Arrays.asList(t.getDependencies()));
                Iterator<Task> j = taskDependencies.iterator();
                while(j.hasNext())
                {
                    Task d = j.next();
                    if(familyTree.containsKey(d))
                    {
                        ArrayList<Task> dc = familyTree.get(d);
                        if(!dc.contains(t))
                        {
                            dc.add(t);
                        }
                        noInternalParents.put(t,false);
                    }
                }
            }
            else
            {
                noParents.put(t,false);
            }
        }
        */


        return r;
    }

    public BufferedImage getBadge(int progress,boolean canStart)
    {
        return getBadge(progress,canStart,1.0f);
    }
    public BufferedImage getBadge(int progress,boolean canStart, double multiplier)
    {
        int canvasSize = (int)((double)badgeSize*multiplier);
        int ringSize = (int)((double)badgeRingSize*multiplier);
        int badgeRingThickness = (int)((double)getBadgeRingThickness*multiplier);
        int badgeFontSize = (int)((double)fontSize*multiplier);

        int ovalOffset = (canvasSize - ringSize)/2;


        BufferedImage r = new BufferedImage(canvasSize,canvasSize,imageType);
        Graphics2D g2d = r.createGraphics();
        g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint ringColor;
        Paint fillColor;
        int arc = 100;

        if(!canStart)
        {
            g2d.setStroke(stroke.DASHED.getStroke(badgeRingThickness));
            ringColor = badgeColors.CANNOTSTART.getPaint();
            fillColor = badgeColors.CANNOTSTART_FILL.getPaint();
        }
        else if(progress<=0)
        {
            g2d.setStroke(stroke.DASHED.getStroke(badgeRingThickness));
            ringColor = badgeColors.CANSTART.getPaint();
            fillColor = badgeColors.CANSTART_FILL.getPaint();
        }
        else if(progress<100)
        {
            g2d.setStroke(stroke.SOLID.getStroke(badgeRingThickness));
            ringColor = badgeColors.STARTED.getPaint();
            fillColor = badgeColors.STARTED_FILL.getPaint();
            arc = progress;
        }
        else
        {
            g2d.setStroke(stroke.SOLID.getStroke(badgeRingThickness));
            ringColor = badgeColors.FINISHED.getPaint();
            fillColor = badgeColors.FINISHED_FILL.getPaint();
        }

        g2d.setPaint(fillColor);
        g2d.fillOval(ovalOffset,ovalOffset,ringSize,ringSize);

        Font font = new Font("Helvetica",Font.PLAIN,badgeFontSize);
        FontMetrics metrics = g2d.getFontMetrics(font);

        String msg = progress+"%";

        int msgX = ovalOffset + (ringSize - metrics.stringWidth(msg))/2;
        int msgY = ovalOffset + (ringSize - metrics.getHeight())/2 + metrics.getAscent();


        if(arc==100)
        {
            g2d.setPaint(ringColor);
            g2d.drawOval(ovalOffset,ovalOffset,ringSize,ringSize);
        }
        else
        {
            g2d.setPaint(ringColor);
            int startAngle = 90;
            int endAngle = -((int)(((float)arc/100f)*360));
            g2d.drawArc(ovalOffset,ovalOffset,ringSize,ringSize,startAngle,endAngle);
        }


        g2d.setFont(font);
        g2d.drawString(msg, msgX, msgY);


        try {
            File outputfile = new File("badge_"+progress+".png");
            ImageIO.write(r, "png", outputfile);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        return r;
    }

    static void drawMessage(Graphics2D g2d,int fontSize , String msg,int c_x,int c_y,int c_width,int c_height)
    {

        Font font = new Font("Helvetica",Font.PLAIN,fontSize);
        FontMetrics metrics = g2d.getFontMetrics(font);


        int msgX = c_x + (c_width - metrics.stringWidth(msg))/2;
        int msgY = c_y + (c_height - metrics.getHeight())/2 + metrics.getAscent();



        g2d.setFont(font);
        g2d.drawString(msg, msgX, msgY);
    }
}
