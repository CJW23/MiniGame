/*
구현 : 곽동훈
조 : 1등을JAVA(1조)
프로그램 이름 : LimitTime.java
프로그램 설명 : 시간초를 새주는 프로그램
*/
package game3;

import javax.swing.JOptionPane;

public class LimitTime extends Thread {

   int time = 60; //제한시간 60포
   boolean a = false;
   String id;
   Up tmp;
   LimitTime(Up a)
   {
      tmp = a;
   }
   public void run() {
      while (time != 0) { //제한시간이 0이 될때까지
         try {
            Thread.sleep(1000); //1초 쉬고
            time--; //time변수를 1씩 감소
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (time == 0) { //제한시간이 0이 되면

         tmp.gOver = true; //게임오버
      }
   }

}