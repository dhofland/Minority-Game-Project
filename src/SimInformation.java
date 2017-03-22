import java.util.ArrayList;
import java.util.Random;

public class SimInformation extends SimStandard {
	double[] mAv;
	double[][] condAv;
	boolean[] sharedInfo;
	double H;
	double frozen;
	int exp;
	Random randombool;
	double threshold;
	
	public SimInformation(int m, int n, int s, int turns, int rounds, double threshold) {
		super(m, n, s, turns, rounds);
		this.threshold = threshold;
		exp = (int) Math.pow(2, m);
		mAv = new double[exp];
		condAv = new double[exp][2];
		sharedInfo = new boolean[m];
		randombool = new Random();
		for (int i = 0; i < m; i ++) {
			sharedInfo[i] = randombool.nextBoolean();
		}
	}
	
	@Override
	protected void setUp() {
		agents = new ArrayList<Agent>();
		for (int i = 0; i < n; i ++) {
			ArrayList<AgentStrategy> sList = new ArrayList<AgentStrategy>();
			for (int j = 0; j < s; j ++) {
				sList.add(randomStrategy());
			}
			Agent newAgent = new AgentInformation(sList, m, n, sharedInfo.clone(), turns, threshold);
			agents.add(newAgent);
		}
	}
	
	@Override
	protected void simulate() {
		for (int i = 0; i < rounds; i ++) {
			setUp();
			for (int j = 0; j < turns; j ++) {
				playRound();
				scores[j] += sum;
				int index = booleanToNumeral(sharedInfo);
				condAv[index][0] ++;
//				condAv[index][1] += sum;
//				
				if (sum > 0) {
					condAv[index][1] --;
				} else {
					condAv[index][1] ++;
				}
				updateSharedInfo((int) sum);
				// update sharedInfo
				total += sum;
				sum = 0;
				adjustmentMethod(j);
			}			
		}
		super.calculateStats();
		calculateH();
		calculateFrozen();
//		outputHist();
//		outputAttendancePlot();
		
	}
	
	private void updateSharedInfo(int result) {
		for (int i = 1; i < m; i ++) {
			sharedInfo[i-1] = sharedInfo[i];
		}
		if (result > 0) {
			sharedInfo[m-1] = true;
		} else {
			sharedInfo[m-1] = false;
		}
	
	}
	
	
	private void calculateH() {
		
		for (int i = 0; i < mAv.length; i ++) {
			double totalTimes = condAv[i][0];
			double positive = (condAv[i][1]);
			double fracOfN = (totalTimes / turns);
			if (totalTimes != 0 && positive != 0) {
				double prob = (positive / totalTimes);
				H += Math.pow((prob), 2);
			}
			
		}
		H = H / (double) exp;
		
		
//		for (int i = 0; i < mAv.length; i ++) {
//			H += Math.pow((mAv[i] / n), 2);
//		}
//		H = H / (double) exp;
	}
	
	private void calculateFrozen() {
		for (int i = 0; i < agents.size(); i ++) {
			AgentInformation current = (AgentInformation) agents.get(i);
			if (current.isFrozen() == true) {
				frozen ++;
			}
		}
		frozen = frozen / (double) n;
	}
	
	
	private int booleanToNumeral(boolean[] input) {
		String binary = "";
		for (int i = 0; i < input.length; i ++) {
			if (input[i]) {
				binary += '1';
			} else {
				binary += '0';
			}
		}
		return Integer.parseInt(binary, 2);
			
	}
}
