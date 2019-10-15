package main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import content.Haksa;
import util.DBManager;

public class Main extends JFrame {
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	JLabel idLabel, pwLabel;
	JTextField idField;
	JPasswordField pwField;

	public Main() {
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(null);
		this.setTitle("로그인");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    ImageIcon loginMainImg  = new ImageIcon("./img/a.jpg");
	    Image loginMainImgReSize=  loginMainImg.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH );
	    ImageIcon loginMainImgRe  = new ImageIcon(loginMainImgReSize);
	    JLabel imgLabel = new JLabel(loginMainImgRe);
	    imgLabel.setBounds(10, 20, 400, 400);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
	    add(imgLabel);
		
        //id
		idLabel = new JLabel("ID :  ");
		idLabel.setBounds(80, 420, 40, 40);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(idLabel);

		idField = new JTextField(20);
		idField.setBounds(110, 430, 200, 20);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(idField);
	
		// pw
		pwLabel =new JLabel("PW :  ");
		pwLabel.setBounds(80, 440, 40, 40);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(pwLabel);
		pwField = new JPasswordField(20);
		pwField.setBounds(110, 450, 200, 20);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(pwField);

		JButton loginBtn = new JButton("로그인");
		loginBtn.setBounds(110, 480, 95, 30);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		loginBtn.setBackground(Color.BLUE); //바탕 색.
		loginBtn.setForeground(Color.white);//글씨 색.
		
		//마우스 올라 갈때 setRolloverIcon(new ImageIcon);
		//마우스 누를 때 setPressedIcon(new ImageIcon);
		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String check = "";
					rs = stmt.executeQuery("select count(*) as cnt from TBL_USER "
							+ "where id = '"+idField.getText()+"' and pw = '"+pwField.getText()+"'");
					
					System.out.println(check);
					while (rs.next()) {
						check = rs.getString("cnt");
					}

					
					if (check.equals("1")){
						JOptionPane.showMessageDialog(null, "로그인 성공");
						new Haksa(); // 클래스 newWindow를 새로 만들어낸다
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "아이디와 비번을 확인해 주세요.");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
		

			}
		});
		
		add(loginBtn);
		
		
		
		JButton loginAddBtn = new JButton("회원가입");
		loginAddBtn.setBounds(215, 480, 95, 30);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		loginAddBtn.setBackground(Color.BLACK); //바탕 색.
		loginAddBtn.setForeground(Color.white);//글씨 색.
		
		//마우스 올라 갈때 setRolloverIcon(new ImageIcon);
		//마우스 누를 때 setPressedIcon(new ImageIcon);
		loginAddBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				new LoginInsert(); // 클래스 newWindow를 새로 만들어낸다
				dispose();

			}
		});
		
		add(loginAddBtn);
		
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
		new Main();
	}

}
