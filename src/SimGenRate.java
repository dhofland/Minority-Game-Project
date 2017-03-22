import java.util.ArrayList;

public class SimGenRate extends SimStandard {
	int rate;
	
	// Each agent changes its worst strategy at some rate
	public SimGenRate(int m, int n, int s, int turns, int rounds) {
		super(m, n, s, turns, rounds);
	} 
	
	@Override
	protected void adjustmentMethod(int j) {
		if ((j != 0) && (j % rate == 0)) {
			replace();
		}
	}
	
	@Override
	protected void setUp() {
		rate = 10;
		agents = new ArrayList<Agent>();
		for (int i = 0; i < n; i ++) {
			ArrayList<AgentStrategy> sList = new ArrayList<AgentStrategy>();
			ArrayList<AgentStrategy> testList = new ArrayList<AgentStrategy>();
			for (int j = 0; j < s; j ++) {
				sList.add(randomStrategy());
				testList.add(randomStrategy());
			}
			Agent newAgent = new Agent(sList, m, n);
			newAgent.setTestList(testList);
			newAgent.genRate = true;
			agents.add(newAgent);
		}
		
	}
	
	

	
	@Override
	public void outputAttendancePlot() {
		type = "Replace Strategy";
		super.outputAttendancePlot();
	}
	
	protected void replace() {
		for (int i = 0; i < agents.size(); i ++) {
			agents.get(i).replaceWeakestStrategy(randomStrategy());
		}
	}
}
