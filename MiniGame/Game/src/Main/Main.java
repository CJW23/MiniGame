/*
구현 : 최재우
조 : 1등을 JAVA(1조)
프로그램 이름 : Main.java
프로그램 설명 : 프로그램의 메인 함수
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
			if(menu == true)			//메뉴 실행 신호
			{
				new MainScreen();
				menu = false;
			}
			if(rpg3 == true)			//올라 게임 재시작 신호
			{
				new Up().st();
				
			}
			if (g3 == true) {			//올라 게임 시작 신호
				new Up().st();
				g3 = false;
			}
			if(g1 == true)				//달려 게임 시작 신호
			{
				gb = new Run();
				gb.run();
				g1 = false;
			}
			if(rpg1 == true)			//달려 게임 재시작 신호
			{
				new Run().run();
			}
			if(rpg2 == true)			//버려 게임 재시작 신호
			{
				new Dump().START();
			}
			if(g2 == true)				//버려 게임 시작 신호
			{
				new Dump().START();
				g2 = false;
			}
		}

	}

}
