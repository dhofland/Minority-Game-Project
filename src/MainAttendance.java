import java.awt.Color;
import java.util.*;

import org.jfree.ui.RefineryUtilities;

public class MainAttendance {
public static void main(String[] args) {
		
		int n = 2001; 
		int m = 8;
		int s = 8;
		int t = 100000;
		int rounds = 1;
		double threshold = 0.5;
		
//		SimStandard sim = new SimStandard(m, n, s, t, rounds);
		SimMixedParameters simMixed = new SimMixedParameters(m, n, s, t, rounds, true);
//		SimGenReplace sim = new SimGenReplace(m, n, s, t, rounds);
//		SimGenRate sim = new SimGenRate(m, n, s, t, rounds);
//		SimGenRateReplace sim = new SimGenRateReplace(m, n, s, t, rounds);
//		SimSoftMax sim = new SimSoftMax(m, n, s, t, rounds, 0);
//		SimInformation sim = new SimInformation(m, n, s, t, rounds, threshold);
//		SimUniversal sim = new SimUniversal(m, n, s, t, rounds);
		
		
		
		

		simMixed.simulate();
//		// Use only without evolution
		simMixed.outputStratMem();
//		simMixed.outputStratAttendance();
//		simMixed.outputMemAttendance();
		simMixed.outputHist();
		System.out.println(simMixed.sigma);
		System.out.println(simMixed.srInput);
		
//		sim.simulate();
//		sim.outputAttendancePlot();
////		sim.outputHist();
////		sim.outputHistWins();
//		System.out.println(sim.sigma);
//		System.out.println(sim.srInput);
		
//		sim.relAttHist();
	
	}
}

