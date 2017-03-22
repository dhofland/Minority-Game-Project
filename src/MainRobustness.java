import java.awt.Color;
import java.util.*;

import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;

public class MainRobustness {
public static void main(String[] args) {
		
		int n = 2001; 
		int m = 5;
		int s = 3;
		int t = 10000;
		int rounds = 1;
		double threshold = 0.5;
		int max = 11;
		
		
		XYSeries MSeries = new XYSeries("");
		for (int i = 2; i < max; i ++) {
			SimStandard sim = new SimStandard(i, n, s, t, rounds);
			sim.simulate();
			MSeries.add(i, Math.sqrt(sim.sigma));
		}
		
		XYSeries SSeries = new XYSeries("");
		for (int i = 2; i < max; i ++) {
			SimStandard sim = new SimStandard(m, n, i, t, rounds);
			sim.simulate();
			SSeries.add(i, Math.sqrt(sim.sigma));
		}
		
		PlotXY plot = new PlotXY(MSeries, "", "m, |S|", "\u03C3", false, false);
		plot.addDataset(SSeries, Color.BLUE);
		plot.pack();
	    RefineryUtilities.centerFrameOnScreen(plot);
	    plot.setVisible(true);
		
//		
		
	
	}
}

