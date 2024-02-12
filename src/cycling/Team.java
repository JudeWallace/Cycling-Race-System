package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Used when a new team is inputted into the system
 * 
 * @author Jude Wallace
 * @author William See
 * @version 1.0
 */
public class Team implements Serializable{
    
    /**
     * Contains all the Team objects created
     */
    protected static ArrayList<Team> teamList = new ArrayList<>();
             
    /**
     * The name of the team
     */
    private String name;
    
    /**
     * A description about/who the team are
     */
    private String description;
    
    /**
     * The unique Id given to the team
     */
    private int teamID;
    
    /**
     * The counter for the teams unique Ids
     */
    protected static int numberOfTeams = 0;
    
    /**
     * Construct an instance of a team with all the attributes needed
     * 
     * @param name Accepts the name of the team and sets it on the Team object
     * @param description Accepts a description about the team and sets it on the Team object
     */
    public Team(String name, String description){
        this.name = name;
        this.description = description;
        teamID = ++numberOfTeams;
        teamList.add(this); 
    }
    
    /**
     * 
     * @param name Accepts the name of the team and sets it on the Team object
     */
    public void setName(String name){this.name = name;}
    
    /**
     * 
     * @param description Accepts the description about the team and sets it on the Team object
     */
    public void setDescription(String description){this.description = description;}
    
    /**
     * 
     * @return Returns the name of the team
     */
    public String getName(){return name;}
    
    /**
     * 
     * @return Returns the description of the team
     */
    public String getDescription(){return description;}
    
    /**
     * 
     * @return Returns the unique Id of the team
     */
    public int getTeamID(){return teamID;}
    
    /**
     * 
     * @return Returns the Team object as a formatted string
     */
    public String toString(){
        return 
                "TeamID="+teamID+",Team="+name+",Description="+description;
    }
    
}

