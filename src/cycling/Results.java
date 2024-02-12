package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Used when results are being set for each stage in a race
 * 
 * @author Jude Wallace
 * @author William See
 * @version 1.0
 */
public class Results implements Serializable{
    
    /**
     * Contains all the Results objects created
     */
    protected static ArrayList<Results> resultsList = new ArrayList<>();
    
    /**
     * The unique stage Id the results belong to
     */
    private int stageID;
    
    /**
     * The unique Id of the rider who's results they are
     */
    private int riderID;
    
    /**
     * The time in which the rider reached the start line, segment finish lines and the the finish line of the stage
     */
    private LocalTime[] stageTime;

    /**
     * Constructs an instance of a result with all the attributes
     * 
     * @param stageID Accepts the unique stage Id the results correspond to and sets it on the Results object
     * @param riderID Accepts the unique rider Id and sets it on the Results object
     * @param checkpoints Accepts checkpoints of times obtained in the stage and sets it on the Results object
     */
    public Results(int stageID, int riderID, LocalTime[] checkpoints){         
        this.stageID = stageID;
        this.riderID = riderID;
        this.stageTime = checkpoints;

        resultsList.add(this);  
    }
   
    /**
     * 
     * @param stageId Accepts the stage Id the results belong to and sets it on the Results object
     */
    public void setStageId(int stageId){this.stageID = stageId;}
    
    /**
     * 
     * @param riderId Accepts the rider Id the results belong to and sets it on the Results object
     */
    public void setRiderId(int riderId){this.riderID = riderId;}
    
    /**
     * 
     * @param stageTime Accepts the stage time results and sets it on the Results object
     */
    public void setStageTime(LocalTime[] stageTime){this.stageTime = stageTime;}
    
    /**
     * 
     * @return Returns the unique rider Id, which the results belong to
     */
    public int getRiderId(){return riderID;}
    
    /**
     * 
     * @return Returns the unique stage Id, for which is the stage where the results correspond to
     */
    public int getStageId(){return stageID;}
    
    /**
     * 
     * @return Returns the times taken to pass the start lines and finish lines within the stage
     */
    public LocalTime[] getStageTime(){return stageTime;}
    
    /**
     * 
     * @return Returns a formatted string of the Results object
     */
    public String toString(){
        return
                "[stageID="+stageID+" riderID="+riderID+" stageTime="+stageTime+"]";
    }
            
}
