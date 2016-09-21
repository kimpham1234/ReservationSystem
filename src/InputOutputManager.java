/**
 * Hw1 Assignment - InputOutputManager.java
 * @author kimpham
 * @since  09/02/16
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputOutputManager {
	SeatManager manager;
	String filename;
	
	public InputOutputManager(String filename){
		this.manager = new SeatManager();
		this.filename = filename;
	}
	
	public void read(String filename) throws IOException{
		try{
			File file = new File(filename);
			if(file.exists()){
				String line = "";
				Scanner in = new Scanner(file);
				if(in.hasNextLine())
					line = in.nextLine();
				while(in.hasNextLine()){
				    line = in.nextLine();
					String[] detail = line.split(",");
					Passenger passenger = new Passenger(detail);
					manager.addPassenger(passenger);
				}
			}
			else
				file.createNewFile();
		}catch(IOException ex){
			System.out.println("oups");
		}
	}
	
	public void save(String filename){
		try{
			File file = new File(filename);
			FileWriter writer = new FileWriter(file.getAbsoluteFile());
			BufferedWriter buffer = new BufferedWriter(writer);
			ArrayList<Passenger> infoList = manager.passengerInfo();
			buffer.write("First 1-2, Left: A-B, Right: C-D; Economy 10-29, Left: A-C, Right: D-F\n");
			for(int i = 0; i < infoList.size(); i++){
				buffer.write(infoList.get(i).toString());
				buffer.write("\n");
			}
			buffer.close();
			
		}catch(IOException ex){
			System.out.println("File not found");
		}
		
			
		
	}
	
	
	
	public void menu(){
		String choice = "";
		Scanner scanner = new Scanner(System.in);
		do{
			System.out.println("Add [P]assenger");
			System.out.println("Add [G]roup");
			System.out.println("[C]ancel Reservations ");
			System.out.println("Print Seating [A]vailability Chart");
			System.out.println("Print [M]anifest ");
			System.out.println("[Q]uit");
			choice = scanner.nextLine().toLowerCase().trim();
			executeChoice(choice, scanner);
			
		}while(!choice.equals("q"));
		scanner.close();
	}
	
	private boolean executeChoice(String choice, Scanner in){
		String serviceClass = "";
		String name = "";
		switch(choice){
		case "p":
			System.out.println("Name: ");
			name = in.nextLine();
			System.out.println("Service Class: ");
		    serviceClass = in.nextLine().toLowerCase();
			System.out.println("Seat Preference: ");
			String preference = in.nextLine().toLowerCase();
			
			String seat = manager.addSingleRequest(name, serviceClass, preference);
			
			if(!seat.isEmpty())
				System.out.println(seat);
			else
				System.out.println("Request failed");
			
			break;
		case "g":
		    System.out.println("Group Name: ");
		    String groupName = in.nextLine();
		    System.out.println("Names: ");
		    String[] names = in.nextLine().split(",");
		    System.out.println("Service Class: ");
		    serviceClass = in.nextLine().toLowerCase();
		    
		    ArrayList<String> seated = manager.addGroupRequest(groupName, names, serviceClass);
		    
		    if(seated.size()!=0){
		    	for(int i = 0; i < seated.size(); i++){
		    		System.out.println(seated.get(i));
		    	}
		    }else
		    	System.out.println("Request failes");
			
			break;
		case "c":
			System.out.println("Cancel [I]ndividual or [G]roup? ");
			String type = in.nextLine().toLowerCase();
			Boolean isGroup = true;
			if(type.equals("i")){
				System.out.println("Name: ");
				isGroup = false;
			}
			else
				System.out.println("Group name");
		    name = in.nextLine();
		    manager.cancelReservation(name, isGroup);
				
			break;
		case "a":
			System.out.println("Service class: ");
			serviceClass = in.nextLine().toLowerCase();
			manager.printAvailability(serviceClass);
			
			break;
		case "m":
			System.out.println("Service class: ");
			serviceClass = in.nextLine().toLowerCase();
			manager.printManifest(serviceClass);
			
			break;
		case "q":
			save(filename);
			break;
		
		
		}
		
		
		
		
		
		
		
		return true;
	}
}
