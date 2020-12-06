package goalStackPlanning;
import java.util.Stack;

public class Stacks {
	Stack<stackBean> stack;
	int stackSize;
	public Stacks() {
		super();
		this.stack  = new Stack<stackBean>();
		this.stackSize = 0;
	}
	@Override
	public String toString() {
		String str;
		str = stack.toString();
		return str;
	}
}

