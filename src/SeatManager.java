/**
 * Hw1 Assignment - SeatManager.java
 * @author kimpham
 * @since  09/02/16
 */


import java.util.*;
import java.util.logging.Logger;

/**
 * class SeatManager: store information of Passengers on the plane and manage seats
 */
public class SeatManager {

	public enum Col{
		A(0), B(1), C(2), D(3), E(4), F(5);
		private int value;
		private Col(int value){ this.value = value;}
	}
	
	private Col[] col = Col.values();
	private final int FIRST_ROW = 2, FIRST_COL = 4, ECO_ROW = 20, ECO_COL = 6;
	
	private Passenger[][] first = new Passenger[FIRST_ROW][FIRST_COL];
	private Passenger[][] economy = new Passenger[ECO_ROW][ECO_COL];
	
	
	/**
	 * Add a passenger with predetermined info(seat, name, type, groupName)
	 * @param passenger the passenger that needs to be added
	 * @return true if add successfully
	 */
	public boolean addPassenger(Passenger passenger){
		int row = 0;
		int col = 0;
		int substringIndex = 0;
		
		//get passenger seat number as a string
		String seat = passenger.getSeat();
		if(seat.length()==2) substringIndex = 1;
		else substringIndex = 2;
			
		//Parse into the right row and column value
	    row = Integer.parseInt(seat.substring(0,substringIndex));
	    col = Enum.valueOf(Col.class, seat.substring(substringIndex)).value;
	
	    //insert to first class when row between 0-2 or economy when row between 10-29
		if(row > 0 && row <=2 ){ 
			first[row-1][col] = passenger;
			return true;
		}
		else if(row >=10 && row <=29){
			economy[row-10][col] = passenger;
			return true;
		}
		return false;
	}
	
	/**
	 * Add a passenger through an Add Individual Passenger request
	 * @param name name of passenger
	 * @param serviceClass service class of passenger
	 * @param preference seat preference of passenger
	 * @return pseat the seat that the passenger was seated once adding is done
	 */
	public String addSingleRequest(String name, String serviceClass, String preference){
		
		//check if a seat of that preference is available, return the int array {row,col} or {-1.-1}
		int[] seat = checkSingleSeat(serviceClass, preference);
		
		
		String pseat = "";
		String type = "I";
		String pname = name;
		Passenger newPassenger = null;
		
		//if seat is available, then add the passenger
		if(seat[0]!= -1){
			if(serviceClass.equals("economy")){
				pseat = ""+ (seat[0]+10) + col[seat[1]];
				newPassenger = new Passenger(pseat, type, "", pname);
				economy[seat[0]][seat[1]] = newPassenger;
			}
			else{
				pseat = "" + (seat[0]+1) + col[seat[1]];
				newPassenger = new Passenger(pseat, type, "", pname);
				first[seat[0]][seat[1]] = newPassenger;
			}
			return pseat;
		}else 
			return pseat;
	}
	

	/**
	 * Add a group of passengers through an Add Group request
	 * @param groupName
	 * @param name
	 * @param serviceClass
	 * @return
	 */
	public ArrayList<String> addGroupRequest(String groupName, String[] name, String serviceClass){
		int seatNeeded = name.length;
		int availSeat = 0;
		ArrayList<String> seatedPassenger = new ArrayList<>();
		
		if(serviceClass.equals("first")){
			availSeat = getEmptyCount("first");
		}
		else
			availSeat = getEmptyCount("economy");
		
		//return empty list of seated Passenger when not enough seat
		if(availSeat < seatNeeded )
			return seatedPassenger;
		
		//create passengers with empty seat
		ArrayList<Passenger> group = new ArrayList<>();
		
		for(int i = 0; i < name.length; i++){
			String pSeat = null;
			String pType = "G";
			String pGroupName = groupName;
			String pname = name[i];
			
			Passenger p = new Passenger(pSeat, pType, pGroupName, pname);
			group.add(p);
		}
		
    
		//seat in empty rows first
        int remainSeatNeeded = seatEmptyRow(group, serviceClass,seatedPassenger);
		
		//seat in the most adjacent row
		if(remainSeatNeeded > 0){
			seatAdjacent(group, serviceClass,seatedPassenger);
		}
	
		return seatedPassenger;
		
	}
	

