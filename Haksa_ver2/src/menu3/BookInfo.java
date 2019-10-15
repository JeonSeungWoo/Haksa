package menu3;

import java.awt.Color;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Haksa;
import util.ColorUtil;
import util.DBManager;

public class BookInfo extends JPanel{
	JPanel panel = new JPanel();
	//DB
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	
	JLabel titleLabel[];
	JLabel titleField[];
	public BookInfo() {
		
		setSize(400, 500);
		setVisible(true);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		panel.removeAll();// 모든컴포넌스 삭제
		panel.revalidate();// 다시활성화
		panel.repaint(); // 다시 그리기
		ArrayList<HashMap<String, String>> list = new ArrayList<>();
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			rs = stmt.executeQuery("select to_char(indate,'YYYY')  \"Year\",title,count(*) as cnt " + 
					"from bookRent br inner Join tbl_book b  " + 
					"on br.bookno = b.bid  " + 
					"group by to_char(indate,'YYYY'),title");
			
			while (rs.next()) {
				map = new HashMap<String, String>();
				map.put("title",rs.getString("TITLE"));
				map.put("cnt",rs.getString("cnt"));
				list.add(map);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//버전 1  (세로 막대 그래프) Color의 
		int titleX = 0;
		int fillRectX = 0;
	
		titleLabel = new JLabel[list.size()+1];
		titleField = new JLabel[list.size()+1];
		ColorUtil color = new ColorUtil();
		Color[] col= color.ColorReurn(list.size());
		for (int i = 0; i < list.size(); i++) {
			titleLabel[i] = new JLabel(list.get(i).get("title") + " : ");
			titleX = i*40;
			
			fillRectX = i*40;
			titleLabel[i].setBounds(10,7+titleX,50,50);
			add(titleLabel[i]);
			
			titleLabel[i] = new JLabel(list.get(i).get("cnt"));
			titleLabel[i].setBounds(80,7+titleX,50,50);//가로 , 세로 , 박스 
			add(titleLabel[i]);
			
			int result =  Integer.parseInt(list.get(i).get("cnt")) * 10;
			g.setColor(col[i]);
			g.fillRect(110, 20 +fillRectX, result, 20);//
		}


	
	}

	
}
