package content;

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
import menu2.BookInsert;
import menu2.BookRent;
import menu2.BookReturn;
import menu3.StudentSta;
import menu4.BookInfo;
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
		setTitle("학사관리");
//		
		panel = new JPanel();
//		setLayout(new FlowLayout());
		panel.removeAll();// 모든컴포넌스 삭제
		panel.revalidate();// 다시활성화
		panel.repaint(); // 다시 그리기
		panel.add(new MainImg()); // 화면 호충
		panel.setLayout(null);

		// menu1 Start
		JMenuBar mb = new JMenuBar();
		JMenu menu1 = new JMenu("학생관리");
		JMenuItem menu1Item1 = new JMenuItem("학생목록");
		menu1Item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("학생 목록");
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기
				panel.add(new StudentList()); // 화면 호충
				panel.setLayout(null);

			}
		});
		
		

		menu1.add(menu1Item1);

		// menu1 End
		JMenuItem menu1Item2 = new JMenuItem("학생 등록");
		menu1Item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("학생 등록");
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기
				panel.add(new StudentInsert()); // 화면 호충
				panel.setLayout(null);

			}
		});
		
		
		
		menu1.add(menu1Item2);

		
		// menu1 End
		JMenuItem menu1Item3 = new JMenuItem("학과 관리");
		menu1Item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("학생 등록");
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기
				panel.add(new DeptManager()); // 화면 호충
				panel.setLayout(null);
			}
		});
		
		menu1.add(menu1Item3);
		
		
		mb.add(menu1);
		// menu2 Start
		JMenu menu2 = new JMenu("도서관리");
		JMenuItem menu2Item1 = new JMenuItem("대출 정보");
		menu2Item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("학생 대출목록");
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기
				panel.add(new BookReturn()); // 화면 호충
				panel.setLayout(null);

			}
		});
		menu2.add(menu2Item1);
		
		//------------------------------
		JMenuItem menu2Item2 = new JMenuItem("책 대출");
		menu2Item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("대출");
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기
				panel.add(new BookRent()); // 화면 호충
				panel.setLayout(null);
			}
		});
		menu2.add(menu2Item2);
		
		
		//------------------------------
		JMenuItem menu2Item3 = new JMenuItem("책등록");
		menu2Item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("책등록");
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기
				panel.add(new BookInsert()); // 화면 호충
				panel.setLayout(null);
			}
		});
		menu2.add(menu2Item3);
		
		// menu2 End
		mb.add(menu2);
		
		
		
		JMenu menu3 = new JMenu("통계");
		JMenuItem menu3Item1 = new JMenuItem("학사통계");
		menu3Item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기
				panel.add(new StudentSta()); // 화면 호충
				panel.setLayout(null);
			}
		});
		
		
	    menu3.add(menu3Item1);
	    mb.add(menu3);
	   // menu3 End
		
		
		//menu 4---
		JMenu menu4 = new JMenu("통계 g");
		JMenuItem menu4Item1 = new JMenuItem("도서통계");

		menu4Item1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("도서통계");
				panel.removeAll();// 모든컴포넌스 삭제
				panel.revalidate();// 다시활성화
				panel.repaint(); // 다시 그리기	
         		panel.add(new BookInfo());

				panel.setLayout(null);
			}
		});

		
		
		menu4.add(menu4Item1);
		mb.add(menu4);
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

		setResizable(false);
		this.setSize(700, 600);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		new Haksa();
	}

}
