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
	// 데이터 베이스 연결
		ResultSet rs = null;
		Connection conn = null; 
		PreparedStatement pstmt = null;
		Statement stmt = Haksa.stmt;
	
		//택스트 필드
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
				
				
				

				add(new JLabel("학번"));
				tfId = new JTextField(25);
				add(tfId);
				tfId.setEnabled(false);
				add(new JLabel("이름"));
				tfName = new JTextField(25);
				add(tfName);
//		        tfName.setEnabled(false);
				add(new JLabel("학과"));
				tfDept = new JTextField(25);
				add(tfDept);

			    add(new JLabel("주소"));
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
				
				
				
				JButton btnInsert = new JButton("등록");
				btnInsert.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int result = 0;
							result = JOptionPane.showConfirmDialog(null, "등록하시겠습니까?");
							if (result == 0) {
								
								pstmt = conn.prepareStatement("insert into student2(id,name,dept,ADDRESS) values(?,?,?,?)");
								pstmt.setString(1, tfId.getText());
								pstmt.setString(2, tfName.getText());
								pstmt.setString(3, tfDept.getText());
								pstmt.setString(4, tfAdress.getText());
								pstmt.executeUpdate();
								JOptionPane.showMessageDialog(null, "등록되었습니다.");
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
								JOptionPane.showMessageDialog(null, "취소했습니다.");
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
