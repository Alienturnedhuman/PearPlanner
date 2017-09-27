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

package Model;

import Controller.MainController;

import java.util.HashMap;

/**
 * PearPlanner
 * Created by Team BRONZE on 4/27/17
 */
public class VersionControlEntity extends ModelEntity
{
    protected int version;
    protected String uid;
    protected boolean sealed;
    private static HashMap<String, VersionControlEntity> library = new HashMap<>();
    protected boolean importer = false; // used for VCEs created during XML import
    // private methods

    /**
     * This method overwrites the data in the received object with that received
     * This method will need to overrode in every class that extends it
     *
     * @param receivedVCE
     */
    protected void replace(VersionControlEntity receivedVCE)
    {
        name = receivedVCE.getName();
        details = receivedVCE.getDetails();
        version = receivedVCE.getVersion();
        // super.replace(receivedVCE);
    }

    // public methods

    /**
     * Update ths VCE with a given one.
     *
     * @param receivedVCE received VCE for updating the current one.
     * @return whether updated successfully.
     */
    public boolean update(VersionControlEntity receivedVCE)
    {
        if (uid.equals(receivedVCE.getUID()) && version < receivedVCE.getVersion())
        {
            replace(receivedVCE);
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Find the given VCE in the library and then update it.
     *
     * @param receivedVCE a VCE to be looked for and updated.
     * @return whether found and updated successfully.
     */
    public static boolean findAndUpdate(VersionControlEntity receivedVCE)
    {
        String UID = receivedVCE.getUID();
        if (inLibrary(UID))
        {
            library.get(UID).update(receivedVCE);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean makeImporter()
    {
        if (!sealed)
        {
            importer = true;
            return true;
        } else
        {
            return false;
        }
    }

    public boolean isImporter()
    {
        return importer;
    }

    /**
     * Add this VCE to the library.
     *
     * @return whether added successfully.
     */
    public boolean addToLibrary()
    {
        if (importer)
        {
            if (inLibrary(uid))
            {
                return false;
            } else
            {
                importer = false;
                sealed = true;
                library.put(uid, this);
                MainController.getSPC().getPlanner().addToVersionControlLibrary(this);
                return true;
            }
        } else
        {
            return false;
        }
    }

    /**
     * Get a VCE from the library by it's UID
     *
     * @param UID UID to be looked for.
     * @return a valid VCE if found, null otherwise.
     */
    public static VersionControlEntity get(String UID)
    {
        if (inLibrary(UID))
        {
            return library.get(UID);
        } else
        {
            return null;
        }
    }

    /**
     * Check whether a VCE with the given UID exists in the library.
     *
     * @param UID UID to be checked for.
     * @return true if found, false otherwise.
     */
    public static boolean inLibrary(String UID)
    {
        return library.containsKey(UID);
    }

    // getters
    public int getVersion()
    {
        return version;
    }

    public String getUID()
    {
        return uid;
    }

    /**
     * Returns the VCE library.
     *
     * @return HashMap containing all VCEs.
     */
    public static HashMap<String, VersionControlEntity> getLibrary()
    {
        return library;
    }

    /**
     * Returns a summary of the VCEs in the library.
     *
     * @return String
     */
    public static String libraryReport()
    {
        return "Total Entries: " + library.size();
    }

    /**
     * Set a new UID and version for this VCE.
     *
     * @param newUID     new UID
     * @param newVersion new version
     * @return whether changed successfully.
     */
    public boolean setUID(String newUID, int newVersion)
    {
//        setUID(newUID);
        if (importer)
        {
            setUID(newUID);
            version = newVersion;
            return true;
        } else if (sealed || library.containsKey(newUID))
        {
            return false;
        } else
        {
            setUID(newUID);
            version = newVersion;
            return true;
        }
    }

    /**
     * Set a new UID for this VCE.
     *
     * @param newUID new UID
     * @return whether changed successfully.
     */
    public boolean setUID(String newUID)
    {
        if (importer)
        {
            uid = newUID;
            return true;
        } else if (sealed || library.containsKey(newUID))
        {
            return false;
        } else
        {
            uid = newUID;
            library.put(newUID, this);
            MainController.getSPC().getPlanner().addToVersionControlLibrary(this);
            return true;
        }
    }

    /**
     * Called once the program is loaded, adds this VCE to the library if possible.
     */
    public void reload()
    {
        if (!inLibrary(this.uid) && !importer && sealed)
        {
            library.put(this.uid, this);
        }
    }

    // Constructors
    public VersionControlEntity(boolean leaveUnsealed)
    {
        super();
        sealed = !leaveUnsealed;
    }

    public VersionControlEntity()
    {
        super();
        sealed = false;
    }

    public VersionControlEntity(String UID)
    {
        super();
        sealed = setUID(UID);
    }

}
