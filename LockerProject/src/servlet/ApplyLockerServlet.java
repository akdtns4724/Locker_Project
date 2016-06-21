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

@WebServlet("/locker/apply")
public class ApplyLockerServlet extends HttpServlet {
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
			
			stmt = conn.prepareStatement("select * from locker where user_idx=?");
			stmt.setString(1, (String) session.getAttribute("idx"));
			rs = stmt.executeQuery();
			if(rs.next() != true){
				stmt = conn.prepareStatement("select * from locker where locker_idx=?");
				stmt.setString(1, req.getParameter("locker_idx"));
				rs = stmt.executeQuery();
				System.out.println(req.getParameter("locker_idx"));
				if(rs.next() != true){
					stmt = conn.prepareStatement("insert into locker(user_idx, locker_idx) VALUE (?,?)");
					stmt.setString(1, (String) session.getAttribute("idx"));
					stmt.setString(2, req.getParameter("locker_idx"));
					stmt.executeUpdate();

					stmt = conn.prepareStatement("INSERT INTO log(user_idx,act,locker_idx,log_date) VALUES (?,?,?,?)");
					stmt.setString(1, (String) session.getAttribute("idx"));
					stmt.setString(2, "apply_locker");
					stmt.setString(3, req.getParameter("locker_idx"));
					stmt.setString(4,
							new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
					stmt.executeUpdate();
					
					session.setAttribute("LockerApply_Result", "Success");
					session.setAttribute("LockerNumber", req.getParameter("locker_idx"));
					resp.setContentType("text/html; charset=UTF-8");
					resp.sendRedirect("/LockerProject/main.jsp");
					return;
				} else {
					session.setAttribute("LockerApply_Result", "noLocker");
					resp.setContentType("text/html; charset=UTF-8");
					resp.sendRedirect("/LockerProject/main.jsp");
					return;
				}
			} else {
				session.setAttribute("LockerApply_Result", "hasLocker");
				resp.setContentType("text/html; charset=UTF-8");
				resp.sendRedirect("/LockerProject/main.jsp");
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
	}
}
