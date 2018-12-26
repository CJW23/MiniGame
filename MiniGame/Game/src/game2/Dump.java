/*
���� : ��ġȣ
�� : 1����JAVA(1��)
���α׷� �̸� : Dump.java
���α׷� ���� : Dump������ �����ϴ� ���α׷�
 */

package game2;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Main.Main;
import game2.db;

public class Dump extends JFrame implements KeyListener {

	// ĳ���� �� ������ �̹���
	private ImageIcon icon0 = new ImageIcon("2char.gif");   //�ɸ��� ������
	private ImageIcon icon1 = new ImageIcon("2paper1.PNG"); //������ 1 �̹���
	private ImageIcon icon2 = new ImageIcon("2paper3.PNG"); //������ 2 �̹���
	private ImageIcon icon3 = new ImageIcon("2can1.PNG");   // ĵ1 �̹���
	private ImageIcon icon4 = new ImageIcon("2can2.PNG");   // ĵ2 �̹���

	private ImageIcon icon5 = new ImageIcon("2paper.PNG");  //������ �������� �̹���
	private ImageIcon icon6 = new ImageIcon("2can.PNG");    //ĵ �������� �̹���

	private ImageIcon icon7 = new ImageIcon("2loadback.JPG");//����̹���

	private ImageIcon icon8 = new ImageIcon("2angry.gif");//ȭ��ĳ���� �̹���
	private ImageIcon icon9 = new ImageIcon("2left.PNG"); //�������� ������ �̹���
	private ImageIcon icon10 = new ImageIcon("2right.PNG");//���������� ������ �̹���

	//�Ҹ�
	File file = new File("2sound.wav");  // ������ ������ �Ҹ�
	
	// Ű���� �Է½� �ɸ��� �̹��� ���� ����
	boolean left = false;  //���� ������ �̹��� ����
	boolean right = false; //������ ������ �̹��� ����
	boolean angry = false; // ȭ�� �̹��� ����
	

	db dataBase;
	String id;
	
	
	int[][] r = new int[8][1];// ������ �������� �迭

	Random randomGenerator = new Random();

	// �̹���
	Image img;
	Image img2;
	Image buffImage;
	Image background;
	Graphics temp;

	
	String Time;// ȭ�鿡 �ð��� ǥ���ϱ����� ����
	

	
	String Score = "0";  //ȭ�鿡 ������ ǥ���ϱ� ���� ����
	int score = 0;       //������ ����ϱ����� ������ ����

	Time tm = new Time();

	public Dump() {
		setTitle("clean");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addKeyListener(this);
		setVisible(true);
	}

