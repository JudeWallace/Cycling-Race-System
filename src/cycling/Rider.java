package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Used when a new rider is inputted into the system
 * 
 * @author Jude Wallace
 * @author William See
 * @version 1.0
 */
public class Rider implements Serializable{
    /**
     * Contains all the Rider objects created
     */
    protected static ArrayList<Rider> riderList = new ArrayList<>();
    
    /**
     * The full name of the rider
     */
    private String name;
    
    /**
     * The year the rider was born
     */
    private int yearOfBirth;
    
    /**
     * The team Id of which the rider is in
     */
    private int teamID;
    
    /**
     * The unique Id given to the rider
     */
    private int riderID;
    
    /**
     * The counter for the riders unique Ids
     */
    protected static int numberOfRiders = 0;
    
    /**
     * Constructs an instance of the Rider containing all the correct attributes
     * 
     * @param teamID Accepts the team Id of the rider and set it on the Rider object
     * @param name Accepts the name of the rider and sets it on the Rider object
     * @param yearOfBirth Accepts the year the rider was born and sets it on the Rider object
     * 
     */
    public Rider(int teamID, String name, int yearOfBirth){
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        riderID = ++numberOfRiders;
        riderList.add(this);
    }
    /**
     * 
     * @param name Accepts the name of the rider and set it on the Rider object
     */
    public void setName(String name){this.name = name;}
    
    /**
     * 
     * @param dob Accepts the year the rider was born and set it on the Rider object
     */
    public void setDOB(int dob){this.yearOfBirth = dob;}
    
    /**
     * 
     * @param teamID Accepts the team Id of the rider and set it on the rider object
     */
    public void setTeamID(int teamID){this.teamID = teamID;}

    /**
     * 
     * @return Returns the full name of the rider
     */
    public String getName(){return name;}
    
    /**
     * 
     * @return Returns the full name of the rider
     */
    public int getYearOfBirth(){return yearOfBirth;}
    
    /**
     * 
     * @return Returns unique Id of the rider
     */
    public int getRiderID(){return riderID;}
    
    /**
     * 
     * @return Returns the team Id the rider is associated with
     */
    public int getTeamID(){return teamID;}
    
    /**
     * 
     * @return Returns the Rider object as a formatted string
     */
    public String toString(){
        return
                "Rider[Name="+name+",DOB="+yearOfBirth+",riderID="+riderID+",teamId="+teamID+"]";
    }
    
}
