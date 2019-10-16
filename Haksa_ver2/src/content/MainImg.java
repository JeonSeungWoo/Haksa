package content;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainImg extends JPanel{

	public MainImg() {
		setLayout(null);
		
	    ImageIcon loginMainImg  = new ImageIcon("img/main.jpg");
	    Image loginMainImgReSize=  loginMainImg.getImage().getScaledInstance(600, 700, Image.SCALE_SMOOTH );
	    ImageIcon loginMainImgRe  = new ImageIcon(loginMainImgReSize);
	    JLabel imgLabel = new JLabel(loginMainImgRe);
	    imgLabel.setBounds(40, 20, 600, 500);  //좌 여백 , 상백 , 내용물 좌우, 내용물 상하
	    add(imgLabel);

	    this.setSize(700, 600);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainImg();
	}
	
}
