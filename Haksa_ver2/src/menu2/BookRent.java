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
	// ������ ���̽� ����

	
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
		
		setLayout(null);// ���̾ƿ�����. ���̾ƿ� ��� ����.

		JLabel l_dept = new JLabel("�а�");
		l_dept.setBounds(10, 10, 30, 20);
		
		add(l_dept);
 
		String[] dept = { "��ü", "��ǻ�ͽý���", "��Ƽ�̵��", "��ǻ�Ͱ���" };
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
			      
			      if(deptIndex==0){ // ��ü
			       // Select�� ����
			       query += " order by br.no";
			       list();
			      }else if(deptIndex==1){ // ��ǻ�ͽý���     
			       query += " and s.dept='��ǻ�ͽý���' ";
			       query += " order by br.no";
			       list();
			      }else if(deptIndex==2){ // ��Ƽ�̵��
			       query += " and s.dept='��Ƽ�̵��' ";
			       query += " order by br.no";
			       list();
			      }else if(deptIndex==3){ // ��ǻ�Ͱ���
			       query += " and s.dept='��ǻ�Ͱ���' ";
			       query += " order by br.no";
			       list();
			      }
			}
		});
		

		String colName[] = { "�й�", "�̸�", "������", "������" };
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
			System.out.println("����Ǿ����ϴ�.....");
			System.out.println(query);
			// Select�� ����
			ResultSet rs = stmt.executeQuery(query);

			// JTable �ʱ�ȭ
			model.setNumRows(0);

			while (rs.next()) {
				String[] row = new String[4];// �÷��� ������ 4
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
