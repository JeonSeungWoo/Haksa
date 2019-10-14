package menu2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.Haksa;
import util.DBManager;

public class BookRent extends JPanel {

	// DBCon
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;

	// layout
	// 레이아웃
	DefaultTableModel model;
	JTable table;
	JLabel bookLabel;
	JTextField bookField;
	JLabel idLabel;
	JTextField idField;
	
	
	JTextField bookNo;

	boolean idCheck = false;

	public BookRent() {
		// DBCon
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new FlowLayout());

		// 필드 설정.
		bookNo = new JTextField();
		
		
		bookLabel = new JLabel("  책     : ");
		add(bookLabel);

		bookField = new JTextField(25);
		add(bookField);
		JButton searchBtn = new JButton("검색");
		searchBtn.setBounds(290, 17, 80, 29); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		searchBtn.setBackground(Color.gray); // 바탕 색.
		searchBtn.setForeground(Color.white);// 글씨 색.

		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					String bookTitle = bookField.getText();
					rs = stmt.executeQuery("select * from tbl_book where title ='" + bookTitle + "'");

					model.setNumRows(0);
					String[] row = new String[2];//
					while (rs.next()) {
						row[0] = rs.getString("BID");
						row[1] = rs.getString("TITLE");
						model.addRow(row);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});

		add(searchBtn);

		idLabel = new JLabel("학번  : ");
		add(idLabel);

		idField = new JTextField(25);
		add(idField);
		JButton checkBtn = new JButton("체크");
		checkBtn.setBounds(290, 57, 80, 29); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		checkBtn.setBackground(Color.BLUE); // 바탕 색.
		checkBtn.setForeground(Color.white);// 글씨 색.

		checkBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String idCnt = "";
				String idVal = "";
				String nameVal = "";
				try {
					String id = idField.getText();
					rs = stmt.executeQuery("select count(*)as cnt from student2 where id = '" + id + "'");
					while (rs.next()) {
						idCnt = rs.getString("cnt");
					}

					System.out.println("cnt  :  " + idCnt);

					if (idCnt.equals("1")) {
						rs = stmt.executeQuery("select * from student2 where id = '" + id + "'");

						while (rs.next()) {
							idVal = rs.getString("ID");
							nameVal = rs.getString("NAME");
						}
						JOptionPane.showMessageDialog(null, "학번  : " + idVal + " ,  이름    : " + nameVal + "  ");
						idCheck = true;
					} else {
						JOptionPane.showMessageDialog(null, "해당 아이디가 없습니다.");
						idCheck = false;
					}

				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
		add(checkBtn);

		String colName[] = { "번호", "책" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(370, 300));
		table.setBounds(20, 100, 300, 300); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		add(new JScrollPane(table));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable) e.getComponent();
				model = (DefaultTableModel) table.getModel();
				String bno = (String) model.getValueAt(table.getSelectedRow(), 0);
				String bookName = (String) model.getValueAt(table.getSelectedRow(), 1);

				bookNo.setText(bno);
				bookField.setText(bookName);
			}
		});
		
		JButton RentBtn = new JButton("대출");
		RentBtn.setBackground(Color.BLACK); // 바탕 색.
		RentBtn.setForeground(Color.white);// 글씨 색.
		RentBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

                      
					if (bookField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "책 을 선택 하세요.");
					} else if(idField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "학번을 입력해 주세요.");
					}else {
						if(idCheck == true) {
							int result = 0;
							result = JOptionPane.showConfirmDialog(null, "대출 하시겠습니까?");
							if (result == 0) {
								pstmt = conn.prepareStatement("insert into bookrent(no,id,bookno) values(bookrent_seq.nextval,?,?)");
								pstmt.setString(1, idField.getText());
								pstmt.setString(2, bookNo.getText());
								pstmt.executeUpdate();
								
								pstmt = conn.prepareStatement("update tbl_book set numcheck = ? where BID = ?");
								pstmt.setInt(1, 0);
								pstmt.setString(2, bookNo.getText());
								pstmt.executeUpdate();
								
								
								bookField.setText("");
								idField.setText("");
								totalList();
								JOptionPane.showMessageDialog(null, "대출 되었습니다.");
							} else {
								JOptionPane.showMessageDialog(null, "취소했습니다.");
							}
						}else {
							JOptionPane.showMessageDialog(null, "학번이 있는지 체크해 주세요.");
						}
						
					}

				} catch (Exception e2) {
				}

			}
		});
		add(RentBtn);

		totalList();
		setSize(400, 600);
		setVisible(true);

	}

	public void totalList() {
		try {
			rs = stmt.executeQuery("SELECT * from tbl_book where numcheck = 1");
			// JTable 초기화
			model.setNumRows(0);
			String[] row = new String[2];//
			while (rs.next()) {
				row[0] = rs.getString("BID");
				row[1] = rs.getString("TITLE");
				model.addRow(row);
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new BookRent();
	}

}
