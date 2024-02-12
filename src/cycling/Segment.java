package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Used when a new segment is added to a stage
 * 
 * @author Jude Wallace
 * @author William See
 * @version 1.0
 */
public class Segment implements Serializable{
    
    /**
     * Contains all the Segment objects created
     */
    protected static ArrayList<Segment> segmentsList = new ArrayList<>();
    
    /**
     * The unique stage Id the segment belongs to
     */
    private int stageID;
    
    /**
     * The location of the segment within the race
     */
    private double location;
    
    /**
     * The type of segment it is
     */
    private SegmentType type;
    
    /**
     * The average gradient of the segment
     */
    private double averageGradient;
    
    /**
     * The total length of the segment
     */
    private double length;
    
    /**
     * The unique Id of the segment
     */
    private int segmentID;
    
    /**
     * Counter for the unique Id of the segment
     */
    protected static int numberOfSegments = 0;
    
    /**
     * Construct an instance of a segment with all the stageId, SegmentType and type of the segment only
     * This constructor is used for Sprint segments within the stage
     * 
     * @param stageId Accepts the unique stage Id the segment belongs to and sets it on the Segment object
     * @param location Accepts the location of the segment and sets it on the Segment object
     * @param type Accepts the type of segment it is and sets it on the Segment object
     */
    public Segment(int stageId, double location, SegmentType type){
        this.stageID = stageId;
        this.location = location;
        this.type = type;
        
        segmentID = ++numberOfSegments;
        segmentsList.add(this);
    }
    
    /**
     * Construct an instance of a segment with all the attributes 
     * This constructor is used for all other segments in the race
     * 
     * @param stageId Accepts the unique stage Id the segment belongs to and sets it on the Segment object
     * @param location Accepts the location of the segment and sets it on the Segment object
     * @param type Accepts the type of segment it is and sets it on the Segment object
     * @param averageGradient Accepts the average gradient of the segment and sets it on the Segment object
     * @param length Accepts the total length of the segment and sets it on the Segment object
     */
    public Segment(int stageId, double location, SegmentType type, double averageGradient,
			Double length){
        this.stageID = stageId;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
        
        segmentID = ++numberOfSegments;
        segmentsList.add(this);
    }
    /**
     * 
     * @param stageId Accepts the unique stage Id the segment belongs to and sets it on the Segment object
     */
    public void setStageId(int stageId){this.stageID = stageId;}
    
    /**
     * 
     * @param location Accepts the location of the segment and sets it on the Segment object
     */
    public void setLocation(double location){this.location = location;}
    
    /**
     * 
     * @param type Accepts the type the segment is and sets it on the Segment object
     */
    public void setType(SegmentType type){this.type = type;}
    
    /**
     * 
     * @param averageGradient Accepts the average gradient within the segment and sets it on the Segment object
     */
    public void setAverageGradient(double averageGradient){this.averageGradient = averageGradient;}
    
    /**
     * 
     * @param length Accepts the total length of the segment and sets it on the Segment object
     */
    public void setLength(double length){this.length = length;}
    
    /**
     * 
     * @return Returns the unique Id of the segment
     */
    public int getSegmentID(){return segmentID;}
    
    /**
     * 
     * @return Returns the type of segment it is
     */
    public SegmentType getSegmentType(){return type;}
    
    /**
     * 
     * @return Returns the unique stage Id the segment belongs to
     */
    public int getStageID(){return stageID;}
    
    /**
     * 
     * @return Returns a formatted string of the Segment object
     */
    public String toString(){
        return
                "Segment[segmentId= " +segmentID + " stageId= "+stageID+" segmentType= "+segmentID+"]";
    }
    
}
