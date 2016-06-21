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

@WebServlet("/locker/delete")
public class LockerDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session != null) {
			if ((session.getAttribute("email").equals("*"))
					|| ((String) session.getAttribute("idx") != req.getParameter("user_idx"))) {
			} else {
				// 관리자 계정 여부 체크.
				session.setAttribute("UserDelete_Result", "noManager");
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
			
			stmt = conn.prepareStatement("select * from locker where user_idx=?");
			System.out.println(req.getParameter("user_idx"));
			stmt.setString(1, req.getParameter("user_idx"));
			rs = stmt.executeQuery();
			if(rs.next() == true){
				stmt = conn.prepareStatement("insert INTO log(user_idx, act, locker_idx, log_date) VALUES (?,?,?,?)");
				stmt.setString(1, req.getParameter("user_idx"));
				stmt.setString(2, "Delete Locker");
				stmt.setString(3, rs.getString("locker_idx"));
				stmt.setString(4,
						new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
				stmt.executeUpdate();
				
				stmt = conn.prepareStatement("delete from locker where user_idx=?");
				stmt.setString(1, req.getParameter("user_idx"));
				stmt.executeUpdate();
			} else {
				// 화면엔 있는데 실데이터는 없다.
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().print("Abnormally");
			}
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().print("Success");
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
