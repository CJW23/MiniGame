/*
구현 : 최재우
조 : 1등을JAVA(1조)
프로그램 이름 : PrintRank.java
프로그램 설명 : 랭킹에 출력될 데이터를 저장해주는 클래스
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
	
	public ArrayList<Pair<String, Integer>> getRankInformation(final String tableName, final int limit) {	//점수 데이터를 Pair객체에 저장해 반환
		ArrayList<Pair<String, Integer>> rank = new ArrayList<>();		//점수 데이터 저장할 arraylist

		// query statement
		String query = "SELECT id, score FROM " + tableName + " ORDER BY score DESC LIMIT " + limit;	//내림차순 데이터 1~5위까지 SQl문
		try {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery(query);		//실행한 SQL문 rs에 저장
			while (rs.next()) {
				String id = rs.getString(1);	// id를 저장
				int score = rs.getInt(2);		// 점수 저장
				rank.add(new Pair<String, Integer>(id, score));		//arraylist에 추가
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rank;
	}
}
