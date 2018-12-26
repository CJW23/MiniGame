/*
���� : �����
�� : 1���� JAVA(1��)
���α׷� �̸� : Run.java
���α׷� ���� : Run������ �����ϴ� ���α׷�
 */
package game1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Main.Main;
import game1.Run.BlockXY;

public class Run extends JFrame implements Runnable, KeyListener {
	//����� ��ġ ��
	public final int ONE = 460;
	public final int TWO = 665;
	public final int THREE = 870;
	public final int FOUR = 1075;
	public final int FIVE = 1280;
	//DB ��ü
	db dataBase;
	//key �Է� Ȯ�� ����
	boolean keyLeft = false;
	boolean keyRight = false;

	int tempspeed = 2;	//�ʱ�ӵ�
	int levelup = 1;	//level ����
	int balance = 0;	//������ �ӵ��� ����
	String cscore;		//������ string���� ������ ����
	String id;			//����� �̸� �Է¹��� ����
	int score = 0;		//���� ���� ����
	int xcld = 400, ycld = 100;		//��� ����1 ��ǥ
	int xcld2 = 1500, ycld2 = 300;	//��� ���� 2 ��ǥ
	boolean bcld = true;		//��� ����1 ���� ��ȯ ����
	boolean bcld2 = true;		//��� ����2 ���� ��ȯ ����
	ArrayList<BlockXY> bl = new ArrayList<BlockXY>();	//block ��ü ������ arraylist
	boolean gOver = false;		//gameover Ȯ�� ����
	
	//�̹��� ����
	Image cld1;
	Image cld2;
	Image buffImage;
	Image mother;
	Image blockImage;
	Image blockImage2;
	Image bar;
	Image characterImage;
	Image background;
	Graphics buffg;

	
	BlockXY q;	//��� ��ü �ӽ� ���� 
	CharacterXY crt;		//ĳ���� ��ü

	File file = new File("1sound.wav");	//ȿ����
	public Run() {										//������
		addKeyListener(this);							//Ű���� �Է�
		
		//�̹��� ����
		cld1 = new ImageIcon("1cld.png").getImage();
		cld2 = new ImageIcon("1cld1.png").getImage();
		bar = new ImageIcon("1lie.jpg").getImage();
		background = new ImageIcon("awd.jpg").getImage();
		mother = new ImageIcon("1angrymom.gif").getImage();
		blockImage = new ImageIcon("1blockImage.gif").getImage();
		blockImage2 = new ImageIcon("1blockImage.png").getImage();
		characterImage = new ImageIcon("1movech.gif").getImage();
		
		crt = new CharacterXY(0, 0, 0, tempspeed);	//ĳ���� ��ü �ʱ�ȭ

		//Frame ����
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setVisible(true);
	}

