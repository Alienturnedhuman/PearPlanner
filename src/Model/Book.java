package Model;

import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Book extends Requirement
{
    // private data
    private ArrayList<String> chapters;

    // public methods

    // getters
    public ArrayList<String> getChapters()
    {
        // initial set up code below - check if this needs updating
        return chapters;
    }

    // setters
    public void setChapters(ArrayList<String> newChapters)
    {
        // initial set up code below - check if this needs updating
        chapters = newChapters;
    }


    // constructor

    public Book(ArrayList<String> chapters)
    {
        this.chapters = chapters;
    }
}
