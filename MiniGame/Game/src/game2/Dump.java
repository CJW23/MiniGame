/*
구현 : 이치호
조 : 1등을JAVA(1조)
프로그램 이름 : Dump.java
프로그램 설명 : Dump게임을 실행하는 프로그램
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

	// 캐릭터 및 쓰레기 이미지
	private ImageIcon icon0 = new ImageIcon("2char.gif");   //케릭터 이지미
	private ImageIcon icon1 = new ImageIcon("2paper1.PNG"); //폐지류 1 이미지
	private ImageIcon icon2 = new ImageIcon("2paper3.PNG"); //폐지류 2 이미지
	private ImageIcon icon3 = new ImageIcon("2can1.PNG");   // 캔1 이미지
	private ImageIcon icon4 = new ImageIcon("2can2.PNG");   // 캔2 이미지

	private ImageIcon icon5 = new ImageIcon("2paper.PNG");  //폐지류 쓰레기통 이미지
	private ImageIcon icon6 = new ImageIcon("2can.PNG");    //캔 쓰레기통 이미지

	private ImageIcon icon7 = new ImageIcon("2loadback.JPG");//배경이미지

	private ImageIcon icon8 = new ImageIcon("2angry.gif");//화난캐릭터 이미지
	private ImageIcon icon9 = new ImageIcon("2left.PNG"); //왼쪽으로 던지는 이미지
	private ImageIcon icon10 = new ImageIcon("2right.PNG");//오른쪽으로 던지는 이미지

	//소리
	File file = new File("2sound.wav");  // 쓰레기 던지는 소리
	
	// 키보드 입력시 케릭터 이미지 구분 변수
	boolean left = false;  //왼쪽 던지는 이미지 변수
	boolean right = false; //오른쪽 던지는 이미지 변수
	boolean angry = false; // 화난 이미지 변수
	

	db dataBase;
	String id;
	
	
	int[][] r = new int[8][1];// 쓰레기 정보저장 배열

	Random randomGenerator = new Random();

	// 이미지
	Image img;
	Image img2;
	Image buffImage;
	Image background;
	Graphics temp;

	
	String Time;// 화면에 시간을 표시하기위한 변수
	

	
	String Score = "0";  //화면에 점수를 표시하기 위한 변수
	int score = 0;       //점수를 계산하기위한 정수형 변수

	Time tm = new Time();

	public Dump() {
		setTitle("clean");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addKeyListener(this);
		setVisible(true);
	}

	// 시작함수
	public void START() {

		tm.time = 60;   //초기시간 60초
		tm.start();
		randomtrash(); //랜덤으로 쓰레기 생성
		while (true) {
			repaint();   
			if (tm.time <= 0) {//시간이 끝나면 프로그램 멈춤
				break;
			}
		}
		tm.time = 0;
		dataBase = new db("third");
		if (dataBase.lowRank < score) {  //현재 점수가 데이터 베이스에 저장된 변수보다 높다면 데이터에 저장
			while (true) {
				id = JOptionPane.showInputDialog(this, "순위권에 들었어요 이름 입력을 입력하세요(3글자 이하)", "예제",
						JOptionPane.QUESTION_MESSAGE);
				if (id.length() > 3)
					JOptionPane.showMessageDialog(null, "이름을 3글자이하로 입력하세요");
				else
					break;
			}
			dataBase.insertScore(id, score, "third");
		}
		String[] buttons = { "재시도", "메인화면" };
		int result = JOptionPane.showOptionDialog(this, "프로그램을 종료하시겠습니까?", "제목", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째");

		if (result == 0) {
			Main.rpg2 = true;
			dispose();
		} else {
			Main.menu = true;
			Main.rpg2 = false;
			dispose();
		}
	}

	// 배경그리기
	public void bgprint() {
		background = icon7.getImage();
		temp.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

	// 시간출력
	public void timeprint() {
		Time = Integer.toString(tm.time);
		temp.setFont(new Font("Arial", Font.ITALIC, 100));
		temp.drawString("time", 100, 310);
		temp.drawString(Time, 100, 400);
	}

	// 쓰레기통 및 캐릭터 그리기
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

	// 점수 출력
	public void Scoreprint() {

		temp.setFont(new Font("Arial", Font.ITALIC, 100));
		temp.drawString("score", 1400, 310);
		temp.drawString(Score, 1430, 400);

	}

	// 화면에 출력 함수
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

	// 쓰레기 생성함수
	public void start_trash() { 
		for (int i = 7; i >= 0; i--) {  //쓰레기값에 따라 이미지를 출력
			if (r[i][0] == 0)
				img = icon1.getImage();
			else if (r[i][0] == 1)
				img = icon2.getImage();
			else if (r[i][0] == 2)
				img = icon3.getImage();
			else if (r[i][0] == 3)
				img = icon4.getImage();

			temp.drawImage(img, 900, (80 * i), 120, 80, this);  //화면에 출력

		}
	}

	// 쓰레기 설정
	public void randomtrash() {
		for (int i = 7; i >= 0; i--) {  //쓰레기를 맞출경우 기존에 저장된 쓰레기값을 한칸씩 앞으로 이동
			r[i][0] = randomGenerator.nextInt(4);
		}
	}

	// 새로운 쓰레기 생성함수
	public void newtrash() {
		for (int i = 7; i > 0; i--) {  //기존의 쓰레기를 한칸씩 앞으로 이동
			r[i][0] = r[i - 1][0];  
		}

		r[0][0] = randomGenerator.nextInt(4);  //마지막 쓰레기를 랜덤으로 새로운 쓰레기를 설정
	}

	// 스코어 설정 함수
	public void add() { 
		++score;  //맞출경우 점수를 더하기
		Score = String.valueOf(score);
	}

	// 왼쪽 쓰레기 확인함수
	public void lefttrash() {  //왼쪽 키를 입력받을 경우
		if (r[7][0] == 0 || r[7][0] == 1) {  //현재 쓰레기 값이 왼쪽 쓰레기통에 맞는 쓰레기일경우
			angry = false;
			left = true;  //왼쪽던지는 이미지 출력하기위해 변수 변경
			right = false;

			repaint();
			newtrash();
			add();
		} else {       //현재 쓰레기 값이 왼쪽 쓰레기통에 맞지않는 쓰레기일경우
			angry = true;      //화난이미지 출력을 위해 변수 변경
			tm.time=tm.time-5; //시간을 5초 줄임
			repaint();
		}
	}

	// 오른쪽 쓰레기 확인함수
	public void righttrash() {   //오른쪽 키를 입력받을 경우
		if (r[7][0] == 2 || r[7][0] == 3) {//현재 쓰레기 값이 오른쪽 쓰레기통에 맞는 쓰레기일경우
			left = false;
			right = true;//오른쪽던지는 이미지 출력하기위해 변수 변경
			angry = false;

			repaint();

			newtrash();
			add();
		} else {//현재 쓰레기 값이 오른쪽 쓰레기통에 맞지않는 쓰레기일경우
			angry = true;//화난이미지 출력을 위해 변수 변경
			tm.time=tm.time-5; //시간을 5초 줄임
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
			case 37:   //왼쪽방향 눌렀을 때
				lefttrash(); //왼쪽 분리수거함수 실행
				try {  //소리출력
					AudioInputStream stream = AudioSystem.getAudioInputStream(file);
					Clip clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();

				} catch (Exception e1) {

					e1.printStackTrace();
				}
				break;

			case 39:  //오른쪽방향 눌렀을 때
				righttrash();//오른쪽 분리수거함수 실행
				try {   //소리출력
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
		case 37://왼쪽 키를 누르고 있는동안
			sleep(100);  //왼쪽으로 던지는 이미지 출력 후 0.1초후 
			left = false; //원래 이미지 출력을 위해 왼쪽으로 던지는 이미지 값 변경
			break;
		case 39://오른쪽 키를 누르고 있는동안
			sleep(100);//오른쪽으로 던지는 이미지 출력 후 0.1초후 
			right = false;//원래 이미지 출력을 위해 오른쪽으로 던지는 이미지 값 변경
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

}
