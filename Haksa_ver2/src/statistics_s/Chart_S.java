package statistics_s;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Chart_S extends JFrame {

	//pie
	public void pieChartEx() {
		
		// Create dataset
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("JavaWorld", new Integer(75));
		pieDataset.setValue("Other", new Integer(25));

		JFreeChart chart = ChartFactory.createPieChart("title", // Title
				           pieDataset, // Dataset
				           true,true,false);
		// Create Panel
		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);

		this.setSize(500, 500);
		setVisible(true);
	}
	
	
	//area
		public void areaChartEx() {
			
			// Create dataset
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			dataset.addValue(10, "JavaWorld", "2017");
			dataset.addValue(20, "JavaWorld", "2018");
			dataset.addValue(30, "JavaWorld", "2019");

			dataset.addValue(7, "Other", "2017");
			dataset.addValue(11, "Other", "2018");
			dataset.addValue(16, "Other", "2019");
			
			// Create chart
			JFreeChart chart = ChartFactory.createAreaChart("Title", // 제목
					"Year", // 하단 내용.
					"Percentage(%)", // 좌측 내용
					dataset); // 데이터.

			// Create Panel
			ChartPanel chartPanel = new ChartPanel(chart);
			setContentPane(chartPanel);
			setSize(800, 400);
			setVisible(true);
		}
		
	//rod
	public void rod() {
		
	}
	

	public static void main(String[] args) {
		Chart_S pie = new Chart_S();
//		pie.pieChartEx();
		pie.areaChartEx();
	}
}
