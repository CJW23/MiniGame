/*
구현 : 최재우
조 : 1등을JAVA(1조)
프로그램 이름 : db.java
프로그램 설명 : 데이터베이스 연동
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameProject", "root", "awdsd123");		//database 연결
			stmt = conn.createStatement();
			String SQL = "SELECT id, score FROM " + table + " ORDER BY score DESC";		//table의 데이터를 점수로 내림차순하는 SQL문 
			rs = stmt.executeQuery(SQL);			//SQL문 실행한 내용을 rs에 저장
			while (i < 5 && rs.next()) {			//rs를 5번 넘겨 5위의 점수를 참조
				i += 1;
			}
			lowRank = rs.getInt("score");			//5위의 점수를 저장

		} catch (Exception e) {
			System.out.println("데이터베이스 연결 오류여기?");

		}
	}
	public void insertScore(String ID, int score,String table) {		//삽입 메소드
		
		if (score > lowRank) {
			try {
				String SQL = "insert into " + table + " values('" + ID + "', " + score + ")" + "";		//삽입 SQL문
				System.out.println(SQL);
				stmt.executeUpdate(SQL);
			} catch (Exception e) {
				System.out.println("데이터베이스 연결 오류삽입");
			}
		}
	}
}
