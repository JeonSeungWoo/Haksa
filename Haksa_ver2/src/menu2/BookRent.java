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
	// ���̾ƿ�
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

		// �ʵ� ����.
		bookNo = new JTextField();
		
		
		bookLabel = new JLabel("  å     : ");
		add(bookLabel);

		bookField = new JTextField(25);
		add(bookField);
		JButton searchBtn = new JButton("�˻�");
		searchBtn.setBounds(290, 17, 80, 29); // �� ���� , ��� , ���빰 �¿�, ���빰 ����
		searchBtn.setBackground(Color.gray); // ���� ��.
		searchBtn.setForeground(Color.white);// �۾� ��.

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

		idLabel = new JLabel("�й�  : ");
		add(idLabel);

		idField = new JTextField(25);
		add(idField);
		JButton checkBtn = new JButton("üũ");
		checkBtn.setBounds(290, 57, 80, 29); // �� ���� , ��� , ���빰 �¿�, ���빰 ����
		checkBtn.setBackground(Color.BLUE); // ���� ��.
		checkBtn.setForeground(Color.white);// �۾� ��.

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
						JOptionPane.showMessageDialog(null, "�й�  : " + idVal + " ,  �̸�    : " + nameVal + "  ");
						idCheck = true;
					} else {
						JOptionPane.showMessageDialog(null, "�ش� ���̵� �����ϴ�.");
						idCheck = false;
					}

				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
		add(checkBtn);

		String colName[] = { "��ȣ", "å" };
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(370, 300));
		table.setBounds(20, 100, 300, 300); // �� ���� , ��� , ���빰 �¿�, ���빰 ����
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
		
		JButton RentBtn = new JButton("����");
		RentBtn.setBackground(Color.BLACK); // ���� ��.
		RentBtn.setForeground(Color.white);// �۾� ��.
		RentBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

                      
					if (bookField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "å �� ���� �ϼ���.");
					} else if(idField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "�й��� �Է��� �ּ���.");
					}else {
						if(idCheck == true) {
							int result = 0;
							result = JOptionPane.showConfirmDialog(null, "���� �Ͻðڽ��ϱ�?");
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
								JOptionPane.showMessageDialog(null, "���� �Ǿ����ϴ�.");
							} else {
								JOptionPane.showMessageDialog(null, "����߽��ϴ�.");
							}
						}else {
							JOptionPane.showMessageDialog(null, "�й��� �ִ��� üũ�� �ּ���.");
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
			// JTable �ʱ�ȭ
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
