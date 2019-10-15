package util;

import java.awt.Color;
import java.util.Arrays;

public class ColorUtil {

	
	public Color[] ColorReurn(int size) {
		
		//���� �迭�� ����.
		Color[] colorArr = new Color[size];
		
		
		//�ֱ������� ���� color �迭
		Color[] colorReturn = {Color.red,Color.blue,Color.green,Color.YELLOW,Color.black};
		
		//���� �迭�� �� ��
		int colorRe = 0;
		
		for (int i = 0; i < size; i++) {
			System.out.println(i);
			colorArr[i] = colorReturn[colorRe];
			colorRe = colorRe + 1;
			if ((i+1)%5 == 0) {
				colorRe = 0;
			}
		    
			if ((i+1)%5 == 0 && (i+1)%10 != 0) {
				colorArr[i] = Color.gray;
			}
			
			
		}
		
		return colorArr;
	}
	
	
	public static void main(String[] args) {
		ColorUtil co = new ColorUtil();
		System.out.println(Arrays.toString(co.ColorReurn(20)));
		
		
	}

}
