import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class PlotXY extends ApplicationFrame  {
	XYPlot plot;
	ChartPanel chartPanel;
	int counter = 1;
	Font standardFont = new Font("Lucida Sans Unicode", Font.PLAIN, 25);
	
	public PlotXY (int [] data, String label) {
		super("Title");
		final XYSeries series = new XYSeries("Average outcome");
	    for (int i = 0; i < data.length; i ++) {
	    	series.add(i, data[i]);
	    }
	    final XYSeriesCollection dataCol = new XYSeriesCollection(series);
	    final JFreeChart chart = ChartFactory.createXYLineChart(
	        label,
	        "t",
	        "A(t)", 
	        dataCol,
	        PlotOrientation.VERTICAL,
	        false,
	        true,
	        false
	    );

	    chartPanel = new ChartPanel(chart);
	    plot = (XYPlot) chart.getPlot();
	    plot.getDomainAxis().setLabelFont(standardFont);
	    plot.getRangeAxis().setLabelFont(standardFont);
//	    plot.getRenderer().setSeriesPaint(0, Color.BLACK);
	    	    
	    chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
	    setContentPane(chartPanel);  
	}
	
	// Redundant
	public PlotXY(XYSeries series, String title, String xAxis, String YAxis, boolean XLog, boolean YLog) {
		super("Title");
		final XYSeriesCollection dataCol = new XYSeriesCollection(series);
	    final JFreeChart chart = ChartFactory.createXYLineChart(
	        title,
	        xAxis,
	        YAxis, 
	        dataCol,
	        PlotOrientation.VERTICAL,
	        false,
	        true,
	        false
	    );
        plot = (XYPlot) chart.getPlot();
	    if (XLog) {
		    final NumberAxis domainAxis = new LogarithmicAxis(xAxis);
		 
	        plot.setDomainAxis(domainAxis);
	    }
	    if (YLog) {
	    	final NumberAxis rangeAxis = new LogarithmicAxis(YAxis);
	        plot.setRangeAxis(rangeAxis);
	    }

	    plot.getDomainAxis().setLabelFont(standardFont);
	    plot.getRangeAxis().setLabelFont(standardFont);
	    chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
	    setContentPane(chartPanel);  
	}
	
	public void addDataset(XYSeries series, Color color) {
		final XYSeriesCollection dataCol = new XYSeriesCollection(series);
		StandardXYItemRenderer ren1 = new StandardXYItemRenderer();
		ren1.setSeriesPaint(1,  color);
		plot.setDataset(counter, dataCol);
		plot.setRenderer(counter, ren1);
		counter ++;
	}
	


	
}
