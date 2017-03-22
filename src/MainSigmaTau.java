import java.awt.Color;
import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;

public class MainSigmaTau {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int m = 4;
		double n = 1001.00000; 
		int s = 2;
		int t = 10000;
		int rounds = 1;
		double varOverN;
		PlotXY plot2 = null;
		
		ArrayList<Color> colList = new ArrayList<Color>();
		colList.add(Color.blue);
		colList.add(Color.black);
		colList.add(Color.GREEN);
		colList.add(Color.YELLOW);
		colList.add(Color.PINK);
		colList.add(Color.cyan);
		colList.add(Color.gray);
		colList.add(Color.ORANGE);

		int counter = 0;
		for (int k = m; k <= 10; k += 2) {
//			 low ratio: m = 4; n = 1001, s = 2		
//			 variance of tau with rho fixed
			double scale = 0.0001;
			final XYSeries ratioSeries = new XYSeries("");	
			for (int i = 0; i < 5; i  ++) {
				double increment;
				if (i == 4) {
					increment = 2;
				} else {
					increment = (scale / 2);
				}
				for (double j = scale; j <= (9*scale); j += scale) {
					SimSoftMax sim1 = new SimSoftMax(k, (int) n, s, t, rounds, j);
					sim1.simulate();
					double var = sim1.sigma;
					varOverN = var / n;
					// Do sqrt, versterkt verschillen < 1
					double varOverNRoot = Math.sqrt(varOverN);
					ratioSeries.add(j, varOverNRoot);
				}
				scale *= 10;	
			}
			
			if (k == m) {
//				PlotRatio plot = new PlotRatio(ratioSeries, "", "beta", "sigma^2/n", true, true);
				plot2 = new PlotXY(ratioSeries, "", "\u03B2", "\u03C3/ \u221A|N|", true, true);	
			} else {
				plot2.addDataset(ratioSeries, colList.get(counter));
				counter ++;
			}
		}
		
		plot2.pack();
	    RefineryUtilities.centerFrameOnScreen(plot2);
	    plot2.setVisible(true);	
			

	}
	
	
}
