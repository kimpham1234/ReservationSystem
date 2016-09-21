import java.util.ArrayList;
import java.util.logging.Logger;

public class driver {
		
	
	
	public static void main(String[] args) {
		SeatManager manager = new SeatManager();
		
		//test addPassenger
		String p1 = "29A, I, Karri Johnson";
		Passenger a1 = new Passenger(p1.split(","));
		manager.addPassenger(a1);
	
       System.out.println( manager.addSingleRequest("boo", "first", "w"));
       manager.printManifest("economy");
       manager.cancelReservation("Karri Johnson", false);
       manager.printManifest("economy");
	}

	}

