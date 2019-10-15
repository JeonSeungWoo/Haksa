package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import content.Haksa;
import util.DBManager;

public class LoginInsert extends JFrame{
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	
	//�α������� 
	JTextField idField;
	JPasswordField pwField;
	JTextField nameField;
	JTextField emailField;

	boolean checkConfirm = false;
	
	public LoginInsert() {
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(null);
		this.setTitle("ȸ������");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        //id
		JLabel idLabel = new JLabel("ID :  ");
		idLabel.setBounds(80, 20, 40, 40);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(idLabel);

		idField = new JTextField(20);
		idField.setBounds(130, 30, 130, 20);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(idField);

		JButton checkBtn = new JButton("idüũ");
		checkBtn.setBounds(260, 30, 70, 19);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		checkBtn.setBackground(Color.orange); //���� ��.
		checkBtn.setForeground(Color.white);//�۾� ��.
		checkBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String check = "";
					rs = stmt.executeQuery("select count(*) as cnt from TBL_USER where id = '"+idField.getText()+"'");
					
					while (rs.next()) {
						check = rs.getString("cnt");
					}

					if(idField.getText().equals("") ) {
						check = "1";
					}
					
					if (check.equals("0")){
						JOptionPane.showMessageDialog(null, "��� �����մϴ�.");
						checkConfirm = true;
					}else {
						JOptionPane.showMessageDialog(null, "��� �Ұ���");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		add(checkBtn);
		
		// pw
		JLabel pwLabel =new JLabel("PW :  ");
		pwLabel.setBounds(80, 60, 40, 40);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(pwLabel);
		
		pwField = new JPasswordField(20);
		pwField.setBounds(130, 70, 200, 20);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(pwField);
		
		// �̸�
		JLabel nameLabel =new JLabel("name :  ");
		nameLabel.setBounds(80, 100, 60, 40);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(nameLabel);
		
		nameField = new JTextField(20);
		nameField.setBounds(130, 110, 200, 20);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(nameField);
		
		// email
		JLabel emailLabel =new JLabel("email :  ");
		emailLabel.setBounds(80, 140, 60, 40);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(emailLabel);
		
		emailField = new JTextField(20);
		emailField.setBounds(130, 150, 200, 20);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(emailField);
		
		JButton insertBtn = new JButton("ȸ������");
		insertBtn.setBounds(170, 200, 95, 30);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		insertBtn.setBackground(Color.black); //���� ��.
		insertBtn.setForeground(Color.white);//�۾� ��.
		
		insertBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					if(checkConfirm == true) {
						int result = JOptionPane.showConfirmDialog(null, "ȸ������ �Ͻðڽ��ϱ�?");
						if (result == 0) {
							pstmt = conn.prepareStatement("insert into TBL_USER(id,pw,name,email) values(?,?,?,?)");
							pstmt.setString(1, idField.getText());
							pstmt.setString(2, pwField.getText());
							pstmt.setString(3, nameField.getText());
							pstmt.setString(4, emailField.getText());
							pstmt.executeUpdate();
							JOptionPane.showMessageDialog(null, "ȸ������ �Ǿ����ϴ�.");
							
							new Main(); // Ŭ���� newWindow�� ���� ������
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "����߽��ϴ�.");
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "���̵� üũ�� �ּ���!!");
					}
						
				

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				

			}
		});
		
		add(insertBtn);
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

		this.setSize(440,600);
		this.setVisible(true);
		
	}
	
	

	public static void main(String[] args) {
		new LoginInsert();
	}
	

}
