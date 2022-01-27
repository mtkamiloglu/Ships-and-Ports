
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
import ports.Port;

import org.hamcrest.core.IsInstanceOf;

public abstract class Container implements Comparable<Container> {
	/**
	 * ID of the container.
	 */
	protected int ID;
	/**
	 *The weight of the container
	 */
	protected int weight;
	/**
	 * Fuel consumption per km for the container
	 */
	public double fuelConsumption;
	/**
	 * Keeps track of which port the container is in
	 */
	public Port currPort;
	/**
	 * Type of container
	 */
	protected String cType;
	
	Container(int ID, int weight){
		this.ID = ID;
		this.weight = weight;
		}
	
	/**This method calculates consumption of container
	 * 
	 * @return consumption of this container
	 */
	abstract public double consumption();
	
	/**
	 * This method returns is it equal or not
	 * @param other
	 * @return true or false
	 */
	abstract public boolean equals(Container other);
	
	/**
	 * Getter method for weight of this container
	 * @return weight of this container
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Getter method for ID of this container
	 * @return ID of this container
	 */
	public int getID() {
		return this.ID;
	}
	 
	/**
	 * Getter method for container type
	 * @return type of container
	 */
	public String getContainerType() {
		return this.cType;
	}
	
	/**
	 * Setter method for containers' current port
	 * @param p 
	 */
	public void setCurrPort(Port p) {
		this.currPort = p;
	}
	
	/**
	 * This method compares two container with respect to their ID
	 */
	public int compareTo(Container otherContainer) {
		return Integer.compare(getID(), otherContainer.getID());
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

