package menu3;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Haksa;
import util.DBManager;

public class BookInfo extends JPanel{
	//DB
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	
	
	
	public BookInfo() {
		setSize(400, 500);
		setVisible(true);
	}
	
	public void paintComponent(Graphics g){
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel idLabel = new JLabel("ID :  ");
		idLabel.setBounds(10, 20,  40, 40);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(idLabel);
		
		super.paintComponent(g);
		int hight = 50;
		   //가로 가 값이다.
		g.setColor(Color.red);
		g.fillRect(100, 100, 200, 20);
		g.setColor(Color.BLUE);
		g.fillRect(100, 100+hight, 100, 20);
		g.setColor(Color.BLACK);
		g.fillRect(100, 100+(hight*2), 150, 20);
		g.setColor(Color.gray);
		g.fillRect(100, 100+(hight*3), 80, 20);
		
	
	}

	
}
