
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import ships.*;
import containers.*;
import ports.Port;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {

		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));

		int N = in.nextInt();

		final ArrayList<Ship> ships = new ArrayList<Ship>();
		final ArrayList<Port> ports = new ArrayList<Port>();
		final ArrayList<Container> conts = new ArrayList<Container>();

		int containerCount = 0;
		int shipCount = 0;
		int portCount = 0;

		while(in.hasNext()) {
			String data = in.nextLine();
			String[] els = data.split(" ");

			//Creating a container   (Working!)
			if(els[0].equals("1")) {
				int portID = Integer.parseInt(els[1]);
				int pWeight = Integer.parseInt(els[2]);
				if(els.length == 4) {
					if (els[3].equals("L")) {
						conts.add(new LiquidContainer(containerCount, pWeight));
						ports.get(portID).containers.add(conts.get(containerCount));
						ports.get(portID).getLiqs().add(conts.get(containerCount));
						conts.get(containerCount).setCurrPort(ports.get(portID));
					}
					else if(els[3].equals("R")) {
						conts.add(new RefrigeratedContainer(containerCount, pWeight));
						ports.get(portID).containers.add(conts.get(containerCount));
						ports.get(portID).getRefs().add(conts.get(containerCount));
						conts.get(containerCount).setCurrPort(ports.get(portID));
					}
				}
				else if(pWeight<=3000 && els.length == 3) {
					conts.add(new BasicContainer(containerCount, pWeight));
					ports.get(portID).containers.add(conts.get(containerCount));
					ports.get(portID).getBasics().add(conts.get(containerCount));
					conts.get(containerCount).setCurrPort(ports.get(portID));}
				else if(pWeight>3000) {
					conts.add(new HeavyContainer(containerCount, pWeight));
					ports.get(portID).containers.add(conts.get(containerCount));
					ports.get(portID).getHeavys().add(conts.get(containerCount));
					conts.get(containerCount).setCurrPort(ports.get(portID));
				}
				containerCount++;
			}

			//Creating a ship   (Working!)
			if(els[0].equals("2")) {
				int portID = Integer.parseInt(els[1]);
				int pTotalWeightCapacity = Integer.parseInt(els[2]);
				int pMaxNumberOfAllContainers = Integer.parseInt(els[3]);
				int pMaxNumberOfHeavyContainers = Integer.parseInt(els[4]);
				int pMaxNumberOfRefrigeratedContainers = Integer.parseInt(els[5]);
				int pMaxNumberOfLiquidContainers = Integer.parseInt(els[6]);
				double pFuelConsumptionPerKM = Double.parseDouble(els[7]);
				ships.add(new Ship(shipCount, ports.get(portID), pTotalWeightCapacity, pMaxNumberOfAllContainers, pMaxNumberOfHeavyContainers, pMaxNumberOfRefrigeratedContainers, pMaxNumberOfLiquidContainers, pFuelConsumptionPerKM));
				shipCount++;
			}

			//Creating a port   (Working!)
			if(els[0].equals("3")) {
				//System.out.println("Creating port");
				double pX = Double.parseDouble(els[1]);
				double pY = Double.parseDouble(els[2]);
				ports.add(new Port(portCount, pX, pY));
				portCount++;
			}

			//Loading a container   (Working!)
			if(els[0].equals("4")) {
				//System.out.println("Loading a container");
				int shipID = Integer.parseInt(els[1]);
				int contID = Integer.parseInt(els[2]);
				if(conts.get(contID).getContainerType().equals("H")) {
					if(ships.get(shipID).load(conts.get(contID))) {
						ships.get(shipID).getCurrentContainers().add(conts.get(contID));
						ships.get(shipID).getHeavys().add(conts.get(contID));
						ships.get(shipID).getCurrentPort().containers.remove(conts.get(contID));
						ships.get(shipID).getCurrentPort().getHeavys().remove(conts.get(contID));
						ships.get(shipID).currHeavyContainer++; ships.get(shipID).currTotalContainer++;
						ships.get(shipID).currentWeight = ships.get(shipID).currentWeight + conts.get(contID).getWeight(); 
					}
				}
				else if(conts.get(contID).getContainerType().equals("R")) {
					if(ships.get(shipID).load(conts.get(contID))) {						
						ships.get(shipID).getCurrentContainers().add(conts.get(contID));
						ships.get(shipID).getRefs().add(conts.get(contID));
						ships.get(shipID).getCurrentPort().containers.remove(conts.get(contID));
						ships.get(shipID).getCurrentPort().getRefs().remove(conts.get(contID));
						ships.get(shipID).currHeavyContainer++; ships.get(shipID).currRefrigeratedContainer++; ships.get(shipID).currTotalContainer++;
						ships.get(shipID).currentWeight = ships.get(shipID).currentWeight + conts.get(contID).getWeight();
					}
				}
				else if(conts.get(contID).getContainerType().equals("L")) {
					if(ships.get(shipID).load(conts.get(contID))) {
						ships.get(shipID).getCurrentContainers().add(conts.get(contID));
						ships.get(shipID).getLiqs().add(conts.get(contID));
						ships.get(shipID).getCurrentPort().containers.remove(conts.get(contID));
						ships.get(shipID).getCurrentPort().getLiqs().remove(conts.get(contID));
						ships.get(shipID).currHeavyContainer++; ships.get(shipID).currLiquidContainer++; ships.get(shipID).currTotalContainer++;
						ships.get(shipID).currentWeight = ships.get(shipID).currentWeight + conts.get(contID).getWeight();
					}
				}
				else if(conts.get(contID).getContainerType().equals("B")) {
					if(ships.get(shipID).load(conts.get(contID))) {
						ships.get(shipID).getCurrentContainers().add(conts.get(contID));
						ships.get(shipID).getBasics().add(conts.get(contID));
						ships.get(shipID).getCurrentPort().containers.remove(conts.get(contID));
						ships.get(shipID).getCurrentPort().getBasics().remove(conts.get(contID));
						ships.get(shipID).currTotalContainer++;
						ships.get(shipID).currentWeight = ships.get(shipID).currentWeight + conts.get(contID).getWeight();
					}
				}
			}

			//Unloading a container from a ship   (Working!)	
			if(els[0].equals("5")) {
				int shipID = Integer.parseInt(els[1]);
				int contID = Integer.parseInt(els[2]);
//				boolean statement = ships.get(shipID).unLoad(conts.get(contID)); 
				ships.get(shipID).getCurrentContainers().remove(conts.get(contID));
				ships.get(shipID).getCurrentPort().containers.add(conts.get(contID));
				conts.get(contID).setCurrPort(ships.get(shipID).currentPort);
				if(conts.get(contID).getContainerType().equals("B")) {
					ships.get(shipID).getBasics().remove(conts.get(contID));
					ships.get(shipID).getCurrentPort().getBasics().add(conts.get(contID));
				}
				else if(conts.get(contID).getContainerType().equals("H")) {
					ships.get(shipID).getCurrentPort().getHeavys().add(conts.get(contID));
					ships.get(shipID).getHeavys().remove(conts.get(contID));
				}
				else if(conts.get(contID).getContainerType().equals("L")) {
					ships.get(shipID).getLiqs().remove(conts.get(contID));
					ships.get(shipID).getCurrentPort().getLiqs().add(conts.get(contID));
				}
				else if(conts.get(contID).getContainerType().equals("R")) {
					ships.get(shipID).getRefs().remove(conts.get(contID));
					ships.get(shipID).getCurrentPort().getRefs().add(conts.get(contID));
				}
			}

			//Ships travel from one port to another  (Working!)
			if(els[0].equals("6")) {
				int shipID = Integer.parseInt(els[1]);
				int portID = Integer.parseInt(els[2]);
//				boolean statement = ships.get(shipID).sailTo(ports.get(portID));			
			}



			//Fuel adding to a ship   (Working!)
			if(els[0].equals("7")) {
				int shipID = Integer.parseInt(els[1]);
				double amount = Double.parseDouble(els[2]);
				ships.get(shipID).reFuel(amount);
			}


			//Printing the ports
			for(int i=0; i<ports.size(); i++) {
				out.println("Port " + ports.get(i).getID() + ": (" + String.format("%.2f", ports.get(i).getX()) + ", "+String.format("%.2f", ports.get(i).getY()) + ")");

				//Printing basics containers on a port
				if(ports.get(i).getBasics().size() != 0) {
					out.print("  BasicContainer:");
					for(int m=0; m<ports.get(i).getBasics().size(); m++) {
						out.print(" " + ports.get(i).getBasics().get(m).getID());
					}
					out.println();
				}
				//Printing heavy containers on a port
				if(ports.get(i).getHeavys().size() != 0) {
					out.print("  HeavyContainer:");
					for(int n=0; n<ports.get(i).getHeavys().size(); n++) {
						out.print(" " + ports.get(i).getHeavys().get(n).getID());
					}
					out.println();
				}
				//Printing refrigerated containers on a port
				if(ports.get(i).getRefs().size() != 0) {
					out.print("  RefrigeratedContainer:");
					for(int b=0; b<ports.get(i).getRefs().size(); b++) {
						out.print(" " + ports.get(i).getRefs().get(b).getID());
					}
					out.println();
				}
				//Printing liquid containers on a port
				if(ports.get(i).getLiqs().size() != 0) {
					out.print("  LiquidContainer:");
					for(int v=0; v<ports.get(i).getLiqs().size(); v++) {
						out.print(" " + ports.get(i).getLiqs().get(v).getID());
					}
					out.println();
				}

				//Printing ships
				for(int z=0; z<ports.get(i).getCurrent().size(); z++) {
					out.println("  Ship " + ports.get(i).getCurrent().get(z).getID() + ": " + String.format("%.2f", ports.get(i).getCurrent().get(z).getFuel()));
					//Printing basic containers on a ship
					if(ports.get(i).getCurrent().get(z).getBasics().size() != 0) {
						out.print("    BasicContainer:");
						for(int x=0; x<ports.get(i).getCurrent().get(z).getBasics().size(); x++) {
							out.print(" " + ports.get(i).getCurrent().get(z).getBasics().get(x).getID());
						}
						out.println();
					}
					//Printing heavy containers on a ship
					if(ports.get(i).getCurrent().get(z).getHeavys().size() != 0) {
						out.print("    HeavyContainer:");
						for(int q =0; q<ports.get(i).getCurrent().get(z).getHeavys().size(); q++) {
							out.print(" " + ports.get(i).getCurrent().get(z).getHeavys().get(q).getID());
						}
						out.println();
					}
					//Printing refrigerated containers on a ship
					if(ports.get(i).getCurrent().get(z).getRefs().size() != 0) {
						out.print("    RefrigeratedContainer:");
						for(int w =0; w<ports.get(i).getCurrent().get(z).getRefs().size(); w++) {
							out.print(" " + ports.get(i).getCurrent().get(z).getRefs().get(w).getID());
						}
						out.println();
					}
					//Printing liquid containers on a ship
					if(ports.get(i).getCurrent().get(z).getLiqs().size() != 0) {
						out.print("    LiquidContainer:");
						for(int e=0; e<ports.get(i).getCurrent().get(z).getLiqs().size(); e++) {
							out.print(" " + ports.get(i).getCurrent().get(z).getLiqs().get(e).getID());	
						}
						out.println();
					}
				}
			}

		}
		out.close();
	}
}	
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

