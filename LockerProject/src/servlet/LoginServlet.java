package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "tnsaud12");
			stmt = conn.prepareStatement("select * from user where id=? AND pw=?");
			stmt.setString(1, req.getParameter("id"));
			stmt.setString(2, req.getParameter("pw"));
			rs = stmt.executeQuery();

			if (rs.next()) {
				session.setAttribute("idx", rs.getString("idx"));
				session.setAttribute("id", rs.getString("id"));
				session.setAttribute("pw", rs.getString("pw"));
				session.setAttribute("name", rs.getString("name"));
				session.setAttribute("phone", rs.getString("phone"));
				session.setAttribute("email", rs.getString("email"));
				if (rs.getString(5).equals("*")) {
					// 관리자
					resp.setContentType("text/html; charset=UTF-8");
					resp.sendRedirect("/LockerProject/managerMain.jsp");

					stmt = conn.prepareStatement("INSERT INTO user(user_idx,act,log_date) VALUES (?,?,?)");
					stmt.setString(1, rs.getString("idx"));
					stmt.setString(2, "manager_login");
					stmt.setString(3,
							new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
					stmt.executeUpdate();

					return;
				} else {
					String user_pw = rs.getString("pw");
					if (user_pw.equals(req.getParameter("pw"))) {
						// 유저 비밀번호 일치
						stmt = conn.prepareStatement("INSERT INTO log(user_idx,act,log_date) VALUES (?,?,?)");
						stmt.setString(1, rs.getString("idx"));
						stmt.setString(2, "user_login");
						stmt.setString(3,
								new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
						stmt.executeUpdate();

						resp.setContentType("text/html; charset=UTF-8");
						resp.sendRedirect("/LockerProject/main.jsp");
						return;
					}
				}
			} else {
				session.setAttribute("Login_Expired", "Expired");
				resp.setContentType("text/html; charset=UTF-8");
				resp.sendRedirect("/LockerProject/login.jsp");
				return;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return;
	}
}
