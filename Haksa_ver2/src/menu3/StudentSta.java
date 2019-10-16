package menu3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import menu1.StudentInsert;
import statistics_s.AreaChart;
import statistics_s.Chart_S;

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
		
		//버튼
		JButton pieBtn = new JButton("파이차트");
		pieBtn.setBounds(140, 20, 95, 30);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		pieBtn.setBackground(Color.BLACK); //바탕 색.
		pieBtn.setForeground(Color.white); //글씨 색.
		pieBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame방식(다른 창에서 호출)
				Chart_S pie = new Chart_S();
				pie.pieChartEx();

				
			}
		});
		
		
		add(pieBtn);
		
		setSize(700,600);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new StudentSta();
	}
}
