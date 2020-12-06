package goalStackPlanning;
import goalStackPlanning.goalStack;
public class initGS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		goalStack gstack = new goalStack();
		gstack.acceptGoalState();
		gstack.acceptInitialState();
		gstack.displayGoalState();
		gstack.displayStates();
		gstack.algorithm();
	}

}
