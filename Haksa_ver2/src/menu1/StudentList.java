package menu1;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import content.Haksa;
import util.DBManager;

public class StudentList extends JPanel {
	// ������ ���̽� ����
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	// �ý�Ʈ �ʵ�
	JTextField tfSearch = null;
	JTextField tfName = null;
	JTextField tfDept = null;
	JTextField tfAdress = null;
	JTextField tfId = null;
	DefaultTableModel model = null;
	JTable table = null;
	JComboBox selectBox = new JComboBox();
	String query;
	
	JTextField click = null;
	String checkClick = "0";
	public StudentList() {

		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
		} catch (Exception e) {
		}
		
		query = "select * from student2 ";

		DBManager db = new DBManager();
		db.getConnection();

		setLayout(new FlowLayout());

		String[] selectRow = { "��ü", "�й�", "�̸�", "�а�", "�ּ�" };

		selectBox = new JComboBox(selectRow);
		
		add(selectBox);
		click = new JTextField(14);
		tfSearch = new JTextField(14);
		add(tfSearch);

		JButton btnSearch = new JButton("�˻�");
		add(btnSearch);
		// �˻� ��ư .-------------------------------------------------------------------
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("1 : " +selectBox.getSelectedIndex());
					System.out.println("2 : " + tfSearch);
					int selectIdx = selectBox.getSelectedIndex();
					query = "select * from student2 ";
					
					if (selectIdx == 0) {
						query += "order by id desc";
					}else if(selectIdx == 1) {
						query += "where id like '%"+tfSearch.getText()+"%' ";
					    query += "order by id desc";
					}else if(selectIdx == 2) {
						query += "where id like '%"+tfSearch.getText()+"%' ";
					    query += "order by id desc";
				
					}else if(selectIdx == 3) {
						query += "where id like '%"+tfSearch.getText()+"%' ";
					    query += "order by id desc";
					 
					}else if(selectIdx == 4) {
						query += "where id like '%"+tfSearch.getText()+"%' ";
					    query += "order by id desc";
					
					}
				    search();

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		// End--------------------------------------

		String colName[] = { "�й�", "�̸�", "�а�", "�ּ�" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);

		add(new JLabel("�̸�  :  "));
		tfName = new JTextField(20);
		add(tfName);
//        tfName.setEnabled(false);
		add(new JLabel("�а�   :  "));
		tfDept = new JTextField(20);
		add(tfDept);

		add(new JLabel("�ּ�  :  "));
		tfAdress = new JTextField(20);
		add(tfAdress);
		table.setPreferredScrollableViewportSize(new Dimension(250, 200));
		add(new JScrollPane(table));

        
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click.setText("1");
				table = (JTable) e.getComponent();
				model = (DefaultTableModel) table.getModel();
				String id = (String) model.getValueAt(table.getSelectedRow(), 0);
				String name = (String) model.getValueAt(table.getSelectedRow(), 1);
				String dept = (String) model.getValueAt(table.getSelectedRow(), 2);
				String address = (String) model.getValueAt(table.getSelectedRow(), 3);
				
				tfSearch.setText(id);
				tfName.setText(name);
				tfDept.setText(dept);
				tfAdress.setText(address);
				
			}
		});
        
		JButton btnList = new JButton("��ü");
		// btnList��ư �̺�Ʈ
		// Start-----------------------------------------------------------------
		btnList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}

		});

		// btnList��ư �̺�Ʈ
		// End-------------------------------------------------------------
		add(btnList);
		
		String c = click.getText();
		System.out.println(checkClick);
	    	   
	    	   
	    	   //����
			 JButton updateBtn = new JButton("����");
				updateBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String idText = tfSearch.getText();
							stmt.executeUpdate(
									"update student2 set  name = '" + tfName.getText() + "', dept = '" + tfDept.getText()
											+ "', ADDRESS = '" + tfAdress.getText() + "'" + " where id = '" + idText + "'");

							JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
							search();

						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				});

				add(updateBtn);
				
			  //����
			 JButton deleBtn = new JButton("����");

				deleBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String idText = tfSearch.getText();
							System.out.println(idText);
							int result = 0;

							result = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?");
							if (result == 0) {
								stmt.executeUpdate("delete from student2 where id = '" + idText + "'");
								JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
							} else {
								JOptionPane.showMessageDialog(null, "����߽��ϴ�.");
							}

							search();
							tfSearch.setText("");
							tfName.setText("");
							tfDept.setText("");
						} catch (Exception e2) {
							e2.printStackTrace();
						}

					}
				});
				add(deleBtn);
				
		
		
      

		this.setSize(300, 500);
		this.setVisible(true);

		search();
	}

	

	
	public void search() {
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
				row[2] = rs.getString("dept");
				row[3] = rs.getString("ADDRESS");
				model.addRow(row);
			}
			rs.close();

		} catch (Exception e1) {
			// e.getStackTrace();
			System.out.println(e1.getMessage());
		}
	}
	public static void main(String[] args) {
		new StudentList();
	}

}
