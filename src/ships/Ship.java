
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;
import java.util.Collections;

import ports.Port;
import containers.Container;
import interfaces.IShip;

public class Ship implements IShip, Comparable<Ship> {
	/**
	 *The ID of the ship. 
	 */
	private int ID;
	/**
	 * Current fuel of the ship
	 */
	private double fuel;
	/**
	 * The port that ship is in
	 */
	public Port currentPort;
	/**
	 * Ship's total weight capacity
	 */
	public int totalWeightCapacity;
	/**
	 * Ship's max number of all containers
	 */
	public int maxNumberOfAllContainers;
	/**
	 * Ship's max number of heavy cntainers
	 */
	public int maxNumberOfHeavyContainers;
	/**
	 * Ship's max number of refrigerated container
	 */
	public int maxNumberOfRefrigeratedContainers; 
	/**
	 * Ship's max number of liquid containers
	 */
	public int maxNumberOfLiquidContainers; 
	/**
	 * Ship's fuel consumption per km
	 */
	public double fuelConsumptionPerKM;
	/**
	 * Ship's current weight
	 */
	public int currentWeight;
	/**
	 * The list of all containers in the ship
	 */
	private ArrayList<Container> allConts;
	/**
	 * The list of basic containers in the ship
	 */
	private ArrayList<Container> basics; 
	/**
	 * The list of heavy containers in the ship
	 */
	private ArrayList<Container> heavys; 
	/**
	 * The list of refrigerated containers in the ship
	 */
	private ArrayList<Container> refs; 
	/**
	 * The list of liquid containers in the ship
	 */
	private ArrayList<Container> liqs; 
 	
	public Ship(int ID, Port port, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		this.ID = ID;
		this.currentPort = port; port.getCurrent().add(this);
		this.totalWeightCapacity = totalWeightCapacity;
		this.maxNumberOfAllContainers = maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
		this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
		this.fuelConsumptionPerKM = fuelConsumptionPerKM;
		this.currTotalContainer = 0;
		this.currHeavyContainer = 0;
		this.currRefrigeratedContainer = 0;
		this.currLiquidContainer = 0;
		this.currentWeight = 0;
		this.allConts = new ArrayList<Container>();
		this.basics = new ArrayList<Container>(); this.heavys = new ArrayList<Container>();
		this.liqs = new ArrayList<Container>(); this.refs = new ArrayList<Container>();
	}

	/**
	 * The number of current total container
	 */
	public int currTotalContainer;
	/**
	 * The number of current heavy container
	 */
	public int currHeavyContainer;
	/**
	 * The number of current refrigerated container
	 */
	public int currRefrigeratedContainer;
	/**
	 * The number of current liquid Container
	 */
	public int currLiquidContainer;
	
	
	
	/**
	 * The method that adds fuel to the ship as amount of "newFuel".
	 * 
	 * @param newFuel the amount of fuel that will be added to the ship
	 *
	 */
	@Override
	public void reFuel(double newFuel) {
		this.fuel += newFuel;
	}

	/**
	 * The method that loads the given container to the ship.
	 *
	 *@param cont the container which will be loaded to the ship
	 *
	 */
	@Override
	public boolean load(Container cont) {
		boolean isLoadable = true;
		if(this.maxNumberOfAllContainers == this.currTotalContainer) {
			return false;
		}
		if(this.totalWeightCapacity<=this.currentWeight + cont.getWeight()) {
			return false;
		}
		if(!this.currentPort.containers.contains(cont)) {
			return false;
		}
			
		if(cont.getContainerType().equals("H")) {
			if(this.currHeavyContainer == this.maxNumberOfHeavyContainers)
				return false;
		}
		else if(cont.getContainerType().equals("R")) {
			if(this.currHeavyContainer == this.maxNumberOfHeavyContainers)
				return false;
			else if(this.currRefrigeratedContainer == this.maxNumberOfRefrigeratedContainers)
				return false;
		}
		else if(cont.getContainerType().equals("L")) {
			if(this.currHeavyContainer == this.maxNumberOfHeavyContainers)
				return false;
			else if(this.currLiquidContainer == this.maxNumberOfLiquidContainers)
				return false;
		}
		else if(cont.getContainerType().equals("B")) {
			if(this.currTotalContainer == this.maxNumberOfAllContainers)
				return false;
		}
		
		
		
		return isLoadable;
	}

