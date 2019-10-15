package statistics_s;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends JFrame {
	
	public void PieChartEx() {
		// Create dataset
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("JavaWorld", new Integer(75));
		pieDataset.setValue("Other", new Integer(25));

		JFreeChart chart = ChartFactory.createPieChart("Sample Pie Chart", // Title
				pieDataset, // Dataset
				true // Show legend
				, true, false);

		// Create Panel
		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
		setSize(800, 400);
		setVisible(true);
	}
	
	//

	public static void main(String[] args) {
		PieChart pie =    new PieChart();
		pie.PieChartEx();
	}
}
