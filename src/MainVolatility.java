import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;

public class MainVolatility {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int m = 4;
		double n = 1001.00000; 
		int s = 2;
		int t = 10000;
		int rounds = 1;
		double varOverN;
		
		// variance over rho
		final XYSeries ratioSeries = new XYSeries("");		
		double scale = n / 1000;
		
		
		// Original var over rho - outdated
//		for (double i = n/100; i < 100*n; i += 15.00000) {
//			double temp = (Math.log10(i) / Math.log10(2));
//			m = (int) temp;
//			if (m > 0) {
//				SimStandard sim1 = new SimStandard(m, (int) n, s, t, rounds);
//				sim1.simulate();
//				double var = sim1.sigma;
//				varOverN = var / n;
//				double rho = (Math.pow(2, temp)/n);
//				ratioSeries.add(rho, varOverN);
//			}
//		}
		
		// log-adjusted
		for (int i = 0; i < 5; i ++) {
			double tempScale = scale;
			for (double j = tempScale; j <= (9*tempScale); j += tempScale) {
				double temp = (Math.log10(j) / Math.log10(2));
				m = (int) temp;
				if (m > 0) {
					// meerdere per scale voor extra density
					for (int k = 0; k < 5; k ++) {
						SimStandard sim1 = new SimStandard(m, (int) n, s, t, rounds);
//						SimGenRate sim1 = new SimGenRate(m, (int) n, s, t, rounds);
						sim1.simulate();
						double var = sim1.sigma;
						varOverN = var / n;
						double rho = (Math.pow(2, temp)/n);
						ratioSeries.add(rho, varOverN);
					}
					
				}
			}
			scale *= 10;
		}
		
		// outdated
		// low ratio: m = 4; n = 1001, s = 2		
		// variance of tau with rho fixed
//		double scale = 0.001;
//		final XYSeries ratioSeries = new XYSeries("");	
//		for (int i = 0; i < 8; i  ++) {
//			for (double j = scale; j <= (3*scale); j += scale) {
//				SimSoftMax sim1 = new SimSoftMax(m, (int) n, s, t, rounds, j);
//				sim1.simulate();
//				double var = sim1.sigma;
//				varOverN = var / n;
//				ratioSeries.add(j, sim1.stdev);
//			}
//			scale *= 10;
//			
//		}
		
		
		
		
		PlotRatio plot = new PlotRatio(ratioSeries, "", "\u03B1", "\u03C3\u00B2/n", true, true);
		plot.pack();
	    RefineryUtilities.centerFrameOnScreen(plot);
	    plot.setVisible(true);	
	}
	
	
}
