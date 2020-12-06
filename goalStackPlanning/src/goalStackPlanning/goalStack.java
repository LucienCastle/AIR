package goalStackPlanning;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class goalStack {
	int noOfStacks;
    Stacks stacks[]; 
    Stacks goalStacks[]; 
    Stacks tempStack = new Stacks();
    Scanner scan = new Scanner(System.in);
	int element;
	
	public void acceptInitialState(){
		System.out.println("\n----------------->> INITIAL STATE <<------------------");
		System.out.print("\nEnter number of Stacks : ");
		noOfStacks = scan.nextInt();
		stacks = new Stacks[noOfStacks];
		for(int i=0;i<noOfStacks;i++){
			System.out.println("Stack "+i+" : \n\t Size : ");
			stacks[i] = new Stacks();
			stacks[i].stackSize = scan.nextInt();
			for(int j = 0;j<stacks[i].stackSize;j++){
				System.out.println("Element "+j+" : ");
				element = scan.nextInt();
				stacks[i].stack.add(new stackBean(element));
				stacks[i].stack.get(j).stackNumber = j;
			}
		}
		displayStates();
		stackBean e;
		for(int i=0;i<noOfStacks;i++){
			Iterator<stackBean> itr = stacks[i].stack.listIterator();
			while(itr.hasNext()){
				e=itr.next();
				calculateHeuristic(e,i,stacks[i].stack.indexOf(e));
			}
		}
	}
	
	public void acceptGoalState(){
		System.out.println("\n----------------->> GOAL STATE <<------------------");
		System.out.print("\nEnter number of Stacks : ");
		noOfStacks = scan.nextInt();
		goalStacks = new Stacks[noOfStacks];
		for(int i=0;i<noOfStacks;i++){
			System.out.println("Stack "+i+" : \n\t Size : ");
			goalStacks[i] = new Stacks();
			goalStacks[i].stackSize = scan.nextInt();
			for(int j = 0;j<goalStacks[i].stackSize;j++){
				System.out.println("Element "+j+" : ");
				element = scan.nextInt();
				goalStacks[i].stack.add(new stackBean(element));
				goalStacks[i].stack.get(j).stackNumber = j;
			}
		}
	}
	
	public void displayStates(){
		System.out.println("\n----------------->>CURRENT STATE <<------------------");
		for(int i=0;i<noOfStacks;i++){
			System.out.println("Stack "+i+" : "+stacks[i].stack.toString());
		}
		
	}
	
	public void displayGoalState(){
		System.out.println("\n----------------->> GOAL STATE <<------------------");
		for(int i=0;i<noOfStacks;i++){
			System.out.println("Stack "+i+" : "+goalStacks[i].stack.toString());
		}	
	}
	
	public void algorithm(){
		stackBean e;
		int iteration = 0;
		while(!goalStateReached()) {
			System.out.println("-------------------------------------------------------->> Iteration "+iteration);
			//Part 1: Add wrong states to the temp stack
			for(int i=0;i<noOfStacks;i++){
				Iterator<stackBean> itr = stacks[i].stack.listIterator();
				while(itr.hasNext()){
					e = itr.next();
					if(e.heuristicValue ==-1){
						tempStack.stack.add(e);
						itr.remove();					
					}
				}
				
				displayStates();
				displayTempStack();
			}
	
			//Part 2 : pop each state from temp stack and add to the right place
			boolean flag =false;
			while(!tempStack.stack.isEmpty()){
				e = tempStack.stack.pop();
				e.heuristicValue = 1;
				for(int i=0;i<noOfStacks;i++){
					stacks[i].stack.push(e);
					//check if the element is added at correct place
					if((flag=calculateHeuristic(e,i,stacks[i].stack.indexOf(e)))==true){ //means at right position
						break; 
					}else { 
						stacks[i].stack.pop();//try other stack; pop from this stack
						flag=false;
					}
				}
				if(flag==false){//means no correct position found in any of the stacks
					e.heuristicValue = -1;
					stacks[noOfStacks-1].stack.push(e);
				}
				displayStates();
				displayTempStack();
			}
			iteration++;
		}	
	}
	
	public void displayTempStack(){
		System.out.println("\n----------------->> TEMP STACK <<------------------");
		System.out.println(""+tempStack.toString());	
	}
	
	public boolean calculateHeuristic(stackBean e,int stackNumber,int pos){
		int goalPos;	
		if((goalPos = test(stackNumber,e))==pos) {
			stacks[stackNumber].stack.get(pos).heuristicValue = 1;
			return true;
		}
		stacks[stackNumber].stack.get(pos).heuristicValue = -1;
		return false;	
	}
	
	public int test(int stackNumber,stackBean e) {
		Iterator<stackBean> itr = goalStacks[stackNumber].stack.listIterator();
		stackBean s ;
		while(itr.hasNext()) {
			s = itr.next();
			if(s.state == e.state) {
				return goalStacks[stackNumber].stack.indexOf(s);			
			}
		}
		return -1;
	}
	
	public boolean goalStateReached() {
		for(int i=0;i<noOfStacks;i++) {
			Iterator<stackBean> itr = stacks[i].stack.listIterator();
			while(itr.hasNext()) {
				if(itr.next().heuristicValue !=1)
						return false;
			}
		}
		return true;
	}
}
