package cycling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * CyclingPortal is fully compiling, with fully functioning implementer
 * of the CyclingPortalInterface interface.
 * 
 * @author Jude Wallace, William See
 * @version 1.0
 *
 */

public class CyclingPortal implements CyclingPortalInterface {
        /**
         * Checks if the rider has had results already added to a stage
         * 
         * @param stageId The ID of the stage being queried
         * @param riderId The ID of the rider being queried
         * @return Returns true if the rider has results already in the stage. False if no results have been added.
         */
        public boolean checkNoDuplicateResults(int stageId, int riderId){
            for(Results result: Results.resultsList){
                if(result.getRiderId() == riderId && result.getStageId() == stageId)
                    return true;
            }
            return false;
        }
        /**
         * Get the stage Ids within the system
         * 
         * @return Returns an array of the IDs. If no stages are in the system returns and empty array.
         */
        public int[] getStageIds(){
            ArrayList<Integer> currStageIds = new ArrayList<>();
            for(Stage stage: Stage.stagesList){
                currStageIds.add(stage.getStageID());
            }
            int[] allStagesId = currStageIds.stream().mapToInt(i -> i).toArray();
            
            if(allStagesId != null){
                return allStagesId;
            }else{
                int[] noStages = {};
                return noStages;
            }
            
        }
        /**
         * Get an array of all the riders within the system
         * 
         * @return Returns an array of all the Ids of the riders. If no riders are found return an empty array.
         */
        public int[] getRidersIds(){
            ArrayList<Integer> currRiderIds = new ArrayList<>();
            for(Rider rider: Rider.riderList){
                currRiderIds.add(rider.getRiderID());
            }
            int[] allRiderIds = currRiderIds.stream().mapToInt(i -> i).toArray();
            
            if(allRiderIds != null){
                return allRiderIds;
            }else{
                int[] noStages = {};
                return noStages;
            }
            
        }
        
        /**
         * Check what state the stage object has been set to
         * 
         * @param stageId The Id of the stage being queried 
         * @return Returns the state of the stage object. Returns an empty string if no state has been set for the stage
         */
        public String getStageState(int stageId){
            String state = "";
            for(Stage stage: Stage.stagesList){
                if(stage.getStageID() == stageId){
                    state = stage.getStageState();
                    return state;
                }
            }
            return state;
        }
        
        /**
         * Get all the stage names within the system
         * 
         * @return Returns an array containing all the names of the current stages
         */
        public String[] getStageNames(){
            ArrayList<String> stageNames = new ArrayList<>();
            for (Stage stage: Stage.stagesList){
                stageNames.add(stage.getStageName());
            
            }
            String[] array = stageNames.toArray(new String[stageNames.size()]);
            return array;
        }
        
        /**
         * Gets the stage type 
         * 
         * @param stageId The ID of the stage being queried 
         * @return Returns the stage type of the queried stage Id. Returns null if cannot find the stage
         */
        public StageType getStageType(int stageId){
            StageType stageType;
             for (Stage stage: Stage.stagesList){
                if (stage.getStageID() == stageId){
                    stageType = stage.getStageType();
                    return stageType;
                }
             }
            return null;
        }
        
        /**
         * Gets all the riders Ids participating in a specified race
         * 
         * @param raceId The Id of the race being queried
         * @return Returns an array of riders Ids within the race. Returns null if no race exsitis
         */
        public int[] getRidersInRace(int raceId){
            try {
                ArrayList<Integer> ridersInRace = new ArrayList<>();
                int[] stageId = getRaceStages(raceId);
                // loop through stages and get ridersIds
                for(Results result: Results.resultsList){
                    if(IntStream.of(stageId).anyMatch(x -> x == result.getStageId())){
                        if(ridersInRace.contains(result.getRiderId())){
                            continue;
                        }else{
                            ridersInRace.add(result.getRiderId());
                        }
                    }
                }     
                int[] array = ridersInRace.stream().mapToInt(i -> i).toArray();
                Arrays.sort(array);
                
                return array;
                
            } catch (IDNotRecognisedException ex) {
                System.out.println(ex);
            }
              return null;
        }
        
