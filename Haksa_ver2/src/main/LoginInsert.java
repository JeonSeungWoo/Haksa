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
	
	//로그인정보 
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
		this.setTitle("회원가입");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        //id
		JLabel idLabel = new JLabel("ID :  ");
		idLabel.setBounds(80, 20, 40, 40);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(idLabel);

		idField = new JTextField(20);
		idField.setBounds(130, 30, 130, 20);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(idField);

		JButton checkBtn = new JButton("id체크");
		checkBtn.setBounds(260, 30, 70, 19);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		checkBtn.setBackground(Color.orange); //바탕 색.
		checkBtn.setForeground(Color.white);//글씨 색.
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
						JOptionPane.showMessageDialog(null, "사용 가능합니다.");
						checkConfirm = true;
					}else {
						JOptionPane.showMessageDialog(null, "사용 불가능");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		add(checkBtn);
		
		// pw
		JLabel pwLabel =new JLabel("PW :  ");
		pwLabel.setBounds(80, 60, 40, 40);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(pwLabel);
		
		pwField = new JPasswordField(20);
		pwField.setBounds(130, 70, 200, 20);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(pwField);
		
		// 이름
		JLabel nameLabel =new JLabel("name :  ");
		nameLabel.setBounds(80, 100, 60, 40);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(nameLabel);
		
		nameField = new JTextField(20);
		nameField.setBounds(130, 110, 200, 20);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(nameField);
		
		// email
		JLabel emailLabel =new JLabel("email :  ");
		emailLabel.setBounds(80, 140, 60, 40);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(emailLabel);
		
		emailField = new JTextField(20);
		emailField.setBounds(130, 150, 200, 20);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(emailField);
		
		JButton insertBtn = new JButton("회원가입");
		insertBtn.setBounds(170, 200, 95, 30);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		insertBtn.setBackground(Color.black); //바탕 색.
		insertBtn.setForeground(Color.white);//글씨 색.
		
		insertBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					if(checkConfirm == true) {
						int result = JOptionPane.showConfirmDialog(null, "회원가입 하시겠습니까?");
						if (result == 0) {
							pstmt = conn.prepareStatement("insert into TBL_USER(id,pw,name,email) values(?,?,?,?)");
							pstmt.setString(1, idField.getText());
							pstmt.setString(2, pwField.getText());
							pstmt.setString(3, nameField.getText());
							pstmt.setString(4, emailField.getText());
							pstmt.executeUpdate();
							JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
							
							new Main(); // 클래스 newWindow를 새로 만들어낸다
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "취소했습니다.");
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "아이디를 체크해 주세요!!");
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
