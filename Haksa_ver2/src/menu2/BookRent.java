package menu2;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Haksa;
import util.DBManager;

public class BookRent extends JPanel{
	// 데이터 베이스 연결

	
	ResultSet rs = null;
	DefaultTableModel model;
	JTable table;
	String query;
	
	
	Connection conn = null; 
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	public BookRent() {
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
		} catch (Exception e) {
		}
		query = "select s.*,b.*,br.* " + "from student2 s, books b, bookRent br " + "where br.id=s.id "
				+ "and br.bookNo=b.no";
		
		setLayout(null);// 레이아웃설정. 레이아웃 사용 안함.

		JLabel l_dept = new JLabel("학과");
		l_dept.setBounds(10, 10, 30, 20);
		
		add(l_dept);
 
		String[] dept = { "전체", "컴퓨터시스템", "멀티미디어", "컴퓨터공학" };
		JComboBox cb_dept = new JComboBox(dept);
		cb_dept.setBounds(45, 10, 100, 20);
		add(cb_dept);
		cb_dept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb=(JComboBox)e.getSource();     
			      System.out.println(cb.getSelectedIndex());
			      int deptIndex=cb.getSelectedIndex();
			      query = "select s.*,b.*,br.* " + "from student2 s, books b, bookRent br " + "where br.id=s.id "
							+ "and br.bookNo=b.no";
			      
			      if(deptIndex==0){ // 전체
			       // Select문 실행
			       query += " order by br.no";
			       list();
			      }else if(deptIndex==1){ // 컴퓨터시스템     
			       query += " and s.dept='컴퓨터시스템' ";
			       query += " order by br.no";
			       list();
			      }else if(deptIndex==2){ // 멀티미디어
			       query += " and s.dept='멀티미디어' ";
			       query += " order by br.no";
			       list();
			      }else if(deptIndex==3){ // 컴퓨터공학
			       query += " and s.dept='컴퓨터공학' ";
			       query += " order by br.no";
			       list();
			      }
			}
		});
		

		String colName[] = { "학번", "이름", "도서명", "대출일" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(470, 200));
		add(table);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(10, 40, 460, 250);
		add(sp);
		setSize(490, 400);
		setVisible(true);
		list();
	}

	public void list() {
		try {
			System.out.println("연결되었습니다.....");
			System.out.println(query);
			// Select문 실행
			ResultSet rs = stmt.executeQuery(query);

			// JTable 초기화
			model.setNumRows(0);

			while (rs.next()) {
				String[] row = new String[4];// 컬럼의 갯수가 4
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("title");
				row[3] = rs.getString("rdate");
				model.addRow(row);
			}
			rs.close();

		} catch (Exception e1) {
			// e.getStackTrace();
			System.out.println(e1.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new BookRent();
	}
	
	
}
