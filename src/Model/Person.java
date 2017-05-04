package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class Person extends VersionControlEntity
{
    // private data
    private ArrayList<String> givenNames;
    private String familyName;
    private String salutation;
    private String email;
    private boolean familyNameLast = true;

    @Override
    protected void replace(VersionControlEntity receivedVCE)
    {
        if(receivedVCE instanceof Person)
        {
            Person castedVCE = (Person)receivedVCE;
            this.givenNames = castedVCE.getGivenNames();
            this.familyName = castedVCE.getFamilyName();
            this.salutation = castedVCE.getSalutation();
            this.email = castedVCE.getEmail();
            this.familyNameLast = castedVCE.getFamilyNameLast();
        }
        super.replace(receivedVCE);
    }


    // public methods
    // getters
    public String getFullName()
    {
        // initial set up code below - check if this needs updating
        String namesList[] = new String[givenNames.size()];
        if(familyNameLast)
        {
            return (salutation.length()>0?salutation+" ":"")+String.join(" ",givenNames)+" "+familyName;
        }
        else
        {
            return (salutation.length()>0?salutation+" ":"")+familyName+" "+String.join(" ",givenNames);
        }
    }
    public String getFamilyName()
    {
        // initial set up code below - check if this needs updating
        return familyName;
    }
    public String getEmail()
    {
        // initial set up code below - check if this needs updating
        return email;
    }
    public ArrayList<String> getGivenNames()
    {
        // initial set up code below - check if this needs updating
        return (ArrayList<String>)givenNames.clone();
    }
    public boolean getFamilyNameLast()
    {
        // initial set up code below - check if this needs updating
        return familyNameLast;
    }
    public String getSalutation()
    {
        // initial set up code below - check if this needs updating
        return salutation;
    }
    public boolean hasSalutation()
    {
        // initial set up code below - check if this needs updating
        return salutation.length()>0;
    }
    public String getPreferredName()
    {
        // initial set up code below - check if this needs updating
        return name.length()>0? name :(givenNames.size()>0?givenNames.get(0):familyName);
    }
    // setters
    public void setFamilyName(String newFamilyName)
    {
        // initial set up code below - check if this needs updating
        familyName = newFamilyName;
    }


    public void setPreferredName(String newPreferredName)
    {
        name = newPreferredName;
    }

    /**
     * Sets the given names from a string with space separated names
     * @param nameStr String containing names
     */
    public void setGivenNames(String nameStr)
    {
        // initial set up code below - check if this needs updating
        String nameSplit[] = nameStr.split(" ");
        givenNames = new ArrayList<String>(Arrays.asList(nameSplit));
    }

    /**
     * Sets the name from a string with space separated names
     * Family name position at start or end indicated by boolean value
     * @param nameStr   String containing names
     * @param isFamilyNameLast  is the family name at the end?
     */
    public void setName(String nameStr,boolean isFamilyNameLast)
    {
        // initial set up code below - check if this needs updating
        String nameSplit[] = nameStr.split(" ");
        familyNameLast = isFamilyNameLast;
        givenNames = new ArrayList<String>();
        int i = -1;
        int ii = nameSplit.length;
        if(familyNameLast)
        {
            familyName = nameSplit[--ii];
        }
        else
        {
            familyName = nameSplit[++i];
        }
        while(++i<ii)
        {
            givenNames.add(nameSplit[i]);
        }
    }
    public void setEmail(String newEmail)
    {
        email = newEmail;
    }

    public void setSalutation(String newSalutation)
    {
        salutation = newSalutation;
    }

    // constructors

    /**
     *
     * @param cSalutation String for saluation
     * @param cName "NAME1 NAME2 NAME3 .... NAMEn"
     * @param cFamNameLast if true, last name is family name, if not, first is
     */
    public Person(String cSalutation , String cName , Boolean cFamNameLast)
    {
        setSalutation(cSalutation);
        setName(cName,cFamNameLast);
    }

    /**
     *
     * @param cSalutation String for salutation
     * @param cGivenNames Array list of strings for given names
     * @param cFamName String for family name
     * @param cFamNameLast true if family name is at the end
     */
    public Person(String cSalutation , ArrayList<String> cGivenNames , String cFamName , Boolean cFamNameLast)
    {
        setFamilyName(cFamName);
        givenNames = (ArrayList<String>)cGivenNames.clone();
        setSalutation(cSalutation);
        familyNameLast = cFamNameLast;
    }
}
