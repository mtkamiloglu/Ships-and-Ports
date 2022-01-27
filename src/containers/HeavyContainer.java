
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

public class HeavyContainer extends Container{
	
	public HeavyContainer(int ID, int weight){
		super(ID, weight);
		this.cType = "H";
	}

	public double consumption() {
		this.fuelConsumption = 3.00 * this.weight;
		return this.fuelConsumption;
	}

	@Override
	public boolean equals(Container other) {
		return false;
	}
	
}
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

