/*
구현 : 최재우
조 : 1등을 JAVA(1조)
프로그램 이름 : Run.java
프로그램 설명 : Run게임을 실행하는 프로그램
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
	//블록의 위치 값
	public final int ONE = 460;
	public final int TWO = 665;
	public final int THREE = 870;
	public final int FOUR = 1075;
	public final int FIVE = 1280;
	//DB 객체
	db dataBase;
	//key 입력 확인 변수
	boolean keyLeft = false;
	boolean keyRight = false;

	int tempspeed = 2;	//초기속도
	int levelup = 1;	//level 변수
	int balance = 0;	//증가될 속도량 변수
	String cscore;		//점수를 string으로 저장할 변수
	String id;			//사용자 이름 입력받을 변수
	int score = 0;		//점수 젖아 변수
	int xcld = 400, ycld = 100;		//배경 구름1 좌표
	int xcld2 = 1500, ycld2 = 300;	//배경 구름 2 좌표
	boolean bcld = true;		//배경 구름1 방향 변환 변수
	boolean bcld2 = true;		//배경 구름2 방향 변환 변수
	ArrayList<BlockXY> bl = new ArrayList<BlockXY>();	//block 객체 저장할 arraylist
	boolean gOver = false;		//gameover 확인 변수
	
	//이미지 변수
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

	
	BlockXY q;	//블록 객체 임시 저장 
	CharacterXY crt;		//캐릭터 객체

	File file = new File("1sound.wav");	//효과음
	public Run() {										//생성자
		addKeyListener(this);							//키보드 입력
		
		//이미지 변수
		cld1 = new ImageIcon("1cld.png").getImage();
		cld2 = new ImageIcon("1cld1.png").getImage();
		bar = new ImageIcon("1lie.jpg").getImage();
		background = new ImageIcon("awd.jpg").getImage();
		mother = new ImageIcon("1angrymom.gif").getImage();
		blockImage = new ImageIcon("1blockImage.gif").getImage();
		blockImage2 = new ImageIcon("1blockImage.png").getImage();
		characterImage = new ImageIcon("1movech.gif").getImage();
		
		crt = new CharacterXY(0, 0, 0, tempspeed);	//캐릭터 객체 초기화

		//Frame 설정
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setVisible(true);
	}

	public void run() {
		
		try {
			while (true) {				//게임 진행 while
				BlockProcess();			//블록 생성 메소드
				CharacterProcess();		//캐릭터 메소드
				repaint();				//화면 갱신
				if (gOver == true) {	//gameover 확인
					break;
				}
				Thread.sleep(10);
			}
			
			dataBase = new db("first");								//DB 초기화
			if (dataBase.lowRank < score) {							//점수가 DB에 저장된 점수의 5위보다 크다면
				while (true) {
					id = JOptionPane.showInputDialog(this, "순위권에 들었어요 이름 입력을 입력하세요(3글자 이하)", "예제",JOptionPane.QUESTION_MESSAGE);		//이름 입력 창 			
					if (id.length() > 3)		//만약 이름이 3글자 이상이라면
						JOptionPane.showMessageDialog(null, "이름을 3글자이하로 입력하세요");		
					else
						break;
				}
				dataBase.insertScore(id, score, "first");		//데이터 삽입
			}
			String[] buttons = { "재시도", "메인화면" };		
			int result = JOptionPane.showOptionDialog(this, "프로그램을 종료하시겠습니까?", "제목", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째");		//재시도 버튼

			if (result == 0)		//재시도 버튼을 부르면
			{	
				Main.rpg1 = true;
				dispose();
			}
			else		//메인화면 버튼을 누르면
			{
				Main.menu = true;
				Main.rpg1 = false;
				dispose();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {		//갱신 해줄 화면 함수
		buffImage = createImage(2000, 1100);
		buffg = buffImage.getGraphics();
		DrawBackground();
		ScoreAndSpeed();
		DrawBlock();
		DrawCharacter();

		g.drawImage(buffImage, 0, 0, this);

	}

	public void ScoreAndSpeed() {		//점수 표시 및 속도 증가 함수
		cscore = Integer.toString(score);		//점수를 string으로 저장
		if (score == 25 * levelup + balance) {		//속도 증가 식
			balance += 15;
			crt.speedUp();
			for (int i = 0; i < bl.size(); i++) {		//각 블록객체 마다 속도 증가 적용
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

	public void cloud() {		//배경화면 구름1 컨트롤 함수 구름이 움직이는 것처럼 표현
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

	public void cloud2() {		//배경화면 구름2 컨트롤 함수 구름이 움직이는 것처럼 표현
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

	public void DrawBackground() {					//배경화면 함수 Draw 함수

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

	public void DrawBlock() {		//블록 Draw 함수
		int n;
		for (int i = 0; i < bl.size(); i++) {
			q = (BlockXY) bl.get(i);
			buffg.drawImage(blockImage, q.x, q.y, 200, 80, this);
		}
	}

	public void DrawCharacter() {	//캐릭터 Draw 함수
		buffg.drawImage(characterImage, crt.x, crt.y, 200, 100, this);
	}

	public void CharacterProcess() {		//캐릭터 움직임 함수
		if (bl.size() == 1) {		//블록이 한개 일때(게임이 처음 시작될때)
			q = (BlockXY) bl.get(0);
			crt.change(q.x, q.y - 70, q.sl);			//블록 좌표에 캐릭터를 출력
		}
		
		
		if (keyLeft == true) {		//왼쪽 키를 눌렀다면
			crt.leftLocate();		//캐릭터 x좌표 위치를 -1 시켜준다. (왼쪽 -, 오른쪽 +)
			q = (BlockXY) bl.get(bl.size() - crt.ylocate + 1);		//캐릭터가 오를 다음 블록 객체 임시 저장
			if (q.sl == crt.xlocate) {		//블록의 x좌표위치와 캐릭터 x좌표 위치가 같다면
				score += 1;					//점수 +1;
				crt.ylocate -= 1;			//캐릭터의 y좌표를 -1해준다.(구름이 생성될때마다 캐릭터의 y좌표는 +된다.)
				crt.change(q.x, q.y - 70, q.sl);		//캐릭터의 좌표를 바꿔준다.
				keyLeft = false;		//key값 false로 바꿔준다.
			} else
				gOver = true;			//만약 블록의 x좌표위치와 캐릭터 x좌표 위치가 다르다면
		} else if (keyRight == true) {		//오른쪽 키를 눌렀다면 위랑 반대로 똑같은 과정이다.
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
		crt.move();		//캐릭터가 아래로 떨어지는 함수
		
		
		if ((crt.y + 100) > 830) // 종료 부분
		{
			gOver = true;
		}
	}

	public void keyPressed(KeyEvent e) {		//키보드 입력 함수
		if (crt.ylocate > 1) {		//캐릭터의 위치가 블록이 생성된 최대 위쪽이 아니라면
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: {		//왼쪽 키

				if (crt.maxLeft() == false) {		//왼쪽 끝이 아니라면
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
			case KeyEvent.VK_RIGHT: {		//오른쪽 키
				if (crt.maxRight() == false) {		//오른쪽 끝이 아니라면
					keyRight = true;
					try {
						//소리 출력
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

	public void BlockProcess() {		//block 생성 함수
		int n;
		if (bl.isEmpty() == false)			//list가 비어있지 않다면
			q = (BlockXY) bl.get(bl.size() - 1);		//가장 최근에 생성된 블록 객체 임시 저장
		if (bl.isEmpty() == true) {			//만약 블록이 하나도 없다면
			n = 1 + (int) (Math.random() * 6);		//블록 위치 결정 할 난수
			switch (n) {		//난수에 따라 블록 위치를 결정해 블록 객체 생성
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
			}//블록 객체가 비어있지 않을 때
		} else if (q.sl == 1) {			//가장 최근에 생성된 블록의 위치가 1일 때 	
			q = (BlockXY) bl.get(bl.size() - 1);
			if (q.y > 70) {		//블록 전체가 화면에 출력될 때 
				q = new BlockXY(TWO, 0, 2, tempspeed);		//블록 생성
				bl.add(q);
				crt.ylocate += 1;
			}
		} else if (q.sl == 2) {		//위치가 2일 때
			if (q.y > 70) {
				n = 1 + (int) (Math.random() * 3);		//1~2 랜덤 수 생성
				switch (n) {
				case 1: {
					q = new BlockXY(ONE, 0, 1, tempspeed);		//1이면 블록의 왼쪽에 새블록 객체 생성
					bl.add(q);
					crt.ylocate += 1;			
					break;
				}
				case 2: {
					q = new BlockXY(THREE, 0, 3, tempspeed);	//2이면 블록의 오른쪽에 새블록 객체 생성
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

	class BlockXY {		//블록 클래스
		int x, y, speed, sl;

		BlockXY(int x, int y, int sl, int sp) {
			this.x = x;
			this.y = y;
			this.sl = sl;
			speed = sp;
		}

		public void move() {
			y += speed;			//블록 떨어지는 스피드
		}

		public void speedUp() {		//스피드 증가 메소드
			speed += 1;
		}
	}

	class CharacterXY {		//캐릭터 클래스
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
