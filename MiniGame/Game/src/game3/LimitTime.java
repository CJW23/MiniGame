/*
���� : ������
�� : 1����JAVA(1��)
���α׷� �̸� : LimitTime.java
���α׷� ���� : �ð��ʸ� ���ִ� ���α׷�
*/
package game3;

import javax.swing.JOptionPane;

public class LimitTime extends Thread {

   int time = 60; //���ѽð� 60��
   boolean a = false;
   String id;
   Up tmp;
   LimitTime(Up a)
   {
      tmp = a;
   }
   public void run() {
      while (time != 0) { //���ѽð��� 0�� �ɶ�����
         try {
            Thread.sleep(1000); //1�� ����
            time--; //time������ 1�� ����
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if (time == 0) { //���ѽð��� 0�� �Ǹ�

         tmp.gOver = true; //���ӿ���
      }
   }

}