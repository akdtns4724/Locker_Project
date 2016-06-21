<%@ page contentType="text/html;charset=euc-kr"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>로그인페이지</title>
<link rel="stylesheet" href="/LockerProject/common/css/login.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="/LockerProject/common/javascript/login.js"></script>
<%
	String strExpired = (String) session.getAttribute("Login_Expired");
	if (strExpired == "Expired") {
		%>
			<script>
				alert("아이디와 비밀번호를 올바르게 입력해 주십시오.");
			</script>
		<%
		session.invalidate();
	}
%>
</head>
<body>
	<h3 style="margin-top: 80px;">SeoulNational University of Science & Technology</h3>
	<h3>Locker Apply System</h3>
	<div class="login-page">
		<div class="form">
			<form class="login-form" name=login_form method="post" action="/LockerProject/login">
			 	<input type=text placeholder="username" name=id size=20 maxlength=12 style="width: 96%;">
				<input type=password placeholder="password" name=pw size=20 maxlength=8 style="width: 96%;">
				<input type=submit value="로그인" style="background:#76b852"/>
				<p class="message">
					Not registered? 
					<a onclick="window.location='join.jsp'">Create an account</a>
				</p>
			</form>
		</div>
	</div>
</body>
</html>