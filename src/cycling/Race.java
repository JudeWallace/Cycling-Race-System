package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Used when a new race is inputted into the system
 * 
 * @author Jude Wallace
 * @author William See
 * @version 1.0
 */
public class Race implements Serializable{
    
    /**
     * Contains all the Race objects created
     */
    protected static ArrayList<Race> raceList = new ArrayList<>();
    
    /**
     * The name of the race
     */
    private String name;
    
    /**
     * The description about the race
     */
    private String description;
    
    /**
     * The unique Id of the race
     */
    private int raceID;
    
    /**
     * Counter for the unique race IDS
     */
    protected static int numberOfRaces = 0;
    
    /**
     * Construct an instance of a race with all the attributes needed
     * 
     * @param name Accepts the name of the race and sets it on the Race object
     * @param description Accepts the description of the race and sets it on the Race object
     */
    public Race(String name, String description){
        this.name = name;
        this.description = description;
        raceID = ++numberOfRaces;
        raceList.add(this);
              
    }
    
    /**
     * 
     * @param name Accepts the name of the Race and sets it on the Race object
     */
    public void setName(String name){this.name = name;}
    
    /**
     * 
     * @param description Accepts the description about the Race and sets it on the Race object
     */
    public void setDescription(String description){this.description = description;}
    
    /**
     * 
     * @return Returns the name of the race
     */
    public String getName(){return name;}
    
    /**
     * 
     * @return Returns the description of the race
     */
    public String getDescription(){return description;}
    
    /**
     * 
     * @return Returns the unique race Id
     */
    public int getRaceID(){return raceID;}
    
    /**
     * 
     * @return Returns the Race object in a formatted string
     */
    public String toString(){
        return
                "Race[name="+name+",id="+raceID+",description="+description+"]";
    }

}
