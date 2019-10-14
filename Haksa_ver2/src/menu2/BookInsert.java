package menu2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Haksa;
import util.DBManager;

public class BookInsert extends JPanel {
	// 데이터 베이스 연결
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;

	public BookInsert() {
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
		} catch (Exception e) {
		}

		setLayout(null);
		JLabel bookTile = new JLabel("※책 등록");
		bookTile.setFont(new Font("Serif",Font.BOLD,10));
		bookTile.setFont(bookTile.getFont().deriveFont(40.0f));
		bookTile.setBounds(260, 10, 200, 100); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(bookTile);
		
		ImageIcon loginMainImg  = new ImageIcon("./img/catBook.jpg");
	    Image loginMainImgReSize=  loginMainImg.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH );
	    ImageIcon loginMainImgRe  = new ImageIcon(loginMainImgReSize);
	    JLabel imgLabel = new JLabel(loginMainImgRe);
	    imgLabel.setBounds(40, 20, 600, 400);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
	    add(imgLabel);
		// 책이름
		JLabel bookLabel = new JLabel("책이름  : ");
		bookLabel.setBounds(200, 420, 60, 40); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(bookLabel);

		JTextField bookField = new JTextField(20);
		bookField.setBounds(200+60, 431, 200, 19); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(bookField);
		
		// 버튼 시작 ====================================================
		JButton btnInsert = new JButton("등록");

		btnInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String book = bookField.getText();
					if (bookField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "책의 이름을 입력 하세요.");
						System.out.println(book);
					}else {
						int result = 0;
						result = JOptionPane.showConfirmDialog(null, "등록하시겠습니까?");
						if (result == 0) {
							pstmt = conn.prepareStatement("insert into tbl_book(bid,title) values(book_seq.nextval,?)");
							pstmt.setString(1, book);
							pstmt.executeUpdate();
							
							JOptionPane.showMessageDialog(null, "등록되었습니다.");
							bookField.setText("");
						} else {
							JOptionPane.showMessageDialog(null, "취소했습니다.");
						}
					}
					
					
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnInsert.setBounds(330, 460, 60, 30); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		btnInsert.setBackground(Color.black); //바탕 색.
		btnInsert.setForeground(Color.white);//글씨 색.
		add(btnInsert);
		setSize(700, 600);
		setVisible(true);
	}

	
	public static void main(String[] args) {
		new BookInsert();
	}
}
