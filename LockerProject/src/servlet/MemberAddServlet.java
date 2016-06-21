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

@WebServlet("/member/join")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "tnsaud12");

			// 아이디 중복 체크.
			stmt = conn.prepareStatement("select * from user where id=?");
			stmt.setString(1, req.getParameter("user_id"));
			rs = stmt.executeQuery();
			if (rs.next() == false) {
				stmt = conn.prepareStatement("INSERT INTO user(id,pw,name,phone,email) VALUES (?,?,?,?,?)");
				stmt.setString(1, req.getParameter("user_id"));
				stmt.setString(2, req.getParameter("user_pw"));
				stmt.setString(3, new String(req.getParameter("user_name").getBytes("8859_1"), "UTF-8"));
				stmt.setString(4, req.getParameter("user_phone"));
				stmt.setString(5, req.getParameter("user_email"));
				stmt.executeUpdate();

				stmt = conn.prepareStatement("select * from user where id=?");
				stmt.setString(1, req.getParameter("user_id"));
				rs = stmt.executeQuery();
				if (rs.next() == true) {
					System.out.println(rs.getString("idx"));
					
					stmt = conn.prepareStatement("INSERT INTO log(user_idx,act,log_date) VALUES (?,?,?)");
					stmt.setString(1, rs.getString("idx"));
					stmt.setString(2, "join");
					stmt.setString(3,
							new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
					stmt.executeUpdate();
				}

				session.setAttribute("Join_Result", "Success");
				resp.setContentType("text/html; charset=UTF-8");
				resp.sendRedirect("/LockerProject/join.jsp");
				return;
			} else {
				session.setAttribute("Join_Result", "ID_duplicated");
				resp.setContentType("text/html; charset=UTF-8");
				resp.sendRedirect("/LockerProject/join.jsp");
				return;
			}
		} catch (Exception e) {
			session.setAttribute("Join_Result", "Fail");
			resp.setContentType("text/html; charset=UTF-8");
			resp.sendRedirect("/LockerProject/join.jsp");
			return;
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
	}
}
