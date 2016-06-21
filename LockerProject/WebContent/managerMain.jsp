<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="manager" class="servlet.ManagerMainServlet" scope="session" />
<jsp:setProperty name="manager" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="/LockerProject/common/css/managerMain.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="/LockerProject/common/javascript/managerMain.js"></script>
<title>사물함 관리자 페이지</title>
<%
	String idx = (String) session.getAttribute("idx");
	String id = (String) session.getAttribute("id");
	String pw = (String) session.getAttribute("pw");
	String name = (String) session.getAttribute("name");
	String phone = (String) session.getAttribute("phone");
	String email = (String) session.getAttribute("email");
	
	manager.initDB();
	if(manager.checkSession(idx, id, pw, name, phone, email)){

		int userlength = manager.getLowCount("user");
		int lockerlength = manager.getLowCount("locker");
		int loglength = manager.getLowCount("log");
		String[] userInfo = manager.setUserInfo(userlength);
		String[] lockerInfo = manager.setLockerInfo(lockerlength);
		String[] logInfo = manager.setLogInfo(loglength);
		for(int a = 0; a < userlength; a++){
			%>
				<script>
					$(document).ready(function(){
						makeUserListTable("<%= userInfo[a]%>");
					});
				</script>
			<%
		}
		for(int a = 0; a < lockerlength; a++){
			%>
				<script>
					$(document).ready(function(){
						makeLockerListTable("<%= lockerInfo[a]%>");
					});
				</script>
			<%
		}
		for(int a = 0; a < loglength; a++){
			%>
				<script>
					$(document).ready(function(){
						makeLogListTable("<%= logInfo[a]%>");
					});
				</script>
			<%
		}
	} else {
		%>
			<script>
				alert("로그인이 필요합니다.");
				window.location = "/LockerProject/login.jsp";
			</script>
		<%
	}
	manager.disDB();
	
	if((String) session.getAttribute("UserDelete_Result") == "noManager") {
		%>
			<script>
				alert("권한이 없습니다.\n로그인 화면으로 이동합니다.");
				window.location = "/LockerProject/login.jsp";
			</script>
		<%
		session.setAttribute("UserDelete_Result", null);
	} else if((String) session.getAttribute("UserDelete_Result") == "noLogin"){
		%>
			<script>
				alert("로그인이 필요합니다.");
				window.location = "/LockerProject/login.jsp";
			</script>
		<%
	} else if((String) session.getAttribute("UserDelete_Result") == "Success"){
	}
%>
</head>
<body>
	<div class="container" style="margin-top:20px;">
		<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand">Locker Project Manager</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a id="user_btn">User</a></li>
					<li><a id="locker_btn">Locker</a></li>
					<li><a id="log_btn">Log</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active">
						<button id="logout_btn" class="btn btn-lg btn-primary" onclick="logout()">Logout</button></li>
				</ul>
			</div>
		</div>
		</nav>
		<div id='mainbody'>
			<div id='user_div' class="jumbotron selectedBody user">
				<h1>User</h1>
				<table id='userlist_table'>
					<tr>
						<td>index</td>
						<td>id</td>
						<td>pw</td>
						<td>name</td>
						<td>phone</td>
						<td>email</td>
						<td></td><td></td>
					</tr>
				</table>
			</div>
			<div id='locker_div' class="jumbotron nonselectedBody locker">
				<h1>Locker</h1>
				<table id='lockerlist_table'>
					<tr>
						<td>index</td>
						<td>user_index</td>
						<td>user_id</td>
						<td>user_name</td>
						<td>locker_index</td>
						<td></td><td></td>
					</tr>
				</table>
			</div>
			<div id='log_div' class="jumbotron nonselectedBody log">
				<h1>Log</h1>
				<table id='loglist_table'>
					<tr>
						<td>index</td>
						<td>user index</td>
						<td>action</td>
						<td>locker index</td>
						<td>log date</td>
						<td></td><td></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">User Information Modify</h4>
				</div>
				<form accept-charset=utf-8 name=frm1 action=/LockerProject/member/modify method=get onsubmit="return validationCheck()">
					<div class="modal-body">
						<table id="modifyModal_table">
						</table>
					</div>
					<div class="modal-footer">
						<input type=submit class="btn btn-default" value="Modify">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		function validationCheck() {
			if (document.frm1.user_id.value == "") {
				alert("이름을 입력하세요.");
				return false;
			} else if (document.frm1.user_pw.value == "") {
				alert("비밀번호를 입력하세요.");
				return false;
			} else if (document.frm1.user_name.value == "") {
				alert("이름을 입력하세요.");
				return false;
			} else if (document.frm1.user_phone.value == "") {
				alert("전화번호를 입력하세요.");
				return false;
			} else if (document.frm1.user_email.value == "") {
				alert("이메일을 입력하세요.");
				return false;
			} else if (!emailValidation(document.frm1.user_email.value)) {
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