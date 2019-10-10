package menu1;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.Haksa;
import util.DBManager;

public class StudentInsert extends JPanel{
	// ������ ���̽� ����
		ResultSet rs = null;
		Connection conn = null; 
		PreparedStatement pstmt = null;
		Statement stmt = Haksa.stmt;
	
		//�ý�Ʈ �ʵ�
		JTextField tfId = null;
		JTextField tfName = null; 
		JTextField tfDept = null;
		JTextField tfAdress = null;	
		DefaultTableModel model = null;
		JTable table = null;
	
		
		public StudentInsert() {
			try {
				DBManager db = new DBManager();
				conn = db.getConnection();
			} catch (Exception e) {
				
			}
			
			 DBManager db = new DBManager();
			 

			 
			    db.getConnection();
				setLayout(new FlowLayout());
				
				
				

				add(new JLabel("�й�"));
				tfId = new JTextField(25);
				add(tfId);
				tfId.setEnabled(false);
				add(new JLabel("�̸�"));
				tfName = new JTextField(25);
				add(tfName);
//		        tfName.setEnabled(false);
				add(new JLabel("�а�"));
				tfDept = new JTextField(25);
				add(tfDept);

			    add(new JLabel("�ּ�"));
				tfAdress = new JTextField(25);
				add(tfAdress);
				try {
					rs = stmt.executeQuery("select " + 
							"(to_char(sysdate,'YYYY') || stid_seq.nextval  )as \"id\"" + 
							"from dual");

					String id = "";
					while (rs.next()) {
						id = rs.getString("id");
					}
					
					System.out.println(id);
					tfId.setText(id);
				}catch (Exception e) {
				}
				
				
				
				JButton btnInsert = new JButton("���");
				btnInsert.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int result = 0;
							result = JOptionPane.showConfirmDialog(null, "����Ͻðڽ��ϱ�?");
							if (result == 0) {
								
								pstmt = conn.prepareStatement("insert into student2(id,name,dept,ADDRESS) values(?,?,?,?)");
								pstmt.setString(1, tfId.getText());
								pstmt.setString(2, tfName.getText());
								pstmt.setString(3, tfDept.getText());
								pstmt.setString(4, tfAdress.getText());
								pstmt.executeUpdate();
								JOptionPane.showMessageDialog(null, "��ϵǾ����ϴ�.");
								rs = stmt.executeQuery("select " + 
										"(to_char(sysdate,'YYYY') || stid_seq.nextval  )as \"id\"" + 
										"from dual");

								String id = "";
								while (rs.next()) {
									id = rs.getString("id");
								}
								
								System.out.println(id);
								tfId.setText(id);
								tfAdress.setText("");
								tfName.setText("");
								tfDept.setText("");
								
							} else {
								JOptionPane.showMessageDialog(null, "����߽��ϴ�.");
							}

						
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}

				});
				add(btnInsert);
				
				this.setSize(350,500);
				this.setBounds(180,180, 320, 500);
				this.setVisible(true);
		}
		
		
		public static void main(String[] args) {
			new StudentInsert();
		}

}
