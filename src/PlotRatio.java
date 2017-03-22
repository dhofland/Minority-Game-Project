import java.awt.Color;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
 

public class PlotRatio extends ApplicationFrame {
	XYPlot plot = null;
	XYSeriesCollection dataset = null;
	Font standardFont = new Font("Lucida Sans Unicode", Font.PLAIN, 25);
	boolean logX;
	boolean logY;
	boolean extraAxis = false;
	NumberAxis axis2;
	
	public  PlotRatio (XYSeries ratioSeries, String label, String Xlabel, String Ylabel, boolean logX, boolean logY) {
		super("");
		this.logX = logX;
		this.logY = logY;
		final XYSeriesCollection dataset = new XYSeriesCollection(ratioSeries);
	    final JFreeChart chart = ChartFactory.createScatterPlot(
	        label,
	        Xlabel,
	        Ylabel, 
	        dataset,
	        PlotOrientation.VERTICAL,
	        false,
	        true,
	        false
	    );
	    
        plot = (XYPlot) chart.getPlot();
	    if (logX) {
		    final NumberAxis domainAxis = new LogarithmicAxis(Xlabel);
		 
	        plot.setDomainAxis(domainAxis);
	    }
	    if (logY) {
	    	final NumberAxis rangeAxis = new LogarithmicAxis(Ylabel);
	        plot.setRangeAxis(rangeAxis);
	    }
	    plot.getDomainAxis().setLabelFont(standardFont);
	    plot.getRangeAxis().setLabelFont(standardFont);
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(600, 350));
	    setContentPane(chartPanel);  
	}
	
	public void addDataSetInformation(XYSeries dataset, String label, boolean extraAxis) {
		this.extraAxis = extraAxis;
		XYSeriesCollection col = new XYSeriesCollection(dataset);
		
		plot.setDataset(1, col);
		XYDotRenderer ren1 = new XYDotRenderer();		
		ren1.setDotWidth(4);
		ren1.setDotHeight(4);
		ren1.setSeriesPaint(0, Color.RED);
		XYDotRenderer ren0 = new XYDotRenderer();		
		ren0.setDotWidth(4);
		ren0.setDotHeight(4);
		ren0.setSeriesPaint(0, Color.BLUE);
		plot.setRenderer(0, ren0);
		plot.setRenderer(1, ren1);
		if (extraAxis) {
			axis2 = new NumberAxis(label);
			axis2.setLabelFont(standardFont);
			plot.setRangeAxis(1, axis2);
			plot.mapDatasetToRangeAxis(1, 1);
			plot.mapDatasetToRangeAxis(0,  0);
		}
	}
	
	public void addTwoLines(XYSeries dataset1, XYSeries dataset2) {
		XYSeriesCollection data1 = new XYSeriesCollection(dataset1);
		XYSeriesCollection data2 = new XYSeriesCollection(dataset2);
		StandardXYItemRenderer ren1 = new StandardXYItemRenderer();
		StandardXYItemRenderer ren2 = new StandardXYItemRenderer();
		ren1.setSeriesPaint(1,  Color.RED);
		ren2.setSeriesPaint(1, Color.BLUE);
		plot.setDataset(3, data1);
		plot.setDataset(4, data2);
		plot.setRenderer(3, ren1);
		plot.setRenderer(4, ren2);
		if (extraAxis) {
			plot.mapDatasetToRangeAxis(3, 0);
			plot.mapDatasetToRangeAxis(4, 1);
		}		
		
	}
	
	public void addSingleLine(XYSeries dataset1) {
		XYSeriesCollection data1 = new XYSeriesCollection(dataset1);
		StandardXYItemRenderer ren1 = new StandardXYItemRenderer();
		ren1.setSeriesPaint(0,  Color.BLUE);
		plot.setDataset(3, data1);
		plot.setRenderer(3, ren1);
		if (extraAxis) {
			plot.mapDatasetToRangeAxis(3, 0);
		}		
		
	}

}
