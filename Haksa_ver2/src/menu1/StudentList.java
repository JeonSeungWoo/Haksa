package menu1;

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

import main.Haksa;
import util.DBManager;

public class StudentList extends JPanel {
	// ������ ���̽� ����
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	// �ý�Ʈ �ʵ�
	JTextField tfId = null;
	JTextField tfName = null;
	JTextField tfDept = null;
	JTextField tfAdress = null;
	DefaultTableModel model = null;
	JTable table = null;

	public StudentList() {

		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
		} catch (Exception e) {
		}

		DBManager db = new DBManager();
		db.getConnection();

		setLayout(new FlowLayout());

		String[] selectRow = { "��ü", "�й�", "�̸�", "�а�", "�ּ�" };

		JComboBox selectBox = new JComboBox(selectRow);
		
		selectBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println(e.getActionCommand());
		
				System.out.println(e.getSource().equals("selectedItemReminder"));
			}
		});
		
		add(selectBox);
		
		tfId = new JTextField(14);
		add(tfId);

		JButton btnSearch = new JButton("�˻�");
		add(btnSearch);
		// �˻� ��ư .-------------------------------------------------------------------
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					rs = stmt.executeQuery("select * from student2 where id = '" + tfId.getText() + "'");

					model.setNumRows(0);

					String[] row = new String[4];// �÷��� ������ 3
					while (rs.next()) {

						row[0] = rs.getString("id");
						row[1] = rs.getString("name");
						row[2] = rs.getString("dept");
						row[3] = rs.getString("ADDRESS");
						model.addRow(row);
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		// End--------------------------------------

		String colName[] = { "�й�", "�̸�", "�а�", "�ּ�" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(250, 200));
		add(new JScrollPane(table));

		table.addMouseListener(new MouseAdapter() {

		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable) e.getComponent();
				model = (DefaultTableModel) table.getModel();
				String id = (String) model.getValueAt(table.getSelectedRow(), 0);
				String name = (String) model.getValueAt(table.getSelectedRow(), 1);
				String dept = (String) model.getValueAt(table.getSelectedRow(), 2);
				String address = (String) model.getValueAt(table.getSelectedRow(), 3);

				tfId.setText(id);
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
				totalList();
			}

		});

		// btnList��ư �̺�Ʈ
		// End-------------------------------------------------------------
		add(btnList);

		JButton updateBtn = new JButton("����");
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String idText = tfId.getText();
					stmt.executeUpdate(
							"update student2 set  name = '" + tfName.getText() + "', dept = '" + tfDept.getText()
									+ "', ADDRESS = '" + tfAdress.getText() + "'" + " where id = '" + idText + "'");

					JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
					totalList();

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		add(updateBtn);

		JButton deleBtn = new JButton("����");

		deleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String idText = tfId.getText();
					System.out.println(idText);
					int result = 0;

					result = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?");
					if (result == 0) {
						stmt.executeUpdate("delete from student2 where id = '" + idText + "'");
						JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
					} else {
						JOptionPane.showMessageDialog(null, "����߽��ϴ�.");
					}

					totalList();
					tfId.setText("");
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

		totalList();
	}

	// ��ü ����Ʈ
	public void totalList() {
		try {
			rs = stmt.executeQuery("select * from student2");

			// JTable �ʱ�ȭ
			model.setNumRows(0);

			String[] row = new String[4];// �÷��� ������ 3
			while (rs.next()) {
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("dept");
				row[3] = rs.getString("ADDRESS");
				model.addRow(row);
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new StudentList();
	}

}
