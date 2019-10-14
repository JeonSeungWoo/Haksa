package menu2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.Haksa;
import util.DBManager;

public class BookReturn extends JPanel {
	// ������ ���̽� ����
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	
	
	DefaultTableModel model;
	JTable table;
	String query;
	JComboBox deptSelectBox;

	//id, no �������� taxt fild
	JTextField noField;
	JTextField bidField;
	
	
	boolean returnCheck = false;
	

	public BookReturn() {
		// DBCon
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		query = "select s.*,b.*,br.* " + "from student2 s, tbl_book b, bookRent br " + "where br.id=s.id "
				+ "and br.bookNo=b.bid and outdate is null";

		setLayout(null);// ���̾ƿ�����. ���̾ƿ� ��� ����.

		noField =  new JTextField();
		bidField = new JTextField();
		  
		
		JLabel l_dept = new JLabel("�а�");
		l_dept.setBounds(10, 10, 30, 20);

		add(l_dept);

		// ����Ʈ ====================
		// ����Ʈ ====================
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			list.add("��ü");
			rs = stmt.executeQuery("select * from tbl_dept");
			while (rs.next()) {
				list.add(rs.getString("dept"));
			}

		} catch (Exception e) {

		}

		String[] selectRow = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			selectRow[i] = list.get(i);
		}

		deptSelectBox = new JComboBox(selectRow);
		deptSelectBox.setBounds(45, 10, 100, 20);
		add(deptSelectBox);
		// =======================================================

		deptSelectBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String deptString = (String)cb.getSelectedItem();
				query = "select s.*,b.*,br.* " + "from student2 s, tbl_book b, bookRent br " + "where br.id=s.id "
						+ "and br.bookNo=b.bid and outdate is null";

				if(deptString.equals("��ü")) {
					query += " order by br.no";
				}else{
					query += " and s.dept='"+deptString+"' ";
					query += " order by br.no";
				}
				
				list();
			}
		});

		String colName[] = { "�й�", "�̸�", "������", "������", "�ݳ���","��Ʈ��ȣ","å��ȣ"};
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(470, 200));
		table.getColumn("å��ȣ").setWidth(0);
		table.getColumn("å��ȣ").setMinWidth(0);
		table.getColumn("å��ȣ").setMaxWidth(0);

		table.getColumn("��Ʈ��ȣ").setWidth(0);
		table.getColumn("��Ʈ��ȣ").setMinWidth(0);
		table.getColumn("��Ʈ��ȣ").setMaxWidth(0);
		
		add(table);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(10, 40, 460, 250);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable) e.getComponent();
				model = (DefaultTableModel) table.getModel();
				String no = (String) model.getValueAt(table.getSelectedRow(), 5);
				String bid = (String) model.getValueAt(table.getSelectedRow(), 6);

				noField.setText(no);
				bidField.setText(bid);
				returnCheck= true;
			}
		});
		add(sp);
		
		
		
		

		JButton returnBtn = new JButton("�ݳ�");
		returnBtn.setBounds(200, 300, 80, 29); // �� ���� , ��� , ���빰 �¿�, ���빰 ����
		returnBtn.setBackground(Color.BLACK); // ���� ��.
		returnBtn.setForeground(Color.white);// �۾� ��.
		
		returnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					
					if(returnCheck == true) {
						
						int result = 0;
						result = JOptionPane.showConfirmDialog(null, "�ݳ� �Ͻðڽ��ϱ�?");
						if (result == 0) {
							pstmt = conn.prepareStatement("update bookRent set outdate = sysdate where no = ?");
							pstmt.setString(1, noField.getText());
						
							pstmt.executeUpdate();
							
							pstmt = conn.prepareStatement("update tbl_book set numcheck = '1' where BID = ?");
							pstmt.setString(1, bidField.getText());
							
							JOptionPane.showMessageDialog(null, "�ݳ� �Ϸ�.");
							
							list();
						} else {
							JOptionPane.showMessageDialog(null, "����߽��ϴ�.");
						}
						
					
						
						
					}else {
						JOptionPane.showMessageDialog(null, "�ݳ��� å�� ���� �� �ּ���.");
					}
					
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		});
		
		add(returnBtn);
		
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
			String[] row = new String[7];// �÷��� ������ 4
			while (rs.next()) {
				
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("title");
				row[3] = rs.getString("indate");
				row[4] = rs.getString("outdate");
				row[5] = rs.getString("no");
				row[6] = rs.getString("BID");
				model.addRow(row);
			}
			rs.close();

		} catch (Exception e1) {
			// e.getStackTrace();
			System.out.println(e1.getMessage());
		}
	}

	public static void main(String[] args) {
		new BookReturn();
	}

}
