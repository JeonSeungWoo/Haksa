package menu3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import statistics_s.AreaChart;

public class StudentSta extends JFrame {

	JPanel panel = new JPanel();

	public StudentSta() {
		setLayout(null);
		//버튼
		JButton areaBtn = new JButton("선차트");
		areaBtn.setBounds(10, 20, 95, 30);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		areaBtn.setBackground(Color.BLACK); //바탕 색.
		areaBtn.setForeground(Color.white); //글씨 색.
		areaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AreaChart();
			}
		});
		add(areaBtn);
		
		
		
		
		setSize(700,700);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new StudentSta();
	}
}
