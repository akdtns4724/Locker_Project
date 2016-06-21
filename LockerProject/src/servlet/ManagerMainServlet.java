package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
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

	public int getLowCount(String tableName) {
		try {
			String sql = "SELECT count(idx) as count from " + tableName;
			PreparedStatement stat;
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
			if (true == rs.next()) {
				int count = Integer.parseInt(rs.getString("count"));
				return count;
			}
		} catch (Exception ex) {
			ex.getStackTrace();
		}
		return -1;
	}

	public String[] setUserInfo(int length) {
		String a[] = new String[length];
		int num = 0;
		try {
			PreparedStatement stat;
			stat = conn.prepareStatement("SELECT * FROM user");
			rs = stat.executeQuery();
			while(rs.next()){
				a[num] = rs.getString("idx") + " " + rs.getString("id")
				 	+ " " + rs.getString("pw") + " " + rs.getString("name")
				 	+ " " + rs.getString("phone") + " " + rs.getString("email");
				num++;
			}
			return a;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return a;
	}

	public String[] setLockerInfo(int length) {
		PreparedStatement user_stat = null;
		ResultSet user_rs = null;
		String a[] = new String[length];
		int num = 0;
		try {
			PreparedStatement stat;
			stat = conn.prepareStatement("SELECT * FROM locker");
			rs = stat.executeQuery();
			while(rs.next()){
				a[num] = rs.getString("idx") + " " + rs.getString("user_idx")
				 	+ " " + rs.getString("locker_idx");
				user_stat = conn.prepareStatement("SELECT * FROM user where idx=?");
				user_stat.setString(1, rs.getString("user_idx"));
				user_rs = user_stat.executeQuery();
				if(user_rs.next() == true){
					a[num] += " " + user_rs.getString("id") + " " + user_rs.getString("name");
				}
				num++;
			}
			return a;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return a;
	}

	public String[] setLogInfo(int length) {
		String a[] = new String[length];
		int num = 0;
		try {
			PreparedStatement stat;
			stat = conn.prepareStatement("SELECT * FROM log");
			rs = stat.executeQuery();
			while(rs.next()){
				a[num] = rs.getString("idx") + "	" + rs.getString("user_idx")
				 	+ "	" + rs.getString("act") + "	" + rs.getString("locker_idx")
				 	+ "	" + rs.getString("log_date");
				num++;
			}
			return a;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return a;
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
