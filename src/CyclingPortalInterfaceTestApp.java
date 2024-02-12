import cycling.CyclingPortal;
import cycling.CyclingPortalInterface;
import cycling.DuplicatedResultException;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidCheckpointsException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.SegmentType;
import cycling.StageType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;



/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args){
		System.out.println("The system compiled and started the execution...");

		CyclingPortalInterface portal = new CyclingPortal();
          

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";
                
           
            try {
                System.out.println("Races inputted= " +  Arrays.toString(portal.getRaceIds()));
                String x = "Hello ";
                System.out.println(x.contains(" "));
                // Create test teams
                portal.createTeam("Test","This is a team to test functionality");
                portal.createTeam("Jude", "Test for removing teams");
                // Create test riders
                portal.createRider(1, "Jude", 2003);
                portal.createRider(1, "Stuart", 1991);
                portal.createRider(1, "Gary", 2015);
                portal.createRider(1, "Test", 2015);
                portal.createRider(1, "Test2", 2015);
                portal.createRider(1, "Test3", 2015);
                
               
                // Print all teams Id's
                System.out.println("TeamId's"+Arrays.toString(portal.getTeams()));
                // Print all riders Id's belonging to team Id 1
                System.out.println("Riders in team 1"+Arrays.toString(portal.getTeamRiders(1)));
                // Remove rider
    
                // Check rider was removed
                System.out.println("Riders in team 1="+Arrays.toString(portal.getTeamRiders(1)));
                
                //portal.removeTeam(1);
                
                
                System.out.println("TeamId's"+Arrays.toString(portal.getTeams()));
               
                // Create Races
                portal.createRace("Race1","This is a test race");
                portal.createRace("Race2","This is a test race 2");
                // Get Id's of races
                System.out.println("RaceId's"+Arrays.toString(portal.getRaceIds()));
                // Add stage's to race
                portal.addStageToRace(1, "StageTest1", "Testing if stage is added", 8.6, LocalDateTime.MIN, StageType.FLAT);
                portal.addStageToRace(1, "StageTest2", "Testing if stage2 added", 11.4, LocalDateTime.MIN, StageType.MEDIUM_MOUNTAIN);
                portal.addStageToRace(2, "KOMPoints", "Testing if stage2 added", 11.4, LocalDateTime.MIN, StageType.MEDIUM_MOUNTAIN);
                portal.addStageToRace(2, "KOMPointStage2", "Testing if stage2 added", 5.4, LocalDateTime.MIN, StageType.MEDIUM_MOUNTAIN);
                
                // Get Race full details
                System.out.println(portal.viewRaceDetails(1));
                // Get all stage Id's of the race
                System.out.println("Race StagesId in race 1 "+Arrays.toString(portal.getRaceStages(1)));
                // Remove stage from race
                //portal.removeStageById(2);
                System.out.println("Race StagesId in race 2"+Arrays.toString(portal.getRaceStages(2)));
                // Add a segment onto a stage
                portal.addCategorizedClimbToStage(1, 3.0, SegmentType.SPRINT, 3.6, 1.0);
                // Add a climb to Race 2
                portal.addCategorizedClimbToStage(3, 2.0, SegmentType.C4, Double.MAX_VALUE, 3.0);
                portal.addCategorizedClimbToStage(3, 7.0, SegmentType.HC, Double.MAX_VALUE, 3.0);
                portal.addCategorizedClimbToStage(4, 5.0, SegmentType.C1, Double.MAX_VALUE, 3.0);
                // Set stage state to waiting for results
                portal.concludeStagePreparation(1);
                portal.concludeStagePreparation(2);
                // Conclude Race 2
                portal.concludeStagePreparation(3);
                portal.concludeStagePreparation(4);
                
                
                
                System.out.println(portal.viewRaceDetails(2));
                
                LocalTime[] z1 = { LocalTime.of(0,0,0), LocalTime.of(0,15,20), LocalTime.of(0,30,0)}; 
                LocalTime[] z2 = { LocalTime.of(0,0,0), LocalTime.of(0,13,10), LocalTime.of(0,30,1)};
                LocalTime[] z3 = { LocalTime.of(0,0,0), LocalTime.of(0,40,20), LocalTime.of(0,50,20)};
                LocalTime[] z4 = { LocalTime.of(0,0,0), LocalTime.of(0,45,20), LocalTime.of(0,55,21)};
                LocalTime[] z5 = { LocalTime.of(0,0,0), LocalTime.of(0,42,20), LocalTime.of(0,55,21,50000)};
                
                LocalTime[] s1 = { LocalTime.of(0,0,0), LocalTime.of(0,40,20)}; 
                LocalTime[] s2 = { LocalTime.of(0,0,0), LocalTime.of(0,40,20,500000000)};
                LocalTime[] s3 = { LocalTime.of(0,0,0), LocalTime.of(0,50,20)};
                LocalTime[] s4 = { LocalTime.of(0,0,0), LocalTime.of(0,55,21)};
                LocalTime[] s5 = { LocalTime.of(0,0,0), LocalTime.of(1,57,26)};
                
                LocalTime[] g1 = { LocalTime.of(0,0,0), LocalTime.of(0,15,20), LocalTime.of(0,30,0), LocalTime.of(0,35,0)}; 
                LocalTime[] g2 = { LocalTime.of(0,0,0), LocalTime.of(0,13,10), LocalTime.of(0,30,1), LocalTime.of(0,46,0)};
                LocalTime[] g3 = { LocalTime.of(0,0,0), LocalTime.of(0,40,20), LocalTime.of(0,50,20), LocalTime.of(0,58,0)};
                LocalTime[] g4 = { LocalTime.of(0,0,0), LocalTime.of(0,45,20), LocalTime.of(0,55,21), LocalTime.of(1,10,0)};
                LocalTime[] g5 = { LocalTime.of(0,0,0), LocalTime.of(0,42,20), LocalTime.of(0,55,21,50000), LocalTime.of(1,30,0)};
                
                portal.registerRiderResultsInStage(1, 1, z1);
                 portal.registerRiderResultsInStage(1, 2, z2);
                portal.registerRiderResultsInStage(1, 3, z3);
                portal.registerRiderResultsInStage(1, 4, z4);
                portal.registerRiderResultsInStage(1, 5, z5);
                
                portal.registerRiderResultsInStage(2, 1, s5);
                portal.registerRiderResultsInStage(2, 2, s4);
                portal.registerRiderResultsInStage(2, 3, s3);
                portal.registerRiderResultsInStage(2, 4, s2);
                portal.registerRiderResultsInStage(2, 5, s1);
               
                portal.registerRiderResultsInStage(3, 5, g5);
                portal.registerRiderResultsInStage(3, 2,g4);
                portal.registerRiderResultsInStage(3, 3, g3);
                portal.registerRiderResultsInStage(3, 4, g2);
                portal.registerRiderResultsInStage(3, 1, g1);
                
                portal.registerRiderResultsInStage(4, 5, z5);
                portal.registerRiderResultsInStage(4, 4,z4);
                portal.registerRiderResultsInStage(4, 3, z3);
                portal.registerRiderResultsInStage(4, 1, z2);
                portal.registerRiderResultsInStage(4, 2, z1);
                
                
                
                System.out.println("Rider 1 stage checkpoint times"+Arrays.toString(portal.getRiderResultsInStage(1,1)));
                System.out.println("RidersRanInStage 2"+ Arrays.toString(portal.getRidersRankInStage(1)));
                System.out.println("Riders in points in stage 1"+ Arrays.toString(portal.getRidersMountainPointClassificationRank(1)));
                System.out.println("Race StagesId in race 1 "+Arrays.toString(portal.getRaceStages(1)));
                
               
                //portal.getRankedAdjustedElapsedTimesInStage(1);
                
                //portal.getRiderAdjustedElapsedTimeInStage(1, 2);
//                System.out.println("Riders 1 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(1, 1));
//                System.out.println("Riders 2 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(1, 2));
//                System.out.println("Rider 3 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(1, 3));
//                System.out.println("Rider 4 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(1, 4));
//                System.out.println("Rider 5 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(1, 5));
//                System.out.println();
//                System.out.println("Riders 1 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(2, 1));
//                System.out.println("Riders 2 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(2, 2));
//                System.out.println("Rider 3 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(2, 3));
//                System.out.println("Rider 4 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(2, 4));
//                System.out.println("Rider 5 edited elapsedTime "+ portal.getRiderAdjustedElapsedTimeInStage(2, 5));
//                //portal.getStageSegments(1);
//                System.out.println();
//                System.out.println("Stage 1 adjusted Times in race 1 "+Arrays.toString(portal.getRankedAdjustedElapsedTimesInStage(1)));
//                System.out.println("Stage 2 adjusted Times in race 1 "+Arrays.toString(portal.getRankedAdjustedElapsedTimesInStage(2)));
//                System.out.println();
//                System.out.println("Riders Times in order in Race 1 "+Arrays.toString(portal.getGeneralClassificationTimesInRace(1)));
//                System.out.println("Riders General Classification Rank"+Arrays.toString(portal.getRidersGeneralClassificationRank(1)));
//                
//                //portal.getRidersPointsInStage(1);
//                System.out.println("Points in Order of ^^ "+Arrays.toString(portal.getRidersPointsInRace(1)));
//                portal.getRidersPointClassificationRank(1);
//                System.out.println("Riders in Order of Points  "+Arrays.toString(portal.getRidersPointClassificationRank(1)));
//                
//                //portal.getRidersMountainPointsInStage(3);
                  System.out.println("RidersRanInStage 2"+ Arrays.toString(portal.getRidersRankInStage(3)));
                  System.out.println("Riders in Order of KOM points in Race 2 stage 1  "+Arrays.toString(portal.getRidersMountainPointsInStage(3)));
                  System.out.println("Riders in Order of KOM points in Race 2  "+Arrays.toString(portal.getRidersMountainPointsInRace(2)));
//                System.out.println("Riders in Order of KOM points in Race 2 stage 2  "+Arrays.toString(portal.getRidersMountainPointsInStage(4)));
//                //System.out.println("Riders in Order of Points  "+Arrays.toString(portal.getRidersGeneralClassificationRank(2)));
//                portal.getRidersMountainPointsInRace(2);
//                System.out.println("Riders in Order of elapsedTime  "+Arrays.toString(portal.getRidersGeneralClassificationRank(2)));
//                System.out.println("RidersId in order of KOM points = " + Arrays.toString(portal.getRidersMountainPointClassificationRank(2)));
                
                // Testing
                
                portal.createRace("Race3","This is a test race");
                System.out.println("Races inputted= " +  Arrays.toString(portal.getRaceIds()));
                //portal.createRace("Race3","This is a test race");
                System.out.println(portal.viewRaceDetails(2));
                portal.removeRaceById(3);  
                System.out.println("Races inputted= " +  Arrays.toString(portal.getRaceIds()));
                //System.out.println("Races 2 Stages= " +  Arrays.toString(portal.getRaceStages(2)));
                //portal.removeStageById(3);
                //System.out.println("Races 2 Stages= " +  Arrays.toString(portal.getRaceStages(2)));
                //portal.addCategorizedClimbToStage(4, 20.0, SegmentType.C1, Double.MAX_VALUE, 3.0);
                //System.out.println("Segments in Race 1 Stages 1 = " +  Arrays.toString(portal.getStageSegments(1)));
                //portal.removeSegment(1);
                //System.out.println("Segments in Race 1 Stages 1 = " +  Arrays.toString(portal.getStageSegments(1)));
                //portal.removeRider(3);
                //System.out.println("Rider 1 resutlts in stage 1= " +  Arrays.toString(portal.getRidersGeneralClassificationRank(1)));
                
                System.out.println("Races inputted= " +  Arrays.toString(portal.getRaceIds()));
                portal.removeRaceById(1);
                System.out.println("Races inputted= " +  Arrays.toString(portal.getRaceIds()));
                System.out.println("Riders in Order of KOM points in Race 2  "+Arrays.toString(portal.getRidersMountainPointsInRace(1)));
                //System.out.println("Race StagesId in race 1 "+Arrays.toString(portal.getRaceStages(1)));
                //System.out.println("RaceIds = " + Arrays.toString(portal.getRaceIds()));
                
                //portal.eraseCyclingPortal();
                //System.out.println("Removed Race = " + Arrays.toString(portal.getRaceIds()));
                //System.out.println("Removed Team = " + Arrays.toString(portal.getTeams()));
                //System.out.println("Removed Stage = " + Arrays.toString(portal.getStageNames()));
                //System.out.println("TeamId's"+Arrays.toString(portal.getTeams()));
                //System.out.println("RaceId's"+Arrays.toString(portal.getRaceIds()));
                //portal.saveCyclingPortal("HelloChickenNugget.txt");
                
               
            } catch (IllegalNameException ex) {
                System.out.println(ex);
            } catch (InvalidNameException ex) {
                System.out.println(ex);
            } catch (IDNotRecognisedException ex) {
                System.out.println(ex);
            } catch (InvalidLengthException ex) {
               System.out.println(ex);
            } catch (InvalidLocationException ex) {
                System.out.println(ex);
            } catch (InvalidStageStateException ex) {
               System.out.println(ex);
            } catch (InvalidStageTypeException ex) {
                System.out.println(ex);
            } catch (DuplicatedResultException ex) {
                System.out.println(ex);
            } catch (InvalidCheckpointsException ex) {
                System.out.println(ex);
            }    
	}

}
