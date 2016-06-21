<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="join" class="servlet.MemberAddServlet" scope="session" />
<jsp:setProperty name="join" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/LockerProject/common/css/login.css">
<title>회원가입 페이지</title>
<%
	String strResult = (String) session.getAttribute("Join_Result");
	if (strResult == "Success") {
		%>
			<script>
				alert("회원가입에 성공하였습니다.\n로그인 화면으로 이동합니다.");
				window.location = "/LockerProject/login.jsp";
			</script>
		<%
		session.invalidate();
	} else if (strResult == "Fail") {
		%>
		<script>
			alert("회원가입에 실패하였습니다.");
		</script>
		<%
	} else if (strResult == "ID_duplicated") {
		%>
		<script>
			alert("중복된 아이디가 존재합니다.\n다른 아이디를 사용해주십시오.");
		</script>
		<%
	}
%>
</head>
<body>
	<h3 style="margin-top: 80px;">SeoulNational University of Science
		& Technology</h3>
	<h3>Locker Apply System</h3>
	<div class="login-page">
		<div class="form">
			<form class="login-form" name=join_form action=/LockerProject/member/join method=post onsubmit="return validationCheck()">
				<input type=text placeholder="id" name=user_id size=20 maxlength=20 style="width: 96%;">
				<input type=password placeholder="password" name=user_pw size=20 maxlength=20 style="width: 96%;">
				<input type=text placeholder="name" name=user_name size=20 maxlength=10 style="width: 96%;">
				<input type=text placeholder="phone" name=user_phone size=20 maxlength=11 style="width: 96%;">
				<input type=text placeholder="email" name=user_email size=20 maxlength=30 style="width: 96%;">
				<input type=submit value="회원가입" style="background: #76b852" />
				<p class="message">
					Already registered? <a onclick="window.location='login.jsp'">
					Back to Login Page</a>
				</p>
			</form>
		</div>
	</div>
	<script>
		function validationCheck() {
			if (document.join_form.user_id.value == "") {
				alert("이름을 입력하세요.");
				return false;
			} else if (document.join_form.user_pw.value == "") {
				alert("비밀번호를 입력하세요.");
				return false;
			} else if (document.join_form.user_name.value == "") {
				alert("이름을 입력하세요.");
				return false;
			} else if (document.join_form.user_phone.value == "") {
				alert("전화번호를 입력하세요.");
				return false;
			} else if (document.join_form.user_email.value == "") {
				alert("이메일을 입력하세요.");
				return false;
			} else if (!emailValidation(document.join_form.user_email.value)) {
				alert("올바른 이메일 형식으로 입력해주세요.");
				return false;
			} else {
				return true;
			}
		}

		function emailValidation(email) {
			var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
			//이메일 형식에 맞지않으면
			if (!email.match(regExp)) {
				return false;
			}
			return true;
		}
	</script>
</body>
</html>