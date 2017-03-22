import java.util.ArrayList;

public class SimUniversal extends SimStandard {
	public SimUniversal(int m, int n, int s, int turns, int rounds) {
		super(m, n, s, turns, rounds);
	}
	
	// universally rational players
//	@Override
//	protected void setUp() {
//		AgentStrategy shared = randomStrategy();
//		agents = new ArrayList<Agent>();
//		for (int i = 0; i < n; i ++) {
//			ArrayList<AgentStrategy> sList = new ArrayList<AgentStrategy>();
//				sList.add(shared);
//			Agent newAgent = new Agent(sList, m, n);
//			agents.add(newAgent);
//		}
//	}
	
	// Randomising players
	@Override
	protected void setUp() {
		agents = new ArrayList<Agent>();
		for (int i = 0; i < n; i ++) {
			ArrayList<AgentStrategy> sList = new ArrayList<AgentStrategy>();
			Agent newAgent = new AgentRandom(sList, m, n);
			agents.add(newAgent);
		}
	}
}
