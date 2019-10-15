package statistics_s;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
public class AreaChart extends JFrame {

	public AreaChart() {

		// Create dataset
		DefaultCategoryDataset dataset = createDataset();

		// Create chart
		JFreeChart chart = ChartFactory.createAreaChart("Smartphone Platform Market Share", // 제목
				"Year", // 우측 내용.
				"Percentage(%)", // 좌측 내용
				dataset); // 데이터.

		// Create Panel
		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
		setSize(800, 400);
		setVisible(true);
	}

	private DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(10, "Android", "2005");
		dataset.addValue(20, "Android", "2010");
		dataset.addValue(30, "Android", "2015");

		dataset.addValue(7, "iOS", "2005");
		dataset.addValue(11, "iOS", "2010");
		dataset.addValue(16, "iOS", "2015");

		dataset.addValue(5, "Window Mobile", "2005");
		dataset.addValue(9, "Window Mobile", "2010");
		dataset.addValue(14, "Window Mobile", "2015");

		return dataset;
	}

	public static void main(String[] args) {
		new AreaChart();

	}
}