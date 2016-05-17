package pack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Loginservice {
	/*
	private String id;
	private String pw;
	*/
	// private String result;
	
	private Connection conn;
	private Statement stat;
	private ResultSet rs;
	
	// DB 제어 함수
	public void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login?useUnicode=true&characterEncoding=EUC_KR", "root", "tnsaud12");
			stat = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean login(String user_id, String user_pw) {
		try {
			PreparedStatement stat;
			stat = conn.prepareStatement("select * from login where id=?");
			stat.setString(1, user_id);
			rs = stat.executeQuery();
			if (true == rs.next()) {
				// 아이디 있음
				String temp = rs.getString(2);
				if (temp.equals(user_pw)) {
					// 아이디 존재 && 비밀번호 일치
					return true;
				}
				else {
					// 아이디는 존재 && 비밀번호 불일치
					stat.close();
					return false;
				}
			} else {
				// 아이디 없음
				stat.close();
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
	/*
	public boolean AddUser() {
		try {
			PreparedStatement stat;

			// 도서 등록번호 데이터 등록
			stat = conn.prepareStatement("insert into login values(?, ?)");
			stat.setString(1, id);
			stat.setString(2, pw);
			stat.executeUpdate();
			stat.close();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	*/
	public void disDB() {
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}