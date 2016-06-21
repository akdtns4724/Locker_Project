package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/locker/getInfo")
public class GetLockerInfoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session != null) {
			if ((String) session.getAttribute("idx") != req.getParameter("user_idx")) {
			} else {
				// 본인의 계정인지 여부 체크.
				session.setAttribute("LockerWithdraw_Result", "noHimself");
				resp.setContentType("text/html; charset=UTF-8");
				resp.sendRedirect("/LockerProject/main.jsp");
				return;
			}
		} else {
			// 세션 여부 체크. ==> 로그인 여부
			session.setAttribute("LockerWithdraw_Result", "noLogin");
			resp.setContentType("text/html; charset=UTF-8");
			resp.sendRedirect("/LockerProject/main.jsp");
			return;
		}
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "tnsaud12");
			
			stmt = conn.prepareStatement("select * from locker where user_idx=?");
			stmt.setString(1, (String) session.getAttribute("idx"));
			rs = stmt.executeQuery();
			if(rs.next() == true){
				System.out.println(rs.getString("locker_idx"));
				session.setAttribute("LockerInfo", rs.getString("locker_idx"));
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().print(rs.getString("locker_idx"));
			} else {
				session.setAttribute("LockerInfo", "noLocker");
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().print("noLocker");
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
	}
}
