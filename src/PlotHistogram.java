import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

public class PlotHistogram extends ApplicationFrame {
	
	public PlotHistogram(int[] values, int n, int mod, String key, String title, String XAxis, String YAxis) {
		super("");
		 double[] data = new double[n];
		 
		 for (int i = 0; i < n; i ++) {
			 data[i] = values[i];
		 }
		 
		 HistogramDataset dataset = new HistogramDataset();
		 dataset.setType(HistogramType.FREQUENCY);
		 dataset.addSeries(key, data, mod);
		 JFreeChart chart = ChartFactory.createHistogram(
				 title, 
				 XAxis, 
				 YAxis, 
				 dataset, 
				 PlotOrientation.VERTICAL, 
				 false, 
				 false, 
				 false);
		 
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
	    setContentPane(chartPanel);  
	 }
	
}
