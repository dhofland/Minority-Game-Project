import java.util.ArrayList;

public class AgentInformation extends Agent {
	int[][] frozen;
	AgentStrategy played = null;
	boolean isFrozen = true;
	int turnCount = 0;
	int turns;
	double threshold;
	
	
	public AgentInformation(ArrayList<AgentStrategy> strategies, int m, int n, boolean[] sharedMemory, int turns, double threshold) {
		super(strategies, m, n);
		this.memory = sharedMemory;
		frozen = new int[(int) Math.pow(2, m)][2];
		this.turns = turns;
		this.threshold = threshold;
	}
	
//	@Override
//	public void updatePerformance(int result)  {
//		frozen[booleanToNumeral(memory)][0] ++;
//		if (currentAction == 1) {
//			frozen[booleanToNumeral(memory)][1] ++;
//		}
//		
//		super.updatePerformance(result);
//	}
	
	@Override 
	public int action(){
		turnCount ++;
		AgentStrategy best = bestStrategy(sList);
		if (turnCount > (n * threshold)) {
			if (played == null) {
				played = best;
			} else {
				if (played != best) {
					isFrozen = false;
				}
			}
		}
		int result = best.action(memory);
		currentAction = result;
		if (result == 1) {
			attendanceY ++;		
		} else {
			attendanceN ++;
		}
		return result;
	}
	
	
//	private int booleanToNumeral(boolean[] input) {
//		String binary = "";
//		for (int i = 0; i < input.length; i ++) {
//			if (input[i]) {
//				binary += '1';
//			} else {
//				binary += '0';
//			}
//		}
//		return Integer.parseInt(binary, 2);
//	}
	
	public boolean isFrozen() {
		return isFrozen;
//		boolean result = true;
//		for (int i = 0; i < frozen.length; i ++) {
//			if (frozen[i][0] != frozen[i][1]) {
//				return false;
//			}
//		}
//		return result;
	}
}
