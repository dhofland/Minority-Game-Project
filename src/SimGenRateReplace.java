import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimGenRateReplace extends SimGenRate {
	
	public SimGenRateReplace(int m, int n, int s, int turns, int rounds) {
		super(m, n, s, turns, rounds);
	}
	
	@Override
	protected void replace() {
		Map<Integer, Agent> bestMap = new HashMap<Integer, Agent>();
		int worstScore = 0;
		Map<Integer, Agent> worstMap = new HashMap<Integer, Agent>();
		int bestScore = 0;
		
		for (int i = 0; i < agents.size(); i ++) {
			Agent current = agents.get(i);
			current.replaceWeakestStrategy(randomStrategy());

			// could also be successes
			int curScore = current.utility;
			if (i == 0) {
				bestMap.put(i, current);
				worstMap.put(i, current);
				worstScore = curScore;
				bestScore = curScore;
			} else {
				if (curScore == bestScore) {
					bestMap.put(i, current);
				} if (curScore == worstScore) {
					worstMap.put(i, current);
				} if (curScore > bestScore) {
					bestMap.clear();
					bestMap.put(i, current);
					bestScore = curScore;
				} if (curScore < worstScore) {
					worstMap.clear();
					worstMap.put(i, current);
					worstScore = curScore;
				}	
			}
		}
		int rdmB = random.nextInt(bestMap.size());
		int rdmW = random.nextInt(worstMap.size());
		
		Iterator<Integer> bestIt = bestMap.keySet().iterator();
		for (int i = 0; i < rdmB; i ++) {
			bestIt.next();
		}
		int bestIndex = bestIt.next();
		Agent best = agents.get(bestIndex);
		
		Iterator<Integer> worstIt = worstMap.keySet().iterator();
		for (int i = 0; i < rdmW; i ++) {
			worstIt.next();
		}
		int worstIndex = worstIt.next();
		
		ArrayList<AgentStrategy> bestStrategies = best.sList;
		ArrayList<AgentStrategy> newList = new ArrayList<AgentStrategy>();
		
		// copy strategy; reset to zero;
		for (int i = 0; i < bestStrategies.size(); i ++) {
			AgentStrategy newStrat = new AgentStrategy(m, bestStrategies.get(i).responseVector);
			newList.add(newStrat);
		}
		int rdmS = random.nextInt(bestStrategies.size());
		newList.remove(rdmS);
		newList.add(rdmS, randomStrategy());
		Agent newAgent = new Agent(newList, m, n);
		
		ArrayList<AgentStrategy> testList = new ArrayList<AgentStrategy>();
		for (int j = 0; j < s; j ++) {
			testList.add(randomStrategy());
		}
		newAgent.genRate = true;
		newAgent.setTestList(testList);
		
//		newAgent.utility = best.utility;
//		newAgent.memory = best.memory.clone();
		agents.remove(worstIndex);
		agents.add(newAgent);
//		agents.add(best);
	}
}
