/*
구현 : 이치호
조 : 1등을JAVA(1조)
프로그램 이름 : Time.java
프로그램 설명 : 시간초를 새주는 프로그램
*/
package game2;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import Main.Main;
import game1.db;

public class Time extends Thread {

	int score;
	String id;
	db dataBase;
	public int time = 60;

	public void run() {
		while (time > 0) {

			try {
				Thread.sleep(1000);
				time--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
