/*
���� : �����
�� : 1����JAVA(1��)
���α׷� �̸� : db.java
���α׷� ���� : �����ͺ��̽� ����
 */
package game1;
import java.sql.*;

public class db {

	Connection conn;
	Statement stmt;
	ResultSet rs;
	ResultSet wd;
	public int lowRank;
	int i;

	public db(String table) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameProject", "root", "awdsd123");		//database ����
			stmt = conn.createStatement();
			String SQL = "SELECT id, score FROM " + table + " ORDER BY score DESC";		//table�� �����͸� ������ ���������ϴ� SQL�� 
			rs = stmt.executeQuery(SQL);			//SQL�� ������ ������ rs�� ����
			while (i < 5 && rs.next()) {			//rs�� 5�� �Ѱ� 5���� ������ ����
				i += 1;
			}
			lowRank = rs.getInt("score");			//5���� ������ ����

		} catch (Exception e) {
			System.out.println("�����ͺ��̽� ���� ��������?");

		}
	}
	public void insertScore(String ID, int score,String table) {		//���� �޼ҵ�
		
		if (score > lowRank) {
			try {
				String SQL = "insert into " + table + " values('" + ID + "', " + score + ")" + "";		//���� SQL��
				System.out.println(SQL);
				stmt.executeUpdate(SQL);
			} catch (Exception e) {
				System.out.println("�����ͺ��̽� ���� ��������");
			}
		}
	}
}
