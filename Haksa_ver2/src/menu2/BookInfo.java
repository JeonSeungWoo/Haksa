package menu2;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JPanel;

import util.DBManager;

public class BookInfo extends JPanel{
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	
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
