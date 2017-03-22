import java.util.ArrayList;
import java.util.Random;
import java.util.*;

import org.jfree.ui.RefineryUtilities;

public class SimStandard {
	private int[] av;
	protected int[] scores;
	protected double sum;
	protected int m;
	protected int n;
	protected int s;
	protected int turns;
	protected int rounds;
	protected int exp;
	protected int exp2;
	protected double total;
	protected double average;
	protected double successRate;
	protected double sigma;
	protected double stdev;
	protected double stdevString;
	protected double sigmaString;
	protected double relStdev;
	ArrayList<Agent> agents;
	protected Random random;
	String srInput;
	String type;
	int sigmaInt;

	
	
	
	
	public SimStandard(int m, int n, int s, int turns, int rounds) {
		this.m = m;
		this.n = n;
		this.s = s;
		type = "Standard";
		this.rounds = rounds;
		av = new int[turns];
		scores = new int[turns];
		this.turns = turns;
		exp = (int) Math.pow(2, m);
		exp2 = (int) Math.pow(2, exp);
		stdev = 0;
		
		total = 0;
		
	} 	
	protected void simulate() {
		for (int i = 0; i < rounds; i ++) {
			setUp();
			for (int j = 0; j < turns; j ++) {
				playRound();
				scores[j] += sum;
				total += sum;
				sum = 0;
				adjustmentMethod(j);
			}			
		}
		calculateStats();
//		outputHist();
//		outputAttendancePlot();
		
	}
	
	protected void adjustmentMethod(int j){
	
	}

protected void calculateStats() {
	average = (total / (turns*rounds));
//	average = 0;
	for (int i = 0; i < turns; i ++) {
		av[i] = (scores[i] / rounds);
		double dev = av[i] - average;
		sigma += Math.pow(dev, 2);
	}
	
	// Old sigma
//	sigma = sigma / (turns*rounds);
//	stdev = Math.sqrt(sigma);
//	int stdevInt = (int) (stdev * 10000);
//	stdevString = (double) stdevInt / 10000;
//	relStdev = stdev/n;
	
	// Cf Moro
	sigma = sigma / (turns*rounds);
//	sigma = sigma / Math.pow(2, m);
	
	sigmaInt = (int) (sigma * 10000);
	sigmaString = (double) sigmaInt / 10000;
}

// attendance at +
public void outputHist() {
	successRate = 0;
	int[] attendanceYArray = new int[n]; 

    
    
    for (int i = 0; i < agents.size(); i ++) {
    	Agent current = agents.get(i);
    	successRate += current.successes;
    	attendanceYArray[i] = current.attendanceY;
  
    }
    
    
    successRate = ((successRate/(n*turns*rounds)) * 100);
    String srString = Double.toString(successRate);
    srInput = "";
    for (int i = 0; i < 5; i ++) {
    	srInput += srString.charAt(i);
    }
    
    PlotHistogram hist = new PlotHistogram(attendanceYArray, n, 100, "", "", "Attendance at +", "Number of Agents");
    hist.pack();
    RefineryUtilities.centerFrameOnScreen(hist);
    hist.setVisible(true);
}

// attendance in minority group
public void outputHistWins() {
	successRate = 0;
	int[] successes = new int[n]; 

    for (int i = 0; i < agents.size(); i ++) {
    	Agent current = agents.get(i);
    	successRate += current.successes;
    	successes[i] = current.successes;
  
    }
    
    
    successRate = ((successRate/(n*turns*rounds)) * 100);
    String srString = Double.toString(successRate);
    srInput = "";
    for (int i = 0; i < 5; i ++) {
    	srInput += srString.charAt(i);
    }
    
    PlotHistogram hist = new PlotHistogram(successes, n, 100, "", "", "Wins", "Number of Agents");
    hist.pack();
    RefineryUtilities.centerFrameOnScreen(hist);
    hist.setVisible(true);
}



public void outputAttendancePlot() {
    
    
//    String label = ("m = " + m + ", n = " + n + ", s = " + s + ", r = " + rounds + ", sigma: " + sigmaString);
//    String label = ("stdev = " + stdevString); // + ", Type: " + type);

    PlotXY score = new PlotXY(av, "");
	score.pack();
    RefineryUtilities.centerFrameOnScreen(score);
    score.setVisible(true);
}



protected void playRound() {
	for (int i = 0; i < agents.size(); i ++) {
		int act = agents.get(i).action();
		sum += act;
	}
	for (int i = 0; i < agents.size(); i ++) {
		agents.get(i).updatePerformance((int) sum);
	}
}

protected void setUp() {
	agents = new ArrayList<Agent>();
	for (int i = 0; i < n; i ++) {
		ArrayList<AgentStrategy> sList = new ArrayList<AgentStrategy>();
		for (int j = 0; j < s; j ++) {
			sList.add(randomStrategy());
		}
		Agent newAgent = new Agent(sList, m, n);
		agents.add(newAgent);
		newAgent.startR = 0;
	}
}

protected AgentStrategy randomStrategy() {
	AgentStrategy result = null;
	boolean[] vector = new boolean[exp];
	random = new Random();		
	for (int i = 0; i < exp; i ++) {
		vector[i] = random.nextBoolean();
	}
	result = new AgentStrategy(m, vector);
	return result;
}

public double sigma() {
	return sigma;
}

public int[] avData() {
	return av;
}
}
