import java.util.ArrayList;

/*
 * 
	
	public void print(String serviceClass){
		Passenger[][] array = new Passenger[0][0];
		if(serviceClass.equals("first"))
			array = first;
		else
			array = economy;
		for(int i = 0; i < array.length; i++){
			for(int j = 0; j < array[i].length; j++){
				if(array[i][j] == null)
					System.out.printf(" __ ");
				else
					System.out.printf("  X ");
			}
			System.out.println();
		}
	}
 */





public class test {
	public static void main(String[] args) {
		
		Passenger a = new Passenger("1D, I, Henry Barron".split(","));
		Passenger b = a.copy();
		System.out.println(a.toString());
		System.out.println(b.toString());
		b.setName("Henry Change");
		System.out.println(a.toString());
		System.out.println(b.toString());
	}
	
		

	}


