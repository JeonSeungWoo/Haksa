package menu4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

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
		
		this.setSize(700, 600);
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
		//제목
		JLabel titlela = new JLabel("※도서 빌린 건수");
		titlela.setFont(new Font("Serif",Font.BOLD,10));
		titlela.setFont(titlela.getFont().deriveFont(40.0f));
		titlela.setBounds(170, 10, 550, 100); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(titlela);
		
        //가로 막대 그래프----------------------------------------
		int titleX = 0;
		int fill = 0;
		titleLabel = new JLabel[list.size()+1];
		titleField = new JLabel[list.size()+1];
		ColorUtil color = new ColorUtil();
		Color[] col= color.ColorReurn(list.size());
		for (int i = 0; i < list.size(); i++) {
			titleLabel[i] = new JLabel( i +". " + list.get(i).get("title") + " : ");
			titleX = i*40;
			
			fill = i*40;
			titleLabel[i].setBounds(80,107+titleX,50,50);
			add(titleLabel[i]);
			
			titleLabel[i] = new JLabel(list.get(i).get("cnt"));
			titleLabel[i].setBounds(150,107+titleX,50,50);//가로 , 세로 , 박스 
			add(titleLabel[i]);
			
			int result =  Integer.parseInt(list.get(i).get("cnt")) * 10;
			g.setColor(col[i]);
			//막대
			g.fillRect(180, 120 +fill, result, 20);
			//원
//			g.fillArc(200, 120 +fill, 400, 400, result*fill,result*i*fill);// 좌 , 상, 원 크기 , 시작, 끝
		}
	//--
		
	}

	
}