	public void cancelReservation(String name, Boolean group){
	
		//find Passenger in first class
		for(int i = 0; i < FIRST_ROW; i++){
			for(int j = 0; j < FIRST_COL; j++){
				if(first[i][j] != null){
					if(group){
						if(first[i][j].getGroupName().equals(name))
							first[i][j] = null;
					}
					else{
						if(first[i][j].getName().equals(name)){
							first[i][j] = null;
							return;
						}
					}
				}
			}
		 }
		
		
		//find Passenger in economy class
		for(int i = 0; i < ECO_ROW; i++){
			for(int j = 0; j < ECO_COL; j++){
				if(economy[i][j] != null){
					if(group){
						if(economy[i][j].getGroupName().equals(name)){
							economy[i][j] = null;
						}
					}
					else{
						if(economy[i][j].getName().equals(name)){
							economy[i][j] = null;
							return;
						}
					}
				}
			}
		}
	}
	
	public void printAvailability(String serviceClass){
		Passenger[][] array = new Passenger[0][0];
		int pad = 0;
		if(serviceClass.equals("first")){
			array = first;
			pad = 1;
		}
		else{
			array =  economy;
			pad = 10;
		}
		
		System.out.println(serviceClass);
		for(int i = 0; i < array.length; i++){
			String line = "";
			if(!isFullRow(array[i])){    
				line+= (i+pad)+" :";
				for(int j = 0; j < array[i].length; j++ ){
						if(array[i][j] == null){
							line += col[j] + ", ";
						}
				}
				line = line.substring(0, line.length()-2);
				System.out.println(line);
			}
		}	
		
	}
	
