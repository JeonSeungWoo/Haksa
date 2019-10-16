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
		//��ư
		JButton areaBtn = new JButton("����Ʈ");
		areaBtn.setBounds(10, 20, 95, 30);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		areaBtn.setBackground(Color.BLACK); //���� ��.
		areaBtn.setForeground(Color.white); //�۾� ��.
		areaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AreaChart();
			}
		});
		add(areaBtn);
		
		//��ư
		JButton pieBtn = new JButton("������Ʈ");
		pieBtn.setBounds(140, 20, 95, 30);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		pieBtn.setBackground(Color.BLACK); //���� ��.
		pieBtn.setForeground(Color.white); //�۾� ��.
		pieBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame���(�ٸ� â���� ȣ��)
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
