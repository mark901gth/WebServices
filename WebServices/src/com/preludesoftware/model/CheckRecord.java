package com.preludesoftware.model;

/**
 * Chk table
 * <p>
 * This class contains the representation of a subset of the database table 'Chk' along with the
 * appropriate getters and setters for each field. 
 * </p>
 * 
 * @author Mark Levine
 * @version 0.1 - &copy; Prelude Software, 2014.
 */

public class CheckRecord
{
    //
    // Chk table fields
    //
    
    private String OrigID = null;
    private String ID     = null;

    //
    // Getters/Setters for each field
    //
    
    // OrigID

    public String getOrigID()
    {
        return this.OrigID;
    }


    public void setOrigID(String origID)
    {
        this.OrigID = origID;
    }

    // ID
    
    public String getID()
    {
        return this.ID;
    }


    public void setID(String iD)
    {
        this.ID = iD;
    }

}