	public void printManifest(String serviceClass){
		Passenger[][] array = new Passenger[0][0];
		if(serviceClass.equals("first"))
			array = first;
		else
			array =  economy;
		System.out.println(serviceClass);
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				if(array[i][j]!=null){
					System.out.printf("%s: %s\n", array[i][j].getSeat(), array[i][j].getName());
				}
			}
			
		}
		
	}
	
	public ArrayList<Passenger> passengerInfo(){
		ArrayList<Passenger> infoList = new ArrayList<>();
		for(int i = 0; i < FIRST_ROW; i++){
			for(int j = 0; j < FIRST_COL; j++){
				if(first[i][j] != null)
					infoList.add(first[i][j].copy());
			}
		}
		
		for(int i = 0; i < ECO_ROW; i++){
			for(int j = 0; j < ECO_COL; j++){
				if(economy[i][j] !=null)
					infoList.add(economy[i][j].copy());
			}
		}
		return infoList;
	}
	
	
	
	
	/*
	 * return empty count of first class or economy class
	 */
	public int getEmptyCount(String serviceClass){
		int count = 0;
	    Passenger[][] array = new Passenger[0][0];
		if(serviceClass.equals("first")){
			array = first;
		}else{
			array = economy;
		}
		
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				if(array[i][j] == null)
					count++;
			}
		}
		return count;
	}
	
	
	
	/*
	 * check if one seat of M, A or W is available, used in addingSingleRequest
	 */
	private int[] checkSingleSeat(String serviceClass, String preference){
		int[] notfound = new int[]{-1,-1};
		//for first class
		if(serviceClass.equalsIgnoreCase("first")){
			if(preference.equalsIgnoreCase("w")){
				return checkColumn(col[0].value, col[3].value, first);
			}
			else if(preference.equalsIgnoreCase("a") || preference.equalsIgnoreCase("m")){
				//check column b,c
				return checkColumn(col[1].value, col[2].value, first);
			}
			else{
				Logger.getGlobal().info("invalid preference");
				return notfound;
			}
		}
		else if(serviceClass.equalsIgnoreCase("economy")){  //for economy
			if(preference.equalsIgnoreCase("w")){
				//check column a,f
				return checkColumn(col[0].value, col[5].value, economy);
			}
			else if(preference.equalsIgnoreCase("a")){
				//check column c,d
				return checkColumn(col[2].value, col[3].value, economy);
			}
			else if(preference.equalsIgnoreCase("m")){
				//check column b,e
				return checkColumn(col[1].value, col[4].value, economy);
			}
			else{
				Logger.getGlobal().info("invalid reference");
				return notfound;
			}
		}
		else{
			Logger.getGlobal().info("invalid service class");
			return notfound;
		}
	}
	

	/*
	 * check if a seat in 2 columns are available, used in checking Single seat
	 */
	private int[] checkColumn(int col1, int col2, Passenger[][] array){
		for(int i = 0; i < array.length; i++){
			if(array[i][col1] == null) return new int[]{i,col1};
		}
		for(int i = 0; i < array.length; i++){
			if(array[i][col2] == null) return new int[]{i,col2};
		}
		return new int[]{-1,-1};
	}
	

	/*
	 * seat passenger in empty row if applicable
	 */
	private int seatEmptyRow(ArrayList<Passenger> passenger, String serviceClass, ArrayList<String> seated){
		Passenger[][] array = new Passenger[0][0];
		int pad = 0;
		if(serviceClass.equals("first")){
			array = first;
			pad = 1;
		}
		else{
			array = economy;
			pad = 10;
		}
		
		int seatNeeded = passenger.size();
		int seatSizeForRow = 0;
		for(int i = 0; i < array.length; i++){
			if(isEmptyRow(array[i]) && seatNeeded > 0){
				if(seatNeeded < array[i].length){ // seat all customer
					seatSizeForRow = seatNeeded;
					seatNeeded = 0;
				}else{ //seat all in this row then the rest for next row
					seatSizeForRow = array[i].length;
					seatNeeded -= array[i].length;
				}
				for(int j = 0; j  < seatSizeForRow ; j++){
					String pseat = ""+ (i+pad) + col[j];
					Passenger p = passenger.get(0);
					p.setSeat(pseat);
					array[i][j] = p;
					seated.add(pseat +": "+ p.getName());
					passenger.remove(p);
				}
				if(seatNeeded == 0)
					return seatNeeded;		
			}
		}
		return seatNeeded;
	}
	
	private boolean isEmptyRow(Passenger[] array){
		for(int i = 0; i < array.length; i++){
			if(array[i] != null)
				return false;
		}
		return true;
	}
	
	private boolean isFullRow(Passenger[] array){
		for(int i = 0; i < array.length; i++){
			if(array[i] == null)
				return false;
		}
		return true;
	}
	
	
	private void seatAdjacent(ArrayList<Passenger> passenger, String serviceClass, ArrayList<String> seated){	
		int pad = 0; 
		
		Passenger[][] array = new Passenger[0][0];
		if(serviceClass.equals("first")){
			array = first;
			pad = 1;
		}
		else{
			array = economy;
			pad = 10;
		}
		
		//locate the row with most adjacent seat
		while(passenger.size() > 0){
			int max_pos = 0;
			int adjacentIndex = countAdjacentSeat(max_pos, array)[1];
			for(int i = 0; i < array.length-1; i++){
				int[] result1 = countAdjacentSeat(max_pos, array);
				int[] result2 = countAdjacentSeat(i+1, array);
				if(result1[0] < result2[0]){
					max_pos = i+1;
					adjacentIndex = result2[1];
				}
			}
	
			//seat all passenger in the row with most adjacent seat, starting at the adjacent index
			for(int i = adjacentIndex; i < array[max_pos].length; i++){
				if(array[max_pos][i] == null && passenger.size() > 0){ //if empty then seat
					String pseat = ""+ (max_pos+pad) + col[i];
					Passenger p = passenger.get(0);
					p.setSeat(pseat);
					array[max_pos][i] = p;
					seated.add(pseat +": "+ p.getName());
					passenger.remove(p);
				}
			}
			
			//keep seating passenger in non-adjacent seats in the same row before moving on
			if(passenger.size() > 0){ 
				for(int i = 0; i < adjacentIndex; i++){
					if(array[max_pos][i] == null && passenger.size() > 0){ //if empty then seat
						String pseat = ""+ (max_pos+pad) + col[i];
						Passenger p = passenger.get(0);
						p.setSeat(pseat);
						array[max_pos][i] = p;
						passenger.remove(p);
					}
				}
				
			}
			
		}
	}
	
	/*
	 * count the number of adjacent seat in a row
	 */
	private int[] countAdjacentSeat(int row, Passenger[][] array){
		int count = 0;
		int maxcount = count;
		int index  = 0;
		int maxindex = index;
		for(int i = 0; i < array[row].length; i++){
			if(array[row][i] == null){
				count++;
				index = i;
			}else{
				if(maxcount < count){
					maxcount = count;
					maxindex = index;
				}
				count = 0;
			}
			
		}
		
		if(maxcount < count){
			maxcount = count;
			maxindex = index;
		}
		return new int[]{maxcount, maxindex-maxcount+1};
	}
	
	
	
}