	@Override
	public int[] getRaceIds() {
            ArrayList<Integer> currRaces = new ArrayList<>();
            for(Race race: Race.raceList){
                currRaces.add(race.getRaceID());
            }
            int[] allRaceID = currRaces.stream().mapToInt(i -> i).toArray();
            
            // If races have been found return int[] with them in
            if(allRaceID != null){
                return allRaceID;
            // No races found return empty int[] array
            }else{
                int[] noRaces = {};
                return noRaces;
                        
            }
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
            if(name == null || name.length() > 30 || name.isEmpty() || name.contains(" ")){
                    throw new InvalidNameException("Race name is not in a valid format");
            }else{
                ArrayList<String> currRaces = new ArrayList<>();
                for(Race race: Race.raceList){
                   currRaces.add(race.getName());
                }
                // If race with the name already exsits throw exception
                if(currRaces.contains(name)){
                    throw new IllegalNameException("A Race with this name already exits");
                }else{
                   Race newRace = new Race(name,description);
                   // return races unqiue int Id
                   return newRace.getRaceID();
                }
            }
           
        }  
        
	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
            int raceID = 0;
            String raceName = "";
            String raceDescription = "";
            int numberOfStages = 0;
            double sumLengthOfStages = 0.0;
            // Check that race has been created
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No race with the given id exists");
            }
            // Get the race details from class using the class get methods
            for(Race race: Race.raceList){
                if(race.getRaceID() == raceId){
                    raceID = raceId;
                    raceName = race.getName();
                    raceDescription = race.getDescription();
                }
            }
            // Find all stages in race and add there length to get race length
            for(Stage stage:Stage.stagesList){
                if(stage.getRaceID() == raceId){
                    sumLengthOfStages = Double.sum(sumLengthOfStages, stage.getStagelength());//sumLengthOfStages + stage.getStagelength();
                    
                    numberOfStages++;
                }
            }
            // Return all values calculated and fetched about the race
            return 
                    raceName+" [RaceId= "+raceID+", RaceDescription= "+raceDescription+
                    ", numberOfRaceStages= "+numberOfStages+", RaceTotalLength= "+sumLengthOfStages+"km]";
        }

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                    throw new IDNotRecognisedException("No race with the given id exists");
            }
            // Remove all stages connected to the Race in stage + remove Segments + remove results
            int[] stagesInRace = getRaceStages(raceId);
            for(int stageId: stagesInRace){
                removeStageById(stageId);
            }

            int i = 0;
            // Find race object in array
            for(Race race: Race.raceList){
                if(race.getRaceID() == raceId){
                    // Remove the race object from the array
                    Race.raceList.remove(i);
                    break;
                    
                }
                i++;
            }
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
            int numberOfStages = 0;
            // Check there is a race with the given Ids
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No race with the given id exists");
            }else{
                // Loop through all the stages been set
                for(Stage stage: Stage.stagesList){
                    // If stage has a raceId of the param incremenet numberOfStages counter
                    if(stage.getRaceID() == raceId){
                        numberOfStages += 1;
                    }else{
                        continue;
                    }
                }
                // Return counter value
                return numberOfStages;
            }
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No race with the given id exists");
            }else if(Arrays.asList(getStageNames()).contains(stageName)){
                throw new IllegalNameException("Stage name has already been used for another stage");
            }else if(stageName == null || stageName.length() > 30 || stageName.isEmpty()){
                throw new InvalidNameException("Stage name is not of a valid format");
            }else if(length < 5.0){
                throw new InvalidLengthException("Stage lenght is too small");
            }else{
                // If stage follow all needed conventions, create the stage
                Stage newStage = new Stage(raceId, stageName, description,length, startTime, type);
                // Return Id of newly create stage
                return newStage.getStageID();
            }
               
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
            ArrayList<Integer> raceStages = new ArrayList<>();
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No race with the given id exists");
            }
            // Find all stages with race Id given in para
            for(Stage stage: Stage.stagesList){
                if(stage.getRaceID() == raceId){
                    raceStages.add(stage.getStageID());
                }
            }
            int[] raceStagesArray = raceStages.stream().mapToInt(i -> i).toArray();
            // Return stages in order 
            return raceStagesArray;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
            double stageLength = 0.0;
            // Find stage given in param
            for(Stage stage: Stage.stagesList){
                if(stage.getStageID() == stageId){
                    // Increment stageLength by stages length
                    stageLength = stage.getStagelength();
                    return stageLength;
                }
            }
            // If no stage was found with the Id from param throw excpetion
            throw new IDNotRecognisedException("No race with the given id exists");
                
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage has the Id given");
            }
            // Remove segments associated with the stage
            int[] segmentsInStage = getStageSegments(stageId);
            
            // Remove stage specified by param
            // Set counter for position in stage class arrayList
            int i = 0;
            for(Stage stage: Stage.stagesList){
                if(stage.getStageID() == stageId){
                    // Remove stage if matches param Id
                    Stage.stagesList.remove(i);
                    return;
                }
                i++;
            }
            int k = 0;
            for(int segment: segmentsInStage){
                for(Segment segmentArray: Segment.segmentsList){
                    if(segmentArray.getSegmentID() == segment){
                        Segment.segmentsList.remove(k);
                    }
                }
                k++;
            }
            //Remove results connected to stage
            int j =0;
            for(Results result: Results.resultsList){
                if(result.getStageId() == stageId){
                    Results.resultsList.remove(j);     
                }
                j++;
            }
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage with the given Id has been set");
            }else if(location > getStageLength(stageId)){
                throw new InvalidLocationException("Segmet length Id out of bounds for the stage");
            }else if(getStageState(stageId).equals("waiting for results")){
                throw new InvalidStageStateException("Stage is waiting for results so no more stages can be added");
            }else if(getStageType(stageId) == StageType.TT){
                throw new InvalidStageTypeException("Stage is set to time trial so cannot add a Catogrized climb to stage");
            }else{
                Segment newClimbToStage = new Segment(stageId, location, type, averageGradient, length);
                // Return Id of newly created Climb stage
                return newClimbToStage.getSegmentID();

            }
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage within the given Id has been set");
            }else if(location > getStageLength(stageId)){
                throw new InvalidLocationException("Segmet length Id out of bounds for the stage");
            }else if(getStageState(stageId).equals("waiting for results")){
                throw new InvalidStageStateException("Stage is waiting for resuts so no more stages can be added");
            }else if(getStageType(stageId) == StageType.TT){
                throw new InvalidStageTypeException("Stage type is a time trial so cannot add Intermidate sprint");
            }else{
                Segment newIntermediateSprintToStage = new Segment(stageId, location, SegmentType.SPRINT);
                // Return Id of newly created Sprint Stage
                return newIntermediateSprintToStage.getSegmentID();

            }
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
            // Counter for position in segment object arrayList
            int i = 0;
            // Loop through segments
            for(Segment segment: Segment.segmentsList){
                if(segment.getSegmentID() == segmentId){
                    int stageId = segment.getStageID();
                    // Loop through stages to check exceptions
                    for(Stage stage: Stage.stagesList){
                        if(stage.getStageID() == stageId){
                            // Check if stage has been set to waiting for results
                            if(stage.getStageState().equals("waiting for results")){
                                // Stage can't be removed if it has been set to waiting for results, hence throw error
                                throw new InvalidStageStateException("Stage is waiting for results so segment cannot be removed");
                            }else{
                                // If no exception is thrown, remove the segment
                                segment.segmentsList.remove(i);
                                return;
                            }
                        }
                    }
                }
                i++;
            }
            // If no segment was found with the Id from param throw exception
            throw new IDNotRecognisedException("No segments exists with the specified id");
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage within the given Id has been set");
            }else if(getStageState(stageId).equals("waiting for reults")){
                throw new InvalidStageStateException("Stage state is already set to waiting for results");
            }else{
                // Loop through stages object arrayList
                for(Stage stage: Stage.stagesList){
                    if(stage.getStageID() == stageId){
                        // Using class set method set state to waiting for results
                        stage.setStageState();
                        break;
                    }      
                }
            }   
	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
            if (IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage has the given ID");
            }
            ArrayList<Integer> stageSegments = new ArrayList<>();
            for(Segment segment: Segment.segmentsList){
                if(segment.getStageID() == stageId){
                    // If segment is in the stage, add to arrayList
                    stageSegments.add(segment.getSegmentID());
                }
            }
            // Convert arrayList of all segments in stage to an int[]
            int[] segmentsInStage = stageSegments.stream().mapToInt(i -> i).toArray();

            return segmentsInStage;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
            if(name == null || name.length() > 30 || name.isEmpty()){
                throw new InvalidNameException("Team name is not in a valid format");
            }
            else{
                ArrayList<String> teams = new ArrayList<>();
                for(Team team: Team.teamList){
                    teams.add(team.getName());
                }
                // Check no team has the name given in param
                if(teams.contains(name)){
                    throw new IllegalNameException("A team with this name already exits");
                }
                else{
                    // Create the team
                    Team a = new Team(name, description);
                    // Return the teams unqiue Id
                    return a.getTeamID();
                }        
            }
        }

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException { 
            // Counter for position in team object arrayList
            int i = 0;
            for(Team team: Team.teamList){
                if(team.getTeamID() == teamId){
                    // Get riders in Team
                    int[] teamRiders = getTeamRiders(teamId);
                    for(int rider: teamRiders){
                        // Remove the riders
                        removeRider(rider);
                    }
                    // If teamId matches remove the team
                    Team.teamList.remove(i);
                    return;
                }
                i++;
            }
            // Throw exception if no team has been assigned the given team param Id
            throw new IDNotRecognisedException("No team exists with the specified id");
        }

	@Override
	public int[] getTeams() {
            ArrayList<Integer> teams = new ArrayList<>();
            for(Team team: Team.teamList){
                // Add teams unqiue Id to arrayList
                teams.add(team.getTeamID());
            } 
            
            int[] arr = teams.stream().mapToInt(i -> i).toArray();
            // Check array of Teams isn't null
            if(arr != null){
                return arr;
            }else{
                // Return empty array
                int[] noTeams = {};
                return noTeams;
            }
        }

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
            if(IntStream.of(getTeams()).anyMatch(x -> x == teamId) == false){
                throw new IDNotRecognisedException("No Teams are resgistered to the given id");
            }else{
                ArrayList<Integer> teamRiders = new ArrayList<>();
                for(Rider rider: Rider.riderList){
                    // If rider has the given team Id in param, add to arrayList
                    if(rider.getTeamID() == teamId){
                        teamRiders.add(rider.getRiderID());
                    } 
                }

                int[] arr = teamRiders.stream().mapToInt(i -> i).toArray();
                // Return all riders unique Ids in the team
                return arr;
            }
        }

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
            if(name.isEmpty() || yearOfBirth < 1990){
                throw new IllegalArgumentException("Rider creation arguments aren't valid");
            }else{
                // Check theres a valid team for the rider
                if(IntStream.of(getTeams()).anyMatch(x -> x == teamID) == false){
                    throw new IDNotRecognisedException("No team in the system with the specified ID, when adding a new rider");
                }else{
                    // If no exceptions thrown create the rider 
                    Rider newRider = new Rider(teamID,name,yearOfBirth);
                    // Return the unique Id of the rider
                    return newRider.getRiderID();
                
                }
            }
        }
        
	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
            if(IntStream.of(getRidersIds()).anyMatch(x -> x == riderId) == false){
                throw new IDNotRecognisedException("No rider with the given Id");
            }
            int i = 0;
            for(Rider rider: Rider.riderList){
                // If riderId in param matches, remove the rider from the object arrayList
                if(rider.getRiderID() == riderId){
                    Rider.riderList.remove(i);
                    break;
                }
                i++;
            }
            // Remove any results the rider has been given in the system
            int j = 0;
            for(Results result: Results.resultsList){
                if(result.getRiderId() == riderId){
                    Results.resultsList.remove(j);
                    return;
                }
                j++;
            }        
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false || IntStream.of(getRidersIds()).anyMatch(x -> x == riderId) == false){
                throw new IDNotRecognisedException("No rider or stage as the given Id");
            }else if(checkNoDuplicateResults(stageId,riderId)){
                throw new DuplicatedResultException("Rider has a result for this stage already");
            }else if(getStageSegments(stageId).length != (checkpoints.length)-2){
                throw new InvalidCheckpointsException("Amount of checkpoints times doesnt match the amount of segments in the stage");
            }else if(!getStageState(stageId).equals("waiting for results")){
                throw new InvalidStageStateException("Stage isn't waiting for results");
            }else{
                // Add riders Results to the system
                Results addResults = new Results(stageId, riderId, checkpoints);
                
            }
        }

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false || IntStream.of(getRidersIds()).anyMatch(x -> x == riderId) == false){
                throw new IDNotRecognisedException("No rider or stage has the given Id");
            }else {
                ArrayList<Integer> currRidersInStage = new ArrayList<>();
                LocalTime[] checkpoints = {};
                LocalTime[] allTimesOfStage;

                for(Results results: Results.resultsList){
                    if(results.getRiderId() == riderId && results.getStageId() == stageId){
                        checkpoints = results.getStageTime();
                        // Get the start line time and the finish line time of the stage
                        LocalTime startLine = checkpoints[0];
                        LocalTime finsihLine = checkpoints[checkpoints.length -1];
                        // Convert start line to seconds
                        long stageTimes = startLine.toSecondOfDay();
                        // Get the elapsed Time of the stage 
                        LocalTime elapsedTime = finsihLine.minus(stageTimes, ChronoUnit.SECONDS);

                        allTimesOfStage = Arrays.copyOf(checkpoints, checkpoints.length + 1);
                        allTimesOfStage[allTimesOfStage.length -1] = elapsedTime;

                        return allTimesOfStage;
                    }
                    currRidersInStage.add(results.getRiderId());             
                }
                if(currRidersInStage.contains(riderId) == false){
                    return checkpoints;

                }
            }
            LocalTime[] arr = {};
            return arr;
                     
        }


        @Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false || IntStream.of(getRidersIds()).anyMatch(x -> x == riderId) == false){
                throw new IDNotRecognisedException("No rider or stage has the given Id");
            }
            // Get riders in stage
            int[] ridersInStage = getRidersRankInStage(stageId);

            LocalTime oneSecond = LocalTime.of(0, 0,1);
            // If the rider finished first in the race there time can't be changed Or if stageType is a Time Trial
            if(riderId == ridersInStage[0] || getStageType(stageId) == StageType.TT){
                for(Results results: Results.resultsList){
                    if(results.getRiderId() == riderId && results.getStageId() == stageId){
                        LocalTime[] elapsedTime = results.getStageTime();
                        // Return the time the rider got
                        return elapsedTime[elapsedTime.length -1];

                    } 
                }
            // If no results have been registered to the rider
            }else if(IntStream.of(ridersInStage).anyMatch(x -> x == riderId) == false){
                return null;
                
            }else{
                 ArrayList<LocalTime> ridersElapsedTimes= new ArrayList<>();
                 // Loop through riders times and get there elapsed stage time add to array in order
                 for(int i = 0; i < ridersInStage.length; i++){
                     LocalTime[] time = getRiderResultsInStage(stageId, ridersInStage[i]);
                     LocalTime stageElapsedTime = time[time.length -1];
                     ridersElapsedTimes.add(stageElapsedTime);
                 }
                 // Get the position of the rider in the getRidersRankInStage order
                 int pos = 0;
                 for(int counter = 0; counter < ridersInStage.length; counter++){
                     if (ridersInStage[counter] == riderId){
                         pos = counter;
                         break;
                    }
                 }
                 LocalTime initalTime = null;
                 LocalTime tempTime = null;
                 
                 for(int x = pos; x >= 0; x--){
                     // If rider is at postion 1 in array only one comparison can be performed
                    if(x == 1){
                        initalTime = ridersElapsedTimes.get(x);
                        tempTime = ridersElapsedTimes.get(x - 1);
                        // Compare the riders time to the one infront
                        long tempTimetoSeconds = tempTime.toSecondOfDay();
                        LocalTime compareDifference = initalTime.minus(tempTimetoSeconds, ChronoUnit.SECONDS);
                        int value = compareDifference.compareTo(oneSecond);
                        
                        if(value == -1 || value == 0){
                          return tempTime;
                          
                        }else if(value == 1){
                           return initalTime;
                        }
                    }
                    // Get the times of the rider and the rider infront
                    initalTime = ridersElapsedTimes.get(x);
                    tempTime = ridersElapsedTimes.get(x - 1);
                    // compare times
                    long tempTimetoSeconds = tempTime.toSecondOfDay();
                    LocalTime compareDifference = initalTime.minus(tempTimetoSeconds, ChronoUnit.SECONDS);
                    int value = compareDifference.compareTo(oneSecond);
                    // If they are within 1 second, compare the rider infront again
                    if(value == -1 || value == 0){
                        continue;
                    // If the aren't within 1 second return the correct elapsed time    
                    }else if(value == 1){
                        return initalTime;
                    } 
                 }
             } 
        return null;
        }
        
	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false || IntStream.of(getRidersIds()).anyMatch(x -> x == riderId) == false){
                throw new IDNotRecognisedException("No rider or stage has the given Id");
            }
            // Position of rider in results object arrayList
            int i = 0;
            for(Results result: Results.resultsList){
                if(result.getStageId() == stageId && result.getRiderId() == riderId){
                    // Remove the riders results
                    Results.resultsList.remove(i);
                    break;
                }
                i++;
            }
        }

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage has the given Id");
            }else {
                // ArrayList of all the riders with Results in the stage
                ArrayList<Integer> ridersIdInStage = new ArrayList<>();
                for (Results results: Results.resultsList){
                    if (results.getStageId() == stageId){
                        ridersIdInStage.add(results.getRiderId());
                    }
                }
                // ArrayList for all the riders elapsedTimes in the staeg
                ArrayList<LocalTime> riderTimes = new ArrayList<>();
                for (int counter = 0; counter < ridersIdInStage.size(); counter++){
                    LocalTime[] elapsedTime = getRiderResultsInStage(stageId, ridersIdInStage.get(counter));
                    riderTimes.add(elapsedTime[elapsedTime.length -1]);
                }
                // Sort the times in ascending order (least time first)
                Collections.sort(riderTimes);

                ArrayList<Integer> ridersIdInOrder = new ArrayList<>();
                // Compare the times in order
                for(int i = 0; i < riderTimes.size(); i++){
                    for(int j = 0; j < ridersIdInStage.size(); j++){
                        // If the rider is already added to the list, continue 
                        if(ridersIdInOrder.contains(ridersIdInStage.get(j))){
                            continue;
                        }
                        // Get the local time of the rider
                        LocalTime[] elapsedTime = getRiderResultsInStage(stageId, ridersIdInStage.get(j));
                        LocalTime riderElapsedTime = elapsedTime[elapsedTime.length -1];
                        int value = riderElapsedTime.compareTo(riderTimes.get(i));
                        // If the time is the elapsed time of the rider, add rider to ridersIdInOrder
                        if(value == 0){
                            ridersIdInOrder.add(ridersIdInStage.get(j));
                            break;
                        }
                    }
                }    
                // Return the riders Id in order of there elapsed Times. NOTE: Not adjusted elapsed times
                int[] orderedRidersIds = ridersIdInOrder.stream().mapToInt(i -> i).toArray();
                int[] empty = {};
                if(orderedRidersIds == empty){
                    return empty;
                }
                return orderedRidersIds;
            }
        }

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage has the given Id");
            // Check the stage isn't a Time trial
            }else if(getStageType(stageId) == StageType.TT){
                LocalTime[] TTStage = null;
                return TTStage;
            }
            // Get the riders with results in the stage
            int[] ridersInStage = getRidersRankInStage(stageId);

            ArrayList<LocalTime> riderAdjustedElapsedTimes = new ArrayList<>();
            // Get the adjusted elapsed time of the rider
            for(int counter = 0; counter < ridersInStage.length; counter++){
                riderAdjustedElapsedTimes.add(getRiderAdjustedElapsedTimeInStage(stageId, ridersInStage[counter]));

            }
            // Return the adjusted elapsed times of the riders, in order of the getRidersRankInStage method
            LocalTime[] adjustedTimes = riderAdjustedElapsedTimes.toArray(new LocalTime[riderAdjustedElapsedTimes.size()]);
            LocalTime[] empty = {};
            if(adjustedTimes == empty){
                return empty;
            }
            return adjustedTimes;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage has the given Id");
            }
            // Int arrays of points table
            int[] flatPoints = {50,30,20,18,16,14,12,10,8,7,6,7,4,3,2};
            int[] mediumMoutainPoints = {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
            int[] highMonutainPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
            int[] individualTTPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
            int[] intermSprintPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};


            // arraylist int for points
            ArrayList<Integer> ridersPoints = new ArrayList<>();
            // Check what stage type it is
            StageType type = null;
            for(Stage stage: Stage.stagesList){
                if(stage.getStageID() == stageId){
                    type = stage.getStageType();
                }       
            }
            // get the rideres rank in race using getRidersRankInStage
            int[] ridersInStage = getRidersRankInStage(stageId);
            int[] nullArray = {};
            // If not results have been set return an empty array
            if(ridersInStage == nullArray){
                return nullArray;
            }

            // Give out points based on the overall stage type
            for(int i = 0; i < ridersInStage.length; i++){
                if(StageType.FLAT == type){
                    if(i > flatPoints.length){
                        ridersPoints.add(0);
                    }
                    ridersPoints.add(flatPoints[i]);

                }else if(StageType.MEDIUM_MOUNTAIN == type){
                    if(i > mediumMoutainPoints.length){
                        ridersPoints.add(0);
                    }
                    ridersPoints.add(mediumMoutainPoints[i]);

                }else if(StageType.HIGH_MOUNTAIN == type){
                    if(i > highMonutainPoints.length){
                        ridersPoints.add(0);
                    }
                    ridersPoints.add(highMonutainPoints[i]);

                }else if(StageType.TT == type){
                    if(i > individualTTPoints.length){
                        ridersPoints.add(0);
                    }
                    ridersPoints.add(individualTTPoints[i]);
                }

            }
            int[] segmentsInStage = getStageSegments(stageId);

            // Loop through segements to see if intermidates sprint occur
            for(int i = 0; i <  segmentsInStage.length; i++){
                for(Segment segment: Segment.segmentsList){
                    if(segment.getStageID() == stageId && segment.getSegmentType() == SegmentType.SPRINT){
                        int segmentId = segment.getSegmentID();
                        int sprintSegmentEnd = IntStream.range(0, segmentsInStage.length)
                                                .filter(l -> segmentId == segmentsInStage[l])
                                                .findFirst() // first occurrence
                                                .orElse(-1);

                        Map<Integer, LocalTime> ridersTimes = new HashMap<Integer, LocalTime>();
                        ArrayList<LocalTime> sprintElapedTime = new ArrayList<>();
                        sprintSegmentEnd++;       

                        for(int j = 0; j < ridersInStage.length; j++){
                            for(Results result: Results.resultsList){
                                if(result.getRiderId() == ridersInStage[j] && result.getStageId() == stageId){
                                    // Get the elapsed time of the rider in the segment
                                    LocalTime[] stageCheckpoints = result.getStageTime();
                                    LocalTime sprintFinishLine = stageCheckpoints[sprintSegmentEnd];
                                    LocalTime sprintStartLine = stageCheckpoints[sprintSegmentEnd - 1];

                                    long tempTimetoSeconds = sprintStartLine.toSecondOfDay();
                                    LocalTime elapsedTimeOfSprint = sprintFinishLine.minus(tempTimetoSeconds, ChronoUnit.SECONDS);

                                    sprintElapedTime.add(elapsedTimeOfSprint);
                                    ridersTimes.put(ridersInStage[j], elapsedTimeOfSprint);

                                }  
                            }
                        }
                        // Sort the sprint elapsed times in ascending order
                        Collections.sort(sprintElapedTime);

                        ArrayList<Integer> ridersInOrder = new ArrayList<>();
                        // Get Ids of riders in order of the sprintElapsedTime
                        for(int k = 0; k < sprintElapedTime.size() ; k++){
                            for(Map.Entry<Integer, LocalTime> entry : ridersTimes.entrySet()){
                                if(sprintElapedTime.get(k) == entry.getValue()){
                                    if(ridersInOrder.contains(entry.getKey())){
                                        continue;
                                    }else{
                                        ridersInOrder.add(entry.getKey());
                                        break;
                                    }
                                }
                            }
                        }
                        for(int z = 0; z < ridersInOrder.size(); z++){
                            int j = ridersInOrder.get(z);
                            int index = IntStream.range(0, ridersInStage.length)
                                                .filter(l -> j == ridersInStage[l])
                                                .findFirst() // first occurrence
                                                .orElse(-1);

                            int tempPoints = ridersPoints.get(index);

                            if(z > intermSprintPoints.length){
                                break;
                            }
                            // Add points to the riders who are in the top group
                            tempPoints = tempPoints + intermSprintPoints[z];
                            ridersPoints.set(index, tempPoints);
                            continue;
                        }

                    }
                }
            }
            // Return the array of the combined stage points in order of getRidersRankInStage method
            int[] arr = ridersPoints.stream().mapToInt(i -> i).toArray();
            int[] empty = {};
            if(arr == empty){
                return empty;
            }

            return arr;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
            if(IntStream.of(getStageIds()).anyMatch(x -> x == stageId) == false){
                throw new IDNotRecognisedException("No stage has the given Id");
            }
            int[] categorisedClimb4 = {1};
            int[] categorisedClimb3= {2,1};
            int[] categorisedClimb2 = {5,3,2,1};
            int[] categorisedClimb1 = {10,8,6,4,2,1};
            int[] horsCategorie = {20,15,12,10,8,6,3,2,1};

            int[] segmentsInStage = getStageSegments(stageId);
            int[] ridersInStage = getRidersRankInStage(stageId);

            Map<Integer, Integer> riderKOMPoints = new HashMap<Integer, Integer>();
            Map<Integer, LocalTime> segmentKOMTimes = new HashMap<Integer, LocalTime>();
            // Set all riders to having 0 points
            for(int i = 0; i < ridersInStage.length;i++){
                 riderKOMPoints.put(ridersInStage[i], 0);
                 segmentKOMTimes.put(ridersInStage[i], LocalTime.of(0,0,0));
            }
            for(int counter = 0; counter < segmentsInStage.length; counter++){
                // Get the Segment type
                SegmentType type = null;

                for(Segment segment: Segment.segmentsList){
                    if(segmentsInStage[counter] == segment.getSegmentID()){
                        type = segment.getSegmentType();
                    }
                }
                // If type is a Sprint, move to the next segment in the stage
                if(type == null || type == SegmentType.SPRINT){
                    continue;        
                
                }else{
                    ArrayList<LocalTime> climbTimeInOrder = new ArrayList<>();
                    // Loop through results in the stage
                    for(Results result: Results.resultsList){
                        if(result.getStageId() == stageId){
                            int riderId = result.getRiderId();
                            // Get the elapsed time of the rider in the segment
                            LocalTime[] stageTime = result.getStageTime();
                            LocalTime finishLineOfClimb = stageTime[counter + 1];
                            LocalTime startLineOfClimb = stageTime[counter];
 
                            long tempTimetoSeconds = startLineOfClimb.toSecondOfDay();
                            LocalTime elapsedTimeOfClimb = finishLineOfClimb.minus(tempTimetoSeconds, ChronoUnit.SECONDS);

                            climbTimeInOrder.add(elapsedTimeOfClimb);
                            segmentKOMTimes.replace(riderId, elapsedTimeOfClimb);
                        }
                    }
                    // Order the times in ascending order
                    Collections.sort(climbTimeInOrder);
                    ArrayList<Integer> ridersIdInOrder = new ArrayList<>();
                    // Loop through the ordered elapsed times array
                    for(int j = 0; j < climbTimeInOrder.size(); j++){
                        for(Map.Entry<Integer, LocalTime> entry : segmentKOMTimes.entrySet()){
                            if(entry.getValue() == climbTimeInOrder.get(j)){
                                // If rider has already been given points, continue
                                if(ridersIdInOrder.contains(entry.getKey())){
                                    continue;
                                // If the segmentType is a categorised 4 climb
                                }else if(type == SegmentType.C4){ 
                                    // If all points have been given out, break
                                    if(ridersIdInOrder.size() >= categorisedClimb4.length){
                                        break;
                                    }else{
                                        for(Map.Entry<Integer, Integer> enterPoints : riderKOMPoints.entrySet()){
                                            if(enterPoints.getKey() == entry.getKey()){
                                                // Update the points to the riders hashMap
                                                int value = enterPoints.getValue();
                                                value = value + categorisedClimb4[j];
                                                riderKOMPoints.replace(enterPoints.getKey(), value);
                                                ridersIdInOrder.add(enterPoints.getKey());

                                            }

                                        }
                                    }
                                // If the segmentType is a categorised 3 climb
                                }else if(type == SegmentType.C3){ 
                                    // If all points have been given out, break
                                    if(ridersIdInOrder.size() >= categorisedClimb3.length){
                                        break;
                                    }else{
                                        for(Map.Entry<Integer, Integer> enterPoints : riderKOMPoints.entrySet()){
                                            if(enterPoints.getKey() == entry.getKey()){
                                                // Update the points to the riders hashMap
                                                int value = enterPoints.getValue();
                                                value = value + categorisedClimb3[j];
                                                riderKOMPoints.replace(enterPoints.getKey(), value);
                                                ridersIdInOrder.add(enterPoints.getKey());

                                            }

                                        }
                                    }
                                // If the segmentType is a categorised 2 climb
                                }else if(type == SegmentType.C2){ 
                                    // If all points have been given out, break
                                    if(ridersIdInOrder.size() >= categorisedClimb2.length){
                                        break;
                                    }else{
                                        for(Map.Entry<Integer, Integer> enterPoints : riderKOMPoints.entrySet()){
                                            if(enterPoints.getKey() == entry.getKey()){
                                                // Update the points to the riders hashMap
                                                int value = enterPoints.getValue();
                                                value = value + categorisedClimb2[j];
                                                riderKOMPoints.replace(enterPoints.getKey(), value);
                                                ridersIdInOrder.add(enterPoints.getKey());

                                            }

                                        }
                                    }
                                // If the segmentType is a categorised 1 climb
                                }else if(type == SegmentType.C1){ 
                                    // If all points have been given out, break
                                    if(ridersIdInOrder.size() >= categorisedClimb1.length){
                                        break;
                                    }else{
                                        for(Map.Entry<Integer, Integer> enterPoints : riderKOMPoints.entrySet()){
                                            if(enterPoints.getKey() == entry.getKey()){
                                                // Update the points to the riders hashMap
                                                int value = enterPoints.getValue();
                                                value = value + categorisedClimb1[j];
                                                riderKOMPoints.replace(enterPoints.getKey(), value);
                                                ridersIdInOrder.add(enterPoints.getKey());

                                            }

                                        }
                                    }
                                // If the segmentType is a "Hors Categorie"
                                }else if(type == SegmentType.HC){ 
                                    // If all points have been given out, break
                                    if(ridersIdInOrder.size() >= horsCategorie.length){
                                        break;
                                    }else{
                                        for(Map.Entry<Integer, Integer> enterPoints : riderKOMPoints.entrySet()){
                                            if(enterPoints.getKey() == entry.getKey()){
                                                // Update the points to the riders hashMap
                                                int value = enterPoints.getValue();
                                                value = value + horsCategorie[j];
                                                riderKOMPoints.replace(enterPoints.getKey(), value);
                                                ridersIdInOrder.add(enterPoints.getKey());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            ArrayList<Integer> ridersPointsInRankOrder = new ArrayList<>();
            // Put the riders Points in the correct order, getRidersRankInStage
            for(int counter = 0; counter < ridersInStage.length; counter++){
                for(Map.Entry<Integer, Integer> enterPoints : riderKOMPoints.entrySet()){
                    if(enterPoints.getKey() == ridersInStage[counter]){
                        ridersPointsInRankOrder.add(enterPoints.getValue());
                        break;
                    }
                }
            }
            // Return the KOM points in the order of getRidersRankInStage method 
            int[] pointsOrdered = ridersPointsInRankOrder.stream().mapToInt(i -> i).toArray();
            // Check if any score have been given
            boolean noScores = true;
            for(int i: pointsOrdered){
                if(i != 0){
                    noScores = false;
                    break;
                }
            }
            // If all scores are 0 return an empty array
            if(noScores){
                int[] emptyArr = {};
                return emptyArr;       
            }
            // Return points in rider order of getRidersRankInStage
            return pointsOrdered;
        }

	@Override
	public void eraseCyclingPortal() {
            // Delete all objects in all arrayLists
            Team.teamList.clear();
            Rider.riderList.clear();
            Race.raceList.clear();
            Stage.stagesList.clear();
            Segment.segmentsList.clear();
            Results.resultsList.clear();
            
            // Reset counters
            Team.numberOfTeams = 0;
            Rider.numberOfRiders = 0;
            Race.numberOfRaces = 0;
            Stage.numberOfStages = 0;
            Segment.numberOfSegments = 0;
        }
        
	@Override
	public void saveCyclingPortal(String filename) throws IOException {
            // Seralize the array of objects
            // Create the output file
            FileOutputStream  fis= new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fis);
            // Seralise the objects
            for(Rider rider: Rider.riderList){
                out.writeObject(rider);
            }
            for(Team team: Team.teamList){
                out.writeObject(team);
            }
            for(Race race: Race.raceList){
                out.writeObject(race);
            }
            for(Stage stage: Stage.stagesList){
                out.writeObject(stage);
            }
            for(Segment segment: Segment.segmentsList){
                out.writeObject(segment);
            }
            for(Results result: Results.resultsList){
                 out.writeObject(result);
            } 
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
            // Deceralise the array of objects

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
            int i = 0;
            int raceId = -1;
            for(Race race: Race.raceList){
                if(race.getName().equals(name)){
                    // Remove segments and stages
                    raceId = race.getRaceID();
                    Race.raceList.remove(i);
                    break;
                }
            }
            // Remove Race
            if(raceId != -1){
                try {
                    removeRaceById(raceId);
                } catch (IDNotRecognisedException ex) {
                    System.out.println(ex);
                }
            }
            
            throw new NameNotRecognisedException("No Race has been created with the given name");
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No Race has the given Id");
            }
            Map<Integer, LocalTime> ridersTimes = new HashMap<Integer, LocalTime>();

            ArrayList<LocalTime> allRidersAdjustedTimes = new ArrayList<>();
            // Get the stages in the race and riders in the race
            int[] stageId = getRaceStages(raceId);
            int[] riderId = getRidersInRace(raceId); 

            LocalTime riderTotalAdjustedTime = LocalTime.of(0,0,0,0);

            // Loop throughthe riders in the race
            for(int i = 0; i < riderId.length; i++){
                // Loop through the stages in the race
                for(int j = 0; j < stageId.length; j++){
                    // Get the elapsed time of the rider in the stage
                    LocalTime elapsedTime = getRiderAdjustedElapsedTimeInStage(stageId[j], riderId[i]);
                    long tempTimetoSeconds = elapsedTime.toSecondOfDay();
                     riderTotalAdjustedTime = riderTotalAdjustedTime.plus(tempTimetoSeconds, ChronoUnit.SECONDS);                              
                }
                // Add the riders total adjusted time in race to array
                allRidersAdjustedTimes.add(riderTotalAdjustedTime);
                // Add the riders adjusted time to the hashmap of riderId, LocalTime
                ridersTimes.put(riderId[i],riderTotalAdjustedTime);
                // Reset the race adjusted time start number
                riderTotalAdjustedTime = LocalTime.of(0,0,0,0);

            }
            // Sort the riders race adjusted times in order, least time first
            Collections.sort(allRidersAdjustedTimes);
            // Return an empty array if no results are in the race
            LocalTime[] emptyArr = {};
            if(allRidersAdjustedTimes.toArray() == emptyArr){
                return emptyArr;
            }

            LocalTime[] ridersAdjustedElapasedTimeInOrder = allRidersAdjustedTimes.toArray(new LocalTime[allRidersAdjustedTimes.size()]); 
            // Return the Race elapsed times in order 
            return ridersAdjustedElapasedTimeInOrder;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No Race has the given Id");
            }
            // Get riders and stages in the race
            int[] stagesInRace = getRaceStages(raceId);
            int[] riderIdsInRace = getRidersInRace(raceId);

            Map<Integer, Integer> ridersPointsInRace = new HashMap<Integer, Integer>();
            // Set all riders to have 0 points
            for(int i = 0; i < riderIdsInRace.length;i++){
                ridersPointsInRace.put(riderIdsInRace[i], 0);
            }
            // Loop through each stage in the race
            for(int counter: stagesInRace){
                // Get the points of the rider in the stage
                int[] ridersPoints = getRidersPointsInStage(counter);
                int[] ridersIdsInOrder = getRidersRankInStage(counter);

                for(int j = 0; j < riderIdsInRace.length; j++){
                        for(Map.Entry<Integer, Integer> entry : ridersPointsInRace.entrySet()){
                            if(entry.getKey() == ridersIdsInOrder[j]){
                                // Get current points of the rider in the race
                                int riderPoints = entry.getValue();
                                riderPoints = riderPoints + ridersPoints[j];
                                // Replace points with updated points in hashMap ridersPointsInRace
                                ridersPointsInRace.replace(entry.getKey(), riderPoints);
                                break;
                            }
                        }
                    }      
            }
            // Get the rider Ids from getRidersGeneralClassificationRank -> Ids in order of elapsed Times
            int[] raceOrderOfRiders = getRidersGeneralClassificationRank(raceId);
            ArrayList<Integer> pointsInOrderById = new ArrayList<>();
            // Loop through the riders orderd times in the race
            for(int k = 0; k < raceOrderOfRiders.length; k++){
                for(Map.Entry<Integer, Integer> entry : ridersPointsInRace.entrySet()){
                    if(entry.getKey() == raceOrderOfRiders[k]){
                        // Add the points of the rider to the pointsInOrderById
                        pointsInOrderById.add(entry.getValue());
                        break;
                    }
                }
            }
            // If the Race has no results return empty int array
            Integer[] emptyArr = {};
            if(pointsInOrderById.toArray() == emptyArr){
                int[] emptyArray = {};
                return emptyArray;
            }
            // Return the points in the order of getRidersGeneralClassificationRank return
            int[] pointsOrdered = pointsInOrderById.stream().mapToInt(i -> i).toArray();

            return pointsOrdered;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No Race has the given Id");
            }
            // Get riders and stages in the race
            int[] stagesInRace = getRaceStages(raceId);
            int[] riderIdsInRace = getRidersInRace(raceId);
            int[] emptyArr = {};

            Map<Integer, Integer> KOMPointsInRace = new HashMap<Integer, Integer>();
            // Create empty hasmap of all riders with 0 points
            for(int i = 0; i < riderIdsInRace.length;i++){
                 KOMPointsInRace.put(riderIdsInRace[i], 0);  
            }
            // Loop through all the stages in the race
            for(int stage: stagesInRace){
                // Get the the points in the stage
                int[] pointsInRankOrder = getRidersMountainPointsInStage(stage);
                int[] ridersIdRankOrder = getRidersRankInStage(stage);
                // Loop through each rider in getRiderRankInStage order
                if(pointsInRankOrder.length == 0){
                    continue;
                }
                for(int i = 0; i < ridersIdRankOrder.length; i++){
                    for(Map.Entry<Integer, Integer> entry : KOMPointsInRace.entrySet()){
                        if(entry.getKey() == ridersIdRankOrder[i]){
                            // Add the points from the stage to the overall points in the race
                            int temp = entry.getValue();
                            temp = temp + pointsInRankOrder[i];
                            KOMPointsInRace.replace(entry.getKey(), temp);
                        }
                    }             
                }
            }

            ArrayList<Integer> pointsInOrder = new ArrayList<>();

            int[] pointsInRankOrder = getRidersGeneralClassificationRank(raceId);
            // Sort the points into the rider order specified in getRidersGeneralClassificationRank return
            for(int j = 0; j < pointsInRankOrder.length; j++){
                for(Map.Entry<Integer, Integer> entry : KOMPointsInRace.entrySet()){
                    if(entry.getKey() == pointsInRankOrder[j]){
                        pointsInOrder.add(entry.getValue());
                        break;
                    }
                }
            }
            //Check if Race has results, if no points given return an empty array
            boolean noScores = true;
            for(int i: pointsInOrder){
                if(i != 0){
                    noScores = false;
                    break;
                }
            }
            // If all scores are 0 return an empty array
            if(noScores){
                return emptyArr;       
            }
            // Return int[] of the points in order of rider Id given by getRidersGeneralClassificationRank return
            int[] arr = pointsInOrder.stream().mapToInt(i -> i).toArray();

            return arr;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No Race has the given Id");
            }

            Map<Integer, LocalTime> ridersTimes = new HashMap<Integer, LocalTime>();

            ArrayList<LocalTime> allRidersAdjustedTimes = new ArrayList<>();

            int[] stageId = getRaceStages(raceId);
            int[] riderId = getRidersInRace(raceId); 

            LocalTime riderTotalAdjustedTime = LocalTime.of(0,0,0,0);

            // Loop through the riders in the race
            for(int i = 0; i < riderId.length; i++){
                // Loop through the stages in the race
                for(int j = 0; j < stageId.length; j++){
                    // Get the adjusted elapsed time of the rider in the race
                    LocalTime elapsedTime = getRiderAdjustedElapsedTimeInStage(stageId[j], riderId[i]);
                    long tempTimetoSeconds = elapsedTime.toSecondOfDay();
                    riderTotalAdjustedTime = riderTotalAdjustedTime.plus(tempTimetoSeconds, ChronoUnit.SECONDS);                              
                }
                // Add riders elapsed time to array and hashmap
                allRidersAdjustedTimes.add(riderTotalAdjustedTime);
                ridersTimes.put(riderId[i],riderTotalAdjustedTime);

                riderTotalAdjustedTime = LocalTime.of(0,0,0,0);
            }
            // Sort the adjusted elapsed time into order (least time first)
            Collections.sort(allRidersAdjustedTimes);

            LocalTime[] ridersAdjustedElapasedTimeInOrder = allRidersAdjustedTimes.toArray(new LocalTime[allRidersAdjustedTimes.size()]);
            // If theres no times for the riders, return empty list
            LocalTime[] emptyArray = {};
            if(ridersAdjustedElapasedTimeInOrder == emptyArray){
                int[] arr = {};
                return arr;
            }
            ArrayList<Integer> ridersInOrder = new ArrayList<>();
            // Get the riders Id in order of there times
            for(int i = 0; i < ridersAdjustedElapasedTimeInOrder.length; i++){
                for(Map.Entry<Integer, LocalTime> entry : ridersTimes.entrySet()){
                    if(ridersAdjustedElapasedTimeInOrder[i] == entry.getValue()){
                        if(ridersInOrder.contains(entry.getKey())){
                            continue;
                        }else{
                            ridersInOrder.add(entry.getKey());
                            break;
                        }
                    }
                }
            }
            // Return riders Ids from fastest in race to slowest
            int[] arr = ridersInOrder.stream().mapToInt(i -> i).toArray();

            return arr;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No Race has the given Id");
            }
            // Get riders and stage in race
            int[] stagesInRace = getRaceStages(raceId);
            int[] riderIdsInRace = getRidersInRace(raceId);

            Map<Integer, Integer> ridersPointsInRace = new HashMap<Integer, Integer>();
            // Set all riders to having 0 points
            for(int i = 0; i < riderIdsInRace.length;i++){
                ridersPointsInRace.put(riderIdsInRace[i], 0);
            }
            // Loop through all the stages in the race 
            for(int counter: stagesInRace){
                // Get the poinrs of the rider in the stage
                int[] ridersPoints = getRidersPointsInStage(counter);
                int[] ridersIdsInOrder = getRidersRankInStage(counter);
                // Loop through all the riders in the race
                for(int j = 0; j < riderIdsInRace.length; j++){
                        for(Map.Entry<Integer, Integer> entry : ridersPointsInRace.entrySet()){
                            if(entry.getKey() == ridersIdsInOrder[j]){
                                // Add the correct points to the race total points of the rider
                                int riderPoints = entry.getValue();
                                riderPoints = riderPoints + ridersPoints[j];
                                ridersPointsInRace.replace(entry.getKey(), riderPoints);
                                break;
                            }
                        }
                    }  
            }
            int[] raceOrderOfRiders = getRidersGeneralClassificationRank(raceId);
            ArrayList<Integer> pointsInOrderById = new ArrayList<>();
            // Loop through the riders in order given by getRidersGeneralClassificationRank return
            for(int k = 0; k < raceOrderOfRiders.length; k++){
                for(Map.Entry<Integer, Integer> entry : ridersPointsInRace.entrySet()){
                    if(entry.getKey() == raceOrderOfRiders[k]){
                        // Add riders points to arrayList
                        pointsInOrderById.add(entry.getValue());
                        break;
                    }
                }
            }
            // Sort the points into most to least
            Collections.sort(pointsInOrderById, Collections.reverseOrder());
            ArrayList<Integer> pointsInOrder = new ArrayList<>();
            // Loop through the points in order to get corresponding rider Id
            for(int k = 0; k < pointsInOrderById.size(); k++){
                  for(Map.Entry<Integer, Integer> entry : ridersPointsInRace.entrySet()){
                      if(entry.getValue() == pointsInOrderById.get(k)){
                          // If rider has already been added to the arrayList continue
                          if(pointsInOrder.contains(entry.getKey())){
                              continue;
                          }
                          // Add riderId to the arrayList
                          pointsInOrder.add(entry.getKey());
                          break;
                      }
                  }
            }
            // Return the array of ridersIds in most points to least points
            int[] arr = pointsInOrder.stream().mapToInt(i -> i).toArray();

            return arr;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
            if(IntStream.of(getRaceIds()).anyMatch(x -> x == raceId) == false){
                throw new IDNotRecognisedException("No Race has the given Id");
            }
            // Get riders and stages in the race
            int[] stagesInRace = getRaceStages(raceId);
            int[] riderIdsInRace = getRidersInRace(raceId);

            Map<Integer, Integer> KOMPointsInRace = new HashMap<Integer, Integer>();
            // Set all riders to have 0 points
            for(int i = 0; i < riderIdsInRace.length;i++){
                 KOMPointsInRace.put(riderIdsInRace[i], 0);

            }
            // Loop through all the stages in the race
            for(int stage: stagesInRace){
                // Get the points and riders Ids in stage
                int[] pointsInRankOrder = getRidersMountainPointsInStage(stage);
                int[] ridersIdRankOrder = getRidersRankInStage(stage);
                // Check if the stage has any points, if not continue
                if(pointsInRankOrder.length == 0){
                    continue;
                }
                // Loop through the riders in order
                for(int i = 0; i < ridersIdRankOrder.length; i++){
                    for(Map.Entry<Integer, Integer> entry : KOMPointsInRace.entrySet()){
                        if(entry.getKey() == ridersIdRankOrder[i]){
                            // Add points to the riders toal race points
                            int temp = entry.getValue();
                            temp = temp + pointsInRankOrder[i];
                            KOMPointsInRace.replace(entry.getKey(), temp);
                        }
                    }
                }
            }
            ArrayList<Integer> pointsInOrder = new ArrayList<>();
            // Loop through and get all the riders total points in the race
            int[] pointsInRankOrder = getRidersGeneralClassificationRank(raceId);
            for(int j = 0; j < riderIdsInRace.length; j++){
                for(Map.Entry<Integer, Integer> entry : KOMPointsInRace.entrySet()){
                    if(entry.getKey() == riderIdsInRace[j]){
                        // Add total points to the arrayList 
                        pointsInOrder.add(entry.getValue());
                        break;
                    }
                }
            }
            // Sort the points into descending order (most points first)
            Collections.sort(pointsInOrder, Collections.reverseOrder());
            // Check if no scores have been added to the riders
            int emptyScore = 0;
            for(int i = 0; i < pointsInOrder.size();i++){
                if(pointsInOrder.get(i) == 0){
                    emptyScore++;
                }
            }
            // Return empty array if all riders scores are 0
            if(emptyScore == pointsInOrder.size()){
                int[] emptyArr = {};
                return emptyArr;
            }
            
            ArrayList<Integer> ridersIdInPointsOrder = new ArrayList<>();
            // Loop through the points in order
            for(int i = 0; i < pointsInOrder.size();i++){
                for(Map.Entry<Integer, Integer> entry : KOMPointsInRace.entrySet()){
                    if(entry.getValue() == pointsInOrder.get(i)){
                        if(ridersIdInPointsOrder.contains(entry.getKey())){
                            continue;
                        }
                        // Add riders Id to arrayList
                        ridersIdInPointsOrder.add(entry.getKey());          
                    }
                }
            }
            // Return array of ridersId in order of most points to least points
            int[] arr = ridersIdInPointsOrder.stream().mapToInt(i -> i).toArray();

            return arr;
	}
}
