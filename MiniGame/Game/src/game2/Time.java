/*
���� : ��ġȣ
�� : 1����JAVA(1��)
���α׷� �̸� : Time.java
���α׷� ���� : �ð��ʸ� ���ִ� ���α׷�
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
