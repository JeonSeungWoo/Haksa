package menu1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.Haksa;

public class DeptManager extends JPanel {
	// DB연결.
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;

	// 레이아웃
	JLabel deptLabel;
	JTextField deptField;
	DefaultTableModel model;
	JTable table;
	
	
	JTextField dnoField;

	public DeptManager() {
		// DB
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:myoracle", "ora_user", "woo");
			stmt = conn.createStatement();

		} catch (Exception e) {

			e.printStackTrace();
		}

		setLayout(new FlowLayout());
		deptLabel = new JLabel();
		deptField = new JTextField();

		// id
		deptLabel = new JLabel("학과  : ");
		add(deptLabel);

		deptField = new JTextField(20);
		add(deptField);

		JButton searchBtn = new JButton("검색");
		searchBtn.setBackground(Color.BLUE); // 바탕 색.
		searchBtn.setForeground(Color.white);// 글씨 색.

		//dno 객체 생성.
		dnoField = new JTextField(20);
		
		
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String dept = deptField.getText();
					rs = stmt.executeQuery("select * from tbl_dept where dept ='" + dept + "'");

					model.setNumRows(0);
					String[] row = new String[2];//
					while (rs.next()) {
						row[0] = rs.getString("dno");
						row[1] = rs.getString("dept");
						model.addRow(row);
					}

				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});

		add(searchBtn);

		String colName[] = { "번호", "학과" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(320, 300));
		add(new JScrollPane(table));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable) e.getComponent();
				model = (DefaultTableModel) table.getModel();
				String dno = (String) model.getValueAt(table.getSelectedRow(), 0);
				String dept = (String) model.getValueAt(table.getSelectedRow(), 1);

				deptField.setText(dept);
				dnoField.setText(dno);
			}
		});

		JButton listBtn = new JButton("목록");
		listBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					totalList();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		add(listBtn);
		JButton insertBtn = new JButton("등록");
		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String dept = deptField.getText();

					stmt.executeUpdate("insert into tbl_dept(dno,dept) values(dno_seq.nextval,'" + dept + "')");
					totalList();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		add(insertBtn);
		JButton updateBtn = new JButton("수정");
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String dno = dnoField.getText();
					
					String dept = deptField.getText();
					
					stmt.executeUpdate("update tbl_dept  " + 
							"  set dept = '"+dept+"' " + 
							"  where dno = '"+dno+"' ");
					
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				totalList();
			}
		});

		add(updateBtn);
		JButton deleBtn = new JButton("삭제");
		deleBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String dno = dnoField.getText();
					
					stmt.executeUpdate("delete from tbl_dept where dno = '"+dno+"'");
					
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				totalList();
			}
		});
		add(deleBtn);
		
		setSize(400, 500);
		setVisible(true);
		totalList();
	}

	public void totalList() {
		try {
			rs = stmt.executeQuery("select * from tbl_dept");
			// JTable 초기화
			model.setNumRows(0);
			String[] row = new String[2];//
			while (rs.next()) {
				row[0] = rs.getString("dno");
				row[1] = rs.getString("dept");
				model.addRow(row);
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new DeptManager();
	}

}
