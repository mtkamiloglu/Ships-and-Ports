
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

public class BasicContainer extends Container{ 
	
	public BasicContainer(int ID, int weight){
		super(ID, weight);
		this.cType="B";
	}

	public double consumption() {
		this.fuelConsumption = 2.50 * this.weight;
		return this.fuelConsumption;
	}

	@Override
	public boolean equals(Container other) {
		return false;
	}
	
}
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

