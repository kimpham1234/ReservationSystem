/**
 * Hw1 Assignment - Passenger.java
 * @author kimpham
 * @since  09/02/16
 */


/**
* Represent a passenger with reserved seat on a plane
*/
public class Passenger {
	
	
	private String seat;
	private String type;
	private String groupName;
	private String name;
	
	/**
	 * Constructor
	 * @param detail (required) string arrays of seat, type, groupName, and name
	 */
	public Passenger(String [] detail){
		seat = detail[0].trim();
		type = detail[1].trim();
		
		//if type not equal [G]roup, groupName is empty
		if(!type.equals("G")){
			groupName = "";			
			name = detail[2].trim();
		}
		else{
			groupName = detail[2].trim();
			name = detail[3].trim();
		}
	}
	
	/**
	 * Constructor
	 * @param seat (required) seat of passenger
	 * @param type (required) type of passenger, either [I]ndividual or [G]roup
	 * @param groupName (required) name of the group, pass in empty string if individual
	 * @param name (required) name of passenger
	 */
	public Passenger(String seat, String type, String groupName, String name){
		this.seat = seat;
		this.type = type;
		this.groupName = groupName;
		this.name = name;
	}
	
	/**
	 * Return string representation of Passenger
	 * @return string presentation of this passenger
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if(!groupName.isEmpty())
			return  seat+", "+type+", " + groupName + ", " +name;
		else
			return seat+", "+type+", "+name;
	}
	
	
	/**
	 * Return the copy of passenger object
	 * @return the copy of passenger object
	 */
	public Passenger copy(){
		Passenger newP = new Passenger(this.seat, this.type, this.groupName, this.name);
		return newP;
	}
	
	/**
	 * Return seat number
	 * @return seat number of this passenger
	 */
	public String getSeat() {
		return seat;
	}

	/**
	 * Set the seat of a passenger with seat
	 * @param seat seat to be set to
	 * precondition: seat not null
	 * postcondition: the seat of passenger is set to seat
	 */
	public void setSeat(String seat) {
		this.seat = seat;
	}

	/**
	 * Return this passenger type
	 * @return the passenger type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of this passenger by type
	 * @param type
	 * precondition: type is not null
	 * postcondition: passenger's type is set to type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Return the groupName of this Passenger
	 * @return groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Sets the groupName of this passenger
	 * @param groupName
	 * precondition: groupName is not null
	 * postcondition: this passenger's groupName is set to groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Returns the name of this passenger
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this passenger
	 * @param name
	 * precondition: name is not null
	 * postcondition: this passenger name is set to name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
