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
	// 데이터 베이스 연결
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
		JFreeChart chart = ChartFactory.createPieChart("파이차트", // Title
				           pieDataset, // Dataset
				           true,true,false);
		// Create Panel
		//제목 한글 폰트 사용.
		 chart.getTitle().setFont(new Font("돋움", Font.BOLD, 20));
		 chart.getLegend().setItemFont(new Font("돋움", Font.PLAIN, 10));
		 PiePlot plot = (PiePlot) chart.getPlot();
		 plot.setLabelFont(new Font("돋움", Font.PLAIN, 12));
		
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
		public void areaChartEx(int seleted) {
			ArrayList<HashMap<String, String>> list = new ArrayList<>();
			HashMap<String, String> map = new HashMap<String, String>();
			try {
				DBManager db = new DBManager();
				conn = db.getConnection();
				stmt = conn.createStatement();

				if(seleted == 0) {
					query += "select SUBSTR(id,0,4) year, dept ,count(*)cnt " + 
							"from student2 GROUP by SUBSTR(id,0,4),dept " + 
							"order by dept,year asc";
				}else{
					query += "select SUBSTR(id,0,4) year, address ,count(*)cnt " + 
							"from student2 GROUP by SUBSTR(id,0,4),address " + 
							"order by address,year asc";
				}
				
				
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
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (int i = 0; i < list.size(); i++) {
				int cntInt = Integer.parseInt(list.get(i).get("cnt"));
				String content = list.get(i).get("content");
				String year = list.get(i).get("year");
				dataset.addValue(cntInt, content, year);
				
			}
		
			// Create chart
			JFreeChart chart = ChartFactory.createAreaChart("Title", // 제목
					"Year", // 하단 내용.
					"Percentage(%)", // 좌측 내용
					dataset); // 데이터.
			 chart.getTitle().setFont(new Font("돋움", Font.BOLD, 20));
			 chart.getLegend().setItemFont(new Font("돋움", Font.PLAIN, 10));

			 
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
//		pie.pieChartEx(0,"2019");
		pie.areaChartEx(1);
	}
}