	public void run() {
		
		try {
			while (true) {				//���� ���� while
				BlockProcess();			//��� ���� �޼ҵ�
				CharacterProcess();		//ĳ���� �޼ҵ�
				repaint();				//ȭ�� ����
				if (gOver == true) {	//gameover Ȯ��
					break;
				}
				Thread.sleep(10);
			}
			
			dataBase = new db("first");								//DB �ʱ�ȭ
			if (dataBase.lowRank < score) {							//������ DB�� ����� ������ 5������ ũ�ٸ�
				while (true) {
					id = JOptionPane.showInputDialog(this, "�����ǿ� ������ �̸� �Է��� �Է��ϼ���(3���� ����)", "����",JOptionPane.QUESTION_MESSAGE);		//�̸� �Է� â 			
					if (id.length() > 3)		//���� �̸��� 3���� �̻��̶��
						JOptionPane.showMessageDialog(null, "�̸��� 3�������Ϸ� �Է��ϼ���");		
					else
						break;
				}
				dataBase.insertScore(id, score, "first");		//������ ����
			}
			String[] buttons = { "��õ�", "����ȭ��" };		
			int result = JOptionPane.showOptionDialog(this, "���α׷��� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, buttons, "�ι�°");		//��õ� ��ư

			if (result == 0)		//��õ� ��ư�� �θ���
			{	
				Main.rpg1 = true;
				dispose();
			}
			else		//����ȭ�� ��ư�� ������
			{
				Main.menu = true;
				Main.rpg1 = false;
				dispose();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {		//���� ���� ȭ�� �Լ�
		buffImage = createImage(2000, 1100);
		buffg = buffImage.getGraphics();
		DrawBackground();
		ScoreAndSpeed();
		DrawBlock();
		DrawCharacter();

		g.drawImage(buffImage, 0, 0, this);

	}

	public void ScoreAndSpeed() {		//���� ǥ�� �� �ӵ� ���� �Լ�
		cscore = Integer.toString(score);		//������ string���� ����
		if (score == 25 * levelup + balance) {		//�ӵ� ���� ��
			balance += 15;
			crt.speedUp();
			for (int i = 0; i < bl.size(); i++) {		//�� ��ϰ�ü ���� �ӵ� ���� ����
				q = (BlockXY) bl.get(i);
				q.speedUp();
				tempspeed = q.speed;
			}
			levelup += 1;
		}
		buffg.setFont(new Font("Arial", Font.ITALIC, 100));
		buffg.drawString("SCORE", 50, 200);
		buffg.drawString(cscore, 200, 350);
	}

	public void cloud() {		//���ȭ�� ����1 ��Ʈ�� �Լ� ������ �����̴� ��ó�� ǥ��
		if (bcld == true) {
			xcld += 1;
			buffg.drawImage(cld1, xcld, 100, 200, 200, this);
			if (xcld > 1400) {
				xcld -= 3;
				bcld = false;
			}
		} else {
			xcld -= 1;
			buffg.drawImage(cld1, xcld, 100, 200, 200, this);
			if (xcld < 400)
				bcld = true;
		}
	}

	public void cloud2() {		//���ȭ�� ����2 ��Ʈ�� �Լ� ������ �����̴� ��ó�� ǥ��
		if (bcld2 == true) {
			xcld2 += 1;
			buffg.drawImage(cld2, xcld2, 400, 200, 200, this);
			if (xcld2 >= 1600) {
				xcld2 -= 3;
				bcld2 = false;
			}
		} else {
			xcld2 -= 1;
			buffg.drawImage(cld2, xcld2, 400, 200, 200, this);
			if (xcld2 <= 600)
				bcld2 = true;
		}
	}

	public void DrawBackground() {					//���ȭ�� �Լ� Draw �Լ�

		buffg.drawImage(background, 0, 0, 1920, 1080, this);
		buffg.drawImage(bar, 455, 0, 5, 1200, this);
		buffg.drawImage(bar, 660, 0, 5, 1200, this);
		buffg.drawImage(bar, 865, 0, 5, 1200, this);
		buffg.drawImage(bar, 1070, 0, 5, 1200, this);
		buffg.drawImage(bar, 1275, 0, 5, 1200, this);
		buffg.drawImage(bar, 1485, 0, 5, 1200, this);
		cloud();
		cloud2();
		buffg.drawImage(mother, 450, 500, 1000, 730, this);
	}

	public void DrawBlock() {		//��� Draw �Լ�
		int n;
		for (int i = 0; i < bl.size(); i++) {
			q = (BlockXY) bl.get(i);
			buffg.drawImage(blockImage, q.x, q.y, 200, 80, this);
		}
	}

	public void DrawCharacter() {	//ĳ���� Draw �Լ�
		buffg.drawImage(characterImage, crt.x, crt.y, 200, 100, this);
	}

	public void CharacterProcess() {		//ĳ���� ������ �Լ�
		if (bl.size() == 1) {		//����� �Ѱ� �϶�(������ ó�� ���۵ɶ�)
			q = (BlockXY) bl.get(0);
			crt.change(q.x, q.y - 70, q.sl);			//��� ��ǥ�� ĳ���͸� ���
		}
		
		
		if (keyLeft == true) {		//���� Ű�� �����ٸ�
			crt.leftLocate();		//ĳ���� x��ǥ ��ġ�� -1 �����ش�. (���� -, ������ +)
			q = (BlockXY) bl.get(bl.size() - crt.ylocate + 1);		//ĳ���Ͱ� ���� ���� ��� ��ü �ӽ� ����
			if (q.sl == crt.xlocate) {		//����� x��ǥ��ġ�� ĳ���� x��ǥ ��ġ�� ���ٸ�
				score += 1;					//���� +1;
				crt.ylocate -= 1;			//ĳ������ y��ǥ�� -1���ش�.(������ �����ɶ����� ĳ������ y��ǥ�� +�ȴ�.)
				crt.change(q.x, q.y - 70, q.sl);		//ĳ������ ��ǥ�� �ٲ��ش�.
				keyLeft = false;		//key�� false�� �ٲ��ش�.
			} else
				gOver = true;			//���� ����� x��ǥ��ġ�� ĳ���� x��ǥ ��ġ�� �ٸ��ٸ�
		} else if (keyRight == true) {		//������ Ű�� �����ٸ� ���� �ݴ�� �Ȱ��� �����̴�.
			crt.rightLocate();
			q = (BlockXY) bl.get(bl.size() - crt.ylocate + 1);
			if (q.sl == crt.xlocate) {
				score += 1;
				crt.ylocate -= 1;
				crt.change(q.x, q.y - 70, q.sl);
				keyRight = false;
			} else
				gOver = true;
		}
		crt.move();		//ĳ���Ͱ� �Ʒ��� �������� �Լ�
		
		
		if ((crt.y + 100) > 830) // ���� �κ�
		{
			gOver = true;
		}
	}

	public void keyPressed(KeyEvent e) {		//Ű���� �Է� �Լ�
		if (crt.ylocate > 1) {		//ĳ������ ��ġ�� ����� ������ �ִ� ������ �ƴ϶��
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: {		//���� Ű

				if (crt.maxLeft() == false) {		//���� ���� �ƴ϶��
					keyLeft = true;
					try {

						AudioInputStream stream = AudioSystem.getAudioInputStream(file);
						Clip clip = AudioSystem.getClip();
						clip.open(stream);
						clip.start();
							
					} catch (Exception e1) {

						e1.printStackTrace();
					}
				}
				break;
			}
			case KeyEvent.VK_RIGHT: {		//������ Ű
				if (crt.maxRight() == false) {		//������ ���� �ƴ϶��
					keyRight = true;
					try {
						//�Ҹ� ���
						AudioInputStream stream = AudioSystem.getAudioInputStream(file);		
						Clip clip = AudioSystem.getClip();
						clip.open(stream);
						clip.start();

					} catch (Exception e1) {

						e1.printStackTrace();
					}

				}
				break;
			}
			}
		}
	}

	public void BlockProcess() {		//block ���� �Լ�
		int n;
		if (bl.isEmpty() == false)			//list�� ������� �ʴٸ�
			q = (BlockXY) bl.get(bl.size() - 1);		//���� �ֱٿ� ������ ��� ��ü �ӽ� ����
		if (bl.isEmpty() == true) {			//���� ����� �ϳ��� ���ٸ�
			n = 1 + (int) (Math.random() * 6);		//��� ��ġ ���� �� ����
			switch (n) {		//������ ���� ��� ��ġ�� ������ ��� ��ü ����
			case 1: {
				q = new BlockXY(ONE, 0, 1, tempspeed);
				bl.add(q);
				crt.ylocate += 1;
				break;
			}
			case 2: {
				q = new BlockXY(TWO, 0, 2, tempspeed);
				bl.add(q);
				crt.ylocate += 1;
				break;
			}
			case 3: {
				q = new BlockXY(THREE, 0, 3, tempspeed);
				bl.add(q);
				crt.ylocate += 1;
				break;
			}
			case 4: {
				q = new BlockXY(FOUR, 0, 4, tempspeed);
				bl.add(q);
				crt.ylocate += 1;
				break;
			}
			case 5: {
				q = new BlockXY(FIVE, 0, 5, tempspeed);
				bl.add(q);
				crt.ylocate += 1;
				break;
			}
			}//��� ��ü�� ������� ���� ��
		} else if (q.sl == 1) {			//���� �ֱٿ� ������ ����� ��ġ�� 1�� �� 	
			q = (BlockXY) bl.get(bl.size() - 1);
			if (q.y > 70) {		//��� ��ü�� ȭ�鿡 ��µ� �� 
				q = new BlockXY(TWO, 0, 2, tempspeed);		//��� ����
				bl.add(q);
				crt.ylocate += 1;
			}
		} else if (q.sl == 2) {		//��ġ�� 2�� ��
			if (q.y > 70) {
				n = 1 + (int) (Math.random() * 3);		//1~2 ���� �� ����
				switch (n) {
				case 1: {
					q = new BlockXY(ONE, 0, 1, tempspeed);		//1�̸� ����� ���ʿ� ����� ��ü ����
					bl.add(q);
					crt.ylocate += 1;			
					break;
				}
				case 2: {
					q = new BlockXY(THREE, 0, 3, tempspeed);	//2�̸� ����� �����ʿ� ����� ��ü ����
					bl.add(q);
					crt.ylocate += 1;
					break;
				}
				}
			}
		} else if (q.sl == 3) {
			if (q.y > 70) {
				n = 1 + (int) (Math.random() * 3);
				switch (n) {
				case 1: {
					q = new BlockXY(TWO, 0, 2, tempspeed);
					bl.add(q);
					crt.ylocate += 1;
					break;
				}
				case 2: {
					q = new BlockXY(FOUR, 0, 4, tempspeed);
					bl.add(q);
					crt.ylocate += 1;
					break;
				}
				}
			}
		} else if (q.sl == 4) {
			if (q.y > 70) {
				n = 1 + (int) (Math.random() * 3);
				switch (n) {
				case 1: {
					q = new BlockXY(THREE, 0, 3, tempspeed);
					bl.add(q);
					crt.ylocate += 1;
					break;
				}
				case 2: {
					q = new BlockXY(FIVE, 0, 5, tempspeed);
					bl.add(q);
					crt.ylocate += 1;
					break;
				}
				}
			}
		} else if (q.sl == 5) {
			if (q.y > 70) {
				q = new BlockXY(FOUR, 0, 4, tempspeed);
				bl.add(q);
				crt.ylocate += 1;
			}
		}
		for (int i = 0; i < bl.size(); i++) {
			q = (BlockXY) bl.get(i);
			q.move();
			if (q.y > 730) {
				bl.remove(i);
			}
		}
	}

	class BlockXY {		//��� Ŭ����
		int x, y, speed, sl;

		BlockXY(int x, int y, int sl, int sp) {
			this.x = x;
			this.y = y;
			this.sl = sl;
			speed = sp;
		}

		public void move() {
			y += speed;			//��� �������� ���ǵ�
		}

		public void speedUp() {		//���ǵ� ���� �޼ҵ�
			speed += 1;
		}
	}

	class CharacterXY {		//ĳ���� Ŭ����
		int x, y, xlocate, ylocate, speed;

		CharacterXY(int x, int y, int sl, int sp) {
			this.x = x;
			this.y = y;
			this.xlocate = sl;
			speed = sp;
			ylocate = 0;
		}

		public void move() {
			y += speed;
		}

		public void speedUp() {
			speed += 1;
		}

		public void rightLocate() {
			xlocate += 1;
		}

		public void leftLocate() {
			xlocate -= 1;
		}

		public boolean maxLeft() {
			if (xlocate == 1)
				return true;
			else
				return false;
		}

		public boolean maxRight() {
			if (xlocate == 5)
				return true;
			else
				return false;
		}

		public void changeLocate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void change(int x, int y, int sl) {
			this.x = x;
			this.y = y;
			this.xlocate = sl;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
