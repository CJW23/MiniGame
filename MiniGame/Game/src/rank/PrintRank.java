/*
���� : �����
�� : 1����JAVA(1��)
���α׷� �̸� : PrintRank.java
���α׷� ���� : ��ŷ�� ��µ� �����͸� �������ִ� Ŭ����
*/
package rank;

import java.sql.*;
import java.util.ArrayList;

public class PrintRank {

	Connection conn;
	PreparedStatement stmt;
	ResultSet rs;

	public PrintRank() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameProject", "root", "awdsd123");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	public ArrayList<Pair<String, Integer>> getRankInformation(final String tableName, final int limit) {	//���� �����͸� Pair��ü�� ������ ��ȯ
		ArrayList<Pair<String, Integer>> rank = new ArrayList<>();		//���� ������ ������ arraylist

		// query statement
		String query = "SELECT id, score FROM " + tableName + " ORDER BY score DESC LIMIT " + limit;	//�������� ������ 1~5������ SQl��
		try {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery(query);		//������ SQL�� rs�� ����
			while (rs.next()) {
				String id = rs.getString(1);	// id�� ����
				int score = rs.getInt(2);		// ���� ����
				rank.add(new Pair<String, Integer>(id, score));		//arraylist�� �߰�
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rank;
	}
}
