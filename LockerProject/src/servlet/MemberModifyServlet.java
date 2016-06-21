package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/modify")
public class MemberModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn = null;
	private PreparedStatement stmt = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if (session != null) {
			if (session.getAttribute("email").equals("*") || 
					((String) session.getAttribute("idx") != req.getParameter("user_idx"))) {
			} else {
				// 관리자 계정 여부 체크. && 본인의 계정인지 여부 체크.
				if(!session.getAttribute("email").equals("*")){
					session.setAttribute("UserDelete_Result", "noManager");	
				} else {
					session.setAttribute("UserDelete_Result", "noHimself");	
				}
				resp.setContentType("text/html; charset=UTF-8");
				resp.sendRedirect("/LockerProject/managerMain.jsp");
				return;
			}
		} else {
			// 세션 여부 체크. ==> 로그인 여부
			session.setAttribute("UserDelete_Result", "noLogin");
			resp.setContentType("text/html; charset=UTF-8");
			resp.sendRedirect("/LockerProject/managerMain.jsp");
			return;
		}
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "tnsaud12");
			stmt = conn.prepareStatement("update user set pw=?, name=?, phone=?, email=? where id=?");
			stmt.setString(1, req.getParameter("user_pw"));
			stmt.setString(2, new String(req.getParameter("user_name").getBytes("8859_1"), "UTF-8"));
			stmt.setString(3, req.getParameter("user_phone"));
			stmt.setString(4, req.getParameter("user_email"));
			stmt.setString(5, req.getParameter("user_id"));
			stmt.executeUpdate();
			session.setAttribute("InfoModify_Result", "Success");
			return;
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
