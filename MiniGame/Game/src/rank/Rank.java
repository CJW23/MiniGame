/*
���� : �����
�� : 1����JAVA(1��)
���α׷� �̸� : Rank.java
���α׷� ���� : ��ŷ ȭ���� �����ִ� ���α׷�
 */
package rank;

import javax.swing.*;

import Main.MainScreen;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Rank extends JFrame {
	Image backg;
	Image backim;
	Image backmom;
	Image runmom;
	Image runch;
	Image runch1;
	Image buffImage;
	Graphics buffg;
	JLabel title;
	JLabel nametitle;
	JLabel scoretitle;
	JPanel panel;
	JButton back;
	JButton a;
	PrintRank database1;
	int score[][];
	String name[][];
	JLabel rk[][];
	String cscore[][];
	
	ArrayList<Pair<String, Integer>> game1;		//�޷� ���� ������ ������ arraylist
	ArrayList<Pair<String, Integer>> game2;		//�ö� ���� ������ ������ arraylist
	ArrayList<Pair<String, Integer>> game3;		//���� ���� ������ ������ arraylist

	public Rank() {
		System.out.println("awdwd");
		database1 = new PrintRank();		//PrintRank() ��ü ����
		game1 = database1.getRankInformation("first", 5);	//�޷� ���� ������ 1~5�� ����
		game2 = database1.getRankInformation("second", 5);	//�ö� ���� ������ 1~5�� ����
		game3 = database1.getRankInformation("third", 5);	//���� ���� ������ 1~5�� ����
		
		backg = new ImageIcon("4rankback.jpg").getImage();
		backmom = new ImageIcon("4backmom.png").getImage();
		runch = new ImageIcon("4runch.png").getImage();
		runch1 = new ImageIcon("4runch1.png").getImage();
		runmom = new ImageIcon("4starmom.png").getImage();
		
		setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Frame test");
		setLayout(null);
		
		//���ư��� ��ư ����
		back = new JButton(new ImageIcon("4backim.png"));
		back.setPressedIcon(new ImageIcon("press.png"));
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		back.setLocation(400, 800);
		back.setSize(200, 80);
		back.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){}
			public void mousePressed(MouseEvent e)
			{
				new MainScreen();		//���ư��� ������ ����ȭ������ ���ư�
				dispose();				//���� ȭ���� ������
			}
				});
		
		add(back);
		setVisible(true);
	}

	public void paint(Graphics g) {		//ȭ�� �׷��ִ� �Լ�
		buffImage = createImage(2000, 1000);
		buffg = buffImage.getGraphics();
		buffg.drawImage(backg, 0, 0, 1920, 1080, this);
		buffg.drawImage(backmom, 120, 700, 400, 400, this);
		buffg.drawImage(runmom, 500, 500, this);
		buffg.drawImage(runch, 200, 50, 200, 200, this);
		buffg.drawImage(runch1, 600, 50, 200, 200, this);
		printScoreId();

		g.drawImage(buffImage, 0, 0, this);
	}

	public void printScoreId() {		//�ؽ�Ʈ ��� 

		buffg.setFont(new Font("����", Font.ITALIC, 30));
		buffg.drawString("RANKING", 443, 100);
		buffg.drawString("Run!", 175, 250);
		buffg.drawString("Name", 100, 300);
		buffg.drawString("Score", 230, 300);
		buffg.drawString("Up!", 475, 250);
		buffg.drawString("Name", 400, 300);
		buffg.drawString("Score", 530, 300);
		buffg.drawString("Dump!", 775, 250);
		buffg.drawString("Name", 700, 300);
		buffg.drawString("Score", 830, 300);

		int sum = 400;
		//DB���� ����� �����͸� ������ִ� �κ�
		for (Pair<String, Integer> item : game1) {		
			buffg.drawString(item.getFirst(), 100, sum);
			buffg.drawString(item.getSecond().toString(), 230, sum);
			sum += 50;
		}
		sum = 400;
		for (Pair<String, Integer> item : game2) {
			buffg.drawString(item.getFirst(), 400, sum);
			buffg.drawString(item.getSecond().toString(), 530, sum);
			sum += 50;
		}
		sum = 400;
		for (Pair<String, Integer> item : game3) {
			buffg.drawString(item.getFirst(), 700, sum);
			buffg.drawString(item.getSecond().toString(), 830, sum);
			sum += 50;
		}

	}
}
