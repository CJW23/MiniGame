/*
���� : �����
�� : 1���� JAVA(1��)
���α׷� �̸� : Main.java
���α׷� ���� : ���α׷��� ���� �Լ�
*/
package Main;

import game1.*;
import game2.*;
import game3.*;
import rank.*;
public class Main {
	public static boolean g2 = false;
	public static boolean g1 = false;
	public static boolean g3 = false;
	public static boolean rpg3 = false;
	public static boolean rpg2 = false;
	public static boolean rpg1 = false;
	public static boolean menu= false;
	public static final int SCREEN_WIDTH = 1920;
	public static final int SCREEN_HEIGHT = 1080;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Up ol = null;
		Run gb = null;
		MainScreen ms;
		ms = new MainScreen();
		while (true) {
			System.out.println("");
			if(menu == true)			//�޴� ���� ��ȣ
			{
				new MainScreen();
				menu = false;
			}
			if(rpg3 == true)			//�ö� ���� ����� ��ȣ
			{
				new Up().st();
				
			}
			if (g3 == true) {			//�ö� ���� ���� ��ȣ
				new Up().st();
				g3 = false;
			}
			if(g1 == true)				//�޷� ���� ���� ��ȣ
			{
				gb = new Run();
				gb.run();
				g1 = false;
			}
			if(rpg1 == true)			//�޷� ���� ����� ��ȣ
			{
				new Run().run();
			}
			if(rpg2 == true)			//���� ���� ����� ��ȣ
			{
				new Dump().START();
			}
			if(g2 == true)				//���� ���� ���� ��ȣ
			{
				new Dump().START();
				g2 = false;
			}
		}

	}

}
