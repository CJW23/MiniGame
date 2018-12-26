/*
구현 : 곽동훈
조 : 1등을JAVA(1조)
프로그램 이름 : Up.java
프로그램 설명 : 올라 게임 실행 프로그램
*/
package game3;

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
import javax.swing.border.EmptyBorder;

import game3.db;
import Main.Main;

public class Up extends JFrame implements Runnable, KeyListener {

   int xsize = 120;
   int ysize = 100;
   int base_x = 200;

   public final int ONE = base_x + 0; // 1번째 블럭 x좌표
   public final int TWO = base_x + xsize; // 2번째 블럭 x좌표
   public final int THREE = base_x + (2 * xsize); // 3번째 블럭 x좌표
   public final int FOUR = base_x + (3 * xsize); // 4번째 블럭 x좌표
   public final int FIVE = base_x + (4 * xsize); // 5번째 블럭 x좌표
   public final int SIX = base_x + (5 * xsize); // 6번째 블럭 x좌표
   public final int SEVEN = base_x + (6 * xsize); // 7번째 블럭 x좌표
   public final int EIGHT = base_x + (7 * xsize); // 8번째 블럭 x좌표
   public final int NINE = base_x + (8 * xsize); // 9번째 블럭 x좌표
   public final int TEN = base_x + (9 * xsize); // 10번째 블럭 x좌표
   int keycode;
   boolean keyChange = false; // Space키
   boolean keyUP = false; // Enter키

   ArrayList<Block> arr = new ArrayList<Block>(); // 블록 배열
   Block q, v, fst, scd;
   Character tmp;
   Character c;
   Direction d;
   db database;
   MomCharacter mom;
   int result;
   int sz;
   int x = 0;
   int y = 900;
   String cscore;
   String ltime;
   int score = 0;
   int xcld = 400, ycld = 100;
   int xcld2 = 1500, ycld2 = 300;
   boolean bcld = true;
   boolean bcld2 = true;
   boolean makeStair = false;
   boolean momstart = false;
   boolean momMove = false;
   boolean Chmove = false;
   boolean gOver = false;
   Image img;
   Image img2;
   Image img3;
   Image img4;
   Image buffImage;
   Image leftCI;
   Image rightCI;
   Image leftStopCI;
   Image rightStopCI;
   Image rEffect;
   Image lEffect;
   Image cld1;
   Image cld2;
   Image background;
   Image background2;
   Image background3;
   Image background4;
   Image mother;
   Graphics buffg;
   String id;

   File file = new File("3sound.wav");

   int m = 1 + (int) (Math.random() * 2);

   LimitTime t = new LimitTime(this);

   public Up() // 화면 크기
   {
      this.addKeyListener(this);
      img2 = new ImageIcon("2cloud.gif").getImage();
      img4 = new ImageIcon("2block.png").getImage();
      leftCI = new ImageIcon("2runch.png").getImage(); // 캐릭터 이미지 파일
      rightCI = new ImageIcon("2runch2.png").getImage();
      rightStopCI = new ImageIcon("2Rstop.png").getImage();
      leftStopCI = new ImageIcon("2Lstop.png").getImage();
      rEffect = new ImageIcon("2effect.png").getImage();
      cld1 = new ImageIcon("2cld.png").getImage();
      cld2 = new ImageIcon("2cld2.png").getImage();
      background2 = new ImageIcon("2skyback.jpg").getImage();
      background4 = new ImageIcon("2greenback.png").getImage();
      mother = new ImageIcon("2runmom.gif").getImage();
      tmp = new Character(0, 0, 0);
      c = new Character(0, 0, 0);
      mom = new MomCharacter();
      
      setSize(1980,1080); //화면 사이즈
      setVisible(true);
   }