	/**
	 * The method that unloads the given container from the ship.
	 * 
	 * @param cont the container which will be unloaded from the ship
	 * 
	 */
	@Override
	public boolean unLoad(Container cont) {
		boolean existence = false;
		for(int i=0; i<this.allConts.size(); i++) {
			if(cont.getID() == this.allConts.get(i).getID()) {
				existence = true;
				break;
			}
			else {
				existence = false;
			}
		}
		if(existence) {
			this.getCurrentContainers().remove(cont);
			this.currentPort.containers.add(cont);
			cont.setCurrPort(this.currentPort);
			if(cont.getContainerType().equals("B")) {
				this.getBasics().remove(cont);
				this.currentPort.getBasics().add(cont);
			}
			else if(cont.getContainerType().equals("H")) {
				this.getCurrentPort().getHeavys().add(cont);
				this.getHeavys().remove(cont);
			}
			else if(cont.getContainerType().equals("L")) {
				this.getLiqs().remove(cont);
				this.currentPort.getLiqs().add(cont);
			}
			else if(cont.getContainerType().equals("R")) {
				this.getRefs().remove(cont);
				this.currentPort.getRefs().add(cont);
			}
			return true;
		}
		else
			return false;
	}

	/**
	 * The method that moves a ship from its current port to the given port.
	 * 
	 * @param p the port that the ship will go to
	 * 
	 */
	@Override
	public boolean sailTo(Port p) {
		double allConsumption = 0;
		for(int i=0; i<this.allConts.size(); i++) {
			allConsumption += this.allConts.get(i).consumption();
		}
		allConsumption += this.fuelConsumptionPerKM;
		double totalConsumption = allConsumption * this.currentPort.getDistance(p);
		if(this.fuel >= totalConsumption) {
			this.fuel -= totalConsumption;
			this.currentPort.outgoingShip(this);
			p.incomingShip(this);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * The method that returns the ArrayList of all containers in the ship.
	 * 
	 * @return allConts
	 * 
	 */
	public ArrayList<Container> getCurrentContainers(){
		Collections.sort(allConts);
		return allConts;
	}
	
	/**
	 * The method that returns ID of the ship.
	 * 
	 * @return ID
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * The method that returns fuel of the ship.
	 * 
	 * @return fuel
	 */
	public double getFuel() {
		return this.fuel;
	}
	
	/**
	 * The method that returns ship's port.
	 * 
	 * @return currentPort
	 */
	public Port getCurrentPort() {
		return this.currentPort;
	}

	/**
	 * The method that compares to ship with respect to their ID
	 * 
	 * @param otherShip ship that will be compared
	 */
	@Override
	public int compareTo(Ship otherShip) {
		return Integer.compare(getID(), otherShip.getID());
	}
	
	/**
	 * Getter for basics ArrayList
	 * @return basics ArrayList
	 */
	public ArrayList<Container> getBasics(){
		Collections.sort(this.basics);
		return this.basics;
	}
	
	/**
	 * Getter for heavys ArrayList
	 * @return heavys ArrayList
	 */
	public ArrayList<Container> getHeavys(){
		Collections.sort(this.heavys);
		return this.heavys;
	}

	/**
	 * Getter for liquids' ArrayList
	 * @return liqs ArrayList
	 */
	public ArrayList<Container> getLiqs(){
		Collections.sort(this.liqs);
		return this.liqs;
	}

	/**
	 * Getter for refrigerated ArrayList
	 * @return refs ArrayList
	 */
	public ArrayList<Container> getRefs(){
		Collections.sort(this.refs);
		return this.refs;
	}
}
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

