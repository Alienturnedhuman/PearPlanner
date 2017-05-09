package Model;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class StudyPlanner implements Serializable {
    static final long serialVersionUID = 101L; //probably needs to be linked to the verison control or such

    // private data
    private Account account;
    private ArrayList<QuantityType> quantityTypes = new ArrayList<QuantityType>();
    private ArrayList<TaskType> taskTypes = new ArrayList<TaskType>();
    private ArrayList<StudyProfile> studyProfiles = new ArrayList<StudyProfile>();
    private ArrayList<ActivityEvent> activityList = new ArrayList<ActivityEvent>();
    private ArrayList<TimeTableEventType> timeTableEventTypes = new ArrayList<TimeTableEventType>();
    private ArrayList<Event> calendar = new ArrayList<Event>();




    /**
     * returns a String array of studyProfile names
     * @return
     */
    public String[] getListOfStudyProfiles()
    {
        int i = -1;
        String[] r = new String[studyProfiles.size()];
        while(++i<studyProfiles.size())
        {
            r[i] = studyProfiles.get(i).getName();
        }
        return r;
    }

    // public methods
    public void loadFile (String filePath)
    {
        // initial set up code below - check if this needs updating
        throw new UnsupportedOperationException("This method is not implemented yet");

    }

    public boolean containsStudyProfile(int sYear, int sSem)
    {
        int i = -1;
        int ii = studyProfiles.size();
        while(++i<ii)
        {
            if(studyProfiles.get(i).matches(sYear,sSem))
            {
                return true;
            }
        }
        return false;
    }

    // getters
    public void processHubFile(HubFile newHubFile)
    {
        // initial set up code below - check if this needs updating
        if(newHubFile.isUpdate())
        {
            // process update file - to do
        }
        else
        {
            if(!containsStudyProfile(newHubFile.getYear(),newHubFile.getSemester()))
            {
                StudyProfile newSP = new StudyProfile(newHubFile);
            }
        }
        throw new UnsupportedOperationException("This method is not implemented yet");

    }


    void writeObject(Cipher cipher, String fileName, SecretKey key64) throws IOException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        cipher.init( Cipher.ENCRYPT_MODE, key64 );

        StudyPlanner sp = new StudyPlanner();
        SealedObject sealedObject = new SealedObject( sp, cipher);
        CipherOutputStream cipherOutputStream = new CipherOutputStream( new BufferedOutputStream( new FileOutputStream( fileName ) ), cipher );
        ObjectOutputStream outputStream = new ObjectOutputStream( cipherOutputStream );
        outputStream.writeObject( sealedObject );
        outputStream.close();

    }


    void readObject(Cipher cipher, String fileName, SecretKey key64) throws InvalidKeyException, IOException, ClassNotFoundException, BadPaddingException, IllegalBlockSizeException {
        cipher.init( Cipher.DECRYPT_MODE, key64 );
        CipherInputStream cipherInputStream = new CipherInputStream( new BufferedInputStream( new FileInputStream( fileName ) ), cipher );
        ObjectInputStream inputStream = new ObjectInputStream( cipherInputStream );
        SealedObject sealedObject = (SealedObject) inputStream.readObject();
        //Person person1 = (Person) sealedObject.getObject( cipher );
        StudyPlanner sp = (StudyPlanner)sealedObject.getObject( cipher );

    }


    // setters




    // constructor
    public StudyPlanner(Account newAccount) throws NoSuchPaddingException, NoSuchAlgorithmException {
        // it may make sense to clone this to stop someone retaining access to the
        // object
        account = newAccount;
        String fileName = "result.dat";
        SecretKey key64 = new SecretKeySpec( new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 }, "Blowfish" );
        Cipher cipher = Cipher.getInstance( "Blowfish" );


    }

    //empty constructor
    public StudyPlanner() {



    }



}