   public void cloud() { //움직이는 배경 구흠 함수
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

   public void cloud2() { //움직이는 배경 구흠 함수 2
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

   public void Limittime() { //제한시간 설정 함수
      ltime = Integer.toString(t.time);
      buffg.setFont(new Font("Arial", Font.ITALIC, 70));
      buffg.drawString("Limit time", 1500, 400); //제한시간 문구
      buffg.drawString(ltime, 1580, 500); //제한시간
   }

   public void Score() { //점수판 함수
      cscore = Integer.toString(score);
      buffg.setFont(new Font("Arial", Font.ITALIC, 70));
      buffg.drawString("SCORE", 1500, 200); //점수판 문구
      buffg.drawString(cscore, 1600, 300); //점수
   }

   public void st() {

      while (y >= 0) //전장에 닿을때까지
      {
         makeBlock(); //블록을 생성한다.
      } //시작시 블족이 천장까지 생성되어 있는 상태에서
      t.start(); //게임을 시작한다

      while (true) { //계속 반복
         sz = arr.size(); //블록 배열개수

         if (c.ylocate >= arr.size() / 2) //블록이 중간에 도달했을 때
            blockProcess(); //블록을 하나 새로 생성
         processCharacter(); // 캐릭터 움직이는 함수
         momProcess(); //밑에서 쫓아오는 엄마 
         
         this.paint(this.getGraphics());
         
         if (gOver == true) //게임 오버가 되면
            break; //게임 오버
      }
      database = new db("second"); //데이더 베이스
      if (database.lowRank < score) { //데이터 베이스에 있는 제일 낮은 점수보다 점수가 높으면
         while (true) {
            id = JOptionPane.showInputDialog(this, "순위권에 들었어요 이름 입력을 입력하세요(3글자 이하로 입력하시오)", "예제",
                  JOptionPane.QUESTION_MESSAGE);
            if (id.length() > 3) //이름의 길이가 3을 넘으면
               JOptionPane.showMessageDialog(null, "이름을 3글자이하로 입력하세요"); //오류 문구 출력
            else //아니면
               break; //break
         }
         database.insertScore(id, score, "second");
      }
      String[] buttons = { "재시도", "메인화면" }; //재도전 or 메인화면 창을 띄음
      result = JOptionPane.showOptionDialog(this, "프로그램을 종료하시겠습니까?", "제목", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째");
      if (result == 0) 
      {
         Main.rpg3 = true;
         dispose();
      } else
      {
         Main.menu = true;
         Main.rpg3= false;
         dispose();
      }
   }
   
   public void paint(Graphics g) {
      buffImage = createImage(2000, 1100);
      buffg = buffImage.getGraphics();
      update(g);
   }
   
   public void update(Graphics g) //점수 제한시간 그래팍
   {
      background(); //배경 함수
      drawBlock(); // 블록 그리는 함수
      DrawCharacter(); // 캐릭터 그리는 함수
      if (momstart == true) { //엄마가 올라올 상황에
         DrawMom(); //엄마를 그림
      }
      Score(); //점수
      Limittime();//제한시간을 표시
      g.drawImage(buffImage, 0, 0, null);
      try {
         Thread.sleep(20);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
   
   public void keyPressed(KeyEvent e) { //키보드 누를 때
      keycode = e.getKeyCode();
      switch (keycode) {
      case KeyEvent.VK_SPACE: { //스페이스를 누를 시
         
         keyUP = true; //업 플래그를 활성화
         keyChange = false; //체인지 플래그를 비활성화
         try { //소리나게 함(발판 오르는 소리)
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
         } catch (Exception e1) {
            e1.printStackTrace();
         }
         break;
      }
      case KeyEvent.VK_ENTER: { //엔터가 눌리면
         keyUP = false; //업 플래그를 비활성화
         keyChange = true; //체인지 플래그를 활성화
         try { //소리나게 함(발판 오르는 소리)
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
   }
   
   public void drawBlock() { // 블록 그리는 함수
      for (int i = 0; i < arr.size(); i++) { // 배열의 개수만큼 반복
         q = (Block) arr.get(i); // i번째 블럭의 정보
         switch (m) { // 맵과 플럭을 랜덤으로 생성하므로 case에 따라서 다른 블럭이 생성
         case 1:
            buffg.drawImage(img2, q.x, q.y, xsize, 50, this);
            break;
         case 2:
            buffg.drawImage(img4, q.x, q.y, xsize, 50, this);
            break;
         }
      }
   }

   public void DrawCharacter() { // 캐릭터 초기위치 함수

      if (c.getDirection() == 1) {
         if (Chmove == true) {
            buffg.drawImage(leftCI, tmp.x, c.y - 20, xsize, ysize + 50, this);
            buffg.drawImage(rEffect, tmp.x, c.y - 20, xsize, ysize + 50, this);
            Chmove = false;
         } else
            buffg.drawImage(leftStopCI, c.x, c.y - 20, xsize, ysize + 50, this);
      }

      else {
         if (Chmove == true) {
            buffg.drawImage(rightCI, tmp.x, tmp.y - 20, xsize, ysize + 50, this);
            buffg.drawImage(rEffect, tmp.x, tmp.y - 20, xsize, ysize + 50, this);
            Chmove = false;
         } else
            buffg.drawImage(rightStopCI, c.x, c.y - 20, xsize, ysize + 50, this);
      }
   }

   public void background() { //배경화면 함수
      switch (m) {// 맵과 플럭을 랜덤으로 생성하므로 case에 따라서 다른 배경이 생성
      case 1:
         buffg.drawImage(background2, 0, 0, getWidth(), getHeight(), this);
         cloud(); //움직이는 구흠 1
         cloud2(); //움직이는 구흠 2
         break;
      case 2:
         buffg.drawImage(background4, 0, 0, getWidth(), getHeight(), this);
         cloud(); //움직이는 구흠 1
         cloud2(); //움직이는 구흠 2
         break;
      }
   }

   public void DrawMom() { //엄마를 그리는 함수
      buffg.drawImage(mother, mom.x, mom.y - 130, xsize + 300, ysize + 250, this);
   }

   public void blockProcess() { //블록 프로세스 함수
      int n;
      for (int i = 0; i < arr.size(); i++) {
         q = (Block) arr.get(i);
         q.y += ysize;
      }
      q = (Block) arr.get(arr.size() - 1);
      if (q.num == 1) { // 첫번째블럭이 1번블럭일때
         q = new Block(TWO, 0, 2); // 다음 블럭은 두번째 블럭
         arr.add(q); // 블록정보를 배열에 저장
         c.change(c.x, c.y + ysize, c.xlocate);
         c.ylocate -= 1;
         arr.remove(0);

      } else if (q.num == 2) { // 첫번째블럭이 2번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(ONE, 0, 1); // 다음 블럭은 1번째 블럭
            arr.add(q); // 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(THREE, 0, 3); // 다음 블럭은 3번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 3) { // 첫번째블럭이 3번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(TWO, 0, 2); // 다음 블럭은 2번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(FOUR, 0, 4); // 다음 블럭은 4번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 4) { // 첫번째블럭이 4번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(THREE, 0, 3); // 다음 블럭은 3번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(FIVE, 0, 5); // 다음 블럭은 5번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 5) { // 첫번째블럭이 5번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(FOUR, 0, 4); // 다음 블럭은 4번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(SIX, 0, 6); // 다음 블럭은 6번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 6) { // 첫번째블럭이 6번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(FIVE, 0, 5); // 다음 블럭은 5번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(SEVEN, 0, 7); // 다음 블럭은 7번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 7) { // 첫번째블럭이 7번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(SIX, 0, 6); // 다음 블럭은 6번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(EIGHT, 0, 8); // 다음 블럭은 8번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 8) { // 첫번째블럭이 8번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(SEVEN, 0, 7); // 다음 블럭은 7번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(NINE, 0, 9); // 다음 블럭은 9번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 9) { // 첫번째블럭이 9번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(EIGHT, 0, 8); // 다음 블럭은 8번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(TEN, 0, 10); // 다음 블럭은 10번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 10) { // 첫번째블럭이 10번블럭일때
         q = new Block(NINE, 0, 9); // 다음 블럭은 9번째 블럭
         arr.add(q);// 블록정보를 배열에 저장
         c.change(c.x, c.y + ysize, c.xlocate);
         c.ylocate -= 1;
         arr.remove(0);
      }
   }

   public void makeBlock() { //블록 만들기 함수
      int n; // 랜덤수 변수

      if (arr.isEmpty() == false) // 배열이 비어있지않으면
         q = (Block) arr.get(arr.size() - 1); // 리스트 크기 -1 값을 q에 반환
      if (arr.isEmpty() == true) { // 배열이 비어있으면
         n = 1 + (int) (Math.random() * 10); // 1~10까지 랜덤수 생성
         switch (n) { // case문은 첫 블럭생성할 때 1번만 들어가고 else if문으로 넘어감
         case 1: { // 랜덤수가 1일 경우
            q = new Block(ONE, y, 1); // 1번째 블럭 생성
            arr.add(q); // 배열에 1번째 블럭 정보 저장
            y = y - ysize; // 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: {// 랜덤수가 2일 경우
            q = new Block(TWO, y, 2);// 2번째 블럭 생성
            arr.add(q);// 배열에 2번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 3: {// 랜덤수가 3일 경우
            q = new Block(THREE, y, 3);// 3번째 블럭 생성
            arr.add(q);// 배열에 3번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 4: {// 랜덤수가 4일경우
            q = new Block(FOUR, y, 4);// 4번째 블럭 생성
            arr.add(q);// 배열에 4번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 5: {// 랜덤수가 5일 경우
            q = new Block(FIVE, y, 5);// 5번째 블럭 생성
            arr.add(q);// 배열에 5번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 6: {// 랜덤수가 6일 경우
            q = new Block(SIX, y, 6);// 6번째 블럭 생성
            arr.add(q);// 배열에 6번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 7: {// 랜덤수가 7일 경우
            q = new Block(SEVEN, y, 7);// 7번째 블럭 생성
            arr.add(q);// 배열에 7번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 8: {// 랜덤수가 8일 경우
            q = new Block(EIGHT, y, 8);// 8번째 블럭 생성
            arr.add(q);// 배열에 8번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 9: {// 랜덤수가 9일 경우
            q = new Block(NINE, y, 9);// 9번째 블럭 생성
            arr.add(q);// 배열에 9번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 10: {// 랜덤수가 10일 경우
            q = new Block(TEN, y, 10);// 10번째 블럭 생성
            arr.add(q);// 배열에 10번째 블럭 정보 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 1) { // 첫번째블럭이 1번블럭일때
         q = new Block(TWO, y, 2); // 다음 블럭은 두번째 블럭
         arr.add(q); // 블록정보를 배열에 저장
         y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
      } else if (q.num == 2) { // 첫번째블럭이 2번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(ONE, y, 1); // 다음 블럭은 1번째 블럭
            arr.add(q); // 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(THREE, y, 3); // 다음 블럭은 3번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 3) { // 첫번째블럭이 3번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(TWO, y, 2); // 다음 블럭은 2번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(FOUR, y, 4); // 다음 블럭은 4번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 4) { // 첫번째블럭이 4번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(THREE, y, 3); // 다음 블럭은 3번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(FIVE, y, 5); // 다음 블럭은 5번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 5) { // 첫번째블럭이 5번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(FOUR, y, 4); // 다음 블럭은 4번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(SIX, y, 6); // 다음 블럭은 6번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 6) { // 첫번째블럭이 6번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(FIVE, y, 5); // 다음 블럭은 5번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(SEVEN, y, 7); // 다음 블럭은 7번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 7) { // 첫번째블럭이 7번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(SIX, y, 6); // 다음 블럭은 6번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(EIGHT, y, 8); // 다음 블럭은 8번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 8) { // 첫번째블럭이 8번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(SEVEN, y, 7); // 다음 블럭은 7번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(NINE, y, 9); // 다음 블럭은 9번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 9) { // 첫번째블럭이 9번블럭일때
         n = 1 + (int) (Math.random() * 2); // 1~2 랜덤 수 생성
         switch (n) {
         case 1: { // 랜덤수가 1이면
            q = new Block(EIGHT, y, 8); // 다음 블럭은 8번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         case 2: { // 랜덤수가 2이면
            q = new Block(TEN, y, 10); // 다음 블럭은 10번째 블럭
            arr.add(q);// 블록정보를 배열에 저장
            y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
            break;
         }
         }
      } else if (q.num == 10) { // 첫번째블럭이 10번블럭일때
         q = new Block(NINE, y, 9); // 다음 블럭은 9번째 블럭
         arr.add(q);// 블록정보를 배열에 저장
         y = y - ysize;// 다음 블럭생성을 위해 y좌표 감소
      }
   }

   public void processCharacter() { //캐릭터 프로세스 함수

      if (c.x == 0 && c.y == 0) {
         fst = (Block) arr.get(0);
         scd = (Block) arr.get(1);
         if (fst.num > scd.num) {
            c.change(fst.x + xsize, fst.y + 40, fst.num);
            c.changeDirection(1);
         } else {
            c.change(fst.x - xsize, fst.y + 40, fst.num);
            c.changeDirection(2);
         }
      }
      if (keyChange == true) {
         int a;
         a = ChangeDirection();
         momMove = true;
         if (a == 0)
            gOver = true;
         keyChange = false;
      } else if (keyUP == true) {
         int a;
         a = UPDirection();
         momMove = true;
         if (a == 0)
            gOver = true;
         keyUP = false;
      }
      if (score == 6) //6점 이먼
         momstart = true; //엄마가 출발함
      if (c.ylocate == arr.size() / 2) //계단 이 중간정도사이즈에 캐릭터가 위치하면
         makeStair = true; //계단 생성 플래그 활성화
   }

   public int ChangeDirection() { //방향 바꾸는 함수
      q = (Block) arr.get(c.ylocate); //블록의 위치 배열
      if (c.ylocate == 0) 
         return 0;
      else if (q.num > c.xlocate && c.direction == 1) {
         tmp.change(c.x, c.y, c.direction); //캐릭터 위치 바꿈
         Chmove = true;
         System.out.println("올라갈 계단  x위치 : " + q.num + "서있는 계단 x위치 : " + c.xlocate); //올라갈 계단의 위치와 서있는 계단의 위치
         c.changeDirection(2);
         c.change(q.x, q.y - ysize, q.num); //계단 바로 위에 캐릭터 생성
         c.ylocate += 1;
         score += 1; //점수 1점 추가
         return 1;
      } else if (q.num < c.xlocate && c.direction == 2) {
         tmp.change(c.x, c.y, c.direction);
         Chmove = true;
         c.changeDirection(1);
         c.change(q.x, q.y - ysize, q.num); //발판 바로 위에 캐릭터 생성
         c.ylocate += 1;
         score += 1; //점수 1점 추가
         return 1;
      } else {
         c.change(q.x, q.y - ysize, q.num);
         return 0;
      }

   }

   public int UPDirection() { //위로 올라가는 함수
      q = (Block) arr.get(c.ylocate); //블록의 위치를 저장한 배열
      if (c.ylocate == 0) {  //캐릭터 y위치가 0이면(가장 왼쪽이면)
         tmp.change(c.x, c.y, c.direction); //방향을 바꾸고
         Chmove = true; //체인지 무브 플래그를 활성화 시키고
         c.change(q.x, q.y - ysize, q.num); //블록 바로 위에 캐릭터 생성
         c.ylocate += 1; //y의 위치를 한블럭 증가 //움직이는 구흠 1
         score += 1; //점수에 1점 추가
         return 1; //정확하게 오르면 1을 반환
      } else {
         if (q.num < c.xlocate && c.getDirection() == 1) {
            tmp.change(c.x, c.y, c.direction);
            Chmove = true;
            c.change(q.x, q.y - ysize, q.num); //블록 바로 위에 캐릭터 생성
            c.ylocate += 1; //캐릭터 1블럭 증가
            score += 1; //점수 1점 추가
            momMove = true;
            return 1;
         } else if (q.num > c.xlocate && c.getDirection() == 2) {
            tmp = c;
            Chmove = true;
            c.change(q.x, q.y - ysize, q.num);//블록 바로 위에 캐릭터 생성
            c.ylocate += 1; //캐릭터 1블럭 증가
            score += 1; //점수 1점 추가
            momMove = true;
            return 1;
         } else {
            c.change(q.x, q.y - ysize, q.num); //블록 바로 위에 캐릭터 생성
            return 0;
         }
      }
   }

   public void momProcess() { //엄마위치 배열 저장 함수
      Block m;
      if (momstart == true) { //엄마 스타트 플래그가 활성화 되면
         if (momMove == true) { //엄마를 움직이고
            m = (Block) arr.get(0); //배열에 저장하면서
            mom.move(m.x, m.y); //엄마위치를 움직인다
            momMove = false;
         }
      }
   }

   public int GetResult() { //결과반환 함수
      return result;
   }

   class Direction { //방향 클래스
      boolean left = false;
      boolean right = false;
   }

   class Block { //블록 클래스
      int x, y, num; //x좌표 y좌표 몇번째 인지를 나타내는 변수

      Block(int x, int y, int num) {
         this.x = x; // x좌표
         this.y = y; // y좌표
         this.num = num; // 블록번호
      }
   }

   class Character { //캐릭터 클래스
      int x, y, xlocate, ylocate, direction;

      Character(int x, int y, int num) { //캐릭터 위치(몇번째 블록에 위치하는지 까지)
         this.x = x;
         this.y = y;
         this.xlocate = num;
         this.ylocate = 0;
      }

      public void change(int x, int y, int xlocate) {
         this.x = x;
         this.y = y;
         this.xlocate = xlocate;
      }

      public void changeDirection(int dir) { //뱡향바꾸기
         this.direction = dir;
      }

      public int getDirection() { //방향
         return direction;
      }
   }

   class MomCharacter { //엄마 캐릭터 클래스
      int x, y; //x,y좌표

      MomCharacter() {}
      void move(int x, int y) {
         this.x = x;
         this.y = y;
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

   @Override
   public void run() {

   }

}