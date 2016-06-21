package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

public class UserMainServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public boolean checkSession(String idx, String id, String pw, String name, String phone, String email) {
		try {
			PreparedStatement stat;
			stat = conn.prepareStatement("SELECT * FROM user where id=? AND pw=? AND name=? AND phone=? AND email=?");
			stat.setString(1, id);
			stat.setString(2, pw);
			stat.setString(3, name);
			stat.setString(4, phone);
			stat.setString(5, email);
			rs = stat.executeQuery();
			if (!rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/login?useUnicode=true&characterEncoding=utf8", "root", "tnsaud12");
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disDB() {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
