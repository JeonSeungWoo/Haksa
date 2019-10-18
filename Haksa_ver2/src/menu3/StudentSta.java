package menu3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StudentSta extends JPanel {

	JPanel panel = new JPanel();

	
	JComboBox deptSelectBox;
	JComboBox deptSelectBox2;
	
	JComboBox areaDeptSelectBox;
	
	public StudentSta() {
		setLayout(null);
		/* ù��° ������*/
		JLabel pieTile = new JLabel("���а�,������ pie��Ʈ");
		pieTile.setFont(new Font("Serif",Font.BOLD,10));
		pieTile.setFont(pieTile.getFont().deriveFont(30.0f));
		pieTile.setBounds(190, 10, 350, 100); // �� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(pieTile);
		
		ImageIcon pieImg  = new ImageIcon("img/pie.jpg");
	    Image pieImgReSize=  pieImg.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH );
	    ImageIcon pieImgRe  = new ImageIcon(pieImgReSize);
	    JLabel imgLabel = new JLabel(pieImgRe);
	    imgLabel.setBounds(40, 20, 220, 220);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
	    add(imgLabel);
		
		String[] selectRow = {"�а�","����"};
		deptSelectBox = new JComboBox(selectRow);
		deptSelectBox.setBounds(300, 100, 80, 35);
		add(deptSelectBox);
		
		String[] selectRow2 = {"2019","2018","2017","2016","2015"};
		deptSelectBox2 = new JComboBox(selectRow2);
		deptSelectBox2.setBounds(300, 150, 80, 35);
		add(deptSelectBox2);
		//��ư
		JButton pieBtn = new JButton("chart");
		pieBtn.setBounds(420, 127, 85, 30);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		pieBtn.setBackground(Color.BLACK); //���� ��.
		pieBtn.setForeground(Color.white); //�۾� ��.
		pieBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame���(�ٸ� â���� ȣ��)
				Chart_S chart = new Chart_S();			
				//selected   , date
				int deptIdx = deptSelectBox.getSelectedIndex();
				String yearString = (String)deptSelectBox2.getSelectedItem();
				
				chart.pieChartEx(deptIdx,yearString);
			}
		});
		add(pieBtn);
		
		JLabel bookLine = new JLabel("-----------------------------------"
				+ "-------------------------------");
		bookLine.setFont(new Font("Serif",Font.BOLD,10));
		bookLine.setFont(bookLine.getFont().deriveFont(30.0f));
		bookLine.setBounds(10, 250, 700, 30); // �� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(bookLine);
		
		/* �ι��� ������ */
		JLabel areaTile = new JLabel("�س⵵�� area��Ʈ");
		areaTile.setFont(new Font("Serif",Font.BOLD,10));
		areaTile.setFont(areaTile.getFont().deriveFont(30.0f));
		areaTile.setBounds(190, 250, 350, 100); // �� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(areaTile);
		
		ImageIcon areaImg  = new ImageIcon("img/area.jpg");
	    Image areaImgReSize=  areaImg.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH );
	    ImageIcon areaImgRe  = new ImageIcon(areaImgReSize);
	    JLabel imgLabel2 = new JLabel(areaImgRe);
	    imgLabel2.setBounds(40, 300, 220, 220);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
	    add(imgLabel2);

		String[] areaSelectRow = {"�а�","����"};
		areaDeptSelectBox = new JComboBox(areaSelectRow);
		areaDeptSelectBox.setBounds(300, 399, 80, 35);
		add(areaDeptSelectBox);
	    
		//��ư
		JButton areaBtn = new JButton("chart");
		areaBtn.setBounds(420, 400, 85, 30);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		areaBtn.setBackground(Color.BLACK); //���� ��.
		areaBtn.setForeground(Color.white); //�۾� ��.
		areaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//JFrame���(�ٸ� â���� ȣ��)
				Chart_S chart = new Chart_S();			
				//selected   , date
				int areaDeptIdx = areaDeptSelectBox.getSelectedIndex();
				chart.areaChartEx(areaDeptIdx);
			}
		});
		add(areaBtn);
		

		
		setSize(700,600);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new StudentSta();
	}
}
