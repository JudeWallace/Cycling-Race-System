package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Used when a new stage in a race is created
 * 
 * @author Jude Wallace
 * @author William See
 * @version 1.0
 */
public class Stage implements Serializable{
    
    /**
     * Contains all the Stage objects created
     */
    protected static ArrayList<Stage> stagesList = new ArrayList<>();
    
    /**
     * The unique Id of the race the stage belongs to
     */
    private int raceID;
    
    /**
     * The name of the stage
     */
    private String stageName;
    
    /**
     * The description of the stage
     */
    private String description;
    
    /**
     * The length of the stage in kilo meters (km)
     */
    private double length;
    
    /**
     * The time the stage starts 
     */
    private LocalDateTime startTime; 
    
    /**
     * The type of stage it is 
     */
    private StageType type;
    
    /**
     * The state the stage is set to
     */
    private String state;
    
    /**
     * The unique Id of the stage
     */
    private int stageID;
    
    /**
     * Counter for the unique stage Ids
     */
    protected static int numberOfStages = 0;
    
    /**
     * Construct an instance of a stage with all the attributes needed
     * 
     * @param raceId Accepts the unique Id of the race the stage is associated to and sets it on the Stage object
     * @param stageName Accepts the name of the stage and sets it on the Stage object
     * @param description Accepts the description about the stage and sets it on the Stage object
     * @param length Accepts the length of the stage and sets it on the Stage object
     * @param startTime Accepts the starting time of the stage and sets it on the Stage object
     * @param type Accepts the type of stage it is and sets it on the Stage object
     */
    public Stage(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type){
        this.raceID = raceId;
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.state = "waiting to be completed";
        
        stageID = ++numberOfStages;
        stagesList.add(this);
        
    }
    /**
     * 
     * @param raceId Accepts the race Id the stage belongs to and sets it on the Stage object
     */
    public void setRaceId(int raceId){this.raceID = raceId;}
    
    /**
     * 
     * @param name Accepts the name of the stage and sets it on the Stage object
     */
    public void setStageName(String name){this.stageName = name;}
    
    /**
     * 
     * @param description Accepts the description about the stage and sets it on the Stage object
     */
    public void setStageDescription(String description){this.description = description;}
    
    /**
     * 
     * @param length Accepts the total length of the stage and sets it on the Stage object
     */
    public void setLength(double length){this.length = length;}
    
    /**
     * 
     * @param startTime Accepts start time of the stage and sets it on the Stage object
     */
    public void setStartTime(LocalDateTime startTime){this.startTime = startTime;}
    
    /**
     * 
     * @param type Accepts the type the stage is  and sets it on the Stage object
     */
    public void setStageType(StageType type){this.type = type;}
   
    /**
     * Changes the state of the stage to "waiting for results" in the Stage object
     */
    public void setStageState(){
        this.state = "waiting for results";
    }
    
    /**
     * 
     * @param state Accepts the state the stage should be set to and sets it on the Stage object
     */
    public void setStageState(String state){
        this.state = state;
    }
    /**
     * 
     * @return Returns the unique race Id the stage belongs to
     */
    public int getRaceID(){return raceID;}
    
    /**
     * 
     * @return Returns the name of the stage
     */
    public String getStageName(){return stageName;}
    
    /**
     * 
     * @return Returns the description about the stage
     */
    public String getDescriptione(){return description;}
    
    /**
     * 
     * @return Returns the length of the stage
     */
    public double getStagelength(){return length;}
    
    /**
     * 
     * @return Returns the starting time of the stage
     */
    public LocalDateTime getStartTime(){return startTime;}
    
    /**
     * 
     * @return Returns the type of stage it is
     */
    public StageType getStageType(){return type;}
    
    /**
     * 
     * @return Returns the unique Id of the stage
     */
    public int getStageID(){return stageID;}
    
    /**
     * 
     * @return Returns the state of the stage
     */
    public String getStageState(){return state;}
    
    /**
     * 
     * @return Returns a formatted string of the Stage object
     */
    public String toString(){
        return
                "Stage[raceId"+raceID+",stageId="+stageID+",stageName="+stageName+
                ",description"+description+",length="+length+",startTime="+startTime+",type="+type+"]";
    }
}
