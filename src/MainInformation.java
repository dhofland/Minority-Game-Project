import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;

public class MainInformation {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int m = 7;
		double n = 101.00000; 
		int s = 2;
		int t = 10000;
		int rounds = 1;
		double varOverN;
		double exp = Math.pow(2, m);
		double threshold = 0.5;
		
		
		final XYSeries ratioSeries = new XYSeries("");
		final XYSeries frozenSeries = new XYSeries("Frozen");
		
		int logTimes = 4;
		
		double[][] frozenAv = new double [10*logTimes][3];
		double [][] HAv = new double[10*logTimes][3];
		
		// Information H + Frozen Phi
		// extra repetition k-times for more density
		int counter = 0;
		double l = 6; // repetitions
		for (int k = 0; k < l; k ++) {
			double scale = 0.1;		
			for (int i = 0; i < logTimes; i ++) {
				double tempScale = scale;
				for (double j = tempScale; j <= (9*tempScale); j += tempScale) {
					int nTemp =(int) (exp/j);
					if (nTemp % 2 == 0) {
						nTemp ++;
					}
					SimInformation sim1 = new SimInformation(m, nTemp, s, t, rounds, threshold);
					double alpha =((double) exp)/ ((double) nTemp);
					sim1.simulate();
					double H = sim1.H;
					double frozen = sim1.frozen;
					frozenSeries.add(alpha, frozen);
					ratioSeries.add(alpha, H);
					frozenAv[i][0] += frozen;
					HAv[i][0] += H;
					frozenAv[i][1] = alpha;
					HAv[i][1] = alpha;
					frozenAv[i][2] ++;
					HAv[i][2] ++;

					counter ++;
				}
				scale *= 10;
			}
		}
		
		// Kan proberen met mapping
		XYSeries frozenAvSeries = new XYSeries("");
		XYSeries hAvSeries = new XYSeries("");
		for (int i = 0; i < frozenAv.length; i ++) {
			double frozenAvX = (frozenAv[i][0] / frozenAv[i][2]);
			if (frozenAvX <= 0) frozenAvX = 0.000001;
			double hAv = (HAv[i][0] / HAv[i][2]);
			if (hAv <= 0) hAv = 0.00001;
			double frozenAlpha = frozenAv[i][1];
			if (frozenAlpha <= 0) frozenAlpha = 0.1;
			double hAlpha = HAv[i][1];
			if (hAlpha <= 0) hAlpha = 0.1;
		
			frozenAvSeries.add(frozenAlpha,frozenAvX );
			hAvSeries.add(hAlpha, hAv);
		}
		

		
		
		
		PlotRatio plot = new PlotRatio(frozenSeries, "", "\u03B1", "\u03A6", true, false);
		plot.addDataSetInformation(ratioSeries, "\u0397", true); 
//		plot.addTwoLines(frozenAvSeries, hAvSeries);
		
		plot.pack();
	    RefineryUtilities.centerFrameOnScreen(plot);
	    plot.setVisible(true);	
	}
}
	


