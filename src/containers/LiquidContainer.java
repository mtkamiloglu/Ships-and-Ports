
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;

public class LiquidContainer extends HeavyContainer{
	
	public LiquidContainer(int ID, int weight) {
		super(ID, weight);
		this.cType = "L";
	}

	public double consumption() {
		this.fuelConsumption = 5.00 * this.weight;
		return this.fuelConsumption;
	}


}
//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

