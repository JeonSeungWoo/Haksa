package menu3;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale.Category;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import content.Haksa;
import util.DBManager;

public class Chart_S extends JFrame {
	// ������ ���̽� ����
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
		String query = "";
		
	//pie------------------------------------------------------------------------------
	public void pieChartEx(int seleted,String year){
		ArrayList<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String, String> map = new HashMap<String, String>();
		query = "select * from";
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();

			if(seleted == 0) {
				query += "(select SUBSTR(id,0,4) year,dept,count(*) cnt " + 
						"from student2 " + 
						"GROUP by SUBSTR(id,0,4),dept)";
			}else{
				query += "(select " + 
						"SUBSTR(id,0,4) year,address,count(*) cnt " + 
						"from student2 " + 
						"GROUP by SUBSTR(id,0,4),address)";
			}
			
			query += "where year = '"+year+"'";
			
			rs = stmt.executeQuery(query);	
			while (rs.next()) {
				map = new HashMap<String, String>();
				
				if (seleted == 0) {
					map.put("year",rs.getString("YEAR"));
					map.put("content",rs.getString("DEPT"));
					map.put("cnt",rs.getString("CNT"));
					list.add(map);
					
				}else {
					map.put("year",rs.getString("YEAR"));
					map.put("content",rs.getString("ADDRESS"));
					map.put("cnt",rs.getString("CNT"));
					list.add(map);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		System.out.println(list);
		
		// Create dataset
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		
		for (int i = 0; i < list.size(); i++) {
			int cntInt = Integer.parseInt(list.get(i).get("cnt"));
			
			pieDataset.setValue(list.get(i).get("content"), new Integer(cntInt));
			
		}
		JFreeChart chart = ChartFactory.createPieChart("������Ʈ", // Title
				           pieDataset, // Dataset
				           true,true,false);
		// Create Panel
		//���� �ѱ� ��Ʈ ���.
		 chart.getTitle().setFont(new Font("����", Font.BOLD, 20));
		 chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 10));
		 PiePlot plot = (PiePlot) chart.getPlot();
		 plot.setLabelFont(new Font("����", Font.PLAIN, 12));
		
		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);
		
		

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (rs != null) {
						rs.close();
					}
					if (conn != null) {
						conn.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (Exception e1) {
				}
			}
		});
		setSize(500, 500);
		setVisible(true);
	}
	
	
	//area------------------------------------------------------------------------------
		public void areaChartEx() {
			try {
				DBManager db = new DBManager();
				conn = db.getConnection();
				stmt = conn.createStatement();
				
				
			} catch (Exception e) {
			}
			// Create dataset
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			dataset.addValue(10, "JavaWorld", "2017");
			dataset.addValue(20, "JavaWorld", "2018");
			dataset.addValue(30, "JavaWorld", "2019");

			dataset.addValue(7, "Other", "2017");
			dataset.addValue(11, "Other", "2018");
			dataset.addValue(16, "Other", "2019");
			
			// Create chart
			JFreeChart chart = ChartFactory.createAreaChart("Title", // ����
					"Year", // �ϴ� ����.
					"Percentage(%)", // ���� ����
					dataset); // ������.

			// Create Panel
			ChartPanel chartPanel = new ChartPanel(chart);
			setContentPane(chartPanel);
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					try {
						if (rs != null) {
							rs.close();
						}
						if (conn != null) {
							conn.close();
						}
						if (stmt != null) {
							stmt.close();
						}
						if (pstmt != null) {
							pstmt.close();
						}
					} catch (Exception e1) {
					}
				}
			});
			setSize(800, 400);
			setVisible(true);
		}
		
	//rod
	public void rod() {
		
	}
	

	public static void main(String[] args) {
		Chart_S pie = new Chart_S();
		pie.pieChartEx(0,"2019");
//		pie.areaChartEx();
	}
}
