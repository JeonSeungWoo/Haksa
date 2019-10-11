package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import menu1.DeptManager;
import menu1.StudentInsert;
import menu1.StudentList;
import menu2.BookInfo;
import menu2.BookRent;
import util.DBManager;

public class Haksa extends JFrame {
	JPanel panel = new JPanel();
	public static Statement stmt = null;
	public static PreparedStatement pstmt = null;
	ResultSet rs = null;
	public static Connection conn = null;
	public Haksa() {  

		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();

		} catch (Exception e) {

			e.printStackTrace();
		}
//		
		panel = new JPanel();
//		setLayout(new FlowLayout());
		panel.removeAll();// ��������ͽ� ����
		panel.revalidate();// �ٽ�Ȱ��ȭ
		panel.repaint(); // �ٽ� �׸���
		panel.add(new MainImg()); // ȭ�� ȣ��
		panel.setLayout(null);

		// menu1 Start
		JMenuBar mb = new JMenuBar();
		JMenu menu1 = new JMenu("�л�����");
		JMenuItem menu1Item1 = new JMenuItem("�л����");
		menu1Item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("�л� ���");
				panel.removeAll();// ��������ͽ� ����
				panel.revalidate();// �ٽ�Ȱ��ȭ
				panel.repaint(); // �ٽ� �׸���
				panel.add(new StudentList()); // ȭ�� ȣ��
				panel.setLayout(null);

			}
		});
		
		

		menu1.add(menu1Item1);

		// menu1 End
		JMenuItem menu1Item2 = new JMenuItem("�л� ���");
		menu1Item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("�л� ���");
				panel.removeAll();// ��������ͽ� ����
				panel.revalidate();// �ٽ�Ȱ��ȭ
				panel.repaint(); // �ٽ� �׸���
				panel.add(new StudentInsert()); // ȭ�� ȣ��
				panel.setLayout(null);

			}
		});
		
		
		
		menu1.add(menu1Item2);

		
		// menu1 End
		JMenuItem menu1Item3 = new JMenuItem("�а� ����");
		menu1Item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("�л� ���");
				panel.removeAll();// ��������ͽ� ����
				panel.revalidate();// �ٽ�Ȱ��ȭ
				panel.repaint(); // �ٽ� �׸���
				panel.add(new DeptManager()); // ȭ�� ȣ��
				panel.setLayout(null);
			}
		});
		
		menu1.add(menu1Item3);
		
		
		mb.add(menu1);
		// menu2 Start
		JMenu menu2 = new JMenu("��������");
		JMenuItem menu2Item1 = new JMenuItem("���� ����");
		menu2Item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("�л� ������");
				panel.removeAll();// ��������ͽ� ����
				panel.revalidate();// �ٽ�Ȱ��ȭ
				panel.repaint(); // �ٽ� �׸���
				panel.add(new BookRent()); // ȭ�� ȣ��
				panel.setLayout(null);

			}
		});
		menu2.add(menu2Item1);
		mb.add(menu2);
		// menu2 End

		JMenu menu3 = new JMenu("�������");
		JMenuItem menu3Item1 = new JMenuItem("���� ��Ȳ");

		menu3Item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("�л� ������Ȳ");
				panel.removeAll();// ��������ͽ� ����
				panel.revalidate();// �ٽ�Ȱ��ȭ
				panel.repaint(); // �ٽ� �׸���	
         		panel.add(new BookInfo());

				panel.setLayout(null);
			}
		});

		menu3.add(menu3Item1);
		mb.add(menu3);
		this.setJMenuBar(mb);
		
		this.add(panel);
		


		this.addWindowListener(new WindowAdapter() {
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

		
		this.setSize(700, 600);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		new Haksa();
	}

}
