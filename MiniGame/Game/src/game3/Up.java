/*
���� : ������
�� : 1����JAVA(1��)
���α׷� �̸� : Up.java
���α׷� ���� : �ö� ���� ���� ���α׷�
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

   public final int ONE = base_x + 0; // 1��° �� x��ǥ
   public final int TWO = base_x + xsize; // 2��° �� x��ǥ
   public final int THREE = base_x + (2 * xsize); // 3��° �� x��ǥ
   public final int FOUR = base_x + (3 * xsize); // 4��° �� x��ǥ
   public final int FIVE = base_x + (4 * xsize); // 5��° �� x��ǥ
   public final int SIX = base_x + (5 * xsize); // 6��° �� x��ǥ
   public final int SEVEN = base_x + (6 * xsize); // 7��° �� x��ǥ
   public final int EIGHT = base_x + (7 * xsize); // 8��° �� x��ǥ
   public final int NINE = base_x + (8 * xsize); // 9��° �� x��ǥ
   public final int TEN = base_x + (9 * xsize); // 10��° �� x��ǥ
   int keycode;
   boolean keyChange = false; // SpaceŰ
   boolean keyUP = false; // EnterŰ

   ArrayList<Block> arr = new ArrayList<Block>(); // ��� �迭
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

   public Up() // ȭ�� ũ��
   {
      this.addKeyListener(this);
      img2 = new ImageIcon("2cloud.gif").getImage();
      img4 = new ImageIcon("2block.png").getImage();
      leftCI = new ImageIcon("2runch.png").getImage(); // ĳ���� �̹��� ����
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
      
      setSize(1980,1080); //ȭ�� ������
      setVisible(true);
   }

   public void cloud() { //�����̴� ��� ���� �Լ�
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

   public void cloud2() { //�����̴� ��� ���� �Լ� 2
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

   public void Limittime() { //���ѽð� ���� �Լ�
      ltime = Integer.toString(t.time);
      buffg.setFont(new Font("Arial", Font.ITALIC, 70));
      buffg.drawString("Limit time", 1500, 400); //���ѽð� ����
      buffg.drawString(ltime, 1580, 500); //���ѽð�
   }

   public void Score() { //������ �Լ�
      cscore = Integer.toString(score);
      buffg.setFont(new Font("Arial", Font.ITALIC, 70));
      buffg.drawString("SCORE", 1500, 200); //������ ����
      buffg.drawString(cscore, 1600, 300); //����
   }

   public void st() {

      while (y >= 0) //���忡 ����������
      {
         makeBlock(); //����� �����Ѵ�.
      } //���۽� ������ õ����� �����Ǿ� �ִ� ���¿���
      t.start(); //������ �����Ѵ�

      while (true) { //��� �ݺ�
         sz = arr.size(); //��� �迭����

         if (c.ylocate >= arr.size() / 2) //����� �߰��� �������� ��
            blockProcess(); //����� �ϳ� ���� ����
         processCharacter(); // ĳ���� �����̴� �Լ�
         momProcess(); //�ؿ��� �Ѿƿ��� ���� 
         
         this.paint(this.getGraphics());
         
         if (gOver == true) //���� ������ �Ǹ�
            break; //���� ����
      }
      database = new db("second"); //���̴� ���̽�
      if (database.lowRank < score) { //������ ���̽��� �ִ� ���� ���� �������� ������ ������
         while (true) {
            id = JOptionPane.showInputDialog(this, "�����ǿ� ������ �̸� �Է��� �Է��ϼ���(3���� ���Ϸ� �Է��Ͻÿ�)", "����",
                  JOptionPane.QUESTION_MESSAGE);
            if (id.length() > 3) //�̸��� ���̰� 3�� ������
               JOptionPane.showMessageDialog(null, "�̸��� 3�������Ϸ� �Է��ϼ���"); //���� ���� ���
            else //�ƴϸ�
               break; //break
         }
         database.insertScore(id, score, "second");
      }
      String[] buttons = { "��õ�", "����ȭ��" }; //�絵�� or ����ȭ�� â�� ����
      result = JOptionPane.showOptionDialog(this, "���α׷��� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, buttons, "�ι�°");
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
   
   public void update(Graphics g) //���� ���ѽð� �׷���
   {
      background(); //��� �Լ�
      drawBlock(); // ��� �׸��� �Լ�
      DrawCharacter(); // ĳ���� �׸��� �Լ�
      if (momstart == true) { //������ �ö�� ��Ȳ��
         DrawMom(); //������ �׸�
      }
      Score(); //����
      Limittime();//���ѽð��� ǥ��
      g.drawImage(buffImage, 0, 0, null);
      try {
         Thread.sleep(20);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
   
   public void keyPressed(KeyEvent e) { //Ű���� ���� ��
      keycode = e.getKeyCode();
      switch (keycode) {
      case KeyEvent.VK_SPACE: { //�����̽��� ���� ��
         
         keyUP = true; //�� �÷��׸� Ȱ��ȭ
         keyChange = false; //ü���� �÷��׸� ��Ȱ��ȭ
         try { //�Ҹ����� ��(���� ������ �Ҹ�)
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
         } catch (Exception e1) {
            e1.printStackTrace();
         }
         break;
      }
      case KeyEvent.VK_ENTER: { //���Ͱ� ������
         keyUP = false; //�� �÷��׸� ��Ȱ��ȭ
         keyChange = true; //ü���� �÷��׸� Ȱ��ȭ
         try { //�Ҹ����� ��(���� ������ �Ҹ�)
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
   
   public void drawBlock() { // ��� �׸��� �Լ�
      for (int i = 0; i < arr.size(); i++) { // �迭�� ������ŭ �ݺ�
         q = (Block) arr.get(i); // i��° ���� ����
         switch (m) { // �ʰ� �÷��� �������� �����ϹǷ� case�� ���� �ٸ� ���� ����
         case 1:
            buffg.drawImage(img2, q.x, q.y, xsize, 50, this);
            break;
         case 2:
            buffg.drawImage(img4, q.x, q.y, xsize, 50, this);
            break;
         }
      }
   }

   public void DrawCharacter() { // ĳ���� �ʱ���ġ �Լ�

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

   public void background() { //���ȭ�� �Լ�
      switch (m) {// �ʰ� �÷��� �������� �����ϹǷ� case�� ���� �ٸ� ����� ����
      case 1:
         buffg.drawImage(background2, 0, 0, getWidth(), getHeight(), this);
         cloud(); //�����̴� ���� 1
         cloud2(); //�����̴� ���� 2
         break;
      case 2:
         buffg.drawImage(background4, 0, 0, getWidth(), getHeight(), this);
         cloud(); //�����̴� ���� 1
         cloud2(); //�����̴� ���� 2
         break;
      }
   }

   public void DrawMom() { //������ �׸��� �Լ�
      buffg.drawImage(mother, mom.x, mom.y - 130, xsize + 300, ysize + 250, this);
   }

   public void blockProcess() { //��� ���μ��� �Լ�
      int n;
      for (int i = 0; i < arr.size(); i++) {
         q = (Block) arr.get(i);
         q.y += ysize;
      }
      q = (Block) arr.get(arr.size() - 1);
      if (q.num == 1) { // ù��°���� 1�����϶�
         q = new Block(TWO, 0, 2); // ���� ���� �ι�° ��
         arr.add(q); // ��������� �迭�� ����
         c.change(c.x, c.y + ysize, c.xlocate);
         c.ylocate -= 1;
         arr.remove(0);

      } else if (q.num == 2) { // ù��°���� 2�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(ONE, 0, 1); // ���� ���� 1��° ��
            arr.add(q); // ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(THREE, 0, 3); // ���� ���� 3��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 3) { // ù��°���� 3�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(TWO, 0, 2); // ���� ���� 2��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(FOUR, 0, 4); // ���� ���� 4��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 4) { // ù��°���� 4�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(THREE, 0, 3); // ���� ���� 3��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(FIVE, 0, 5); // ���� ���� 5��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 5) { // ù��°���� 5�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(FOUR, 0, 4); // ���� ���� 4��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(SIX, 0, 6); // ���� ���� 6��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 6) { // ù��°���� 6�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(FIVE, 0, 5); // ���� ���� 5��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(SEVEN, 0, 7); // ���� ���� 7��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 7) { // ù��°���� 7�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(SIX, 0, 6); // ���� ���� 6��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(EIGHT, 0, 8); // ���� ���� 8��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 8) { // ù��°���� 8�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(SEVEN, 0, 7); // ���� ���� 7��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(NINE, 0, 9); // ���� ���� 9��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 9) { // ù��°���� 9�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(EIGHT, 0, 8); // ���� ���� 8��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(TEN, 0, 10); // ���� ���� 10��° ��
            arr.add(q);// ��������� �迭�� ����
            c.change(c.x, c.y + ysize, c.xlocate);
            c.ylocate -= 1;
            arr.remove(0);
            break;
         }
         }
      } else if (q.num == 10) { // ù��°���� 10�����϶�
         q = new Block(NINE, 0, 9); // ���� ���� 9��° ��
         arr.add(q);// ��������� �迭�� ����
         c.change(c.x, c.y + ysize, c.xlocate);
         c.ylocate -= 1;
         arr.remove(0);
      }
   }

   public void makeBlock() { //��� ����� �Լ�
      int n; // ������ ����

      if (arr.isEmpty() == false) // �迭�� �������������
         q = (Block) arr.get(arr.size() - 1); // ����Ʈ ũ�� -1 ���� q�� ��ȯ
      if (arr.isEmpty() == true) { // �迭�� ���������
         n = 1 + (int) (Math.random() * 10); // 1~10���� ������ ����
         switch (n) { // case���� ù �������� �� 1���� ���� else if������ �Ѿ
         case 1: { // �������� 1�� ���
            q = new Block(ONE, y, 1); // 1��° �� ����
            arr.add(q); // �迭�� 1��° �� ���� ����
            y = y - ysize; // ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: {// �������� 2�� ���
            q = new Block(TWO, y, 2);// 2��° �� ����
            arr.add(q);// �迭�� 2��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 3: {// �������� 3�� ���
            q = new Block(THREE, y, 3);// 3��° �� ����
            arr.add(q);// �迭�� 3��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 4: {// �������� 4�ϰ��
            q = new Block(FOUR, y, 4);// 4��° �� ����
            arr.add(q);// �迭�� 4��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 5: {// �������� 5�� ���
            q = new Block(FIVE, y, 5);// 5��° �� ����
            arr.add(q);// �迭�� 5��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 6: {// �������� 6�� ���
            q = new Block(SIX, y, 6);// 6��° �� ����
            arr.add(q);// �迭�� 6��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 7: {// �������� 7�� ���
            q = new Block(SEVEN, y, 7);// 7��° �� ����
            arr.add(q);// �迭�� 7��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 8: {// �������� 8�� ���
            q = new Block(EIGHT, y, 8);// 8��° �� ����
            arr.add(q);// �迭�� 8��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 9: {// �������� 9�� ���
            q = new Block(NINE, y, 9);// 9��° �� ����
            arr.add(q);// �迭�� 9��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 10: {// �������� 10�� ���
            q = new Block(TEN, y, 10);// 10��° �� ����
            arr.add(q);// �迭�� 10��° �� ���� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 1) { // ù��°���� 1�����϶�
         q = new Block(TWO, y, 2); // ���� ���� �ι�° ��
         arr.add(q); // ��������� �迭�� ����
         y = y - ysize;// ���� �������� ���� y��ǥ ����
      } else if (q.num == 2) { // ù��°���� 2�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(ONE, y, 1); // ���� ���� 1��° ��
            arr.add(q); // ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(THREE, y, 3); // ���� ���� 3��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 3) { // ù��°���� 3�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(TWO, y, 2); // ���� ���� 2��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(FOUR, y, 4); // ���� ���� 4��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 4) { // ù��°���� 4�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(THREE, y, 3); // ���� ���� 3��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(FIVE, y, 5); // ���� ���� 5��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 5) { // ù��°���� 5�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(FOUR, y, 4); // ���� ���� 4��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(SIX, y, 6); // ���� ���� 6��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 6) { // ù��°���� 6�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(FIVE, y, 5); // ���� ���� 5��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(SEVEN, y, 7); // ���� ���� 7��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 7) { // ù��°���� 7�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(SIX, y, 6); // ���� ���� 6��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(EIGHT, y, 8); // ���� ���� 8��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 8) { // ù��°���� 8�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(SEVEN, y, 7); // ���� ���� 7��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(NINE, y, 9); // ���� ���� 9��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 9) { // ù��°���� 9�����϶�
         n = 1 + (int) (Math.random() * 2); // 1~2 ���� �� ����
         switch (n) {
         case 1: { // �������� 1�̸�
            q = new Block(EIGHT, y, 8); // ���� ���� 8��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         case 2: { // �������� 2�̸�
            q = new Block(TEN, y, 10); // ���� ���� 10��° ��
            arr.add(q);// ��������� �迭�� ����
            y = y - ysize;// ���� �������� ���� y��ǥ ����
            break;
         }
         }
      } else if (q.num == 10) { // ù��°���� 10�����϶�
         q = new Block(NINE, y, 9); // ���� ���� 9��° ��
         arr.add(q);// ��������� �迭�� ����
         y = y - ysize;// ���� �������� ���� y��ǥ ����
      }
   }

   public void processCharacter() { //ĳ���� ���μ��� �Լ�

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
      if (score == 6) //6�� �̸�
         momstart = true; //������ �����
      if (c.ylocate == arr.size() / 2) //��� �� �߰���������� ĳ���Ͱ� ��ġ�ϸ�
         makeStair = true; //��� ���� �÷��� Ȱ��ȭ
   }

   public int ChangeDirection() { //���� �ٲٴ� �Լ�
      q = (Block) arr.get(c.ylocate); //����� ��ġ �迭
      if (c.ylocate == 0) 
         return 0;
      else if (q.num > c.xlocate && c.direction == 1) {
         tmp.change(c.x, c.y, c.direction); //ĳ���� ��ġ �ٲ�
         Chmove = true;
         System.out.println("�ö� ���  x��ġ : " + q.num + "���ִ� ��� x��ġ : " + c.xlocate); //�ö� ����� ��ġ�� ���ִ� ����� ��ġ
         c.changeDirection(2);
         c.change(q.x, q.y - ysize, q.num); //��� �ٷ� ���� ĳ���� ����
         c.ylocate += 1;
         score += 1; //���� 1�� �߰�
         return 1;
      } else if (q.num < c.xlocate && c.direction == 2) {
         tmp.change(c.x, c.y, c.direction);
         Chmove = true;
         c.changeDirection(1);
         c.change(q.x, q.y - ysize, q.num); //���� �ٷ� ���� ĳ���� ����
         c.ylocate += 1;
         score += 1; //���� 1�� �߰�
         return 1;
      } else {
         c.change(q.x, q.y - ysize, q.num);
         return 0;
      }

   }

   public int UPDirection() { //���� �ö󰡴� �Լ�
      q = (Block) arr.get(c.ylocate); //����� ��ġ�� ������ �迭
      if (c.ylocate == 0) {  //ĳ���� y��ġ�� 0�̸�(���� �����̸�)
         tmp.change(c.x, c.y, c.direction); //������ �ٲٰ�
         Chmove = true; //ü���� ���� �÷��׸� Ȱ��ȭ ��Ű��
         c.change(q.x, q.y - ysize, q.num); //��� �ٷ� ���� ĳ���� ����
         c.ylocate += 1; //y�� ��ġ�� �Ѻ� ���� //�����̴� ���� 1
         score += 1; //������ 1�� �߰�
         return 1; //��Ȯ�ϰ� ������ 1�� ��ȯ
      } else {
         if (q.num < c.xlocate && c.getDirection() == 1) {
            tmp.change(c.x, c.y, c.direction);
            Chmove = true;
            c.change(q.x, q.y - ysize, q.num); //��� �ٷ� ���� ĳ���� ����
            c.ylocate += 1; //ĳ���� 1�� ����
            score += 1; //���� 1�� �߰�
            momMove = true;
            return 1;
         } else if (q.num > c.xlocate && c.getDirection() == 2) {
            tmp = c;
            Chmove = true;
            c.change(q.x, q.y - ysize, q.num);//��� �ٷ� ���� ĳ���� ����
            c.ylocate += 1; //ĳ���� 1�� ����
            score += 1; //���� 1�� �߰�
            momMove = true;
            return 1;
         } else {
            c.change(q.x, q.y - ysize, q.num); //��� �ٷ� ���� ĳ���� ����
            return 0;
         }
      }
   }

   public void momProcess() { //������ġ �迭 ���� �Լ�
      Block m;
      if (momstart == true) { //���� ��ŸƮ �÷��װ� Ȱ��ȭ �Ǹ�
         if (momMove == true) { //������ �����̰�
            m = (Block) arr.get(0); //�迭�� �����ϸ鼭
            mom.move(m.x, m.y); //������ġ�� �����δ�
            momMove = false;
         }
      }
   }

   public int GetResult() { //�����ȯ �Լ�
      return result;
   }

   class Direction { //���� Ŭ����
      boolean left = false;
      boolean right = false;
   }

   class Block { //��� Ŭ����
      int x, y, num; //x��ǥ y��ǥ ���° ������ ��Ÿ���� ����

      Block(int x, int y, int num) {
         this.x = x; // x��ǥ
         this.y = y; // y��ǥ
         this.num = num; // ��Ϲ�ȣ
      }
   }

   class Character { //ĳ���� Ŭ����
      int x, y, xlocate, ylocate, direction;

      Character(int x, int y, int num) { //ĳ���� ��ġ(���° ��Ͽ� ��ġ�ϴ��� ����)
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

      public void changeDirection(int dir) { //����ٲٱ�
         this.direction = dir;
      }

      public int getDirection() { //����
         return direction;
      }
   }

   class MomCharacter { //���� ĳ���� Ŭ����
      int x, y; //x,y��ǥ

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