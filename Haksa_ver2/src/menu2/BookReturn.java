package menu2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class BookReturn extends JPanel {
	// 데이터 베이스 연결
	ResultSet rs = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement stmt = Haksa.stmt;
	
	
	DefaultTableModel model;
	JTable table;
	String query;
	JComboBox deptSelectBox;

	//id, no 가져오는 taxt fild
	JTextField noField;
	JTextField bidField;
	
	
	boolean returnCheck = false;
	

	public BookReturn() {
		// DBCon
		try {
			DBManager db = new DBManager();
			conn = db.getConnection();
			stmt = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		query = "select s.*,b.*,br.* " + "from student2 s, tbl_book b, bookRent br " + "where br.id=s.id "
				+ "and br.bookNo=b.bid and outdate is null";

		setLayout(null);// 레이아웃설정. 레이아웃 사용 안함.

		noField =  new JTextField();
		bidField = new JTextField();
		  
		
		JLabel l_dept = new JLabel("학과");
		l_dept.setBounds(10, 10, 30, 20);

		add(l_dept);

		// 셀렉트 ====================
		// 셀렉트 ====================
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			list.add("전체");
			rs = stmt.executeQuery("select * from tbl_dept");
			while (rs.next()) {
				list.add(rs.getString("dept"));
			}

		} catch (Exception e) {

		}

		String[] selectRow = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			selectRow[i] = list.get(i);
		}

		deptSelectBox = new JComboBox(selectRow);
		deptSelectBox.setBounds(45, 10, 100, 20);
		add(deptSelectBox);
		// =======================================================

		deptSelectBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String deptString = (String)cb.getSelectedItem();
				query = "select s.*,b.*,br.* " + "from student2 s, tbl_book b, bookRent br " + "where br.id=s.id "
						+ "and br.bookNo=b.bid and outdate is null";

				if(deptString.equals("전체")) {
					query += " order by br.no";
				}else{
					query += " and s.dept='"+deptString+"' ";
					query += " order by br.no";
				}
				
				list();
			}
		});

		String colName[] = { "학번", "이름", "도서명", "대출일", "반납일","랜트번호","책번호"};
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(470, 200));
		table.getColumn("책번호").setWidth(0);
		table.getColumn("책번호").setMinWidth(0);
		table.getColumn("책번호").setMaxWidth(0);

		table.getColumn("랜트번호").setWidth(0);
		table.getColumn("랜트번호").setMinWidth(0);
		table.getColumn("랜트번호").setMaxWidth(0);
		
		add(table);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(10, 40, 460, 250);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable) e.getComponent();
				model = (DefaultTableModel) table.getModel();
				String no = (String) model.getValueAt(table.getSelectedRow(), 5);
				String bid = (String) model.getValueAt(table.getSelectedRow(), 6);

				noField.setText(no);
				bidField.setText(bid);
				returnCheck= true;
			}
		});
		add(sp);
		
		
		
		

		JButton returnBtn = new JButton("반납");
		returnBtn.setBounds(200, 300, 80, 29); // 좌 여백 , 상백 , 내용물 좌우, 내용물 상하
		returnBtn.setBackground(Color.BLACK); // 바탕 색.
		returnBtn.setForeground(Color.white);// 글씨 색.
		
		returnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					
					if(returnCheck == true) {
						
						int result = 0;
						result = JOptionPane.showConfirmDialog(null, "반납 하시겠습니까?");
						if (result == 0) {
							pstmt = conn.prepareStatement("update bookRent set outdate = sysdate where no = ?");
							pstmt.setString(1, noField.getText());
						
							pstmt.executeUpdate();
							
							pstmt = conn.prepareStatement("update tbl_book set numcheck = '1' where BID = ?");
							pstmt.setString(1, bidField.getText());
							
							JOptionPane.showMessageDialog(null, "반납 완료.");
							
							list();
						} else {
							JOptionPane.showMessageDialog(null, "취소했습니다.");
						}
						
					
						
						
					}else {
						JOptionPane.showMessageDialog(null, "반납할 책을 선택 해 주세요.");
					}
					
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		});
		
		add(returnBtn);
		
		setSize(490, 400);
		setVisible(true);
		list();
	}

	public void list() {
		try {
			System.out.println("연결되었습니다.....");
			System.out.println(query);
			// Select문 실행
			ResultSet rs = stmt.executeQuery(query);
			// JTable 초기화
			model.setNumRows(0);
			String[] row = new String[7];// 컬럼의 갯수가 4
			while (rs.next()) {
				
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("title");
				row[3] = rs.getString("indate");
				row[4] = rs.getString("outdate");
				row[5] = rs.getString("no");
				row[6] = rs.getString("BID");
				model.addRow(row);
			}
			rs.close();

		} catch (Exception e1) {
			// e.getStackTrace();
			System.out.println(e1.getMessage());
		}
	}

	public static void main(String[] args) {
		new BookReturn();
	}

}
