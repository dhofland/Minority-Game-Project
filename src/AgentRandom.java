import java.util.ArrayList;

public class AgentRandom extends Agent {
	public AgentRandom(ArrayList<AgentStrategy> strategies, int m, int n) {
		super(strategies, m, n);
	}
	
	@Override
	public int action(){
		if (random.nextBoolean()) {
			attendanceY ++;
			currentAction = 1;
			return 1;
				
		} else {
			attendanceN ++;
			currentAction = -1;
			return -1;
		}
	}
	
}
