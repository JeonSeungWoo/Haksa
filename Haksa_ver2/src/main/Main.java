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
		this.setTitle("�α���");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    ImageIcon loginMainImg  = new ImageIcon("./img/a.jpg");
	    Image loginMainImgReSize=  loginMainImg.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH );
	    ImageIcon loginMainImgRe  = new ImageIcon(loginMainImgReSize);
	    JLabel imgLabel = new JLabel(loginMainImgRe);
	    imgLabel.setBounds(10, 20, 400, 400);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
	    add(imgLabel);
		
        //id
		idLabel = new JLabel("ID :  ");
		idLabel.setBounds(80, 420, 40, 40);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(idLabel);

		idField = new JTextField(20);
		idField.setBounds(110, 430, 200, 20);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(idField);
	
		// pw
		pwLabel =new JLabel("PW :  ");
		pwLabel.setBounds(80, 440, 40, 40);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(pwLabel);
		pwField = new JPasswordField(20);
		pwField.setBounds(110, 450, 200, 20);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		add(pwField);

		JButton loginBtn = new JButton("�α���");
		loginBtn.setBounds(110, 480, 95, 30);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		loginBtn.setBackground(Color.BLUE); //���� ��.
		loginBtn.setForeground(Color.white);//�۾� ��.
		
		//���콺 �ö� ���� setRolloverIcon(new ImageIcon);
		//���콺 ���� �� setPressedIcon(new ImageIcon);
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
						JOptionPane.showMessageDialog(null, "�α��� ����");
						new Haksa(); // Ŭ���� newWindow�� ���� ������
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "���̵�� ����� Ȯ���� �ּ���.");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
		

			}
		});
		
		add(loginBtn);
		
		
		
		JButton loginAddBtn = new JButton("ȸ������");
		loginAddBtn.setBounds(215, 480, 95, 30);  //�� ���� , ��� , ���빰 �¿�, ���빰 ����
		loginAddBtn.setBackground(Color.BLACK); //���� ��.
		loginAddBtn.setForeground(Color.white);//�۾� ��.
		
		//���콺 �ö� ���� setRolloverIcon(new ImageIcon);
		//���콺 ���� �� setPressedIcon(new ImageIcon);
		loginAddBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				new LoginInsert(); // Ŭ���� newWindow�� ���� ������
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
