/**
 * Hw1 Assignment - ReservationSystem.java
 * @author kimpham
 * @since  09/02/16
 */


import java.io.IOException;

public class ReservationSystem{
	public static void main(String[] args) {
		try{
			String filename = "CL34";
			//SeatManager seatManager = new SeatManager();
			InputOutputManager input = new InputOutputManager(filename);
			input.read(filename);
			input.menu();
		}catch(IOException ex){
			System.out.println("main exception");
		}
 }
}
