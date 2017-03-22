import java.util.ArrayList;

public class SimSoftMax extends SimStandard {
	double beta;
	
	public SimSoftMax(int m, int n, int s, int turns, int rounds, double beta) {
		super(m, n, s, turns, rounds);
		this.beta = beta;
	}
	
	@Override
	protected void setUp() {
		agents = new ArrayList<Agent>();
		for (int i = 0; i < n; i ++) {
			ArrayList<AgentStrategy> sList = new ArrayList<AgentStrategy>();
			for (int j = 0; j < s; j ++) {
				sList.add(randomStrategy());
			}
			Agent newAgent = new AgentSoftMax(sList, m, n, beta);
			agents.add(newAgent);
		}
	}
}
