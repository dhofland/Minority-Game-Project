import java.util.ArrayList;
import java.util.Random;

import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;

public class SimMixedParameters extends SimStandard {
	boolean mem;
	boolean strat;
	boolean evolution;
	int rate = 10;

	
	public SimMixedParameters(int m, int n, int s, int turns, int rounds, boolean evolution) {
		super(m, n, s, turns, rounds);
		this.evolution = evolution;
	} 
	

	
	@Override
	protected void setUp() {
		// set manually
		mem = true;
		strat = true;
		agents = new ArrayList<Agent>();
		for (int i = 0; i < n; i ++) {
			int tempS = s;
			if (strat) {
				random = new Random(); 
				tempS = random.nextInt(s+1);
				while (tempS == 0) {
					tempS = random.nextInt(s+1);
				}
			}
			ArrayList<AgentStrategy> testList = new ArrayList<AgentStrategy>();
			ArrayList<AgentStrategy> sList = new ArrayList<AgentStrategy>();
			for (int j = 0; j < tempS; j ++) {
				sList.add(randomStrategy());
				if (evolution) {
					testList.add(randomStrategy());
				}
			}
			
			
			int tempM = m;
			if (mem) {
				tempM = random.nextInt(m+1);
				while (tempM == 0) {
					tempM = random.nextInt(m+1);
				}
			}
			
			Agent newAgent = new Agent(sList, tempM, tempS);
			if (evolution) {
				newAgent.genRate = true;
				newAgent.setTestList(testList);
			}
			agents.add(newAgent);
		}
	}
	
	@Override
	protected void adjustmentMethod(int j) {
		
		if (evolution && (j != 0) && (j % rate == 0)) {
			replace();
		}
	}
	
	protected void replace() {
		for (int i = 0; i < agents.size(); i ++) {
			agents.get(i).replaceWeakestStrategy(randomStrategy());
		}
	}
	
	
	
	public void outputMemAttendance() {
		double[][] mAv = new double[m][2];
		XYSeries series = new XYSeries("");
		for (int i = 0; i < agents.size(); i ++) {
			Agent current = agents.get(i);
			int agentM = current.m;
			double AgentSuccessRate = (((double) current.successes) / (double) (turns));
			series.add(agentM, AgentSuccessRate);
			mAv[agentM-1][0] += AgentSuccessRate;
			mAv[agentM-1][1] ++;
			
		}
		
		XYSeries memAv = new XYSeries("");
		for (int i = 0; i < mAv.length; i ++) {
			double avPerMem = mAv[i][0] / mAv[i][1];
			memAv.add(i + 1, avPerMem);
		}
		

	    PlotRatio scoreM = new PlotRatio(series, "", "m", "Success rate", false, false);
//	    scoreM.addSingleLine(memAv);
	    scoreM.pack();
	    RefineryUtilities.centerFrameOnScreen(scoreM);
	    scoreM.setVisible(true);
	}
	
	public void outputStratAttendance() {
		double[][] sAv = new double[s][2];

		XYSeries seriesStrat = new XYSeries("");
		for (int i = 0; i < agents.size(); i ++) {
			Agent current = agents.get(i);
			int agentS = current.sList.size();
			double AgentSuccessRate = (((double) current.successes) / (double) (turns));
			seriesStrat.add(agentS, AgentSuccessRate);
			sAv[agentS-1][0] += AgentSuccessRate;
			sAv[agentS-1][1] ++;
		}
		
		XYSeries stratAv = new XYSeries("");
		for (int i = 0; i < sAv.length; i ++) {
			double avPerStrat = sAv[i][0] / sAv[i][1];
			stratAv.add(i + 1, avPerStrat);
		}
		
		PlotRatio scoreM = new PlotRatio(seriesStrat, "", "|S|", "Success rate", false, false);
//	    scoreM.addSingleLine(stratAv);
	    scoreM.pack();
	    RefineryUtilities.centerFrameOnScreen(scoreM);
	    scoreM.setVisible(true);
		
	}
	
	public void outputStratMem() {
		double[][] mAv = new double[m][2];
		double[][] sAv = new double[s][2];
		XYSeries seriesStrat = new XYSeries("");
		for (int i = 0; i < agents.size(); i ++) {
			Agent current = agents.get(i);
			int agentS = current.sList.size();
			double AgentSuccessRate = (((double) current.successes) / (double) (turns));
			seriesStrat.add(agentS, AgentSuccessRate);
			sAv[agentS-1][0] += AgentSuccessRate;
			sAv[agentS-1][1] ++;
		}
				
		XYSeries seriesMem = new XYSeries("");
		for (int i = 0; i < agents.size(); i ++) {
			Agent current = agents.get(i);
			int agentM = current.m;
			double AgentSuccessRate = (((double) current.successes) / (double) (turns));
			seriesMem.add(agentM, AgentSuccessRate);
			mAv[agentM-1][0] += AgentSuccessRate;
			mAv[agentM-1][1] ++;
		}
		
		XYSeries memAv = new XYSeries("");
		for (int i = 0; i < mAv.length; i ++) {
			double avPerMem = mAv[i][0] / mAv[i][1];
			memAv.add(i + 1, avPerMem);
		}
		
		XYSeries stratAv = new XYSeries("");
		for (int i = 0; i < sAv.length; i ++) {
			double avPerStrat = sAv[i][0] / sAv[i][1];
			stratAv.add(i + 1, avPerStrat);
		}
		
		
	
	    PlotRatio scoreTotal = new PlotRatio(seriesMem, "", "|M|, |S|", "Success rate", false, false);
	    scoreTotal.addDataSetInformation(seriesStrat, "|M|", false);
	    scoreTotal.addTwoLines(memAv, stratAv);
	    scoreTotal.pack();
	    RefineryUtilities.centerFrameOnScreen(scoreTotal);
	    scoreTotal.setVisible(true);
		
	}
	
	
}
