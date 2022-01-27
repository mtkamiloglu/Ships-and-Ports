
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;
import java.util.Collections;

import containers.Container;
import interfaces.IPort;
import ships.Ship;

public class Port implements IPort {
	/**
	 * The ID of the port
	 */
	private int ID;
	/**
	 * The X coordinate of the port 
	 */
	private double X;
	/**
	 * The Y coordinate of the port
	 */
	private double Y;
	/**
	 * The list of current containers in the port
	 */
	public ArrayList<Container> containers;
	/**
	 * List of ships that have been in port but are not currently in port
	 */
	public ArrayList<Ship> history;
	/**
	 * The list of current ships in port
	 */
	private ArrayList<Ship> current;
	/**
	 * The list of basic containers in the port 
	 */
	private ArrayList<Container> basics;
	/**
	 * The list of heavy containers in the port 
	 */
	private ArrayList<Container> heavys; 
	/**
	 * The list of refrigerated containers in the port 
	 */
	private ArrayList<Container> refs; 
	/**
	 * The list of liquid containers in the port 
	 */
	private ArrayList<Container> liqs;
	 
	public Port(int ID, double X, double Y){
		this.ID = ID;
		this.X = X;
		this.Y = Y;
		this.containers = new ArrayList<Container>(); this.history = new ArrayList<Ship>(); this.current = new ArrayList<Ship>();
		this.basics = new ArrayList<Container>(); this.heavys = new ArrayList<Container>(); this.liqs = new ArrayList<Container>(); this.refs = new ArrayList<Container>();
	}
	
	/**
	 * This method returns the distance between given port and this port
	 * @param other the other port that ship will go
	 * @return Distance between two ports 
	 */
	public double getDistance(Port other) {
		double xDistance = Math.pow(other.X - this.X, 2);
		double yDistance = Math.pow(other.Y - this.Y, 2);
		double Distance = Math.sqrt(xDistance + yDistance);
		return Distance;
	}
	
	/**
	 * This method adds incoming ship to this port's current
	 * 
	 * @param s the incoming ship
	 */
	@Override
	public void incomingShip(Ship s) {
		this.current.add(s);
		s.currentPort = this;
	}

	/**
	 * This method removes outgoing ship from this port's current and adds history ArrayList
	 * 
	 * @param s the outgoing ship
	 */
	@Override
	public void outgoingShip(Ship s) {
		int ind = this.current.indexOf(s);
		this.current.remove(ind);
		this.history.add(s);
	}
	
	/**
	 * This method returns ID of this port
	 * 
	 * @return ID of this port
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * THis method returns X coordinate of this port
	 * 
	 * @return X coordinate of this port 
	 */
	public double getX() {
		return this.X;
	}
	
	/**
	 * This method returns Y coordinate of this port
	 * 
	 * @return Y coordinate of this port
	 */
	public double getY() {
		return this.Y;
	}
	
	/**
	 * Getter for current ArrayList
	 * @return current ArrayList
	 */
	public ArrayList<Ship> getCurrent(){
		Collections.sort(this.current);
		return this.current;
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
	 * Getter for refrigerateds' ArrayList
	 * @return refs ArrayList
	 */
	public ArrayList<Container> getRefs(){
		Collections.sort(this.refs);
		return this.refs;
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
	 * Getter for heavys' ArrayList
	 * @return heavys ArrayList
	 */
	public ArrayList<Container> getHeavys(){
		Collections.sort(this.heavys);
		return this.heavys;
	}
	
}
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

