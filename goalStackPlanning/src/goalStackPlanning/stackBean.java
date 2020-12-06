package goalStackPlanning;

public class stackBean {
	int state;
	int heuristicValue;
	int stackNumber;
	int index;
	public stackBean(){
		state = 0;
		heuristicValue = 0;
	}
	public stackBean(int element){
		state = element;
		heuristicValue = 0;
	}
	@Override
	public String toString() {
		return "StackBean [state=" + state + ", heuristicValue=" + heuristicValue
				+ "]";
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getHeurusticValue() {
		return heuristicValue;
	}
	public void setHeurusticValue(boolean b) {
		if(b==true)
			this.heuristicValue = 1;
		else
			this.heuristicValue = -1;
	}

}