	// �����Լ�
	public void START() {

		tm.time = 60;   //�ʱ�ð� 60��
		tm.start();
		randomtrash(); //�������� ������ ����
		while (true) {
			repaint();   
			if (tm.time <= 0) {//�ð��� ������ ���α׷� ����
				break;
			}
		}
		tm.time = 0;
		dataBase = new db("third");
		if (dataBase.lowRank < score) {  //���� ������ ������ ���̽��� ����� �������� ���ٸ� �����Ϳ� ����
			while (true) {
				id = JOptionPane.showInputDialog(this, "�����ǿ� ������ �̸� �Է��� �Է��ϼ���(3���� ����)", "����",
						JOptionPane.QUESTION_MESSAGE);
				if (id.length() > 3)
					JOptionPane.showMessageDialog(null, "�̸��� 3�������Ϸ� �Է��ϼ���");
				else
					break;
			}
			dataBase.insertScore(id, score, "third");
		}
		String[] buttons = { "��õ�", "����ȭ��" };
		int result = JOptionPane.showOptionDialog(this, "���α׷��� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, buttons, "�ι�°");

		if (result == 0) {
			Main.rpg2 = true;
			dispose();
		} else {
			Main.menu = true;
			Main.rpg2 = false;
			dispose();
		}
	}

	// ���׸���
	public void bgprint() {
		background = icon7.getImage();
		temp.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

	// �ð����
	public void timeprint() {
		Time = Integer.toString(tm.time);
		temp.setFont(new Font("Arial", Font.ITALIC, 100));
		temp.drawString("time", 100, 310);
		temp.drawString(Time, 100, 400);
	}

	// �������� �� ĳ���� �׸���
	public void Trashprint() {
		img = icon5.getImage();
		temp.drawImage(img, 550, 720, 250, 250, this);

		img = icon6.getImage();
		temp.drawImage(img, 1100, 720, 250, 250, this);

		if (!angry && !left && !right) {
			img2 = icon0.getImage();
			temp.drawImage(img2, 850, 850, 200, 200, this);
		} else if (angry && !left && !right) {
			img2 = icon8.getImage();
			temp.drawImage(img2, 900, 850, 200, 200, this);
		} else if (!angry && left && !right) {
			img2 = icon9.getImage();
			temp.drawImage(img2, 850, 850, 200, 200, this);
		} else if (!angry && !left && right) {
			img2 = icon10.getImage();
			temp.drawImage(img2, 850, 850, 200, 200, this);
		}

	}

	// ���� ���
	public void Scoreprint() {

		temp.setFont(new Font("Arial", Font.ITALIC, 100));
		temp.drawString("score", 1400, 310);
		temp.drawString(Score, 1430, 400);

	}

	// ȭ�鿡 ��� �Լ�
	public void paint(Graphics g) {
		buffImage = createImage(2000, 1000);
		temp = buffImage.getGraphics();

		bgprint();
		timeprint();
		Trashprint();
		start_trash();
		Scoreprint();
		g.drawImage(buffImage, 0, 0, this);
	}

	// ������ �����Լ�
	public void start_trash() { 
		for (int i = 7; i >= 0; i--) {  //�����Ⱚ�� ���� �̹����� ���
			if (r[i][0] == 0)
				img = icon1.getImage();
			else if (r[i][0] == 1)
				img = icon2.getImage();
			else if (r[i][0] == 2)
				img = icon3.getImage();
			else if (r[i][0] == 3)
				img = icon4.getImage();

			temp.drawImage(img, 900, (80 * i), 120, 80, this);  //ȭ�鿡 ���

		}
	}

	// ������ ����
	public void randomtrash() {
		for (int i = 7; i >= 0; i--) {  //�����⸦ ������ ������ ����� �����Ⱚ�� ��ĭ�� ������ �̵�
			r[i][0] = randomGenerator.nextInt(4);
		}
	}

	// ���ο� ������ �����Լ�
	public void newtrash() {
		for (int i = 7; i > 0; i--) {  //������ �����⸦ ��ĭ�� ������ �̵�
			r[i][0] = r[i - 1][0];  
		}

		r[0][0] = randomGenerator.nextInt(4);  //������ �����⸦ �������� ���ο� �����⸦ ����
	}

	// ���ھ� ���� �Լ�
	public void add() { 
		++score;  //������ ������ ���ϱ�
		Score = String.valueOf(score);
	}

	// ���� ������ Ȯ���Լ�
	public void lefttrash() {  //���� Ű�� �Է¹��� ���
		if (r[7][0] == 0 || r[7][0] == 1) {  //���� ������ ���� ���� �������뿡 �´� �������ϰ��
			angry = false;
			left = true;  //���ʴ����� �̹��� ����ϱ����� ���� ����
			right = false;

			repaint();
			newtrash();
			add();
		} else {       //���� ������ ���� ���� �������뿡 �����ʴ� �������ϰ��
			angry = true;      //ȭ���̹��� ����� ���� ���� ����
			tm.time=tm.time-5; //�ð��� 5�� ����
			repaint();
		}
	}

	// ������ ������ Ȯ���Լ�
	public void righttrash() {   //������ Ű�� �Է¹��� ���
		if (r[7][0] == 2 || r[7][0] == 3) {//���� ������ ���� ������ �������뿡 �´� �������ϰ��
			left = false;
			right = true;//�����ʴ����� �̹��� ����ϱ����� ���� ����
			angry = false;

			repaint();

			newtrash();
			add();
		} else {//���� ������ ���� ������ �������뿡 �����ʴ� �������ϰ��
			angry = true;//ȭ���̹��� ����� ���� ���� ����
			tm.time=tm.time-5; //�ð��� 5�� ����
			repaint();
		}

	}

	public void sleep(int time) {

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	@Override

	public void keyPressed(KeyEvent ke) {
		int x = ke.getKeyCode();

	
			switch (x) {
			case 37:   //���ʹ��� ������ ��
				lefttrash(); //���� �и������Լ� ����
				try {  //�Ҹ����
					AudioInputStream stream = AudioSystem.getAudioInputStream(file);
					Clip clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();

				} catch (Exception e1) {

					e1.printStackTrace();
				}
				break;

			case 39:  //�����ʹ��� ������ ��
				righttrash();//������ �и������Լ� ����
				try {   //�Ҹ����
					AudioInputStream stream = AudioSystem.getAudioInputStream(file);
					Clip clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();

				} catch (Exception e1) {

					e1.printStackTrace();
				}
				
				break;
			}
		}
	

	@Override
	public void keyReleased(KeyEvent ke) {
		int x = ke.getKeyCode();

		switch (x) {
		case 37://���� Ű�� ������ �ִµ���
			sleep(100);  //�������� ������ �̹��� ��� �� 0.1���� 
			left = false; //���� �̹��� ����� ���� �������� ������ �̹��� �� ����
			break;
		case 39://������ Ű�� ������ �ִµ���
			sleep(100);//���������� ������ �̹��� ��� �� 0.1���� 
			right = false;//���� �̹��� ����� ���� ���������� ������ �̹��� �� ����
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

}
