package menu1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.Haksa;
import util.DBManager;

public class StudentInsert extends JPanel {
	// ������ ���̽� ����
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;

	// �ý�Ʈ �ʵ�
	JTextField tfId = new JTextField("");
	JTextField tfName = new JTextField("");
	JTextField tfAdress = new JTextField("");
	DefaultTableModel model = null;
	JTable table = null;
	JComboBox deptSelectBox; 

	
	boolean idCheck = false;
	public StudentInsert() {
		/* DBcon ========================================== */
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
		} catch (Exception e) {

		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		/* DBcon End */
		add(new JLabel("�й�"));
		tfId = new JTextField(25);
		add(tfId);

		tfId.setText(Integer.toString(year));
//		tfId.setEnabled(false);
		add(new JLabel("�̸�"));
		tfName = new JTextField(25);
		add(tfName);
//		        tfName.setEnabled(false);
		add(new JLabel("�ּ�"));
		tfAdress = new JTextField(25);
		add(tfAdress);

// ����Ʈ ====================
		ArrayList<String> list = new ArrayList<String>();
		try {
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
		add(deptSelectBox);

		// ��ư ���� ====================================================
		JButton btnInsert = new JButton("���");
		btnInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String deptVal = (String) deptSelectBox.getSelectedItem();
					System.out.println("1 : " + deptVal);

					rs = stmt.executeQuery("select count(*)as cnt from student2 where  id ='"+tfId.getText()+"'");

					String idCnt = "1";
					while (rs.next()) {
						idCnt = rs.getString("cnt");
					}
					if (idCnt.equals("1")) {
						idCheck = false;
					}else {
						idCheck = true;
					}
					
					// null üũ
					if (tfId.getText().equals("") || tfName.getText().equals("") || tfAdress.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "������ ������ �Է� �ϼ���.");
					} else {
						if (idCheck == false) {
							JOptionPane.showMessageDialog(null, "�ش� �й��� ���� �մϴ�.");
						}else {
							int result = 0;
							result = JOptionPane.showConfirmDialog(null, "����Ͻðڽ��ϱ�?");
							if (result == 0) {
								pstmt = conn.prepareStatement("insert into student2(id,name,dept,ADDRESS) values(?,?,?,?)");
								pstmt.setString(1, tfId.getText());
								pstmt.setString(2, tfName.getText());
								pstmt.setString(3, deptVal);
								pstmt.setString(4, tfAdress.getText());
								pstmt.executeUpdate();
								JOptionPane.showMessageDialog(null, "��ϵǾ����ϴ�.");

								tfId.setText(Integer.toString(year));
								tfAdress.setText("");
								tfName.setText("");
							} else {
								JOptionPane.showMessageDialog(null, "����߽��ϴ�.");
							}
						}
						
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		});
		add(btnInsert);

		this.setSize(350, 500);
		this.setBounds(180, 180, 320, 500);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new StudentInsert();
	}

}
