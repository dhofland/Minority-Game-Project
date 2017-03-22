import java.util.ArrayList;
import java.util.Random;

public class Agent {
	ArrayList<AgentStrategy> sList;
	ArrayList<AgentStrategy> testList;
	int m;
	int n;
	int attendanceY;
	int attendanceN;
	int successes;
	int currentAction;
	int net;
	int resultSign;
	int utility;
	boolean[] memory;
	Random random;
	boolean genRate = false;
	int startR;

	
	public Agent(ArrayList<AgentStrategy> strategies, int m, int n) {
		sList = strategies;
		this.m = m;
		this.n = n;
		this.attendanceN = 0;
		this.attendanceY = 0;
		this.successes = 0;
		this.currentAction = 0;
		this.utility = 0;
		memory = new boolean[m];
		random = new Random();
		for (int i = 0; i < m; i ++) {
			memory[i] = random.nextBoolean();
		}
	}
	

	
	
	protected AgentStrategy bestStrategy(ArrayList<AgentStrategy> list) {
		double bestScore = 0;
		AgentStrategy result = null;
	
		ArrayList<AgentStrategy> top = new ArrayList<AgentStrategy>();
		
		for (int i = 0; i < list.size(); i ++) {
			AgentStrategy current = list.get(i);
			if (i == 0) {
				bestScore = current.getPerformance();
				top.add(current);
			} else {
				if (current.getPerformance() == bestScore) {
					top.add(current);
				} else if (current.getPerformance() > bestScore) {
					top.clear();
					top.add(current);
					bestScore = current.getPerformance();
				}	
			}
			
		}
		int rdm = random.nextInt(top.size());
		return result = top.get(rdm);
	}
	
	public int action(){
		AgentStrategy best = bestStrategy(sList);
		int result = best.action(memory);
		currentAction = result;
		if (result == 1) {
			attendanceY ++;		
		} else {
			attendanceN ++;
		}
		return result;
	}
	
	// by g(x) = sign(A)
	public void updatePerformance(int result) {
		calculateResultSign(result);
		updateStrategyList(sList);
		updateMemory(result);
		
		//utility -= (currentAction*result);
		utility -= (currentAction * resultSign);
		
		if (resultSign != currentAction) {
			successes ++;
		} 
		if (genRate) {
			updateStrategyList(testList);
		}
	}
	
	protected void calculateResultSign(int result) {
		if (result > 0) {
			resultSign = 1;
			net = result;
		} else {
			net = -result;
			resultSign = -1;

		}
	}
	
	private void updateMemory(int result) {
		for (int i = 1; i < m; i ++) {
			memory[i-1] = memory[i];
		}
		if (result > 0) {
			memory[m-1] = true;
		} else {
			memory[m-1] = false;
		}
	
	}
	
	// 0, 1 ipv -1, 1
	private void updateStrategyList(ArrayList<AgentStrategy> list) {	
		
			for (int i = 0; i < list.size(); i ++) {
				AgentStrategy current = list.get(i);
				int act = current.action(memory);
				if (act != resultSign) {
					// g(x)
//					current.changePerformance(net);
					
					// g(x) from Challet and Zhang N/x-2
//					current.changePerformance((n/net)-2);
					
					
					// g(x) = sign(x)
					current.changePerformance(1);
					
					// sum/n
//					double prop = ((double) net / (double) n);
//					current.changePerformance(prop);
				}
			}
	}
	
	public void setTestList(ArrayList<AgentStrategy> tList) {
		testList = tList;
	}
	
	public void replaceWeakestStrategy(AgentStrategy newStrat) {
		AgentStrategy bestTest = bestStrategy(testList);
		AgentStrategy worstTest = worstStrategy(sList);
		
		if (bestTest.getPerformance() > worstTest.getPerformance()) {
			sList.remove(worstTest);
			sList.add(bestTest);
			testList.remove(bestTest);
			testList.add(newStrat);
		}
		
	}
	
	
	
	private AgentStrategy worstStrategy(ArrayList<AgentStrategy> list) {
		AgentStrategy weakest = null;
		int index = -1;
		double perf = -1;
		
		for (int i = 0; i < list.size(); i ++) {
			AgentStrategy current = list.get(i);
			if (i == 0) {
				weakest = current;
				index = i;
				perf = current.getPerformance();
			}
			if (current.getPerformance() > perf) {
				weakest = current;
				index = i;
				perf = current.getPerformance();
			}
		}
		
		return weakest;

	}
	
}
